import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.net.*;

/*
 * Simple class that creates and shows an About dialog
 */
public class AboutDialog extends JDialog
{
    private JScrollPane jsp;
    private JEditorPane helpfile;
    public AboutDialog(JFrame owner) 
    {
        super(owner, "About Schnufflen");
        URL fileurl=null;
        File file=null;
        helpfile = new JEditorPane();
        helpfile.setEditable(false);
        helpfile.setContentType("text/html");
        try
        {
            fileurl = MainGui.class.getResource("README.htm");
            helpfile.setPage(fileurl);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        jsp = new JScrollPane();
        jsp.getViewport().add(helpfile, BorderLayout.CENTER);
        setSize(600, 300);
        getContentPane().add(jsp);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        
        setVisible(true);
    }
}
