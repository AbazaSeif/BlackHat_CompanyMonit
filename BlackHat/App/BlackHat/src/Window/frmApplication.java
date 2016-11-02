/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Connection.Command;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import java.util.regex.Pattern;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author abaza
 */
public class frmApplication extends javax.swing.JInternalFrame {

    String Result = "";
    OutputStreamWriter osw = null;
    OutputStream os = null;
    BufferedWriter bw = null;
    private static MainGUI MainClass;
    DefaultTableModel Model = null;
    private String Send = "";
    private ActionListener ResevingData = null;
    private Timer ResTimer = null;
    JPopupMenu Menu = new JPopupMenu("Application");

    /**
     * Creates new form frmApplication
     *
     * @param Class
     */
    public frmApplication(MainGUI Class) {
        frmApplication.MainClass = Class;
        initComponents();
        MenuCreator();
        Model = new DefaultTableModel(new String[]{"PID", "Name", "Process Time", "Start Time", "Virtual Memory", "Physical Memory", "CPU", "Priority"}, 0);
        jTable1.setModel(Model);
        jTable1.setAutoCreateColumnsFromModel(true);
        jTable1.setRowSelectionAllowed(true);

        //Start
        frmApplication.MainClass.Connect(Command.APPLICATION);
        ResevingData = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    Socket socket1;
                    int portNumber = 1777;
                    String str = "";

                    socket1 = new Socket(frmApplication.MainClass.GetCurrentIP(), portNumber);
                    socket1.setTcpNoDelay(true);
//                    socket1.setSoTimeout(1000);
                    PrintWriter pw;
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket1.getInputStream()))) {
                        pw = new PrintWriter(socket1.getOutputStream(), true);
                        LedProccing(true);
                        if (!Send.isEmpty()) {
                            pw.println("T:" + Send.trim());
                        } else {
                            pw.println("Get");
                        }
                        pw.flush();
                        LedProccing(false);
                        while ((str = br.readLine()) != null) {
                            LedProccing(true);
                            if ((str.startsWith(":D:")) || (str.contains(":D:"))) {
                                Send = "";
                                jTextField1.setText("");
                                str = str.substring(3, str.length());
                            }
                            Implimntation(str);
                        }

                        pw.close();
                        socket1.close();
                    }
                } catch (IOException ex) {
                }
            }
        };

        ResTimer = new Timer(3000, ResevingData);
    }

    private void MenuCreator() {
        JMenuItem item1 = new JMenuItem(frmApplication.MainClass.GetText("procBtnKill"));
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object PID = jTable1.getValueAt(jTable1.getSelectedRow(), 0);
                Model.removeRow(jTable1.getSelectedRow());
                Send = "KILL=" + Integer.parseInt(PID.toString().trim());
            }
        });

        Menu.add(item1);

        JMenuItem item2 = new JMenuItem(frmApplication.MainClass.GetText("procBtnchange_process_priority"));
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object PID = jTable1.getValueAt(jTable1.getSelectedRow(), 0);
                boolean check = true;
                int priotity = 0;
                if (jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString().equals("-")) {
                    priotity = 0;
                } else {
                    priotity = Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString());
                }

                do {
                    check = true;
                    try {
                        priotity = Integer.parseInt(JOptionPane.showInputDialog(null,
                                String.format(frmApplication.MainClass.GetText("proMessageAskpriority"), PID.toString()),
                                frmApplication.MainClass.GetText("proMessageTitelPiriority"), JOptionPane.INFORMATION_MESSAGE, null, null, priotity).toString());
                    } catch (NumberFormatException nfe) {
                        nfe.printStackTrace();
                        check = false;
                    }
                } while (!check);

                Send = "PCC=" + Integer.parseInt(PID.toString().trim()) + "#" + priotity;
            }
        });

        Menu.add(item2);
