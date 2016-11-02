/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compy_client;

import Comman.ConnectionServer;
import Comman.KeysVer;
import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import networksockets.create_port;
import ui_server.Splash;

/**
 *
 * @author abaza
 */
public class ComSpy extends JFrame {

    private globelThreadMonitring DDM = null;
    private static Comman.Tool Tool;
    public Comman.ConnectionServer Server = null;
    networksockets.create_port CP = new create_port();
    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    public static String AppPath, Splach;
    private Splash splash = null;
    static boolean DEMO = true;
    static boolean ADMIN = true;

    public static void MessageLog(Object msg) {
        if (DEMO) {
            System.out.println(msg);
        }
    }

    public static void MessageLog(Object msg, boolean isSys) {
        if (DEMO) {
            System.out.println(msg);
        }
        if (ADMIN) {
            if (isSys) {
                System.err.println(msg);
            }
        }
    }

    public ComSpy GetData() {
        if (ADMIN) {
            ComSpy.MessageLog("Server is Work...", true);
        } else {
            ComSpy.MessageLog("Client is Work...", true);
        }
        Server = new ConnectionServer();
        if (ADMIN) {
            Server.Init("conf/data_admin.xml");
        } else {
            Server.Init("conf/data_clint.xml");
        }
        return this;
    }

