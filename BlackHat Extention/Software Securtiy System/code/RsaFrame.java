// Source File Name:   Sss.java

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import javax.swing.*;

class RsaFrame extends JFrame
    implements ActionListener, ItemListener
{

    RsaFrame(String s, String s1, int i, boolean flag)
    {
        super(s);
        presentfile = new String();
        presentpath = new String();
        isconf = false;
        issign = false;
        issecu = false;
        isbrowse = false;
        issystem = false;
        issystem2 = false;
        issaved = false;
        isencrypted = false;
        isdecrypted = false;
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
        p41 = new JPanel();
        p42 = new JPanel();
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
            strl1 = "RSA : ENCRYPTION";
            strencdec = "Encrypt";
            chencdec = 'E';
            sendtostr = "Send to...";
        } else
        if(senrec.equals("receiver"))
        {
            strl1 = "RSA : DECRYPTION";
            strencdec = "Decrypt";
            chencdec = 'D';
            sendtostr = "Save";
        }
        l1 = new JLabel(strl1);
        message = new JLabel("");
        message.setFont(new Font("Sans", 1, 17));
        l1.setFont(new Font("Sans", 2, 22));
        lmethods = new JLabel("Method");
        encdec = new JButton(strencdec);
        browse = new JButton("Browse");
        clear = new JButton("Clear");
        sendto = new JButton(sendtostr);
        back = new JButton("Back");
        encdec.setBackground(new Color(130, 200, 220));
        browse.setBackground(new Color(130, 200, 220));
        clear.setBackground(new Color(130, 200, 220));
        sendto.setBackground(new Color(130, 200, 220));
        back.setBackground(new Color(130, 200, 220));
        encdec.setMnemonic(chencdec);
        browse.setMnemonic('w');
        clear.setMnemonic('C');
        sendto.setMnemonic('S');
        back.setMnemonic('B');
        methods = new JComboBox();
        methods.addItem(" ");
        methods.addItem("Confidentiality");
        methods.addItem("Signature");
        methods.addItem("Security");
        filecontent = new TextArea(10, 60);
        filecontent.setVisible(true);
        tf = new JTextField(20);
        ipa = new JTextField(16);
        p1.setLayout(new GridLayout(2, 1));
        p11.setLayout(new FlowLayout(1));
        p12.setLayout(new FlowLayout(1));
        p2.setLayout(new FlowLayout(1));
        p3.setLayout(new FlowLayout(1));
        p4.setLayout(new GridLayout(2, 1));
        p41.setLayout(new FlowLayout(1));
        p42.setLayout(new FlowLayout(1));
        p5.setLayout(new FlowLayout(1));
        p6.setLayout(new GridLayout(2, 1));
        p61.setLayout(new FlowLayout(1));
        p62.setLayout(new FlowLayout(1));
        p11.add(l1);
        p12.add(filecontent);
        p1.add(p11);
        p1.add(p12);
        if(senrec.equals("sender"))
        {
            p41.add(lmethods);
            p41.add(methods);
        }
        p41.add(encdec);
        p4.add(p41);
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
        methods.addItemListener(this);
        encdec.addActionListener(this);
        browse.addActionListener(this);
        clear.addActionListener(this);
        sendto.addActionListener(this);
        back.addActionListener(this);
        show();
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
            if(!isconf && !issign && !issecu)
            {
                JOptionPane joptionpane1 = new JOptionPane();
                JOptionPane _tmp1 = joptionpane1;
                JOptionPane.showMessageDialog(this, "You have to select a Method", "Message", 1);
            } else
            {
                JOptionPane joptionpane2 = new JOptionPane();
                JOptionPane _tmp2 = joptionpane2;
                String s2 = JOptionPane.showInputDialog(this, "Enter the Key", "Get Key", 1);
                if(s2.length() > keylen)
                {
                    JOptionPane joptionpane5 = new JOptionPane();
                    JOptionPane _tmp3 = joptionpane5;
                    JOptionPane.showMessageDialog(this, "Length of the key should not exceed " + keylen + " characters", "Invalid Key Length", 1);
                    JOptionPane _tmp4 = joptionpane5;
                    s2 = JOptionPane.showInputDialog(this, "Please ReEnter the Key", "Get Key", 1);
                }
                JOptionPane _tmp5 = joptionpane2;
                JOptionPane.showMessageDialog(this, s2, "The Key", 1);
                for(; s2.length() < keylen; s2 = s2 + " ");
                if(ismac)
                {
                    JOptionPane joptionpane6 = new JOptionPane();
                    macid = joptionpane6.showInputDialog(this, "Enter the Macid of the Receiving System", "Get Receiver's Macid", 1);
                    JOptionPane _tmp6 = joptionpane6;
                    JOptionPane.showMessageDialog(this, macid, "The Receiver's Macid", 1);
                }
                try
                {
                    if(isconf && !issign && !issecu)
                    {
                        String s5 = new String();
                        String s10 = new String();
                        Rsaencconf rsaencconf = new Rsaencconf();
                        s5 = filecontent.getText();
                        if(ismac)
                            s5 = macid + s5;
                        s5 = s2 + s5;
                        s10 = rsaencconf.doit("1", s5);
                        if(ismac)
                            s10 = "111i" + s10;
                        else
                            s10 = "000O" + s10;
                        String s15 = new String();
                        String _tmp7 = s15;
                        for(s15 = String.valueOf(keylen); s15.length() < 2; s15 = "0" + s15);
                        s10 = s15 + s10;
                        s10 = s10 + "00001111";
                        filecontent.setText("");
                        filecontent.append(s10);
                        tf.setText(presentpath + "EncRsa");
                    } else
                    if(issign && !issecu && !isconf)
                    {
                        String s6 = new String();
                        String s11 = new String();
                        Rsaencsign rsaencsign = new Rsaencsign();
                        s6 = filecontent.getText();
                        if(ismac)
                            s6 = macid + s6;
                        s6 = s2 + s6;
                        s11 = rsaencsign.doit("1", s6);
                        if(ismac)
                            s11 = "111i" + s11;
                        else
                            s11 = "000O" + s11;
                        String s16 = new String();
                        String _tmp8 = s16;
                        for(s16 = String.valueOf(keylen); s16.length() < 2; s16 = "0" + s16);
                        s11 = s16 + s11;
                        s11 = s11 + "00001001";
                        filecontent.setText("");
                        filecontent.append(s11);
                        tf.setText(presentpath + "EncRsa");
                    } else
                    if(issecu && !isconf && !issign)
                    {
                        String s7 = new String();
                        String s12 = new String();
                        Rsaencsecu rsaencsecu = new Rsaencsecu();
                        s12 = filecontent.getText();
                        if(ismac)
                            s12 = macid + s12;
                        s12 = s2 + s12;
                        s7 = rsaencsecu.doit("1", "2", s12);
                        if(ismac)
                            s7 = "111i" + s7;
                        else
                            s7 = "000O" + s7;
                        String s17 = new String();
                        String _tmp9 = s17;
                        for(s17 = String.valueOf(keylen); s17.length() < 2; s17 = "0" + s17);
                        s7 = s17 + s7;
                        s7 = s7 + "00001000";
                        filecontent.setText("");
                        filecontent.append(s7);
                        tf.setText(presentpath + "EncRsa");
                    }
                }
                catch(Exception exception1) { }
                isencrypted = true;
                isbrowse = false;
                issaved = false;
            }
            message.setText("");
        } else
        if(s.equals("Decrypt"))
        {
            message.setText("Decrypting......");
            if(filecontent.getText().equals(""))
            {
                JOptionPane joptionpane3 = new JOptionPane();
                JOptionPane _tmp10 = joptionpane3;
                JOptionPane.showMessageDialog(this, "You must select a File or enter Cipher", "Message", 1);
            } else
            {
                String s1 = filecontent.getText();
                String s3 = s1.substring(0, 2);
                s1 = s1.substring(2);
                keylen = Integer.parseInt(s3);
                String s8 = s1.substring(0, 4);
                s1 = s1.substring(4);
                filecontent.setText(s1);
                if(s8.equals("111i"))
                    ismac = true;
                else
                if(s8.equals("000O"))
                    ismac = false;
                if(ismac)
                {
                    Macid macid1 = new Macid();
                    mymacid = macid1.showid();
                }
                JOptionPane joptionpane7 = new JOptionPane();
                JOptionPane _tmp11 = joptionpane7;
                String s14 = JOptionPane.showInputDialog(this, "Enter the Key", "Get Key", 1);
                if(s14.length() > keylen)
                {
                    JOptionPane joptionpane9 = new JOptionPane();
                    JOptionPane _tmp12 = joptionpane9;
                    JOptionPane.showMessageDialog(this, "Length of the key should not exceed " + keylen + " characters", "Invalid Key Length", 1);
                    JOptionPane _tmp13 = joptionpane9;
                    s14 = JOptionPane.showInputDialog(this, "Please ReEnter the Key:", "Get Key", 1);
                }
                JOptionPane _tmp14 = joptionpane7;
                JOptionPane.showMessageDialog(this, s14, "The Key", 1);
                try
                {
                    String s18 = filecontent.getText();
                    String s19 = s18.substring(s18.length() - 8, s18.length());
                    s18 = s18.substring(0, s18.length() - 8);
                    filecontent.setText(s18);
                    String s20 = "";
                    if(s19.equals("00001111"))
                    {
                        String s21 = new String();
                        String s24 = new String();
                        Rsadecconf rsadecconf = new Rsadecconf();
                        s24 = filecontent.getText();
                        s21 = rsadecconf.doit("1", s24);
                        String s27 = s21.substring(0, keylen);
                        s21 = s21.substring(keylen);
                        s27 = s27.trim();
                        if(ismac)
                        {
                            macidr = s21.substring(0, 17);
                            s21 = s21.substring(17);
                        }
                        if(s27.equals(s14))
                        {
                            if(!ismac)
                            {
                                JOptionPane joptionpane14 = new JOptionPane();
                                JOptionPane _tmp15 = joptionpane14;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s21);
                                tf.setText(presentpath);
                            } else
                            if(macidr.equals(mymacid))
                            {
                                JOptionPane joptionpane15 = new JOptionPane();
                                JOptionPane _tmp16 = joptionpane15;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s21);
                                tf.setText(presentpath);
                            } else
                            {
                                JOptionPane joptionpane16 = new JOptionPane();
                                JOptionPane _tmp17 = joptionpane16;
                                JOptionPane.showMessageDialog(this, "Sorry! Your System cannot port this Software", "Unauthorized Porting!", 1);
                            }
                        } else
                        {
                            JOptionPane joptionpane17 = new JOptionPane();
                            JOptionPane _tmp18 = joptionpane17;
                            JOptionPane.showMessageDialog(this, "Sorry! You are not Authorized to use this Software", "Unauthorized User!", 1);
                        }
                    } else
                    if(s19.equals("00001001"))
                    {
                        String s22 = new String();
                        String s25 = new String();
                        Rsadecsign rsadecsign = new Rsadecsign();
                        s25 = filecontent.getText();
                        s22 = rsadecsign.doit("1", s25);
                        String s28 = s22.substring(0, keylen);
                        s22 = s22.substring(keylen);
                        s28 = s28.trim();
                        if(ismac)
                        {
                            macidr = s22.substring(0, 17);
                            s22 = s22.substring(17);
                        }
                        if(s28.equals(s14))
                        {
                            if(!ismac)
                            {
                                JOptionPane joptionpane18 = new JOptionPane();
                                JOptionPane _tmp19 = joptionpane18;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s22);
                                tf.setText(presentpath);
                            } else
                            if(macidr.equals(mymacid))
                            {
                                JOptionPane joptionpane19 = new JOptionPane();
                                JOptionPane _tmp20 = joptionpane19;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s22);
                                tf.setText(presentpath);
                            } else
                            {
                                JOptionPane joptionpane20 = new JOptionPane();
                                JOptionPane _tmp21 = joptionpane20;
                                JOptionPane.showMessageDialog(this, "Sorry! Your System cannot port this Software", "Unauthorized Porting!", 1);
                            }
                        } else
                        {
                            JOptionPane joptionpane21 = new JOptionPane();
                            JOptionPane _tmp22 = joptionpane21;
                            JOptionPane.showMessageDialog(this, "Sorry! You are not Authorized to use this Software", "Unauthorized User!", 1);
                        }
                    } else
                    if(s19.equals("00001000"))
                    {
                        String s23 = new String();
                        Rsadecsecu rsadecsecu = new Rsadecsecu();
                        s23 = rsadecsecu.doit("1", "2", filecontent.getText());
                        String s26 = s23.substring(0, keylen);
                        s23 = s23.substring(keylen);
                        s26 = s26.trim();
                        if(ismac)
                        {
                            macidr = s23.substring(0, 17);
                            s23 = s23.substring(17);
                        }
                        if(s26.equals(s14))
                        {
                            if(!ismac)
                            {
                                JOptionPane joptionpane10 = new JOptionPane();
                                JOptionPane _tmp23 = joptionpane10;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s23);
                                tf.setText(presentpath);
                            } else
                            if(macidr.equals(mymacid))
                            {
                                JOptionPane joptionpane11 = new JOptionPane();
                                JOptionPane _tmp24 = joptionpane11;
                                JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                                filecontent.setText("");
                                filecontent.append(s23);
                                tf.setText(presentpath);
                            } else
                            {
                                JOptionPane joptionpane12 = new JOptionPane();
                                JOptionPane _tmp25 = joptionpane12;
                                JOptionPane.showMessageDialog(this, "Sorry! Your System cannot port this Software", "Unauthorized Porting!", 1);
                            }
                        } else
                        {
                            JOptionPane joptionpane13 = new JOptionPane();
                            JOptionPane _tmp26 = joptionpane13;
                            JOptionPane.showMessageDialog(this, "Sorry! You are not Authorized to use this Software", "Unauthorized User!", 1);
                        }
                    }
                }
                catch(Exception exception2) { }
                isdecrypted = true;
                isbrowse = false;
                issaved = false;
            }
            message.setText("");
        } else
        if(s.equals("Send to...") || s.equals("Save"))
        {
            try
            {
                if(filecontent.getText().equals(""))
                {
                    JOptionPane joptionpane4 = new JOptionPane();
                    JOptionPane _tmp27 = joptionpane4;
                    JOptionPane.showMessageDialog(this, "Empty file cannot be saved!", "Empty File", 1);
                } else
                {
                    FileDialog filedialog = new FileDialog(this, "Send to.....", 1);
                    filedialog.setDirectory(ipa.getText());
                    if(senrec.equals("sender"))
                        filedialog.setFile("EncRsa");
                    filedialog.setVisible(true);
                    String s4 = filedialog.getDirectory() + filedialog.getFile();
                    if(!filedialog.getFile().equals(null))
                        ipa.setText(filedialog.getDirectory() + filedialog.getFile());
                    if(!filedialog.getFile().equals(null))
                    {
                        FileWriter filewriter = new FileWriter(s4);
                        filewriter.write(filecontent.getText());
                        filewriter.close();
                        JOptionPane joptionpane8 = new JOptionPane();
                        JOptionPane _tmp28 = joptionpane8;
                        JOptionPane.showMessageDialog(this, "File:- " + s4 + " saved ", "Message", 1);
                    }
                }
            }
            catch(Exception exception) { }
            issaved = true;
        } else
        if(s.equals("Browse"))
        {
            message.setText("Opening......");
            isbrowse = true;
            FileDialog filedialog1 = new FileDialog(this, "Open your Software");
            if(!tf.getText().equals(""))
                filedialog1.setFile(tf.getText());
            filedialog1.setVisible(true);
            presentfile = filedialog1.getDirectory() + filedialog1.getFile();
            presentpath = filedialog1.getDirectory();
            if(presentfile.equals(null) || presentpath.equals(null))
                tf.setText("");
            if(!presentfile.equals(""))
            {
                tf.setText(presentfile);
                Getfiledata getfiledata = new Getfiledata();
                String s9 = new String();
                s9 = getfiledata.doit(presentfile);
                String s13 = s9.substring(s9.length() - 8, s9.length());
                filecontent.setText("");
                filecontent.append(s9);
            }
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

    public void itemStateChanged(ItemEvent itemevent)
    {
        String s = (String)itemevent.getItem();
        if(s.equals("Confidentiality"))
        {
            isconf = true;
            issign = false;
            issecu = false;
            if(!senrec.equals("sender"));
        } else
        if(s.equals("Signature"))
        {
            isconf = false;
            issign = true;
            issecu = false;
            if(!senrec.equals("sender"));
        } else
        if(s.equals("Security"))
        {
            isconf = false;
            issign = false;
            issecu = true;
            if(!senrec.equals("sender"));
        }
    }

    JLabel l1;
    JLabel lmethods;
    JLabel message;
    JComboBox methods;
    JButton encdec;
    JButton browse;
    JButton sendto;
    JButton clear;
    JButton back;
    TextArea filecontent;
    JTextField tf;
    JTextField ipa;
    String presentfile;
    String presentpath;
    boolean isconf;
    boolean issign;
    boolean issecu;
    boolean isbrowse;
    boolean issystem;
    boolean issystem2;
    boolean issaved;
    boolean isencrypted;
    boolean isdecrypted;
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
    JPanel p41;
    JPanel p42;
    JPanel p5;
    JPanel p6;
    JPanel p61;
    JPanel p62;
    String senrec;
    String strl1;
    String strencdec;
    String sendtostr;
    char chencdec;
    Container c;
}