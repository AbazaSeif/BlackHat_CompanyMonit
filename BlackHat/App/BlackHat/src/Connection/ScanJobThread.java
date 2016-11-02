/*
 * GNU Library or Lesser Public License (LGPL)
 */
package Connection;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.Socket;

/**
 * @author Geoffrey
 */
public class ScanJobThread extends Thread {

    private final ScanJobPool scanJobPool;
    private final int threadIndex;
    private volatile boolean booleanRunning = true;

    public ScanJobThread(ScanJobPool scanJobPool, int threadIndex) {
        super("ScanJobThread " + threadIndex);
        this.scanJobPool = scanJobPool;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        ScanJob currentScanJob = scanJobPool.getNextScanJob();
        while (booleanRunning && currentScanJob != null) {
            Socket socket = null;
            try {
                socket = new Socket(currentScanJob.getAddress(), currentScanJob.getPort());
                scanJobPool.addSuccesfulScanJob(currentScanJob);
            } catch (NoRouteToHostException exception) {
                scanJobPool.removeAddress(currentScanJob.getAddress());
            } catch (IOException exception) {
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException exception) {
                }
            }
            if (booleanRunning && scanJobPool.getPauzeTime() > 0) {
                try {
                    sleep(scanJobPool.getPauzeTime());
                } catch (InterruptedException exception) {
                    return;
                }
            }
            if (booleanRunning) {
                currentScanJob = scanJobPool.getNextScanJob();
            }
        }
        if (booleanRunning) {
            scanJobPool.threadFinished(threadIndex);
        }
    }

    @Override
    public void destroy() {
        booleanRunning = false;
        interrupt();
    }

}
