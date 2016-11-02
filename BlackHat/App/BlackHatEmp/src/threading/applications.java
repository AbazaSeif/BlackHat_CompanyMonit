/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import org.jutils.jprocesses.model.WindowsPriority;

/**
 *
 * @author abaza
 */
public class applications implements ActionListener {

    String Host = null, Result = "";
    int port = 0;
    private static ServerSocket servSocket;
    OutputStreamWriter osw = null;
    OutputStream os = null;
    BufferedWriter bw = null;
    int cTosPortNumber = 1777;

    public applications(String Host, int Port) {
        try {
            this.Host = Host;
            this.port = Port;
            servSocket = new ServerSocket(cTosPortNumber);
            servSocket.setReuseAddress(true);
//            servSocket.setSoTimeout(1000);
        } catch (IOException ex) {
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        String str;
        try {
            try (Socket fromClientSocket = servSocket.accept(); PrintWriter pw = new PrintWriter(fromClientSocket.getOutputStream(), true)) {

                BufferedReader br = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));

                while ((str = br.readLine().trim()) != null) {
                    if (str.contains("T:KILL=")) {
                        String[] Killing = str.split("=");
                        pw.println(":D:" + Kill(Integer.parseInt(Killing[1])).trim());
                        break;
                    }
                    if (str.contains("T:PCC=")) {
                        String[] CPP = str.split("=");
                        String[] Proccing = CPP[1].split("#");
                        pw.println(":D:" + change_process_priority(Integer.parseInt(Proccing[0]), Integer.parseInt(Proccing[1])).trim());
                        break;
                    }
//                    if (str.contains("T:CPU=")) {
//                        String[] CPU = str.split("=");
//                        String[] Proccess = CPU[1].split("#");
//                        pw.println(":D:" + ChangeCPU(Integer.parseInt(Proccess[0]), Proccess[1]));
//                    }
                    if (str.equals("Get")) {
                        pw.println(GetProccecors());
                        break;
                    }
                }
            }
        } catch (IOException ex) {
        }
    }
    

    public String GetProccecors() {
        List<ProcessInfo> processesList = JProcesses.getProcessList();
        String ProccingList = "";
        for (final ProcessInfo processInfo : processesList) {
            ProccingList += "PID=" + processInfo.getPid() + ",";
            ProccingList += "Name=" + processInfo.getName() + ",";
            ProccingList += "Time=" + processInfo.getTime() + ",";
            ProccingList += "Virtual Memory=" + processInfo.getVirtualMemory() + ",";
            ProccingList += "Physical Memory=" + processInfo.getPhysicalMemory() + ",";
            ProccingList += "CPU=" + processInfo.getCpuUsage() + ",";
            ProccingList += "Start Time=" + processInfo.getStartTime() + ",";
            ProccingList += "Priority=" + processInfo.getPriority() + "|";
        }
        return ProccingList;
    }

    private String Kill(int PID) {
        JProcesses.killProcess(PID);
        return GetProccecors();
    }

    private String change_process_priority(int PID, int Level) {
        if (System.getProperty("os.name").contains("win") || System.getProperty("os.name").contains("Win") || System.getProperty("os.name").contains("WIN")) {
            switch (Level) {
                case 1:
                    Level = WindowsPriority.ABOVE_NORMAL;
                    break;
                case 2:
                    Level = WindowsPriority.BELOW_NORMAL;
                    break;
                case 3:
                    Level = WindowsPriority.HIGH;
                    break;
                case 4:
                    Level = WindowsPriority.IDLE;
                    break;
                case 5:
                    Level = WindowsPriority.NORMAL;
                    break;
                case 6:
                    Level = WindowsPriority.REAL_TIME;
                    break;
            }
            JProcesses.changePriority(PID, Level).isSuccess();
        } else if (System.getProperty("os.name").contains("mac") || System.getProperty("os.name").contains("Mac") || System.getProperty("os.name").contains("MAC")) {
            JProcesses.changePriority(PID, Level).isSuccess();
        } else if (System.getProperty("os.name").contains("linux") || System.getProperty("os.name").contains("Linux") || System.getProperty("os.name").contains("LINUX")) {
            JProcesses.changePriority(PID, Level).isSuccess();
        }

        return GetProccecors();
    }

    private String ChangeCPU(int PID, String Level) {
        JProcesses.getProcess(PID).setCpuUsage(Level);
        return GetProccecors();
    }

}
