/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comman;

/**
 *
 * @author abaza Written to work with byte arrays requiring address space larger
 * than 32 bits.
 */
import java.io.FileInputStream;
import java.io.IOException;

public class ByteArray64 {

    private final long CHUNK_SIZE = 1024 * 1024 * 1024; //1GiB

    long size;
    byte[][] data;

    public ByteArray64(long size) {
        this.size = size;
        if (size == 0) {
            data = null;
        } else {
            int chunks = (int) (size / CHUNK_SIZE);
            int remainder = (int) (size - ((long) chunks) * CHUNK_SIZE);
            data = new byte[chunks + (remainder == 0 ? 0 : 1)][];
            for (int idx = chunks; --idx >= 0;) {
                data[idx] = new byte[(int) CHUNK_SIZE];
            }
            if (remainder != 0) {
                data[chunks] = new byte[remainder];
            }
        }
    }

    public byte get(long index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error attempting to access data element " + index + ".  Array is " + size + " elements long.");
        }
        int chunk = (int) (index / CHUNK_SIZE);
        int offset = (int) (index - (((long) chunk) * CHUNK_SIZE));
        return data[chunk][offset];
    }

    public void set(long index, byte b) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Error attempting to access data element " + index + ".  Array is " + size + " elements long.");
        }
        int chunk = (int) (index / CHUNK_SIZE);
        int offset = (int) (index - (((long) chunk) * CHUNK_SIZE));
        data[chunk][offset] = b;
    }

    /**
     * Simulates a single read which fills the entire array via several smaller
     * reads.
     *
     * @param fileInputStream
     * @throws IOException
     */
    public void read(FileInputStream fileInputStream) throws IOException {
        if (size == 0) {
            return;
        }
        for (int idx = 0; idx < data.length; idx++) {
            if (fileInputStream.read(data[idx]) != data[idx].length) {
                throw new IOException("short read");
            }
        }
    }

    public long size() {
        return size;
    }
}
