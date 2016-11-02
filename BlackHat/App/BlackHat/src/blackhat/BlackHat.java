/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhat;

import Configration.Configrations;
import Window.MainGUI;
import com.github.sarxos.webcam.Webcam;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author abaza
 */
public class BlackHat {

    public static boolean Debug = true;
    private static final Language Lang = new Language();
    public static ResourceBundle local = Lang.GetLocal();
    public static final String ConfigrationFile = "/root/Desktop/Proj/BlackHat/conf/system";
    public static String PasswordServer = "S15A0!E19I8";
    private static final String Database_Name = "/root/Desktop/Proj/BlackHat/data/db";
    public static Configrations ConfigrationBlackHat = null;
    public static Webcam webcam = null;
    public static Connection ConnectDB = null;
    public static HashMap<String, String> Args = new HashMap<>();

    private BlackHat Init() {
        Webcam.setAutoOpenMode(true);

        ConfigrationBlackHat = new Configrations();
        File Path = new File(ConfigrationFile);
        if (Path.exists()) {
            ConfigrationBlackHat.Setup(ConfigrationFile, PasswordServer);
            Args = ConfigrationBlackHat.Configr(ConfigrationFile, PasswordServer);
        } else {
            Args = ConfigrationBlackHat.Configr(ConfigrationFile, PasswordServer);
        }
        if (Args == null) {
            JOptionPane.showMessageDialog(null, "Error In System File\nPlease Call BlackHat Team", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        if (!Args.containsKey("PASSWORD")) {
            Args.put("PASSWORD", "PASSWORD");
        }
        return this;
    }

    private BlackHat Database() {
        File Database = new File(Database_Name);
        if (Database.exists()) {
            if (Debug) {
                System.out.println("Find Database");
            }
            try {
                Class.forName("org.sqlite.JDBC");
                ConnectDB = DriverManager.getConnection("jdbc:sqlite:" + Database_Name);
                /* For Connection to MySQL
                 String URL_DATABASE = "localhost";
                 String url = "jdbc:mysql://" + URL_DATABASE + ":3306/" + Database_Name;
                 String user = "root";
                 String password = "PASSWORD";
                 Class.forName("com.mysql.jdbc.Driver");
                 Connection conn = DriverManager.getConnection(url, user, password);
                 String sql = "SELECT photo FROM contacts";
                 PreparedStatement statement = conn.prepareStatement(sql);
                 ResultSet result = statement.executeQuery();
                 */
                if (Debug) {
                    System.out.println("Opened database successfully");
                }
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, local.getString("errDatabase"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(null, local.getString("errFileSystemError"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return this;
    }

    private BlackHat Network() {
        String Mode = Args.get("TYPE_NETWORK");
        if (Mode.equalsIgnoreCase("Local")) {
            if (!isNetworkReacheble()) {
                JOptionPane.showMessageDialog(null, local.getString("msgNoNetwork"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else if (Mode.equalsIgnoreCase("Internet")) {
            if (!isInternetReachable()) {
                JOptionPane.showMessageDialog(null, local.getString("msgNoNetwork"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        } else if (Mode.equalsIgnoreCase("Both")) {
            if (!isNetworkReacheble()) {
                JOptionPane.showMessageDialog(null, local.getString("msgNoNetwork"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            if (!isInternetReachable()) {
                JOptionPane.showMessageDialog(null, local.getString("msgNoNetwork"), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        return this;
    }

    private BlackHat InstallGUI() {
        try {
            SyntheticaBlackEyeLookAndFeel BlackHatStyle = new SyntheticaBlackEyeLookAndFeel();
            UIManager.put("Synthetica.button.animation.cycles", 2);
            UIManager.put("Synthetica.checkBox.animation.cycles", 3);

            UIManager.setLookAndFeel(BlackHatStyle);
        } catch (UnsupportedLookAndFeelException | ParseException ex) {
            String OS = System.getProperty("os.name").toLowerCase();
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        if (OS.contains("linux")) {
                            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                        }
                        if (OS.contains("windows")) {
                            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//info.getClassName()
                        }
                        break;
                    }
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex1) {
                Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return this;
    }

    private BlackHat Start() {
        Splash SplachScreen = new Splash(2500);

        return this;
    }

    private boolean isNetworkReacheble() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface interf = interfaces.nextElement();
                if (interf.isUp() && !interf.isLoopback()) {
                    return true;
                }
            }
        } catch (SocketException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), local.getString("lblErrorLabelMessage"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return false;
    }

    private boolean isInternetReachable() {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Make check if Disk = SERIAL
        new BlackHat().Init().Database().Network().InstallGUI().Start();
    }

}
