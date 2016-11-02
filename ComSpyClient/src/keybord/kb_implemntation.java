/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keybord;

//import de.ksquared.system.keyboard.GlobalKeyListener;
//import de.ksquared.system.keyboard.KeyAdapter;
//import de.ksquared.system.keyboard.KeyEvent;

/**
 *
 * @author Abaza
 */
public class kb_implemntation {

    thrkeybord KB_Thread = null;
    static boolean KeyLisener = false;
    Thread KKB = null;

    public void Status(boolean SwitchKey) {
        if (SwitchKey) {
            if (KB_Thread == null) {
                KeyLisener = true;
//                KB_Thread = new GlobalKeyListener();
                KB_Thread = new thrkeybord();
                System.out.println("Start Keyloag");
                KB_Thread.init();
//                KKB = new Thread(new Runnable() {
//                    public void run() {
//                        while (KeyLisener) {
//                            KB_Thread.addKeyListener(new KeyAdapter() {
//                                @Override
//                                public void keyReleased(KeyEvent event) {
//                                    System.out.println(event.getVirtualKeyCode());
//                                }
//                            });
//                        }
//                        if(!KeyLisener){
//                            KB_Thread.addKeyListener(null);
//                        }
//                    }
//                });
//                KKB.start();
            }
        } else {
            if (KKB != null) {
                System.out.println("Stop Keyloag");
                KeyLisener = false;
                KKB.interrupt();
                KKB = null;
                
                KB_Thread = null;
            }
        }
    }
}
