/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Camera.Motion;
import Camera.MouseObserver;
import Connection.Command;
import Connection.PortScanner;
import Connection.Telnet;
import Database.Client;
import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import eu.hansolo.steelseries.extras.Led;
import eu.hansolo.steelseries.tools.LedColor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 *
 * @author abaza
 */
public final class MainGUI extends javax.swing.JFrame {

    protected Thread ScanComputer = null;
    public static ArrayList ConnectionListClient = null;
    private String ApplicationIcon = "img/blackhatAppIcon.png";
    private MouseObserver MouseBH = null;
    //Forms
    BlackHatSettingWindow SettingForm = null;
    frmScreenRecord frmScreen = null;
    frmScreenShot frmScreenShot = null;
    frmSSViewImage ShowImage = null;
    frmDownload DownloadForm = null;
    frmAllPCs AllComputers = null;
    frmInformation InformationPC = null;
    frmAudio AudioLife = null;
    frmLifeWebcam WebCamOnline = null;
    frmPhone PhoneFram = null;
    frmKeybord Keybord = null;
    frmClipbord fClipbord = null;
    frmApplication TaskManger = null;
    frmShell ShellPowerFrm = null;
    frmFileManger FileMangerBH = null;
    //Menu
    JPopupMenu Menu = new JPopupMenu("BlackHat Menu");
    //Message
    private static boolean MessageHolder = false;
    private static boolean isAction = false;

    //Button
    public static String[] KeysName = {"KEY_AUDIO_PHONE", "KEY_VIDEO_RECORD", "KEY_KEYBORD",
        "KEY_COMMAND_LINE", "KEY_SNIFFER", "KEY_AUDIO_SPY_RECORD", "KEY_VIDEO_LIFE",
        "KEY_TASKMANGER", "KEY_CAMERA", "KEY_FOOTPRINT", "KEY_AUDIO_SPY_LIFE",
        "KEY_VIDEO_SCREENSHOT", "KEY_CLIPBORD", "KEY_USB", "KEY_FILEMANGER_SPY", "KEY_IP_SCAN", "KEY_MOTION_DETACT", "DOWNLOAD_AND_SHOW"};
    public static HashMap<String, JButton> KeysButton = new HashMap();
    private Thread thrMotion = null;

    //Timers
    private Timer ScanerTimer = null;

    //Vars
    private final String CONNECTED = "img/connected-128.png";
    private final String DISCONNECTED = "img/disconnected-128.png";
    public boolean CLIENT_CONNECT = false;
    private Led[] MyLeds = null;
    private static String VideoPath = null;
    private boolean VideoPathFlag = false;
    //Size
    int xSize, ySize;

    /*Var from Client*/
    public String SYSTEMVERSION;
    public String SYSTEMARCH;
    public String OS_Lang;
    public String User_name;
    public String User_Home;
    public String User_Language;
    public String User_Country;
    public String IP_ADDRESS;
    public String MAC_ADDRESS;
    public String JAVA_VERSION;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        try {
            initComponents();
            init_Menu();
            Buttons_Init();
            led1.setLedColor(LedColor.RED_LED);
            led1.setLedOn(false);
            init_Leds();
            //Messages
            HideSubMenu();
            BasyBox(false);
            desktopPane.setBackground(Color.black);
            Toolkit tk = Toolkit.getDefaultToolkit();
            xSize = ((int) tk.getScreenSize().getWidth());
            ySize = ((int) tk.getScreenSize().getHeight());
            this.setSize(xSize, ySize);
            this.setFocusableWindowState(true);
            this.setExtendedState(MainGUI.MAXIMIZED_BOTH);
            this.setTitle(GetText("ApplicationName"));
            this.setIconImage(ImageIO.read(SecuritySupport.getResourceAsStream(ApplicationIcon)));
            jPanel4.setVisible(false);
            Login();
        } catch (IOException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Sleep(long miliscon) {
        try {
            Thread.sleep(miliscon);
        } catch (InterruptedException ex) {
        }
    }

    private void init_Menu() {
        JMenuItem item = new JMenuItem(GetText("menuRefresh")); //0
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Refrshe();
            }
        });

        Menu.add(item);

        item = new JMenuItem(GetText("menuAllComputers")); //1
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (AllComputers == null) {
                    AllComputers = new frmAllPCs(MainGUI.this);
                    Insert(AllComputers, GetText("menuAllComputers"), null);
                } else {
                    AllComputers.dispose();
                    AllComputers = null;
                    this.actionPerformed(e);
                }
            }
        });
        Menu.add(item);

        Menu.addSeparator(); //2

        item = new JMenuItem(GetText("menuComputerInformation")); //3
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                led1.setLedColor(LedColor.MAGENTA_LED);
                led1.setLedBlinking(true);
                if (InformationPC == null) {
                    JOptionPane.showMessageDialog(MainGUI.this, GetText("msgMustConnectfirst"), GetText("msgWARNING"), JOptionPane.WARNING_MESSAGE);
                } else {
                    for (JInternalFrame frm : desktopPane.getAllFrames()) {
                        if (frm == InformationPC) {
                            return;
                        }
                    }
                    led1.setLedColor(LedColor.GREEN_LED);
                    led1.setLedOn(true);
                    Insert(InformationPC, GetText("menuComputerInformation"), null);
                }
            }
        });
        Menu.add(item);

        item = new JMenuItem(GetText("menuChangeParameter")); //4
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            }
        });
        Menu.add(item);

        Menu.addSeparator(); //5

        item = new JMenuItem(GetText("opLogoutfromBlackHat")); //6
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Logout();
            }
        });
        Menu.add(item);

        Menu.getComponent(3).setEnabled(false);
        Menu.getComponent(4).setEnabled(false);
    }

    private void Refrshe() {
        desktopPane = new javax.swing.JDesktopPane() {
            ImageIcon icon = new ImageIcon("data/bg.jpg");
            Image image = icon.getImage();

            Image newimage = image.getScaledInstance(1660, 1000, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(newimage, 0, 0, Color.BLACK, this);
                g.setColor(Color.BLACK);

            }
        };

        desktopPane.setBorder(null);
        desktopPane.setBackground(Color.BLACK);
        HideSubMenu();

        if (jPanel4.isVisible()) {
            ButtonEnable(true);
        } else {
            ButtonEnable(false);
        }
    }

    public void InsertVideoPath(String Path) {
        VideoPath = Path.split(":")[3].trim();
        VideoPathFlag = true;
    }

    public String GetText(String Label) {
        return blackhat.BlackHat.local.getString(Label);
    }

    private synchronized boolean ScanerTasks() {
        ActionListener Scanar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Connect(Command.TASKS);
            }
        };
        if (ScanerTimer == null) {
            ScanerTimer = new Timer(1000, Scanar);
            ScanerTimer.start();
            return true;
        } else if (ScanerTimer != null) {
            ScanerTimer.stop();
            ScanerTimer = null;
            return false;
        }
        return false;
    }

    public void StartMotion() {
        if (Integer.parseInt(blackhat.BlackHat.Args.get("RUN_MOTION")) == 1) {
            if (thrMotion == null) {
                thrMotion = new Thread(new Motion(this));
                thrMotion.setDaemon(true);
                thrMotion.start();
                ActionLisen();
                MouseLisen();
            }
        } else if (thrMotion != null) {
            thrMotion = null;
        }
    }

    public void doAction() {
        if (!isAction) {
            isAction = true;
            if (blackhat.BlackHat.Debug) {
                System.out.println("-> Action is " + isAction);
            }
        }
    }

    public boolean isAction() {
        if (blackhat.BlackHat.Debug) {
            System.out.println("Action is " + isAction);
        }
        return isAction;
    }

    private void MouseLisen() {
        MouseBH = new MouseObserver(this);
        MouseBH.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent me) {
                doAction();
                if (blackhat.BlackHat.Debug) {
                    System.out.println("mouse moved: " + me.getPoint());
                }
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                doAction();
                if (blackhat.BlackHat.Debug) {
                    System.out.println("mouse dragged: " + me.getPoint());
                }
            }
        });
        MouseBH.start();
    }

    private void ActionLisen() {
        ActionListener MyAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isAction) {
                    isAction = false;
                    if (blackhat.BlackHat.Debug) {
                        System.err.println("<- Action is " + isAction);
                    }
                }
            }
        };

        Timer timer = new Timer(5000, MyAction);
        timer.start();
    }

    public static void Notification(String SocketHack) {
        MainGUI.MessageHolder = true;
        MainGUI.MessageHolder = true;
    }

    private void init_Leds() {
        MyLeds = new Led[6];
        MyLeds[0] = led2;
        MyLeds[1] = led3;
        MyLeds[2] = led4;
        MyLeds[3] = led5;
        MyLeds[4] = led6;
        MyLeds[5] = led7;
        for (int i = 0; i <= MyLeds.length - 1; i++) {
            Color_Led(i, LedColor.RED_LED);
            Open_Led(i, false, "");
        }
    }

    private void Open_Led(int led, boolean open, String Text) {
        MyLeds[led].setLedOn(open);
        MyLeds[led].setToolTipText(Text);
    }

    private void Blinking_Led(int led, boolean open, String Text) {
        MyLeds[led].setLedBlinking(open);
        MyLeds[led].setToolTipText(Text);
    }

    private void Color_Led(int led, LedColor Color) {
        MyLeds[led].setLedColor(Color);
    }

    public void UpdateComboBox(String PC_NAME) {
        int Index = autoCompletionComboBox1.getSelectedIndex();
        autoCompletionComboBox1.remove(Index);
        autoCompletionComboBox1.insertItemAt(PC_NAME, Index);
        autoCompletionComboBox1.setSelectedIndex(Index);
    }

    public void HideComputerInformation() {
        txtUUID.setText("");
        txtPCName.setText("");
        txtLOGINTIME.setText("");
        txtSystemTime.setText("");
        jPanel4.setVisible(false);
    }

    public void ShowComputerInformation(String UUID, String PC_NAME, String TIME, String System, String[] Other) {
        try {
            SYSTEMVERSION = Other[6];
            SYSTEMARCH = Other[7];
            OS_Lang = Other[8];
            User_name = Other[9];
            User_Home = Other[10];
            User_Language = Other[11];
            User_Country = Other[12];
            IP_ADDRESS = Other[13];
            MAC_ADDRESS = Other[14];
            JAVA_VERSION = Other[14];

            InformationPC = new frmInformation(this);

            InformationPC.jLabel8.setText(System);
            InformationPC.jLabel11.setText(SYSTEMVERSION);
            InformationPC.jLabel13.setText(SYSTEMARCH);
            InformationPC.jLabel15.setText(User_name);
            InformationPC.jLabel17.setText(User_Language);
            InformationPC.jLabel19.setText(User_Home);
            InformationPC.jLabel21.setText(User_Country);
            InformationPC.jLabel23.setText(JAVA_VERSION);
            InformationPC.jLabel9.setText(UUID);
            InformationPC.lblLogoSystem.setText("");
            ImageIcon icon = null;
            if (System.contains("win") || System.contains("Win") || System.contains("WIN")) {
                icon = new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream("img/windows.png")));
            } else if (System.contains("mac") || System.contains("Mac") || System.contains("MAC")) {
                icon = new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream("img/mac.png")));
            } else if (System.contains("linux") || System.contains("Linux") || System.contains("LINUX")) {
                icon = new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream("img/linux.png")));
            }
            InformationPC.lblLogoSystem.setIcon(icon);
