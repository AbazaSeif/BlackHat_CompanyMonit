/*
 * GNU Library or Lesser Public License (LGPL)
 */
package Connection;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @author Geoffrey
 */
public class ScanJobPool implements Runnable {

    private final static int MAXIMUM_THREADS_AMOUNT = 64;

    private final PortScanner portScanner;
    private final int pauzeTime;
    private final boolean addressIsHostName;
    private final String addressString, portString;

    private final Thread thread;
    private boolean threadRunning = true;
    private final Object destroyLock = new Object();

    private int threadsAmount;
    private ScanJobThread[] scanJobThreads;

    private InetAddress[] addresses;
    // addresses can be null if removed
    private int addressesIndex = -1;
    private NumberRangeCollection ports;
    private int currentPort = 0;

    private final TreeSet successfulScanJobs = new TreeSet();
    private final Object successfulLock = new Object();

    public ScanJobPool(PortScanner portScanner, boolean addressIsHostName, String addressString, String portString, int threadsAmount, int pauzeTime) {
        this.portScanner = portScanner;
        this.addressIsHostName = addressIsHostName;
        this.addressString = addressString;
        this.portString = portString;
        if (threadsAmount > MAXIMUM_THREADS_AMOUNT) {
            this.threadsAmount = MAXIMUM_THREADS_AMOUNT;
        } else {
            this.threadsAmount = threadsAmount;
        }
        this.pauzeTime = pauzeTime;
        thread = new Thread(this);
        thread.setPriority(4);
        thread.start();
    }

    @Override
    public void run() {
        //parse the strings
        if (addressIsHostName) {
            try {
                addresses = InetAddress.getAllByName(addressString);
            } catch (UnknownHostException exception) {
                synchronized (destroyLock) {
                    if (threadRunning) {
                        portScanner.scanJobPoolFinished();
                    }
                }
                return;
            }
        } else {
            StringTokenizer addressTokenizer = new StringTokenizer(addressString, ".:", true);
            Object[] addressParts = new Object[addressTokenizer.countTokens()];
            for (int i = 0; i < addressParts.length; i++) {
                String addressPartString = addressTokenizer.nextToken();
                if (addressPartString.equals(".") || addressPartString.equals(":")) {
                    addressParts[i] = addressPartString;
                } else {
                    try {
                        addressParts[i] = new NumberRangeCollection(addressPartString);
                    } catch (IllegalArgumentException exception) {
                        synchronized (destroyLock) {
                            if (threadRunning) {
                                portScanner.scanJobPoolFinished();
                            }
                        }
                        return;
                    }
                }
            }
            ArrayList addressArrayList = new ArrayList();
            calculateAddresses(addressParts, 0, addressArrayList, "");
            addresses = (InetAddress[]) addressArrayList.toArray(new InetAddress[1]);
            if (addresses[0] == null) {
                synchronized (destroyLock) {
                    if (threadRunning) {
                        portScanner.scanJobPoolFinished();
                    }
                }
                return;
            }
        }
        try {
            ports = new NumberRangeCollection(portString);
            if (!ports.hasNext()) {
                synchronized (destroyLock) {
                    if (threadRunning) {
                        portScanner.scanJobPoolFinished();
                    }
                }
                return;
            }
            currentPort = ports.next();
        } catch (IllegalArgumentException exception) {
            synchronized (destroyLock) {
                if (threadRunning) {
                    portScanner.scanJobPoolFinished();
                }
            }
            return;
        }
        scanJobThreads = new ScanJobThread[threadsAmount];
        for (int i = 0; threadRunning && i <= scanJobThreads.length - 1; i++) {
            scanJobThreads[i] = new ScanJobThread(this, i);
            if (scanJobThreads[i] != null) {
                scanJobThreads[i].setPriority(4);
                scanJobThreads[i].start();
            }
        }
    }

    public int getPauzeTime() {
        return pauzeTime;
    }

    public synchronized ScanJob getNextScanJob() {
        addressesIndex++;
        while (addressesIndex < addresses.length && addresses[addressesIndex] == null) {
            addressesIndex++;
        }
        if (addressesIndex >= addresses.length) {
            if (ports.hasNext()) {
                currentPort = ports.next();
                addressesIndex = 0;
                while (addresses[addressesIndex] == null) {
                    addressesIndex++;
                    if (addressesIndex >= addresses.length) {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }
        ScanJob newScanJob = new ScanJob(addresses[addressesIndex], currentPort);
//        portScanner.refreshProgressState("Scanning " + newScanJob);
        return newScanJob;
    }

    public void addSuccesfulScanJob(ScanJob scanJob) {
        synchronized (successfulLock) {
            successfulScanJobs.add(scanJob);
            Iterator iterator = successfulScanJobs.iterator();
            StringBuilder outputStringBuffer = new StringBuilder();
            while (iterator.hasNext()) {
                outputStringBuffer.append(iterator.next().toString());
                outputStringBuffer.append("\n");
            }
            synchronized (destroyLock) {
                if (threadRunning) {
                    portScanner.refreshOutput(outputStringBuffer.toString().trim());
                }
            }
        }
    }

    public synchronized void removeAddress(InetAddress removeAddress) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] != null && addresses[i].equals(removeAddress)) {
                addresses[i] = null;
                break;
            }
        }
    }

    public void threadFinished(int threadIndex) {
        synchronized (destroyLock) {
            scanJobThreads[threadIndex] = null;
            threadsAmount--;
            if (threadRunning && threadsAmount <= 0) {
                portScanner.scanJobPoolFinished();
            }
        }
    }

    public void destroy() {
        synchronized (destroyLock) {
            threadRunning = false;
            for (ScanJobThread scanJobThread : scanJobThreads) {
                if (scanJobThread != null) {
                    scanJobThread.destroy();
                }
            }
        }
    }

    private void calculateAddresses(Object[] addressParts, int addressPartsIndex, ArrayList addressArrayList, String addressPrefix) {
        if (addressPartsIndex >= addressParts.length) {
            try {
                InetAddress tempAddress = InetAddress.getByName(addressPrefix);
                if (tempAddress != null) {
                    addressArrayList.add(tempAddress);
                }

            } catch (UnknownHostException exception) {
                if (blackhat.BlackHat.Debug) {
                    System.err.println("ERROR : " + exception.getMessage() + "\n");
                }
            }
            return;
        }
        if (addressParts[addressPartsIndex] instanceof String) {
            calculateAddresses(addressParts, addressPartsIndex + 1, addressArrayList, addressPrefix + ((String) addressParts[addressPartsIndex]));
        } else {
            NumberRangeCollection tempCollection = (NumberRangeCollection) addressParts[addressPartsIndex];
            tempCollection.reset();
            while (tempCollection.hasNext()) {
                calculateAddresses(addressParts, addressPartsIndex + 1, addressArrayList, addressPrefix + Integer.toString(tempCollection.next()));
            }
        }
    }

}
