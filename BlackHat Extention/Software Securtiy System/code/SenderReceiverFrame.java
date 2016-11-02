 // Source File Name:   Sss.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class SenderReceiverFrame extends JFrame
    implements ActionListener
{

    SenderReceiverFrame(String s, String s1)
    {
        super(s);
        mbar = new JMenuBar();
        algo = new JMenu("Algorithm");
        settings = new JMenu();
        rsa = new JMenuItem("RSA");
        dss = new JMenuItem("DSS");
        des = new JMenuItem("DES");
        showmac = new JMenuItem("Show Macid");
        macornot = new JCheckBoxMenuItem("Use Macid");
        intkeylen = 30;
        macornotstr = new String();
        senrec = new String();
        strl1 = new String();
        c = getContentPane();
        addWindowListener(new MyWindowAdapter(this));
        setSize(550, 450);
        senrec = s1;
        if(senrec.equals("sender"))
            strl1 = "SENDER";
        else
        if(senrec.equals("receiver"))
            strl1 = "RECEIVER";
        l1 = new JLabel(strl1);
        l1.setFont(new Font("Sans", 1, 24));
        l2 = new JLabel(new ImageIcon("net.jpg"));
        back = new JButton("Back");
        back.setBackground(new Color(130, 200, 220));
        back.setMnemonic('B');
        JPanel jpanel = new JPanel();
        JPanel jpanel1 = new JPanel();
        JPanel jpanel2 = new JPanel();
        jpanel.setLayout(new FlowLayout());
        jpanel1.setLayout(new FlowLayout());
        jpanel2.setLayout(new FlowLayout());
        jpanel.add(l2);
        jpanel1.add(l1);
        jpanel2.add(back);
        c.setLayout(new GridLayout(3, 1));
        c.add(jpanel);
        c.add(jpanel1);
        c.add(jpanel2);
        algo.add(rsa);
        //algo.add(dss);
        //algo.add(des);
        mbar.add(algo);
        if(senrec.equals("sender"))
        {
            settings.add(macornot);
            settings.add(showmac);
            mbar.add(settings);
        }
        setJMenuBar(mbar);
        rsa.addActionListener(this);
        dss.addActionListener(this);
        des.addActionListener(this);
        back.addActionListener(this);
        macornot.addActionListener(this);
        showmac.addActionListener(this);
        algo.setMnemonic('A');
        rsa.setMnemonic('R');
        dss.setMnemonic('D');
        des.setMnemonic('E');
        settings.setMnemonic('S');
        macornot.setMnemonic('M');
        showmac.setMnemonic('h');
        show();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s.equals("RSA"))
        {
            setVisible(false);
            RsaFrame rsaframe = new RsaFrame("RSA algorithm", senrec, intkeylen, macornot.getState());
        } else
        if(s.equals("DSS"))
        {
            setVisible(false);
            DssFrame dssframe = new DssFrame("DSS algorithm", senrec, intkeylen, macornot.getState());
        } else
        if(s.equals("DES"))
        {
            setVisible(false);
            DesFrame desframe = new DesFrame("DES algorithm", senrec, intkeylen, macornot.getState());
        } else
        if(s.equals("Show Macid"))
        {
            JOptionPane joptionpane1 = new JOptionPane();
            JOptionPane _tmp3 = joptionpane1;
            String s2 = JOptionPane.showInputDialog(this, "Enter the Password", "Password", 1);
            if(s2.equals("cmc"))
            {
                Macid macid = new Macid();
                String s3 = macid.showid();
                if(s3.equals(null))
                {
                    JOptionPane _tmp4 = joptionpane1;
                    JOptionPane.showMessageDialog(this, "Cannot access Macid", "No Ethernet Card", 1);
                } else
                {
                    JOptionPane _tmp5 = joptionpane1;
                    JOptionPane.showMessageDialog(this, "This System's Macid is :" + s3, "Key Length", 1);
                }
            } else
            {
                JOptionPane _tmp6 = joptionpane1;
                JOptionPane.showMessageDialog(this, "You are not Authorized to see Macid!", "Unauthorised User!", 1);
            }
        } else
        if(s.equals("Back"))
            setVisible(false);
    }

    JLabel l1;
    JLabel l2;
    JMenuBar mbar;
    JMenu algo;
    JMenu settings;
    JMenuItem rsa;
    JMenuItem dss;
    JMenuItem des;
    JMenuItem showmac;
    JCheckBoxMenuItem macornot;
    JButton back;
    int intkeylen;
    String macornotstr;
    String senrec;
    String strl1;
    Container c;
}