//
//        JMenuItem item3 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetCpuUsage"));
//        item3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Object PID = jTable1.getValueAt(jTable1.getSelectedRow(), 0);
//                String Level = jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString();
//
//                Level = JOptionPane.showInputDialog(null,
//                        String.format(frmApplication.MainClass.GetText("proMessageAskCPU"), PID.toString()),
//                        frmApplication.MainClass.GetText("proMessageTitelCPU"), JOptionPane.INFORMATION_MESSAGE, null, null, Level).toString().trim();
//
//                Send = "CPU=" + Integer.parseInt(PID.toString().trim()) + "#" + Level;
//            }
//        });
//
//        Menu.add(item3);
//
//        JMenuItem item4 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetPhysicalMemory"));
//        item4.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item4);
//
//        JMenuItem item5 = new JMenuItem(frmApplication.MainClass.GetText("btnBtnsetPid"));
//        item5.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item5);
//
//        JMenuItem item6 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetStartTime"));
//        item6.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item6);
//
//        JMenuItem item7 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetTime"));
//        item7.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item7);
//
//        JMenuItem item8 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetUser"));
//        item8.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item8);
//
//        JMenuItem item9 = new JMenuItem(frmApplication.MainClass.GetText("proBtnsetVirtualMemory"));
//        item9.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//            }
//        });
//
//        Menu.add(item9);
    }

    public void Implimntation(String ResultReseved) {
        String[] Rows = ResultReseved.split(Pattern.quote("|"));
        jTable1.removeAll();
        Model.setNumRows(Rows.length);
        for (int Row = 0; Row <= Rows.length - 1; Row++) {
            String[] Fileds = Rows[Row].split(",");
            for (int Colomens = 0; Colomens <= Fileds.length - 1; Colomens++) {
                String[] Cols = Fileds[Colomens].split("=");
                if ((!Cols[1].isEmpty()) || (Cols[1] != null)) {
                    Model.setValueAt(Cols[1], Row, Colomens);
                }
            }
        }

        if (jTextField1.getText().isEmpty()) {
            jTable1.setModel(Model);
        } else {
            searchTableContents(jTextField1.getText());
        }
        LedProccing(false);
    }

    private void LedProccing(boolean Proc) {
        if (Proc) {
            led1.setLedOn(true);
            led2.setLedOn(false);
        } else {
            led1.setLedOn(false);
            led2.setLedOn(true);
        }
    }

    public String ReadBigStringIn(BufferedReader buffIn) throws IOException {
        StringBuilder everything = new StringBuilder();
        String line;
        while ((line = buffIn.readLine()) != null) {
            everything.append(line);
        }
        return everything.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        led2 = new eu.hansolo.steelseries.extras.Led();
        led1 = new eu.hansolo.steelseries.extras.Led();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Search"));

        jTextField1.setFont(new java.awt.Font("Noto Sans", 1, 18)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTextField1)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Applications"));

        jTable1.setAutoCreateColumnsFromModel(false);
        jTable1.setBackground(new java.awt.Color(0, 0, 0));
        jTable1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jTable1.setForeground(new java.awt.Color(1, 213, 14));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "PID", "Name", "Process Time", "Start Time", "Virtual Memory", "Physical Memory", "CPU", "Priority"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setGridColor(new java.awt.Color(0, 0, 0));
        jTable1.setSelectionBackground(new java.awt.Color(248, 253, 75));
        jTable1.setSelectionForeground(new java.awt.Color(1, 1, 1));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        led2.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 978, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(led1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(led2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(led2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(led1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        ResTimer.stop();
        frmApplication.MainClass.Connect(Command.STOP_APPLICATION);
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        ResTimer.start();
    }//GEN-LAST:event_formInternalFrameActivated

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchTableContents(jTextField1.getText());
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        if (evt.isPopupTrigger()) {
            Menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MouseReleased

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        if (evt.isPopupTrigger()) {
            Menu.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_jTable1MousePressed

    public void searchTableContents(String searchString) {
        Vector originalTableModel;
        originalTableModel = (Vector) ((DefaultTableModel) jTable1.getModel()).getDataVector().clone();
        DefaultTableModel currtableModel = (DefaultTableModel) jTable1.getModel();
        //To empty the table before search
        currtableModel.setRowCount(0);
        //To search for contents from original table content
        for (Object rows : originalTableModel) {
            Vector rowVector = (Vector) rows;
            for (Object column : rowVector) {
                if (column.toString().contains(searchString)) {
                    //content found so adding to table
                    currtableModel.addRow(rowVector);
                    break;
                }
            }

        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private eu.hansolo.steelseries.extras.Led led1;
    private eu.hansolo.steelseries.extras.Led led2;
    // End of variables declaration//GEN-END:variables

}