    public static boolean isInternetReachable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            Thread.sleep(20);
            Object objData = urlConnect.getContent();
            return true;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }

    private ComSpy Implimant() {
        if (isInternetReachable()) {
            ComSpy.MessageLog("Internet Connect", true);

            InetAddress IPADD = Tool.getCurrentIp();
            Args.put(Keys.MY_IP, IPADD.getHostAddress());

            if (Server.Connect(Server_Var.CONNECT, false)) {
                HashMap<String, String> Ret = null;
                String[][] Data = null;
                if (ADMIN) {
                    Args.put(Keys.SERVER_PORT, Args.get(Keys.MY_PORT));
                    Args.put(Keys.SUUID, Args.get(Keys.CUUID));
                    Data = new String[][]{
                        {Server_Var.TYPE_COMMENT, Server_Var.IAMSERVER},
                        {Local_Var.SERVER_SUUID, Args.get(Keys.CUUID)},
                        {Local_Var.SERVER_IP, Args.get(Keys.MY_IP)},
                        {Local_Var.SERVER_PORT, Args.get(Keys.MY_PORT)},
                        {Local_Var.SERVER_APORT, Args.get(Keys.AUDIO_PORT)},
                        {Local_Var.SERVER_UUSERNAME, Args.get(Keys.VUSERNAME)},
                        {Local_Var.SERVER_UPASSWORD, Args.get(Keys.VPASSWORD)}
                    };

                } else {
                    Args.put(Keys.MY_PORT, String.valueOf(CP.findFreePort()));
                    Data = new String[][]{
                        {Server_Var.TYPE_COMMENT, Server_Var.IAM},
                        {Local_Var.USERS_CUUID, Args.get(Keys.CUUID)},
                        {Local_Var.USERS_IP, Args.get(Keys.MY_IP)},
                        {Local_Var.USERS_PORT, Args.get(Keys.MY_PORT)},
                        {Local_Var.USERS_LAST_LOG, Tool.getDay()},
                        {Local_Var.USERS_LAST_LOG_TIME, Tool.getTime()},
                        {Local_Var.USERS_STATUS, "1"}
                    };
                }
                Ret = Server.Contaner(Data);
                if (ADMIN) {
                    if (Ret != null) {
                        if (Server.DoneOrNot(Ret)) {
                            Data = null;
                            Data = new String[][]{
                                {Server_Var.TYPE_COMMENT, Server_Var.GETS_USERS},
                                {Local_Var.SERVER_SUUID, Args.get(Keys.CUUID)}
                            };
                            Ret = Server.Contaner(Data);
                            if ((Ret != null) && Server.DoneOrNot(Ret)) {
                                for (int i = 0; i <= Ret.size() - 1; i++) {
                                    String reternData = Ret.get(String.valueOf(i));
                                    if (reternData != null) {
                                        KeysVer.ComputersName.put(i, reternData);
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Sorry this Linses is Finshed", "Error", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    }
                    Data = new String[][]{
                        {Server_Var.TYPE_COMMENT, Server_Var.ADMINUP},
                        {Local_Var.SERVER_UUID, Args.get(Keys.CUUID)},};
                    Ret = null;
                    Ret = Server.Contaner(Data);
                    if (Server.DoneOrNot(Ret)) {
                        Args.put(Keys.USERNAME, Ret.get(Local_Var.SERVER_UUSERNAME));
                        Args.put(Keys.PASSWORD, Ret.get(Local_Var.SERVER_UPASSWORD));
                        Args.put(Keys.FHOST, Ret.get(Local_Var.ADMINSTRATION_FTP_SERVER));
                        Args.put(Keys.FKEY, Ret.get(Local_Var.ADMINSTRATION_KEY_FILES_IN_FTP));
                        Args.put(Keys.FPAS, Ret.get(Local_Var.ADMINSTRATION_FTP_PASS));
                        Args.put(Keys.FPATH, Ret.get(Local_Var.ADMINSTRATION_FTP_PATH));
                        Args.put(Keys.FUSER, Ret.get(Local_Var.ADMINSTRATION_FTP_USER));
                        Args.put(Keys.EXPIRD, Ret.get(Local_Var.ADMINSTRATION_EXPIERD));
                    }
                    //Get Username and Password
                } else {
                    if (!Ret.isEmpty()) {
                        if (Server.DoneOrNot(Ret)) {
                            Args.put(Keys.SUUID, Ret.get(Local_Var.USERS_SUUID));
                            Args.put(Keys.NAME, Ret.get(Local_Var.USERS_COM_NAME));

                            Args.put(Keys.FHOST, Ret.get(Local_Var.ADMINSTRATION_FTP_SERVER));
                            Args.put(Keys.FKEY, Ret.get(Local_Var.ADMINSTRATION_KEY_FILES_IN_FTP));
                            Args.put(Keys.FPAS, Ret.get(Local_Var.ADMINSTRATION_FTP_PASS));
                            Args.put(Keys.FPATH, Ret.get(Local_Var.ADMINSTRATION_FTP_PATH));
                            Args.put(Keys.FUSER, Ret.get(Local_Var.ADMINSTRATION_FTP_USER));
                            Args.put(Keys.EXPIRD, Ret.get(Local_Var.ADMINSTRATION_EXPIERD));
                            Args.put(Keys.SERVER_IP, Ret.get(Local_Var.SERVER_IP));
                            Args.put(Keys.SERVER_PORT, Ret.get(Local_Var.SERVER_PORT));
                            if (Args.get(Keys.EXPIRD).equals(Tool.getDay())) {
                                System.exit(0);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Unorgnized", "Error", JOptionPane.ERROR_MESSAGE);
                            System.exit(0);
                        }
                    } else {
                        TryAgain();
                    }
                }
                if (ADMIN) {
                    splash = new Splash(3000);
                    ComSpy.MessageLog("All Data is Ready");
                } else {
                    DDM = new globelThreadMonitring(Args.get(Keys.SUUID), Args.get(Keys.CUUID), this);
                }
            } else {
                TryAgain();
            }
        } else {
            ComSpy.MessageLog("Not Connect", true);
            TryAgain();
        }
        return this;
    }

    private void TryAgain() {
        try {
            Thread.sleep(2000);
            Implimant();
        } catch (InterruptedException ex) {
        }
    }

    public ComSpy attachShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                ComSpy.MessageLog("Closing..");
            }
        });
        ComSpy.MessageLog("Hook attached..");
        return this;
    }

    public ComSpy EndApp() {
        ComSpy.MessageLog("Stop From Database");
        System.exit(0);
        return this;
    }

    public static void closeClient() {
        new ComSpy().attachShutDownHook().EndApp();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            AppPath = new File(".").getCanonicalPath();
            Tool = new Tool();

            new ComSpy().GetData().Implimant();
        } catch (IOException ex) {
            System.exit(0);
        }
    }

}
