/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Window;

import Audio.lifeAudioSpecker;
import Connection.Command;
import com.sun.org.apache.bcel.internal.util.SecuritySupport;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author abaza
 */
public class frmAudio extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;

    protected boolean SwitchKey = false;
    private MainGUI MainClass = null;
    protected lifeAudioSpecker Audio = null;
    private Thread AudioOn = null;

    /**
     * Creates new form frmAudio
     *
     * @param Class
     */
    public frmAudio(MainGUI Class) {
        this.MainClass = Class;
        initComponents();

    }

    private boolean KeySwitchStatus() {
        try {
            if (SwitchKey) {
                jLabel2.setIcon(new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream("img/off.png"))));
                SwitchKey = false;
            } else {
                jLabel2.setIcon(new ImageIcon(ImageIO.read(SecuritySupport.getResourceAsStream("img/on.png"))));
                SwitchKey = true;
            }
            led1.setLedOn(SwitchKey);
            pack();
            return SwitchKey;
        } catch (IOException ex) {
            return false;
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

        led1 = new eu.hansolo.steelseries.extras.Led();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setClosable(true);
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
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        led1.setLedType(eu.hansolo.steelseries.tools.LedType.RECT_HORIZONTAL);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/speaker.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/off.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(led1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(led1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addComponent(jLabel1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (KeySwitchStatus()) {
            StartAudio();
        } else {
            StopAudio();
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private synchronized void StartAudio() {
        this.MainClass.Connect(Command.AUDIO_LIFE);
        AudioOn = new Thread(new Runnable() {
            @Override
            public void run() {
                Audio = new lifeAudioSpecker();
                Audio.Status(true);
                Audio.run();
                led1.setLedOn(true);
            }
        });
        AudioOn.run();
    }

    private synchronized void StopAudio() {
        this.MainClass.Connect(Command.STOP_AUDIO_LIFE);
        Thread AudioOff = new Thread(new Runnable() {
            @Override
            public void run() {
                Audio.Status(false);
                Audio.suspend();
                led1.setLedOn(false);
                Audio = null;
            }
        });

        AudioOff.run();
        do {
            if (Audio == null) {
                AudioOff = null;
            }
        } while (Audio.isAlive());
        AudioOn = null;

    }

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        this.MainClass.Connect(Command.STOP_AUDIO_LIFE);
    }//GEN-LAST:event_formInternalFrameClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private eu.hansolo.steelseries.extras.Led led1;
    // End of variables declaration//GEN-END:variables
}
