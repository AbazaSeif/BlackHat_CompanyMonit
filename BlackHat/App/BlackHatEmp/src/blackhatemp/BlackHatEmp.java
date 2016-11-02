/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackhatemp;


/**
 *
 * @author abaza
 */
public class BlackHatEmp {

    public static boolean Debug = true;
    public static String PASSWORD = "S15A03E19I8";
    public static String UUID = "000-000-0000-00000";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread ClientRun = new Thread(new Tasks());
        ClientRun.setPriority(5);
        ClientRun.start();

    }
}