//            String IPADD = autoCompletionComboBox1.getSelectedItem().toString();
            txtUUID.setText(UUID);
            txtPCName.setText(PC_NAME);
            txtLOGINTIME.setText(TIME);
            txtSystemTime.setText(System);
            jPanel4.setVisible(true);
            ButtonEnable(true);
            String Q = "WHERE " + Client.FLD_PC_IP_ADDRESS + "='" + IP_ADDRESS + "' OR " + Client.FLD_PC_NAME + "='" + PC_NAME + "'";
            String isExistQ = "WHERE " + Client.FLD_PC_NAME + "='" + PC_NAME + "'";
            Client[] isExist = Client.queryDB(blackhat.BlackHat.ConnectDB, isExistQ);
            Client Computers = Client.queryFirstRow(blackhat.BlackHat.ConnectDB, Q);
            if (Computers == null) {
                if (isExist.length > 0) {
                    PC_NAME = PC_NAME + "(" + (isExist.length) + ")";
                }
                Client Computer = new Client();
                Computer.doSetPc_ip_address(IP_ADDRESS);
                Computer.doSetMac(MAC_ADDRESS);
                Computer.doSetLast_login(TIME);
                Computer.doSetPc_local_port(blackhat.BlackHat.Args.get("SERVER_PORT"));
                Computer.doSetPc_name(PC_NAME);
                Computer.doSetStatus(1);
                Computer.doSetUuid(UUID);
                Computer.insertIntoDB(blackhat.BlackHat.ConnectDB);
                UpdateComboBox(PC_NAME);

            } else if (Computers.getUuid().toString().equals(UUID)) {
                Computers.doSetPc_ip_address(IP_ADDRESS);
                Computers.doSetMac(MAC_ADDRESS);
                Computers.doSetLast_login(TIME);
                Computers.doSetStatus(1);
                Computers.updateDB(blackhat.BlackHat.ConnectDB, Client.FLD_PC_IP_ADDRESS, Client.FLD_MAC, Client.FLD_LAST_LOGIN, Client.FLD_STATUS);
            } else {
                JOptionPane.showMessageDialog(this, GetText("msgHashOrSpy") + autoCompletionComboBox1.getSelectedItem().toString(), "BlackHat", JOptionPane.ERROR_MESSAGE);
                autoCompletionComboBox1.remove(autoCompletionComboBox1.getSelectedIndex());
                HideComputerInformation();
            }
        } catch (SQLException | IOException ex) {
        }
    }

    private void Buttons_Init() {
        KeysButton.put(KeysName[0], btnPhone);
        KeysButton.put(KeysName[1], btnScreen_Video);
        KeysButton.put(KeysName[2], btnTool_Keybord);
        KeysButton.put(KeysName[3], btnTool_Shell);
        KeysButton.put(KeysName[4], btnSniffer); //Sniffer
        KeysButton.put(KeysName[5], btnAudio_Recoed);
        KeysButton.put(KeysName[6], btnScreenLife);
        KeysButton.put(KeysName[7], btnTool_Application);
        KeysButton.put(KeysName[8], btnWebcam); //Camera
        KeysButton.put(KeysName[9], btnFootPrint);
        KeysButton.put(KeysName[10], btnAudio_Life);
        KeysButton.put(KeysName[11], btnScreen_ScreenShut);
        KeysButton.put(KeysName[12], btnTool_Clipbord);
        KeysButton.put(KeysName[13], btnUSB);
        KeysButton.put(KeysName[14], btnFileManger);
        KeysButton.put(KeysName[15], btnIPScan);
        KeysButton.put(KeysName[16], btnInOfficeOrNot); //KEY_MOTION_DETACT
        KeysButton.put(KeysName[17], btnDownload);
        for (Map.Entry thisEntry : blackhat.BlackHat.Args.entrySet()) {
            if (KeysButton.containsKey(thisEntry.getKey())) {
                if (thisEntry.getValue().equals("1")) {
                    if (KeysButton.get(thisEntry.getKey()) != null) {
                        KeysButton.get(thisEntry.getKey()).setVisible(true);
                    }
                } else if (KeysButton.get(thisEntry.getKey()) != null) {
                    KeysButton.get(thisEntry.getKey()).setVisible(false);
                }
            }
        }
        ButtonEnable(false);
    }

    public static InetAddress getLocalAddress() throws SocketException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while (ifaces.hasMoreElements()) {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                    return addr;
                }
            }
        }

        return null;
    }

    private void HideSubMenu() {
        pnShut.setVisible(false);
        panelMenu1.setVisible(false);
        pnAudio.setVisible(false);
        panTools.setVisible(false);
        PanNetwork.setVisible(false);
        ptnMenuScreenshut.setVisible(false);
        panelCamera.setVisible(false);
        doAction();
    }

    public synchronized void ConnectionLocal() {
        try {
            led1.setLedBlinking(true);
            ConnectionListClient = new ArrayList();
            int Port = Integer.parseInt(blackhat.BlackHat.Args.get("SERVER_PORT"));
            String IP = getLocalAddress().getHostAddress();
            ScanComputer = new Thread(new PortScanner(IP, Port, 50, 50));
            ScanComputer.start();
        } catch (SocketException | NumberFormatException ex) {
            System.err.println("Internet Not Connected");
        }
    }

    private void AudioLife() {
        if (AudioLife == null) {
            AudioLife = new frmAudio(this);
            this.Insert(AudioLife, GetText("btnAudioLife"), "img/Audio_Life.png");
        } else {
            AudioLife.dispose();
            AudioLife = null;
            AudioLife();
        }
    }

    public String GetCurrentIP() {
        String IP_ADDRESS_internal = null;
        if (autoCompletionComboBox1.getSelectedItem().toString().contains(".")) {
            IP_ADDRESS_internal = autoCompletionComboBox1.getSelectedItem().toString().trim();
        } else {
            try {
                String Q = "WHERE " + Client.FLD_PC_NAME + "='" + autoCompletionComboBox1.getSelectedItem().toString() + "'";
                Client Computers = Client.queryFirstRow(blackhat.BlackHat.ConnectDB, Q);
                if (Computers != null) {
                    IP_ADDRESS_internal = Computers.getPc_ip_address().toString().trim();
                }
            } catch (SQLException ex) {
            }
        }
        if (IP_ADDRESS_internal.isEmpty()) {
            return null;
        } else if (IP_ADDRESS_internal.contains(".")) {
            return IP_ADDRESS_internal;
        } else {
            return null;
        }
    }

    public synchronized void Connect(int Order) {
        doAction();
        if (autoCompletionComboBox1.getComponentCount() <= 0) {
            return;
        }

        Connection.Telnet CTL = new Telnet();
        int Port = Integer.parseInt(blackhat.BlackHat.Args.get("SERVER_PORT"));
        String IP_ADDRESS_internal = null;
        if (autoCompletionComboBox1.getSelectedItem().toString().contains(".")) {
            IP_ADDRESS_internal = autoCompletionComboBox1.getSelectedItem().toString().trim();
        } else {
            try {
                String Q = "WHERE " + Client.FLD_PC_NAME + "='" + autoCompletionComboBox1.getSelectedItem().toString() + "'";
                Client Computers = Client.queryFirstRow(blackhat.BlackHat.ConnectDB, Q);
                if (Computers != null) {
                    IP_ADDRESS_internal = Computers.getPc_ip_address().toString().trim();
                }
            } catch (SQLException ex) {
            }
        }
        if (IP_ADDRESS_internal != null) {
            CTL.createClient(IP_ADDRESS_internal, Port, this);
            if (CTL.hasActiveSession()) {
                CTL.sendCommand(Order);
            } else {
                Sleep(2000);
                CTL.sendCommand(Order);
            }
        }
    }

    public static void BasyBox(boolean Key) {
        try {
//            jXBusyLabel1.setBusy(Key);
//            jXBusyLabel1.setText(blackhat.BlackHat.local.getString("lblScan"));
//            jXBusyLabel1.setVisible(Key);
            if (Key) {
                led1.setLedOn(false);
                led1.setLedColor(LedColor.RED_LED);
                led1.setLedBlinking(true);

                jLabel3.setVisible(false);
                jLabel1.setVisible(true);
                jLabel7.setVisible(false);
            } else if ((autoCompletionComboBox1.getItemCount()) > 0) {
                led1.setLedBlinking(false);
                led1.setLedColor(LedColor.GREEN_LED);
                led1.setLedOn(true);
                jLabel3.setVisible(true);
                jLabel1.setVisible(true);
                jLabel7.setVisible(true);
            } else {
                led1.setLedBlinking(false);
                led1.setLedColor(LedColor.YELLOW_LED);
                led1.setLedBlinking(true);

                Client[] Computers = Client.queryDB(blackhat.BlackHat.ConnectDB, "");
                for (Client Computer : Computers) {
                    Computer.doSetStatus(0);
                    Computer.updateDB(blackhat.BlackHat.ConnectDB, Client.FLD_STATUS);
                }
                jLabel3.setVisible(true);
                jLabel1.setVisible(true);
                jLabel7.setVisible(false);

            }

        } catch (SQLException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ShowImage(String ImagePath, frmScreenShot Class) {
        if (ShowImage == null) {
            ShowImage = new frmSSViewImage(ImagePath, Class);
            this.Insert(ShowImage, GetText("titelImageView"), "img/screenshot.png");
        } else {
            ShowImage.dispose();
            ShowImage = null;
            ShowImage(ImagePath, Class);
        }
    }

    public void Download() {
        if (DownloadForm == null) {
            DownloadForm = new frmDownload();
            this.Insert(DownloadForm, GetText("tabDownloadSetting"), "img/downloads.png");
        } else {
            DownloadForm.dispose();
            DownloadForm = null;
            Download();
        }
    }

    private synchronized void ButtonEnable(boolean Key) {
//        if (CLIENT_CONNECT)
        jLabel1.setEnabled(Key);
        if (autoCompletionComboBox1.getComponentCount() > 0) {
            jLabel7.setEnabled(true);
            jLabel7.setVisible(true);
            if (CLIENT_CONNECT) {
                jLabel3.setEnabled(true);
            } else {
                jLabel3.setEnabled(false);
            }
            jLabel3.setVisible(true);
        } else {
            jLabel7.setEnabled(Key);
            jLabel7.setVisible(Key);
            jLabel3.setEnabled(false);
            jLabel3.setVisible(false);
        }
        for (JButton button : KeysButton.values()) {
            button.setEnabled(Key);
        }

    }

    public void Scaning() {
        ActionListener TimeNow = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (MainGUI.ConnectionListClient.size() > 0) {
                    autoCompletionComboBox1.setEnabled(true);

                    DefaultComboBoxModel Model = new DefaultComboBoxModel();
                    for (int i = 0; i <= MainGUI.ConnectionListClient.size() - 1; i++) {
                        try {
                            String Quiry = "WHERE " + Client.FLD_PC_IP_ADDRESS + " = '" + MainGUI.ConnectionListClient.get(i) + "' OR "
                                    + Client.FLD_PC_NAME + " = '" + MainGUI.ConnectionListClient.get(i) + "'";
                            Client Computer = Client.queryFirstRow(blackhat.BlackHat.ConnectDB, Quiry);
                            if (Computer != null) {
                                String PC_NAME = Computer.getPc_name().toString();
                                Model.addElement(PC_NAME);
                            } else {
                                Model.addElement(MainGUI.ConnectionListClient.get(i));
                            }
                        } catch (SQLException ex) {
                        }
                    }
                    if (Model.getSize() != 0) {
                        autoCompletionComboBox1.setModel(Model);
                        if (!CLIENT_CONNECT) {
                            ButtonEnable(false);
                        }
                        jLabel7.setEnabled(true);
                    } else {
                        autoCompletionComboBox1.setEnabled(false);
                        if (!CLIENT_CONNECT) {
                            ButtonEnable(false);
                        }
                    }
                } else {
                    autoCompletionComboBox1.setEnabled(false);
                    if (!CLIENT_CONNECT) {
                        ButtonEnable(false);
                    }
                }
            }

        };
        Timer timer = new Timer(5000, TimeNow);
        timer.start();
    }

    public void Insert(javax.swing.JInternalFrame Form, String Tital, String Icon) {
        try {
            doAction();
            int x = (int) ((desktopPane.getWidth() - Form.getWidth()) / 2);
            int y = (int) ((desktopPane.getHeight() - Form.getHeight()) / 2);

            desktopPane.add(Form);

            Form.setLocation(x, y);
            Form.setTitle(Tital);
            if (Icon != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream(Icon)));
                Form.setFrameIcon(icon);
            }
            Form.setClosable(true);
            Form.setVisible(true);
            HideSubMenu();
        } catch (IOException ex) {
        }
    }

    public void Screen_Video_Monitor() {
        if (frmScreen == null) {
            frmScreen = new frmScreenRecord(this, autoCompletionComboBox1.getSelectedItem().toString());
            this.Insert(frmScreen, GetText("btnGetScreen"), "img/record.png");
        } else {
            frmScreen.dispose();
            frmScreen = null;
            Screen_Video_Monitor();
        }
    }

    public void ScreenShot() {
        if (frmScreenShot == null) {
            frmScreenShot = new frmScreenShot(this, autoCompletionComboBox1.getSelectedItem().toString());
            this.Insert(frmScreenShot, GetText("btnprtsc"), "img/screenshot.png");
        } else {
            frmScreenShot.dispose();
            frmScreenShot = null;
            ScreenShot();
        }
    }

    public void ScreenShot_Background() {
        if (Command.KEY_ZIP_FLAG_SCREEN_SHUT) {
            Object[] options = {GetText("btnStopZip_Only"),
                GetText("btnZipStop_And_Download")};
            int Opt = JOptionPane.showOptionDialog(this,
                    GetText("msgZipScreenshut"),
                    GetText("titQuestion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title
            switch (Opt) {
                case 0:
                    Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Connect(Command.STOP_SCREEN_SHUT);
                    Connect(Command.STOP_ZIP_FLAG_SCREEN_SHUT);
                    Blinking_Led(0, false, GetText("ledScreenshot_Close"));
                    break;
                case 1:
                    Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Connect(Command.STOP_SCREEN_SHUT);
                    Connect(Command.STOP_ZIP_FLAG_SCREEN_SHUT);
                    Blinking_Led(0, false, GetText("ledScreenshot_Close"));
                    Download();
                    break;
            }

        } else {
            switch (JOptionPane.showConfirmDialog(this,
                    GetText("msgRunScreenshutInBackground"),
                    GetText("titQuestion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE)) {
                case 0:
                    Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Connect(Command.SCREEN_SHUT);
                    Connect(Command.ZIP_FLAG_SCREEN_SHUT);
                    Blinking_Led(0, true, GetText("ledScreenshot_Open"));
                    break;
                case 1:
                    Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Connect(Command.STOP_SCREEN_SHUT);
                    Connect(Command.STOP_ZIP_FLAG_SCREEN_SHUT);
                    Blinking_Led(0, false, GetText("ledScreenshot_Close"));
                    break;
            }
        }

        HideSubMenu();
    }

    public void ScreenShot_Option() {
        if (Command.KEY_ZIP_FLAG_SCREEN_SHUT) {
            Object[] options = {GetText("btnConf_Keptit"),
                GetText("btnConf_CloseitandSaveinDownloadandOpenLive")};
            int Opt = JOptionPane.showOptionDialog(this,
                    GetText("msgConfirmSS"),
                    GetText("titQuestion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]);
            switch (Opt) {
                case 0:
                    Blinking_Led(0, true, GetText("ledScreenshot_Open"));
                    break;
                case 1:
                    Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Sleep(250);
                    Connect(Command.STOP_ZIP_FLAG_SCREEN_SHUT);
                    Sleep(250);
                    Connect(Command.STOP_SCREEN_SHUT);
                    Sleep(500);
                    Blinking_Led(0, false, GetText("ledScreenshot_Close"));
                    Download();
                    Sleep(200);
                    break;
            }
        } else {
            ScreenShot();
        }
        HideSubMenu();
    }

    @SuppressWarnings("empty-statement")
    private void RecordWebCam() {
        if (!Command.KEY_SCREEN_WEBCAM) {
            int Opt = JOptionPane.showConfirmDialog(this,
                    String.format(GetText("msgDidYouwantRecord"), autoCompletionComboBox1.getSelectedItem().toString()),
                    GetText("titQuestion"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE); //default button title

            switch (Opt) {
                case 0:
                    Connect(Command.SCREEN_WEBCAM);
                    Blinking_Led(2, true, GetText("btnWebCam"));
                    break;
                case 1:
                    break;
            }
        } else {
            Object[] options = {GetText("btnWebCamOnlyStop"),
                GetText("btnWebCamStopandDownload"), GetText("btnWebCamCancel")};
            int Opt = JOptionPane.showOptionDialog(this,
                    GetText("msgWebcamRecord"),
                    GetText("titQuestion"),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title

            switch (Opt) {
                case 0:
                    Connect(Command.STOP_SCREEN_WEBCAM);
                    Blinking_Led(2, false, "");
                    break;
                case 1:
                    Connect(Command.STOP_SCREEN_WEBCAM);
                    Blinking_Led(2, false, "");
                    while (Command.KEY_SCREEN_WEBCAM);
                    Download();
                    break;
                case 2:
                    break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane(){
            ImageIcon icon = new ImageIcon("data/bg.jpg");
            Image image = icon.getImage();

            Image newimage = image.getScaledInstance(1660, 1000, Image.SCALE_SMOOTH);

            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(newimage, 0, 0,Color.BLACK, this);
                g.setColor(Color.BLACK);

            }
        };
        panTools = new javax.swing.JPanel();
        btnTool_Keybord = new javax.swing.JButton();
        btnTool_Clipbord = new javax.swing.JButton();
        btnTool_Application = new javax.swing.JButton();
        btnTool_Shell = new javax.swing.JButton();
        btnFileManger = new javax.swing.JButton();
        btnUSB = new javax.swing.JButton();
        PanNetwork = new javax.swing.JPanel();
        btnIPScan = new javax.swing.JButton();
        btnSniffer = new javax.swing.JButton();
        btnFootPrint = new javax.swing.JButton();
        btnInOfficeOrNot = new javax.swing.JButton();
        pnShut = new javax.swing.JPanel();
        btnShutdown = new javax.swing.JButton();
        btnRestart = new javax.swing.JButton();
        btnWakeUp = new javax.swing.JButton();
        pnAudio = new javax.swing.JPanel();
        btnAudio_Recoed = new javax.swing.JButton();
        btnAudio_Life = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPasswordField1 = new javax.swing.JPasswordField();
        panelMenu1 = new javax.swing.JPanel();
        btnScreen_Video = new javax.swing.JButton();
        btnScreen_ScreenShut = new javax.swing.JButton();
        btnScreenLife = new javax.swing.JButton();
        btnWebcam = new javax.swing.JButton();
        ptnMenuScreenshut = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelCamera = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        panMenu = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        autoCompletionComboBox1 = new com.jidesoft.swing.AutoCompletionComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        btnScreen = new javax.swing.JButton();
        btnAudio = new javax.swing.JButton();
        btnTool = new javax.swing.JButton();
        btnNetwork = new javax.swing.JButton();
        btnPhone = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        led1 = new eu.hansolo.steelseries.extras.Led();
        jPanel1 = new javax.swing.JPanel();
        led2 = new eu.hansolo.steelseries.extras.Led();
        led3 = new eu.hansolo.steelseries.extras.Led();
        led4 = new eu.hansolo.steelseries.extras.Led();
        led5 = new eu.hansolo.steelseries.extras.Led();
        led6 = new eu.hansolo.steelseries.extras.Led();
        led7 = new eu.hansolo.steelseries.extras.Led();
        btnDownload = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        txtUUID = new javax.swing.JLabel();
        txtPCName = new javax.swing.JLabel();
        txtLOGINTIME = new javax.swing.JLabel();
        txtSystemTime = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        desktopPane.setBorder(null);
        desktopPane.setBackground(Color.BLACK);
        desktopPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        desktopPane.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        desktopPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                desktopPaneMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                desktopPaneMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                desktopPaneMouseClicked(evt);
            }
        });
        desktopPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                desktopPaneMouseMoved(evt);
            }
        });

        panTools.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnTool_Keybord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/KEYBOARD.png"))); // NOI18N
        btnTool_Keybord.setText(blackhat.BlackHat.local.getString("btnKeybord"));
        btnTool_Keybord.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTool_Keybord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTool_KeybordActionPerformed(evt);
            }
        });

        btnTool_Clipbord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clipboard.png"))); // NOI18N
        btnTool_Clipbord.setText(blackhat.BlackHat.local.getString("btnClipbord"));
        btnTool_Clipbord.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTool_Clipbord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTool_ClipbordActionPerformed(evt);
            }
        });

        btnTool_Application.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/application.png"))); // NOI18N
        btnTool_Application.setText(blackhat.BlackHat.local.getString("btnTaskManger"));
        btnTool_Application.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTool_Application.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTool_ApplicationActionPerformed(evt);
            }
        });

        btnTool_Shell.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shell.png"))); // NOI18N
        btnTool_Shell.setText(blackhat.BlackHat.local.getString("btnShell"));
        btnTool_Shell.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnTool_Shell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTool_ShellActionPerformed(evt);
            }
        });

        btnFileManger.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/filemange.png"))); // NOI18N
        btnFileManger.setText(blackhat.BlackHat.local.getString("btnFileManger"));
        btnFileManger.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnFileManger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileMangerActionPerformed(evt);
            }
        });

        btnUSB.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usb.png"))); // NOI18N
        btnUSB.setText(blackhat.BlackHat.local.getString("btnUSB"));
        btnUSB.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnUSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUSBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panToolsLayout = new javax.swing.GroupLayout(panTools);
        panTools.setLayout(panToolsLayout);
        panToolsLayout.setHorizontalGroup(
            panToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTool_Keybord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTool_Clipbord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTool_Application, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTool_Shell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFileManger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUSB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panToolsLayout.setVerticalGroup(
            panToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTool_Keybord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTool_Clipbord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTool_Application, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTool_Shell, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFileManger, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUSB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        desktopPane.add(panTools);
        panTools.setBounds(0, 320, 230, 360);

        PanNetwork.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnIPScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/IP_address_icon.png"))); // NOI18N
        btnIPScan.setText(blackhat.BlackHat.local.getString("btnIPScan"));
        btnIPScan.setDisabledIcon(null);
        btnIPScan.setDisabledSelectedIcon(null);
        btnIPScan.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnIPScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIPScanActionPerformed(evt);
            }
        });

        btnSniffer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/sniffer.png"))); // NOI18N
        btnSniffer.setText(blackhat.BlackHat.local.getString("btnSniffer"));
        btnSniffer.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnSniffer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSnifferActionPerformed(evt);
            }
        });

        btnFootPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/step-128.png"))); // NOI18N
        btnFootPrint.setText(blackhat.BlackHat.local.getString("btnFootPrint"));
        btnFootPrint.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnFootPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFootPrintActionPerformed(evt);
            }
        });

        btnInOfficeOrNot.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/step-128.png"))); // NOI18N
        btnInOfficeOrNot.setText(blackhat.BlackHat.local.getString("btnInOfficeOrNot"));
        btnInOfficeOrNot.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnInOfficeOrNot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInOfficeOrNotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanNetworkLayout = new javax.swing.GroupLayout(PanNetwork);
        PanNetwork.setLayout(PanNetworkLayout);
        PanNetworkLayout.setHorizontalGroup(
            PanNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanNetworkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnIPScan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSniffer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFootPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInOfficeOrNot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        PanNetworkLayout.setVerticalGroup(
            PanNetworkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanNetworkLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIPScan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSniffer, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFootPrint, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInOfficeOrNot, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        desktopPane.add(PanNetwork);
        PanNetwork.setBounds(0, 360, 230, 250);

        pnShut.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnShutdown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shutdown.png"))); // NOI18N
        btnShutdown.setText(blackhat.BlackHat.local.getString("btnshutdown"));
        btnShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShutdownActionPerformed(evt);
            }
        });

        btnRestart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Restart.png"))); // NOI18N
        btnRestart.setText(blackhat.BlackHat.local.getString("btnRestart"));
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });

        btnWakeUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Restart.png"))); // NOI18N
        btnWakeUp.setText(blackhat.BlackHat.local.getString("btnWackUp"));
        btnWakeUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWakeUpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnShutLayout = new javax.swing.GroupLayout(pnShut);
        pnShut.setLayout(pnShutLayout);
        pnShutLayout.setHorizontalGroup(
            pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShutdown, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRestart, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnWakeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnShutLayout.setVerticalGroup(
            pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShutLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShutdown, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestart, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWakeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        desktopPane.add(pnShut);
        pnShut.setBounds(0, 30, 500, 160);

        pnAudio.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnAudio_Recoed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Audio_Life.png"))); // NOI18N
        btnAudio_Recoed.setText(blackhat.BlackHat.local.getString("btnAudioLife"));
        btnAudio_Recoed.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAudio_Recoed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAudio_RecoedActionPerformed(evt);
            }
        });

        btnAudio_Life.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Audio_Record.png"))); // NOI18N
        btnAudio_Life.setText(blackhat.BlackHat.local.getString("btnAudioRec"));
        btnAudio_Life.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnAudio_Life.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAudio_LifeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnAudioLayout = new javax.swing.GroupLayout(pnAudio);
        pnAudio.setLayout(pnAudioLayout);
        pnAudioLayout.setHorizontalGroup(
            pnAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAudioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAudio_Life, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAudio_Recoed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnAudioLayout.setVerticalGroup(
            pnAudioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAudioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAudio_Recoed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAudio_Life, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        desktopPane.add(pnAudio);
        pnAudio.setBounds(0, 230, 230, 130);

        jInternalFrame1.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/blackhatAppIcon.png"))); // NOI18N
        try {
            jInternalFrame1.setSelected(true);
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        jInternalFrame1.setVisible(false);

        jPasswordField1.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jPasswordField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPasswordField1.setToolTipText("");
        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPasswordField1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );

        desktopPane.add(jInternalFrame1);
        jInternalFrame1.setBounds(430, 580, 660, 180);

        panelMenu1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnScreen_Video.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/record.png"))); // NOI18N
        btnScreen_Video.setText(blackhat.BlackHat.local.getString("btnGetScreen"));
        btnScreen_Video.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnScreen_Video.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnScreen_Video.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScreen_VideoActionPerformed(evt);
            }
        });

        btnScreen_ScreenShut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/screenshot.png"))); // NOI18N
        btnScreen_ScreenShut.setText(blackhat.BlackHat.local.getString("btnprtsc"));
        btnScreen_ScreenShut.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnScreen_ScreenShut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScreen_ScreenShutActionPerformed(evt);
            }
        });

        btnScreenLife.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monitor.png"))); // NOI18N
        btnScreenLife.setText(blackhat.BlackHat.local.getString("btnScreenLife"));
        btnScreenLife.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnScreenLife.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScreenLifeActionPerformed(evt);
            }
        });

        btnWebcam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/webcam.png"))); // NOI18N
        btnWebcam.setText(blackhat.BlackHat.local.getString("btnWebCam"));
        btnWebcam.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnWebcam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWebcamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMenu1Layout = new javax.swing.GroupLayout(panelMenu1);
        panelMenu1.setLayout(panelMenu1Layout);
        panelMenu1Layout.setHorizontalGroup(
            panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnScreen_Video, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(btnScreenLife, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnWebcam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnScreen_ScreenShut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelMenu1Layout.setVerticalGroup(
            panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnScreen_Video, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScreen_ScreenShut, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScreenLife, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnWebcam, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        panelMenu1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnScreenLife, btnScreen_ScreenShut, btnScreen_Video, btnWebcam});

        desktopPane.add(panelMenu1);
        panelMenu1.setBounds(0, 190, 230, 290);

        ptnMenuScreenshut.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton1.setText(blackhat.BlackHat.local.getString("btnLive"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(blackhat.BlackHat.local.getString("btnBackground"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ptnMenuScreenshutLayout = new javax.swing.GroupLayout(ptnMenuScreenshut);
        ptnMenuScreenshut.setLayout(ptnMenuScreenshutLayout);
        ptnMenuScreenshutLayout.setHorizontalGroup(
            ptnMenuScreenshutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ptnMenuScreenshutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ptnMenuScreenshutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        ptnMenuScreenshutLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        ptnMenuScreenshutLayout.setVerticalGroup(
            ptnMenuScreenshutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ptnMenuScreenshutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ptnMenuScreenshutLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, jButton2});

        desktopPane.add(ptnMenuScreenshut);
        ptnMenuScreenshut.setBounds(230, 250, 300, 130);

        panelCamera.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton4.setText(GetText("btnRecordwebCam"));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText(GetText("btnLifeWebcam"));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCameraLayout = new javax.swing.GroupLayout(panelCamera);
        panelCamera.setLayout(panelCameraLayout);
        panelCameraLayout.setHorizontalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCameraLayout.setVerticalGroup(
            panelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCameraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        desktopPane.add(panelCamera);
        panelCamera.setBounds(230, 400, 300, 110);

        panMenu.setBackground(new java.awt.Color(0, 0, 0));
        panMenu.setBorder(null);
        panMenu.setForeground(new java.awt.Color(0, 0, 0));
        panMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panMenuMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(blackhat.BlackHat.local.getString("lblComputer")));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/repeat.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button-power.png"))); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/disconnected-128.png"))); // NOI18N
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText(GetText("btnOpenAllComputer"));
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(autoCompletionComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addGap(0, 0, 0))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel7});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(autoCompletionComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addGap(0, 0, 0))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel7});

        btnScreen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/testmon.png"))); // NOI18N
        btnScreen.setText(blackhat.BlackHat.local.getString("btnScreenLife"));
        btnScreen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnScreen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScreenActionPerformed(evt);
            }
        });

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/AudioMenu.png"))); // NOI18N
        btnAudio.setText(blackhat.BlackHat.local.getString("btnAudio"));
        btnAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAudioActionPerformed(evt);
            }
        });

        btnTool.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/monitor.png"))); // NOI18N
        btnTool.setText(blackhat.BlackHat.local.getString("tapSpyTool"));
        btnTool.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToolActionPerformed(evt);
            }
        });

        btnNetwork.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/network.png"))); // NOI18N
        btnNetwork.setText("Network Tool");
        btnNetwork.setDisabledIcon(null);
        btnNetwork.setDisabledSelectedIcon(null);
        btnNetwork.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNetwork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNetworkActionPerformed(evt);
            }
        });

        btnPhone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/phone-6.png"))); // NOI18N
        btnPhone.setText(GetText("btnPhone"));
        btnPhone.setDisabledIcon(null);
        btnPhone.setDisabledSelectedIcon(null);
        btnPhone.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhoneActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/shutdown_shut_close_power_off_switch-off-128.png"))); // NOI18N
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/setting.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        led1.setBackground(new java.awt.Color(0, 0, 0));
        led1.setToolTipText("");
        led1.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        led1.setFocusable(false);
        led1.setRequestFocusEnabled(false);
        led1.setVerifyInputWhenFocusTarget(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        led2.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        led3.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        led4.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        led5.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        led6.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        led7.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(led2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(led3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(led4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(led5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(led6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(led7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {led2, led3, led4, led5, led6, led7});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(led7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(led6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(led5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(led4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(led3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(led2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {led2, led3, led4, led5, led6, led7});

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(led1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel5, jLabel6});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(led1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, jLabel6});

        btnDownload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/downloads.png"))); // NOI18N
        btnDownload.setText("Download");
        btnDownload.setDisabledIcon(null);
        btnDownload.setDisabledSelectedIcon(null);
        btnDownload.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(254, 254, 254), 1, true));

        txtUUID.setText("UUID");

        txtPCName.setText("PC Name");

        txtLOGINTIME.setText("Login Time");

        txtSystemTime.setText("System Name");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUUID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPCName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtLOGINTIME, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSystemTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtUUID)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPCName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLOGINTIME)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSystemTime)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/banarMain.png"))); // NOI18N
        jLabel2.setBorder(null);
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panMenuLayout = new javax.swing.GroupLayout(panMenu);
        panMenu.setLayout(panMenuLayout);
        panMenuLayout.setHorizontalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDownload, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPhone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNetwork, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTool, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnScreen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAudio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        panMenuLayout.setVerticalGroup(
            panMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panMenuLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAudio, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTool, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNetwork, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        panMenuLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnAudio, btnNetwork, btnPhone, btnScreen, btnTool});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1103, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
            .addComponent(panMenu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jXBusyLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXBusyLabel1MouseClicked

    }//GEN-LAST:event_jXBusyLabel1MouseClicked

    private void btnShutdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShutdownActionPerformed
        Connect(Command.SHUTDOWN);
    }//GEN-LAST:event_btnShutdownActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        Connect(Command.RESTART);
    }//GEN-LAST:event_btnRestartActionPerformed

    @SuppressWarnings("empty-statement")
    private void btnScreen_VideoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScreen_VideoActionPerformed
        if (Command.KEY_SCREEN_RECORD) {
            switch (JOptionPane.showConfirmDialog(this, GetText("msgScreenRecordisWork"), GetText("titQuestion"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                case 0:
                    Connect(Command.STOP_SCREEN_RECORD);
                    Command.KEY_STOP_SCREEN_RECORD = true;
                    Command.KEY_SCREEN_RECORD = false;
                    Color_Led(1, LedColor.RED_LED);
                    Blinking_Led(1, false, GetText("ledScreenRecord_Close"));
                    while (!VideoPathFlag);
                    Download();
                    break;
                case 1:
                    Command.KEY_STOP_SCREEN_RECORD = false;
                    Command.KEY_SCREEN_RECORD = true;
                    break;
            }
        } else {
            String Message = String.format(GetText("msgVideoScreen"), autoCompletionComboBox1.getSelectedItem().toString());
            switch (JOptionPane.showConfirmDialog(this, Message, GetText("titQuestion"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                case 0:
                    Connect(Command.SCREEN_RECORD);
                    Blinking_Led(1, true, GetText("ledScreenRecord_Open"));
                    Command.KEY_STOP_SCREEN_RECORD = false;
                    Command.KEY_SCREEN_RECORD = true;
                    break;
                case 1:
                    break;
            }
        }
        HideSubMenu();
    }//GEN-LAST:event_btnScreen_VideoActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        HideSubMenu();
        if (!pnShut.isVisible()) {
            pnShut.setVisible(true);
        } else {
            pnShut.setVisible(false);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btnScreenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScreenActionPerformed
        HideSubMenu();
        if (!panelMenu1.isVisible()) {
            panelMenu1.setVisible(true);
        } else {
            panelMenu1.setVisible(false);
        }
    }//GEN-LAST:event_btnScreenActionPerformed

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        EndApplication();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        EndApplication();
    }//GEN-LAST:event_formWindowClosing

    private void desktopPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desktopPaneMouseClicked
        HideSubMenu();
    }//GEN-LAST:event_desktopPaneMouseClicked

    private void btnAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudioActionPerformed
        HideSubMenu();
        if (!pnAudio.isVisible()) {
            pnAudio.setVisible(true);
        } else {
            pnAudio.setVisible(false);
        }
    }//GEN-LAST:event_btnAudioActionPerformed

    private void btnToolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToolActionPerformed
        HideSubMenu();
        if (!panTools.isVisible()) {
            panTools.setVisible(true);
        } else {
            panTools.setVisible(false);
        }
    }//GEN-LAST:event_btnToolActionPerformed

    private void panMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panMenuMouseClicked
        HideSubMenu();
    }//GEN-LAST:event_panMenuMouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        if (SettingForm == null) {
            SettingForm = new BlackHatSettingWindow(this);
            this.Insert(SettingForm, GetText("frmSetting"), ApplicationIcon);
        } else {
            SettingForm.dispose();
            SettingForm = null;
            jLabel6MouseClicked(evt);
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void btnNetworkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNetworkActionPerformed
        HideSubMenu();
        if (!PanNetwork.isVisible()) {
            PanNetwork.setVisible(true);
        } else {
            PanNetwork.setVisible(false);
        }
    }//GEN-LAST:event_btnNetworkActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        jPanel4.setVisible(false);
        ConnectionLocal();
    }//GEN-LAST:event_jLabel1MouseClicked

    public void CloseConnectionAndScanAgain() {
        try {
            init_Menu();
            Buttons_Init();
            led1.setLedColor(LedColor.RED_LED);
            led1.setLedOn(false);
            init_Leds();
            HideSubMenu();
            BasyBox(false);
            jButton3.setVisible(true);
            BufferedImage image = ImageIO.read(SecuritySupport.getResourceAsStream(DISCONNECTED));
            jLabel7.setIcon(new ImageIcon(image));
            CLIENT_CONNECT = false;
            ButtonEnable(false);
            jLabel1.setEnabled(true);
            Menu.getComponent(3).setEnabled(false);
            Menu.getComponent(4).setEnabled(false);
            autoCompletionComboBox1.removeAll();
            jPanel4.setVisible(false);
            ConnectionLocal();

        } catch (IOException ex) {
        }
    }
    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        try {
            if (CLIENT_CONNECT) {
                Connect(Command.CLOSE_SESSION);
                jButton3.setVisible(true);
                BufferedImage image = ImageIO.read(SecuritySupport.getResourceAsStream(DISCONNECTED));
                jLabel7.setIcon(new ImageIcon(image));
                CLIENT_CONNECT = false;
                ButtonEnable(false);
                jLabel1.setEnabled(true);
                Menu.getComponent(3).setEnabled(false);
                Menu.getComponent(4).setEnabled(false);
            } else {
                Connect(Command.INFORMATION);
                jButton3.setVisible(false);
                BufferedImage image = ImageIO.read(SecuritySupport.getResourceAsStream(CONNECTED));
                jLabel7.setIcon(new ImageIcon(image));
                CLIENT_CONNECT = true;
                jLabel1.setEnabled(false);
                Menu.getComponent(3).setEnabled(true);
                Menu.getComponent(4).setEnabled(true);
            }
            ScanerTasks();
            jLabel7.updateUI();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        Logout();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased
        if (jPasswordField1.getText().equals(blackhat.BlackHat.Args.get("DEFAULT_PASSWORD"))) {
            Login();
        }
    }//GEN-LAST:event_jPasswordField1KeyReleased

    private void desktopPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desktopPaneMouseMoved
        doAction();
    }//GEN-LAST:event_desktopPaneMouseMoved

    private void btnScreen_ScreenShutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScreen_ScreenShutActionPerformed
        ptnMenuScreenshut.setVisible(true);
    }//GEN-LAST:event_btnScreen_ScreenShutActionPerformed

    private void btnScreenLifeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScreenLifeActionPerformed
        Screen_Video_Monitor();
    }//GEN-LAST:event_btnScreenLifeActionPerformed

    private void btnWebcamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWebcamActionPerformed
        panelCamera.setVisible(true);
    }//GEN-LAST:event_btnWebcamActionPerformed

    private void btnAudio_RecoedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudio_RecoedActionPerformed
        AudioLife();
    }//GEN-LAST:event_btnAudio_RecoedActionPerformed

    private void btnAudio_LifeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudio_LifeActionPerformed
        Connect(Command.AUDIO_RECORD);
    }//GEN-LAST:event_btnAudio_LifeActionPerformed

    private void btnTool_KeybordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTool_KeybordActionPerformed
        if (Keybord == null) {
            try {
                Keybord = new frmKeybord(this);
                this.Insert(Keybord, GetText("btnKeybord"), "img/KEYBOARD.png");
            } catch (IOException ex) {
            }
        } else {
            Connect(Command.STOP_KEYBORD);
            Keybord.dispose();
            Keybord = null;
            btnTool_KeybordActionPerformed(evt);
        }
    }//GEN-LAST:event_btnTool_KeybordActionPerformed

    private void btnTool_ClipbordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTool_ClipbordActionPerformed
        if (fClipbord == null) {
            fClipbord = new frmClipbord(this);
            this.Insert(fClipbord, GetText("btnClipbord"), "img/clipboard.png");
        } else {
            Connect(Command.STOP_CLIPBORD);
            fClipbord.dispose();
            fClipbord = null;
            btnTool_ClipbordActionPerformed(evt);
        }
    }//GEN-LAST:event_btnTool_ClipbordActionPerformed

    private void btnTool_ApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTool_ApplicationActionPerformed
        if (TaskManger == null) {
            TaskManger = new frmApplication(this);
            this.Insert(TaskManger, GetText("btnTaskManger"), "img/application.png");
        } else {
            Connect(Command.STOP_APPLICATION);
            TaskManger.dispose();
            TaskManger = null;
            btnTool_ApplicationActionPerformed(evt);
        }
    }//GEN-LAST:event_btnTool_ApplicationActionPerformed

    private void btnTool_ShellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTool_ShellActionPerformed
        if (ShellPowerFrm == null) {
            ShellPowerFrm = new frmShell(this);
            this.Insert(ShellPowerFrm, GetText("btnShell"), "img/shell.png");
        } else {
            Connect(Command.STOP_SHELL);
            ShellPowerFrm.dispose();
            ShellPowerFrm = null;
            btnTool_ShellActionPerformed(evt);
        }
    }//GEN-LAST:event_btnTool_ShellActionPerformed

    private void btnFileMangerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileMangerActionPerformed
        if (FileMangerBH == null) {
            frmFileManger FileMang = new frmFileManger(this);
            this.Insert(FileMang, "FileMangert", null);
        } else {
            Connect(Command.STOP_FILEMANGER);
            FileMangerBH.dispose();
            FileMangerBH = null;
            btnFileMangerActionPerformed(evt);
        }
    }//GEN-LAST:event_btnFileMangerActionPerformed

    private void btnUSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUSBActionPerformed
        Connect(Command.USB);
    }//GEN-LAST:event_btnUSBActionPerformed

    private void btnIPScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIPScanActionPerformed
        Connect(Command.IP_SCAN);
    }//GEN-LAST:event_btnIPScanActionPerformed

    private void btnSnifferActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSnifferActionPerformed
        Connect(Command.SNIFFER);
    }//GEN-LAST:event_btnSnifferActionPerformed

    private void btnFootPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFootPrintActionPerformed
        Connect(Command.FOOTPRINT);
    }//GEN-LAST:event_btnFootPrintActionPerformed

    private void btnInOfficeOrNotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInOfficeOrNotActionPerformed
        Connect(Command.INOFFICEORNOT);
    }//GEN-LAST:event_btnInOfficeOrNotActionPerformed

    private void btnPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhoneActionPerformed
