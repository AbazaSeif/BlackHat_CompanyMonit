package Comman;

import java.sql.Connection;
import java.util.HashMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author abaza
 */
public class KeysVer {

    public static Connection GeneralConnection;
    public static String[] WhoOnline;

    public void initWhoOnline(int Lienses) {
        WhoOnline = new String[Lienses];
    }

    public void setWhoOnline(String[] var) {
        if (var.length == 0) {
            return;
        }

        if (WhoOnline == null) {
            WhoOnline = new String[Integer.parseInt(Args.get(Keys.LINSES))];
        }

        for (int i = 0; i <= var.length - 1; i++) {
            WhoOnline[i] = var[i];
        }
    }

    public String[] getWhoOnline() {
        return WhoOnline;
    }

    public void setConnection(Connection con) {
        GeneralConnection = con;
    }

    public Connection getConnection() {
        return GeneralConnection;
    }

    public static enum Keys {

        NAME,
        USERNAME,
        PASSWORD,
        VUSERNAME,
        VPASSWORD,
        SUUID,
        CUUID,
        FHOST,
        FUSER,
        FPAS,
        FKEY,
        FPATH,
        DISK,
        SERVER_IP,
        SERVER_PORT,
        AUDIO_PORT,
        EXPIRD,
        LINSES,
        Lang,
        SL,
        MY_IP,
        MY_PORT

    }

    public static HashMap<Keys, String> Args = new HashMap<>();

    public static HashMap<Integer, String> ComputersName = new HashMap<>();

    public enum CPUKeys {

        Shutdown,
        Restart,
        Shutdown_after,
        Restart_after;

        public int getValue() {
            switch (this) {
                case Shutdown:
                    return 1;
                case Restart:
                    return 2;
                case Shutdown_after:
                    return 3;
                case Restart_after:
                    return 4;
                default:
                    return -1;
            }
        }
    }

    public enum Socket_Type {

        TCP,
        UDP;

        public int getValue() {
            switch (this) {
                case TCP:
                    return 1;
                case UDP:
                    return 2;
            }
            return 0;
        }
    }
}
