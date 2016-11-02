/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Filetransfer;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import compy_client.ComSpy;
import java.util.HashMap;

/**
 *
 * @author abaza
 */
public class client_sendfile_implimntation {

    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    Comman.Tool CTool = new Tool();
    client_sendfile SendingFile = null;
    compy_client.ComSpy ComSpy = null;

    public void Switch(boolean SwitchKey, int Type, compy_client.ComSpy ComPy) {
        this.ComSpy = ComPy;
        if (SwitchKey) {
            switch (Type) {
                case 0:
                    VideoFiles();
                    AduioFiles();
                    break;
                case 1:
                    VideoFiles();
                    break;
                case 2:
                    AduioFiles();
                    break;
            }
        } else {
            //Close
        }
    }

    private void VideoFiles() {
        SendingFile = new client_sendfile();

        String[][] Data = new String[][]{
            {Server_Var.TYPE_COMMENT, Server_Var.GET_VIDEOS_FILES},
            {Local_Var.SOUND_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.SOUND_CUUID, Args.get(Keys.CUUID)},
            {Local_Var.SOUND_ISDONE, "0"}};

        HashMap<String, String> DataRet = this.ComSpy.Server.Contaner(Data);
        if (this.ComSpy.Server.DoneOrNot(DataRet)) {
            for (int i = 0; i <= DataRet.size() - 1; i++) {
                String De = DataRet.get(String.valueOf(i));
                if (De != null) {
                    SendingFile.SetFile(De);
                    if (!SendingFile.isDone()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        } else {
            ComSpy.MessageLog("Error Database");
        }
    }

    private void AduioFiles() {
        SendingFile = new client_sendfile();
        String[][] Data = {
            {Server_Var.TYPE_COMMENT, Server_Var.GET_AUDIO_FILES},
            {Local_Var.SOUND_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.SOUND_CUUID, Args.get(Keys.CUUID)},
            {Local_Var.SOUND_ISDONE, "0"},};
        HashMap<String, String> DataRet = this.ComSpy.Server.Contaner(Data);
        if (this.ComSpy.Server.DoneOrNot(DataRet)) {
            String[] FNames = new String[DataRet.size() - 1];

            if (DataRet != null) {
                for (int i = 0; i <= DataRet.size() - 2; i++) {
                    FNames[i] = DataRet.get(String.valueOf(i));
                }
                for (int i = 0; i <= FNames.length - 1; i++) {
                    SendingFile.SetFile(FNames[i]);
                    if (!SendingFile.isDone()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }

        }
    }

}
