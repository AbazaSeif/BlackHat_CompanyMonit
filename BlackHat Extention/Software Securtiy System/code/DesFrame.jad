// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 5/29/2004 12:25:35 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Sss.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import javax.swing.*;

class DesFrame extends JFrame
    implements ActionListener
{

    DesFrame(String s, String s1, int i, boolean flag)
    {
        super(s);
        presentfile = new String();
        presentpath = new String();
        isbrowse = false;
        ismac = false;
        macid = new String();
        macidr = new String();
        mymacid = new String();
        p = new JPanel();
        p1 = new JPanel();
        p11 = new JPanel();
        p12 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        p6 = new JPanel();
        p61 = new JPanel();
        p62 = new JPanel();
        senrec = new String();
        strl1 = new String();
        strencdec = new String();
        sendtostr = new String();
        c = getContentPane();
        addWindowListener(new MyWindowAdapter(this));
        setSize(550, 450);
        senrec = s1;
        keylen = i;
        ismac = flag;
        if(senrec.equals("sender"))
        {
            strl1 = "DES : ENCRYPTION";
            strencdec = "Encrypt";
            chencdec = 'E';
            sendtostr = "Send to...";
        } else
        if(senrec.equals("receiver"))
        {
            strl1 = "DES : DECRYPTION";
            strencdec = "Decrypt";
            chencdec = 'D';
            sendtostr = "Save";
        }
        l1 = new JLabel(strl1);
        l1.setFont(new Font("Sans", 1, 20));
        message = new JLabel("");
        message.setFont(new Font("Sans", 1, 17));
        encdec = new JButton(strencdec);
        browse = new JButton("Browse");
        clear = new JButton("Clear");
        sendto = new JButton(sendtostr);
        back = new JButton("Back");
        encdec.setBackground(new Color(130, 200, 220));
        browse.setBackground(new Color(130, 200, 220));
        clear.setBackground(new Color(130, 200, 220));
        back.setBackground(new Color(130, 200, 220));
        sendto.setBackground(new Color(130, 200, 220));
        encdec.setMnemonic(chencdec);
        browse.setMnemonic('w');
        clear.setMnemonic('C');
        back.setMnemonic('B');
        sendto.setMnemonic('S');
        filecontent = new TextArea(10, 60);
        tf = new JTextField(20);
        ipa = new JTextField(20);
        p1.setLayout(new GridLayout(2, 1));
        p11.setLayout(new FlowLayout(1));
        p12.setLayout(new FlowLayout(1));
        p2.setLayout(new FlowLayout(1));
        p3.setLayout(new FlowLayout(1));
        p4.setLayout(new FlowLayout(1));
        p5.setLayout(new FlowLayout(1));
        p6.setLayout(new GridLayout(2, 1));
        p61.setLayout(new FlowLayout(1));
        p62.setLayout(new FlowLayout(1));
        p11.add(l1);
        p12.add(filecontent);
        p1.add(p11);
        p1.add(p12);
        p4.add(encdec);
        p5.add(browse);
        p5.add(tf);
        p5.add(clear);
        p61.add(back);
        p61.add(sendto);
        p61.add(ipa);
        p62.add(message);
        p6.add(p61);
        p6.add(p62);
        p.setLayout(new GridLayout(6, 1));
        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        p.add(p6);
        c.add(p);
        show();
        encdec.addActionListener(this);
        browse.addActionListener(this);
        clear.addActionListener(this);
        sendto.addActionListener(this);
        back.addActionListener(this);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s.equals("Encrypt"))
        {
            message.setText("Encrypting......");
            if(filecontent.getText().equals(""))
            {
                JOptionPane joptionpane = new JOptionPane();
                JOptionPane _tmp = joptionpane;
                JOptionPane.showMessageDialog(this, "You must select a File or enter Text", "Message", 1);
            } else
            {
                JOptionPane joptionpane1 = new JOptionPane();
                JOptionPane _tmp1 = joptionpane1;
                String s2 = JOptionPane.showInputDialog(this, "Enter the Key", "Get Key", 1);
                if(s2.length() > keylen)
                {
                    JOptionPane joptionpane4 = new JOptionPane();
                    JOptionPane _tmp2 = joptionpane4;
                    JOptionPane.showMessageDialog(this, "Length of the key should not exceed " + keylen + " characters", "Invalid Key Length", 1);
                    JOptionPane _tmp3 = joptionpane4;
                    s2 = JOptionPane.showInputDialog(this, "Please ReEnter the Key:", "Get Key", 1);
                }
                JOptionPane _tmp4 = joptionpane1;
                JOptionPane.showMessageDialog(this, s2, "The Key", 1);
                for(; s2.length() < keylen; s2 = s2 + " ");
                if(ismac)
                {
                    JOptionPane joptionpane5 = new JOptionPane();
                    macid = joptionpane5.showInputDialog(this, "Enter the Macid of the Receiving System", "Get Receiver's Macid", 1);
                    JOptionPane _tmp5 = joptionpane5;
                    JOptionPane.showMessageDialog(this, macid, "The Receiver's Macid", 1);
                }
                try
                {
                    String s5 = new String();
                    String s8 = new String();
                    Des des = new Des();
                    s5 = filecontent.getText();
                    s5 = s2 + s5;
                    s8 = des.Desalg(s5);
                    if(ismac)
                        s8 = "111i" + macid + s8;
                    else
                        s8 = "000O" + s8;
                    String s10 = new String();
                    String _tmp6 = s10;
                    for(s10 = String.valueOf(keylen); s10.length() < 2; s10 = "0" + s10);
                    s8 = s10 + s8;
                    filecontent.setText("");
                    filecontent.append(s8);
                    tf.setText(presentpath + "EncDes");
                }
                catch(Exception exception1) { }
            }
            message.setText("");
        } else
        if(s.equals("Decrypt"))
        {
            message.setText("Decrypting......");
            if(filecontent.getText().equals(""))
            {
                JOptionPane joptionpane2 = new JOptionPane();
                JOptionPane _tmp7 = joptionpane2;
                JOptionPane.showMessageDialog(this, "You must select a File", "Message", 1);
            } else
            {
                String s1 = filecontent.getText();
                String s3 = s1.substring(0, 2);
                s1 = s1.substring(2);
                keylen = Integer.parseInt(s3);
                String s6 = s1.substring(0, 4);
                s1 = s1.substring(4);
                if(s6.equals("111i"))
                {
                    ismac = true;
                    macidr = s1.substring(0, 17);
                    s1 = s1.substring(17);
                } else
                if(s6.equals("000O"))
                    ismac = false;
                filecontent.setText(s1);
                if(ismac)
                {
                    Macid macid1 = new Macid();
                    mymacid = macid1.showid();
                }
                JOptionPane joptionpane6 = new JOptionPane();
                JOptionPane _tmp8 = joptionpane6;
                String s9 = JOptionPane.showInputDialog(this, "Enter the Key", "Get Key", 1);
                if(s9.length() > keylen)
                {
                    JOptionPane joptionpane8 = new JOptionPane();
                    JOptionPane _tmp9 = joptionpane8;
                    JOptionPane.showMessageDialog(this, "Length of the key should not exceed " + keylen + " characters", "Invalid Key Length", 1);
                    JOptionPane _tmp10 = joptionpane8;
                    s9 = JOptionPane.showInputDialog(this, "Please ReEnter the Key:", "Get Key", 1);
                }
                JOptionPane _tmp11 = joptionpane6;
                JOptionPane.showMessageDialog(this, s9, "The Key", 1);
                try
                {
                    String s11 = new String();
                    String s12 = new String();
                    Desdec desdec = new Desdec();
                    s11 = filecontent.getText();
                    s12 = desdec.Decrypt1(s11);
                    String s13 = s12.substring(0, keylen);
                    s12 = s12.substring(keylen);
                    s13 = s13.trim();
                    if(s13.equals(s9))
                    {
                        if(!ismac)
                        {
                            JOptionPane joptionpane9 = new JOptionPane();
                            JOptionPane _tmp12 = joptionpane9;
                            JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                            filecontent.setText("");
                            filecontent.append(s12);
                            tf.setText(presentpath);
                        } else
                        if(macidr.equals(mymacid))
                        {
                            JOptionPane joptionpane10 = new JOptionPane();
                            JOptionPane _tmp13 = joptionpane10;
                            JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                            filecontent.setText("");
                            filecontent.append(s12);
                            tf.setText(presentpath);
                        } else
                        {
                            JOptionPane joptionpane11 = new JOptionPane();
                            JOptionPane _tmp14 = joptionpane11;
                            JOptionPane.showMessageDialog(this, "Sorry! Your System cannot port this Software", "Unauthorized Porting!", 1);
                        }
                    } else
                    {
                        JOptionPane joptionpane12 = new JOptionPane();
                        JOptionPane _tmp15 = joptionpane12;
                        JOptionPane.showMessageDialog(this, "Sorry! You are not Authorized to use this Software", "Unauthorized User!", 1);
                    }
                }
                catch(Exception exception2) { }
            }
            message.setText("");
        } else
        if(s.equals("Send to...") || s.equals("Save"))
            try
            {
                if(filecontent.getText().equals(""))
                {
                    JOptionPane joptionpane3 = new JOptionPane();
                    JOptionPane _tmp16 = joptionpane3;
                    JOptionPane.showMessageDialog(this, "Empty file cannot be saved!", "Empty File", 1);
                } else
                {
                    FileDialog filedialog = new FileDialog(this, "Send to.....", 1);
                    filedialog.setDirectory(ipa.getText());
                    if(senrec.equals("sender"))
                        filedialog.setFile("EncDes");
                    filedialog.setVisible(true);
                    String s4 = filedialog.getDirectory() + filedialog.getFile();
                    if(!filedialog.getFile().equals(null))
                        ipa.setText(filedialog.getDirectory() + filedialog.getFile());
                    if(!filedialog.getFile().equals(null))
                    {
                        FileWriter filewriter = new FileWriter(s4);
                        filewriter.write(filecontent.getText());
                        filewriter.close();
                        JOptionPane joptionpane7 = new JOptionPane();
                        JOptionPane _tmp17 = joptionpane7;
                        JOptionPane.showMessageDialog(this, "File:- " + s4 + " saved ", "Message", 1);
                    }
                }
            }
            catch(Exception exception) { }
        else
        if(s.equals("Browse"))
        {
            message.setText("Opening......");
            isbrowse = true;
            FileDialog filedialog1 = new FileDialog(this, "Open your Software");
            filedialog1.setVisible(true);
            presentfile = filedialog1.getDirectory() + filedialog1.getFile();
            presentpath = filedialog1.getDirectory();
            if(!presentfile.equals(""))
                tf.setText(presentfile);
            Getfiledata getfiledata = new Getfiledata();
            String s7 = new String(getfiledata.doit(presentfile));
            filecontent.setText("");
            filecontent.append(s7);
            message.setText("");
        } else
        if(s.equals("Clear"))
        {
            filecontent.setText("");
            tf.setText("");
            ipa.setText("");
        } else
        if(s.equals("Back"))
            setVisible(false);
    }

    JLabel l1;
    JLabel message;
    JButton encdec;
    JButton browse;
    JButton clear;
    JButton sendto;
    JButton back;
    JTextField tf;
    JTextField ipa;
    TextArea filecontent;
    String presentfile;
    String presentpath;
    boolean isbrowse;
    int keylen;
    boolean ismac;
    String macid;
    String macidr;
    String mymacid;
    JPanel p;
    JPanel p1;
    JPanel p11;
    JPanel p12;
    JPanel p2;
    JPanel p3;
    JPanel p4;
    JPanel p5;
    JPanel p6;
    JPanel p61;
    JPanel p62;
    String senrec;
    String strl1;
    String strencdec;
    char chencdec;
    String sendtostr;
    Container c;
}