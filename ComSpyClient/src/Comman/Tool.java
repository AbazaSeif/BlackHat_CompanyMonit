/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import org.apache.commons.net.util.Base64;

/**
 *
 * @author abaza
 */
public class Tool {

    public static final TextMessage.Language KEY_LANGAUGAE = TextMessage.Language.EN;
    public static final TextMessage tt = new TextMessage(KEY_LANGAUGAE);
    public static final ResourceBundle local = tt.StartLanguage(tt.getAppLanguage());
    private static final String OS = System.getProperty("os.name").toLowerCase();

    public String getSystemName() {
        return OS;
    }

    public ResourceBundle GetLocal() {
        return local;
    }

    public File createTempDir(int forhow) throws FileNotFoundException {
        String FileName;
        File baseDir = new File(compy_client.ComSpy.AppPath);
        compy_client.ComSpy.Splach = System.getProperty("file.separator");
        Random R = new Random();
        String Extintion = null;
        switch (forhow) {
            case 0:
                Extintion = ".wav";
                break;
            case 1:
                Extintion = ".cap";
                break;
            case 2:
                Extintion = ".gif";
                break;
        }
        FileName = baseDir.getAbsolutePath() + compy_client.ComSpy.Splach + "File_" + String.valueOf(R.nextInt()) + Extintion;
        return new File(FileName);
    }

    public void Log(String Message, String ClassName) {
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Calendar cal = Calendar.getInstance();
//            String Today = dateFormat.format(cal.getTime());
//            Log_message LM = new Log_message();
//            LM.doSetCom_name(Args.get(Keys.NAME));
//            LM.doSetClass_name(ClassName);
//            LM.doSetMessage(Message);
//            LM.doSetSuuid(Args.get(Keys.SUUID));
//            LM.doSetCuuid(Args.get(Keys.CUUID));
//            LM.doSetLog_date(Today);
//            LM.insertIntoDB(ComSpy.getConnection());
//        } catch (SQLException ex) {
//        }
    }

    public InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress()
                            && !ia.isLoopbackAddress()
                            && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
        }
        return null;
    }

    public String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");//
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss - a");//
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public byte[] String_compress(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            try (OutputStream out = new DeflaterOutputStream(baos)) {
                out.write(text.getBytes("UTF-8"));
            }
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        return baos.toByteArray();
    }

    public String String_decompress(byte[] bytes) {
        InputStream in = new InflaterInputStream(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while ((len = in.read(buffer)) > 0) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), "UTF-8");
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public byte[] Image_compress(final byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream(data.length)) {
            final Deflater deflater = new Deflater();
            deflater.setInput(data);

            deflater.finish();
            final byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                out.write(buffer, 0, deflater.deflate(buffer));
            }

            return out.toByteArray();
        } catch (final IOException e) {
            System.err.println("Compression failed! Returning the original data...");
            return data;
        }
    }

    public byte[] Image_decompress(final byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0];
        }

        final Inflater inflater = new Inflater();
        inflater.setInput(data);

        try (final ByteArrayOutputStream out = new ByteArrayOutputStream(data.length)) {
            final byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                out.write(buffer, 0, inflater.inflate(buffer));
            }

            return out.toByteArray();
        } catch (final IOException | DataFormatException e) {
            System.err.println("Decompression failed! Returning the compressed data...");
            return data;
        }
    }

    /**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }
 
    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