//        Connect(Command.PHONE);
        if (PhoneFram == null) {
            PhoneFram = new frmPhone();
            this.Insert(PhoneFram, GetText("btnPhone"), "img/phone-6.png");
        } else {
            JOptionPane.showMessageDialog(this, GetText(""), GetText(""), JOptionPane.ERROR_MESSAGE);

        }
    }//GEN-LAST:event_btnPhoneActionPerformed

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        Connect(Command.DOWNLOAD);
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void btnWakeUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWakeUpActionPerformed
        if (AllComputers == null) {
            AllComputers = new frmAllPCs(MainGUI.this);
            Insert(AllComputers, GetText("menuAllComputers"), null);
        } else {
            AllComputers.dispose();
            AllComputers = null;
            btnWakeUpActionPerformed(evt);
        }
    }//GEN-LAST:event_btnWakeUpActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ScreenShot_Option();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ScreenShot_Background();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void desktopPaneMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desktopPaneMousePressed
        if (evt.isPopupTrigger()) {
            Menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_desktopPaneMousePressed

    private void desktopPaneMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desktopPaneMouseReleased
        if (evt.isPopupTrigger()) {
            Menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_desktopPaneMouseReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (Command.KEY_SCREEN_WEBCAM) {
            JOptionPane.showMessageDialog(this, GetText("infoMsgRecordCam"), GetText("tabInformation"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (WebCamOnline == null) {
            if (Command.KEY_SCREEN_WEBCAM_LIFE) {
                Connect(Command.STOP_SCREEN_WEBCAM_LIFE);
                Sleep(1000);
            }
            Connect(Command.SCREEN_WEBCAM_LIFE);
            Sleep(2000);
            WebCamOnline = new frmLifeWebcam(this);
            this.Insert(WebCamOnline, GetText("btnWebCam"), "img/webcam.png");
        } else {
            Connect(Command.STOP_SCREEN_WEBCAM_LIFE);
            WebCamOnline.dispose();
            WebCamOnline = null;
            btnWebcamActionPerformed(evt);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        for (JInternalFrame Frm : desktopPane.getAllFrames()) {
            if (Frm == this.WebCamOnline) {
                JOptionPane.showMessageDialog(this, GetText("infoMsgCameraBusy"), GetText("tabInformation"), JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (Command.KEY_SCREEN_WEBCAM_LIFE) {
            JOptionPane.showMessageDialog(this, GetText("infoMsgCameraBusy"), GetText("tabInformation"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        RecordWebCam();
        HideSubMenu();
    }//GEN-LAST:event_jButton4ActionPerformed

    public void EndApplication() {
        if (CLIENT_CONNECT) {
            JOptionPane.showMessageDialog(this, "Please Close Connection First", "BlackHat", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        ScanComputer = null;
        System.exit(0);
    }

    public void Logout() {
        this.panMenu.setVisible(false);
        this.jLabel2.setVisible(false);

        desktopPane.setSize(this.getSize());
        int x = (int) ((desktopPane.getWidth() - jInternalFrame1.getWidth()) / 2);
        int y = (int) ((desktopPane.getHeight() - jInternalFrame1.getHeight()) / 2);

        jInternalFrame1.setLocation(x, y);

        JInternalFrame[] Frms = desktopPane.getAllFrames();
        for (JInternalFrame Frm : Frms) {
            Frm.setVisible(false);
        }

        jPasswordField1.setText("");
        jInternalFrame1.setVisible(true);
        desktopPane.setSelectedFrame(jInternalFrame1);
        jPasswordField1.setFocusable(true);

    }

    public void Login() {
        JInternalFrame[] Frms = desktopPane.getAllFrames();
        for (JInternalFrame Frm : Frms) {
            Frm.setVisible(true);
        }
        jInternalFrame1.setVisible(false);
        jPasswordField1.setText("");
        this.panMenu.setVisible(true);
        this.jLabel2.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanNetwork;
    private static com.jidesoft.swing.AutoCompletionComboBox autoCompletionComboBox1;
    private javax.swing.JButton btnAudio;
    private javax.swing.JButton btnAudio_Life;
    private javax.swing.JButton btnAudio_Recoed;
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnFileManger;
    private javax.swing.JButton btnFootPrint;
    private javax.swing.JButton btnIPScan;
    private javax.swing.JButton btnInOfficeOrNot;
    private javax.swing.JButton btnNetwork;
    private javax.swing.JButton btnPhone;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnScreen;
    private javax.swing.JButton btnScreenLife;
    private javax.swing.JButton btnScreen_ScreenShut;
    private javax.swing.JButton btnScreen_Video;
    private javax.swing.JButton btnShutdown;
    private javax.swing.JButton btnSniffer;
    private javax.swing.JButton btnTool;
    private javax.swing.JButton btnTool_Application;
    private javax.swing.JButton btnTool_Clipbord;
    private javax.swing.JButton btnTool_Keybord;
    private javax.swing.JButton btnTool_Shell;
    private javax.swing.JButton btnUSB;
    private javax.swing.JButton btnWakeUp;
    private javax.swing.JButton btnWebcam;
    public javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JInternalFrame jInternalFrame1;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private static javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordField1;
    private static eu.hansolo.steelseries.extras.Led led1;
    private eu.hansolo.steelseries.extras.Led led2;
    private eu.hansolo.steelseries.extras.Led led3;
    private eu.hansolo.steelseries.extras.Led led4;
    private eu.hansolo.steelseries.extras.Led led5;
    private eu.hansolo.steelseries.extras.Led led6;
    private eu.hansolo.steelseries.extras.Led led7;
    private javax.swing.JPanel panMenu;
    private javax.swing.JPanel panTools;
    private javax.swing.JPanel panelCamera;
    private javax.swing.JPanel panelMenu1;
    private javax.swing.JPanel pnAudio;
    private javax.swing.JPanel pnShut;
    private javax.swing.JPanel ptnMenuScreenshut;
    private javax.swing.JLabel txtLOGINTIME;
    private javax.swing.JLabel txtPCName;
    private javax.swing.JLabel txtSystemTime;
    private javax.swing.JLabel txtUUID;
    // End of variables declaration//GEN-END:variables

}
