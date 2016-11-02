/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author abaza
 */
public class Clipbord implements ActionListener, ClipboardOwner {

    String Host = null;
    int port = 0;
    private static Socket socket;
    OutputStreamWriter osw = null;
    OutputStream os = null;
    BufferedWriter bw = null;
    private static String OldData = "";

    public Clipbord(String Host, int Port) {
        this.Host = Host;
        this.port = Port;
    }

    public synchronized void Send(String sendMessage) {
        try {
            InetAddress address = InetAddress.getByName(this.Host);
            socket = new Socket(address, this.port);
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            bw.write(sendMessage);
            bw.flush();
        } catch (IOException ex) {
        } finally {
            //Closing the socket
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String Data = getClipboardContents();
        if (!Data.equals(OldData)) {
            Send(Data);
            OldData = Data;
        }
    }

    @Override
    public void lostOwnership(Clipboard clpbrd, Transferable t) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Place a String on the clipboard, and make this class the owner of the
     * Clipboard's contents.
     *
     * @param aString
     */
    public void setClipboardContents(String aString) {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);
    }

    /**
     * Get the String residing on the clipboard.
     *
     * @return any text found on the Clipboard; if none found, return an empty
     * String.
     */
    public String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        //odd: the Object param of getContents is not currently used
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText
                = (contents != null)
                && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException ex) {
                Send("ERROR : " + ex.getMessage());
            }
        }
        return result;
    }
}
