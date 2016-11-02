/*
 * GNU Library or Lesser Public License (LGPL)
 */
package Connection;

import java.net.InetAddress;

/**
 * @author Geoffrey
 */
public class ScanJob implements Comparable {

    private final InetAddress address;
    private final int port;

    public ScanJob(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object object) {
        try {
            ScanJob scanJob = (ScanJob) object;
            return address.equals(scanJob.address) && port == scanJob.port;
        } catch (ClassCastException exception) {
            return false;
        }
    }

    @Override
    public int compareTo(Object object) {
        try {
            ScanJob scanJob = (ScanJob) object;
            byte[] byteAddress1 = address.getAddress();
            byte[] byteAddress2 = scanJob.address.getAddress();
            for (int i = 0; i < byteAddress1.length; i++) {
                int intAddress1Part = (int) (byteAddress1[i] >= 0 ? byteAddress1[i] : 256 + byteAddress1[i]);
                int intAddress2Part = (int) (byteAddress2[i] >= 0 ? byteAddress2[i] : 256 + byteAddress2[i]);
                if (intAddress1Part < intAddress2Part) {
                    return -1;
                }
                if (intAddress1Part > intAddress2Part) {
                    return 1;
                }
            }
            if (port < scanJob.port) {
                return -1;
            } else if (port > scanJob.port) {
                return 1;
            } else {
                return 0;
            }
        } catch (ClassCastException exception) {
            return -1;
        }
    }

    @Override
    public String toString() {
        return address.toString();
    }

}
