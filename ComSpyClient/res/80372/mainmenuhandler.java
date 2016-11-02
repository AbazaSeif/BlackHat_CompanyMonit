import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/* 
 * Class that creates the programs menus and handles menu events.
 */
public class mainmenuhandler implements ActionListener
{
    private UIManager.LookAndFeelInfo[] landf;
    private MainGui frame;
    private JMenuBar menubar;
    private JMenu landfmenu;
    private JMenu sniffermenu;
    private JMenu aboutmenu;
    private JPopupMenu popup;
   
    /* class constructor */
    public mainmenuhandler(MainGui frm)
    {
        JMenuItem menuItem=null;
        landf = UIManager.getInstalledLookAndFeels();
        frame = frm;
        menubar = new JMenuBar();
        popup = new JPopupMenu();
        aboutmenu = new JMenu("About");
        aboutmenu.setMnemonic(KeyEvent.VK_A);
        sniffermenu = new JMenu("Packet Sniffing");
        sniffermenu.setMnemonic(KeyEvent.VK_P);
        landfmenu = new JMenu("Look And Feel");
        landfmenu.setMnemonic(KeyEvent.VK_L);
        menuItem = new JMenuItem("About Schnufflen");
        menuItem.addActionListener(this);
        aboutmenu.add(menuItem);
        landf = UIManager.getInstalledLookAndFeels();
        for(int j = 0; j < landf.length; j++)
        {
            menuItem = new JMenuItem(getclassname(landf[j].getClassName()));
            menuItem.addActionListener(this);
            landfmenu.add(menuItem);
        }
        menuItem = new JMenuItem("Start Logging Hex");
        menuItem.addActionListener(this);
        popup.add(menuItem);
        menuItem = new JMenuItem("Start Logging Hex");
        menuItem.addActionListener(this);
        sniffermenu.add(menuItem);
        menuItem = new JMenuItem("Start Logging Chars");
        menuItem.addActionListener(this);
        popup.add(menuItem);     
        menuItem = new JMenuItem("Start Logging Chars");
        menuItem.addActionListener(this);
        sniffermenu.add(menuItem);
        menuItem = new JMenuItem("Stop Logging");
        menuItem.addActionListener(this);
        popup.add(menuItem);      
        menuItem = new JMenuItem("Stop Logging");
        menuItem.addActionListener(this);        
        sniffermenu.add(menuItem);
        menuItem = new JMenuItem("Save Log");
        menuItem.addActionListener(this);
        popup.add(menuItem);      
        menuItem = new JMenuItem("Save Log");
        menuItem.addActionListener(this);        
        sniffermenu.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);
        popup.add(menuItem);
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(this);        
        sniffermenu.add(menuItem);
        menubar.add(sniffermenu);
        menubar.add(landfmenu);
        menubar.add(aboutmenu);
        frame.setpopup(popup);
        frame.setJMenuBar(menubar);
    }
    
    /* Function that strips of the actual class name from a fully-qualified java class name */
    String getclassname(String originalname)
    {
        return originalname.substring(originalname.lastIndexOf(".")+1);
    }
    
    /* Function that handles menu events. */
    public void actionPerformed(ActionEvent e)
    {
        FileOutputStream out=null;
        int sz=0;
        listdata lst=null;
        String dst=null;
        JFileChooser fs=null;
        File tmpfl=null;
        File selectedFile=null;
        String strtmp=null;
        String savdir=null;
        AboutDialog ad = null;
        snifferthread tmpthrd = null;
        try
        {
            for(int i = 0; i < landf.length; i++)
            {   
                if(getclassname(landf[i].getClassName()).equals(e.getActionCommand()))
                {
                    UIManager.setLookAndFeel(landf[i].getClassName());
                    SwingUtilities.updateComponentTreeUI(frame);
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        if(e.getActionCommand().equals("comboBoxChanged"))
        {
            frame.setType(frame.getComboBox().getSelectedIndex()-1);
        }
        else if(e.getActionCommand().equals("Start Logging Hex"))
        {
            if(frame.getType()==-1)
            {
                JOptionPane.showMessageDialog(frame, "Please select a network interface.", "No Network Interface Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.getModel().clear();
            frame.getTextArea2().updateUI();
            frame.getTextArea1().setText("");
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
            tmpthrd = new snifferthread(frame.getType(), frame.getModel(), true);
            frame.setsniffer(tmpthrd);
            tmpthrd.start();
        }
        else if(e.getActionCommand().equals("Start Logging Chars"))
        {
            if(frame.getType()==-1)
            {
                JOptionPane.showMessageDialog(frame, "Please select a network interface.", "No Network Interface Selected", JOptionPane.ERROR_MESSAGE);
                return;
            }
            frame.getModel().clear();
            frame.getTextArea2().updateUI();
            frame.getTextArea1().setText("");
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
            tmpthrd = new snifferthread(frame.getType(), frame.getModel(), false);
            frame.setsniffer(tmpthrd);
            tmpthrd.start();            
        }
        else if(e.getActionCommand().equals("Stop Logging"))
        {
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
        }
        else if(e.getActionCommand().equals("Save Log"))
        {
            if(frame.getsniffer()!=null)
            {
                frame.getsniffer().stopthread();
                frame.setsniffer(null);
            }
            sz=frame.getModel().size();
            fs = new JFileChooser();
            fs.addChoosableFileFilter(new LogFilter(".log"));
            fs.setAlignmentX(frame.getAlignmentX());
            fs.setAlignmentY(frame.getAlignmentY());
            fs.setSelectedFile(null);     
            if(fs.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
            {
                savdir=fs.getCurrentDirectory().toString();
                tmpfl=fs.getSelectedFile();
                if(tmpfl!=null)
                {
                    strtmp=tmpfl.toString(); 
                    if(strtmp.indexOf('.')>0)
                    {
                        strtmp=strtmp.substring(0,strtmp.lastIndexOf('.'));
                    }
                    selectedFile = new File(strtmp + fs.getFileFilter().getDescription());
                    try
                    {
                         if(selectedFile!=null)
                         {
                            try
                            {
                                out = new FileOutputStream(selectedFile);
                                for(int i=0;i<sz;i++)
                                {
                                    lst=(listdata)frame.getModel().get(i);
                                    dst = lst.header + "\r\n" + lst.data + "\r\n\r\n---------------------------------\r\n\r\n";
                                    out.write(dst.getBytes(),0,dst.length());
                                 }
                                 out.close();
                                 frame.getModel().removeAllElements();
                                 frame.getTextArea1().setText("");
                             }
                             catch(Exception errr)
                             {
                                 System.out.println(errr.toString()); 
                             }
                         }
                     }
                     catch(Exception err)
                     {
                         System.out.println(err.toString());
                     }
                }
            }
        }
        else if(e.getActionCommand().equals("Exit"))
        {
            System.exit(0);
        }
        else if(e.getActionCommand().equals("About Schnufflen"))
        {
            ad = new AboutDialog(frame);
        }
    }
}
