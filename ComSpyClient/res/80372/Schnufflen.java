import jpcap.*;
/*
 * The main application class. See README.htm for the full details about this program.
 */

public class Schnufflen
{
    /**
     * Application entry point.
     */
    public static void main(String[] args) throws Exception 
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                MainGui mg = new MainGui();
                mg.setTitle("Schnufflen - A Simple Packet Sniffer");
                mg.setVisible(true);
            }
        });
    }
}