import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import javax.swing.*;

class DssFrame extends JFrame
    implements ActionListener
{

    DssFrame(String s, String s1, int i, boolean flag)
    {
        super(s);
        presentfile = new String();
        presentpath = new String();
        isbrowse = false;
        isverified = false;
        fileverbuffer = new String();
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
        p.setBackground(new Color(130, 200, 220));
        p1.setBackground(new Color(130, 200, 220));
        p11.setBackground(new Color(130, 200, 220));
        p12.setBackground(new Color(130, 200, 220));
        p2.setBackground(new Color(130, 200, 220));
        p3.setBackground(new Color(130, 200, 220));
        p4.setBackground(new Color(130, 200, 220));
        p5.setBackground(new Color(130, 200, 220));
        p6.setBackground(new Color(130, 200, 220));
        p61.setBackground(new Color(130, 200, 220));
        p62.setBackground(new Color(130, 200, 220));
        senrec = new String();
        strl1 = new String();
        strgenver = new String();
        sendtostr = new String();
        c = getContentPane();
        addWindowListener(new MyWindowAdapter(this));
        senrec = s1;
        keylen = i;
        ismac = flag;
        if(senrec.equals("sender"))
        {
            strl1 = "DSS : ENCRYPTION";
            strgenver = "Signature Generation";
            chgenver = 'G';
            sendtostr = "Send to...";
        } else
        if(senrec.equals("receiver"))
        {
            strl1 = "DSS : DECRYPTION";
            strgenver = "Signature Verification";
            chgenver = 'V';
            sendtostr = "Save";
        }
        l1 = new JLabel(strl1);
        l1.setFont(new Font("Sans", 1, 20));
        message = new JLabel("");
        message.setFont(new Font("Sans", 1, 17));
        genver = new JButton(strgenver);
        browse = new JButton("Browse");
        clear = new JButton("Clear");
        sendto = new JButton(sendtostr);
        back = new JButton("Back");
        genver.setBackground(Color.pink);
        browse.setBackground(Color.pink);
        clear.setBackground(Color.pink);
        sendto.setBackground(Color.pink);
        back.setBackground(Color.pink);
        genver.setMnemonic(chgenver);
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
        p4.add(genver);
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
        genver.addActionListener(this);
        browse.addActionListener(this);
        clear.addActionListener(this);
        back.addActionListener(this);
        sendto.addActionListener(this);
        Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
        setSize(d.width,d.height);
        show();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        if(s.equals("Signature Generation"))
        {
            message.setText("Generating Signature......");
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
                String s4 = new String();
                String s7 = new String();
                Dsasigngen dsasigngen = new Dsasigngen();
                try
                {
                    String s5 = filecontent.getText();
                    if(ismac)
                        s5 = macid + s5;
                    s5 = s2 + s5;
                    String s8 = dsasigngen.doit(s5);
                    if(ismac)
                        s8 = "111i" + s8;
                    else
                        s8 = "000O" + s8;
                    String s12 = new String();
                    String _tmp6 = s12;
                    for(s12 = String.valueOf(keylen); s12.length() < 2; s12 = "0" + s12);
                    s8 = s12 + s8;
                    filecontent.setText("");
                    filecontent.append(s8);
                    tf.setText(presentpath + "EncDss");
                    JOptionPane joptionpane11 = new JOptionPane();
                    JOptionPane _tmp7 = joptionpane11;
                    JOptionPane.showMessageDialog(this, "Signature Generated", "Message", 1);
                }
                catch(Exception exception2) { }
                isbrowse = false;
            }
            message.setText("");
        } else
        if(s.equals("Signature Verification"))
        {
            message.setText("Verifying Signature......");
            if(!isbrowse)
            {
                JOptionPane joptionpane2 = new JOptionPane();
                JOptionPane _tmp8 = joptionpane2;
                JOptionPane.showMessageDialog(this, "You must select a File", "Message", 1);
            } else
            {
                String s1 = new String();
                Dsasignver dsasignver = new Dsasignver();
                boolean flag = false;
                try
                {
                    String s9 = fileverbuffer;
                    String s10 = s9.substring(0, 2);
                    s9 = s9.substring(2);
                    fileverbuffer = s9;
                    keylen = Integer.parseInt(s10);
                    String s13 = s9.substring(0, 4);
                    s9 = s9.substring(4);
                    fileverbuffer = s9;
                    if(s13.equals("111i"))
                        ismac = true;
                    else
                    if(s13.equals("000O"))
                        ismac = false;
                    if(ismac)
                    {
                        Macid macid1 = new Macid();
                        mymacid = macid1.showid();
                    }
                    s1 = dsasignver.doit(fileverbuffer);
                    fileverbuffer = "";
                }
                catch(StringIndexOutOfBoundsException stringindexoutofboundsexception)
                {
                    flag = true;
                }
                catch(Exception exception1) { }
                if(flag)
                {
                    JOptionPane joptionpane6 = new JOptionPane();
                    JOptionPane _tmp9 = joptionpane6;
                    JOptionPane.showMessageDialog(this, "Select a Signed File", "Message", 1);
                } else
                {
                    JOptionPane joptionpane7 = new JOptionPane();
                    JOptionPane _tmp10 = joptionpane7;
                    String s11 = JOptionPane.showInputDialog(this, "Enter the Key", "Get Key", 1);
                    if(s11.length() > keylen)
                    {
                        JOptionPane joptionpane10 = new JOptionPane();
                        JOptionPane _tmp11 = joptionpane10;
                        JOptionPane.showMessageDialog(this, "Length of the key should not exceed " + keylen + " characters", "Invalid Key Length", 1);
                        JOptionPane _tmp12 = joptionpane7;
                        s11 = JOptionPane.showInputDialog(this, "Please ReEnter the Key:", "Get Key", 1);
                    }
                    JOptionPane _tmp13 = joptionpane7;
                    JOptionPane.showMessageDialog(this, s11, "The Key", 1);
                    String s14 = s1.substring(0, keylen);
                    s1 = s1.substring(keylen);
                    s14 = s14.trim();
                    if(ismac)
                    {
                        macidr = s1.substring(0, 17);
                        s1 = s1.substring(17);
                    }
                    if(s14.equals(s11))
                    {
                        if(!ismac)
                        {
                            JOptionPane joptionpane12 = new JOptionPane();
                            JOptionPane _tmp14 = joptionpane12;
                            JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                            if(s1.equals("failure"))
                            {
                                JOptionPane joptionpane16 = new JOptionPane();
                                JOptionPane _tmp15 = joptionpane16;
                                JOptionPane.showMessageDialog(this, "Signature Not Verified", "Message", 1);
                                filecontent.setText("");
                            } else
                            {
                                JOptionPane joptionpane17 = new JOptionPane();
                                JOptionPane _tmp16 = joptionpane17;
                                JOptionPane.showMessageDialog(this, "Signature Verified", "Message", 1);
                                filecontent.setText("");
                                filecontent.append(s1);
                                tf.setText(presentpath);
                            }
                        } else
                        if(macidr.equals(mymacid))
                        {
                            JOptionPane joptionpane13 = new JOptionPane();
                            JOptionPane _tmp17 = joptionpane13;
                            JOptionPane.showMessageDialog(this, "You are Welcome", "Authorized User!", 1);
                            if(s1.equals("failure"))
                            {
                                JOptionPane joptionpane18 = new JOptionPane();
                                JOptionPane _tmp18 = joptionpane18;
                                JOptionPane.showMessageDialog(this, "Signature Not Verified", "Message", 1);
                                filecontent.setText("");
                            } else
                            {
                                JOptionPane joptionpane19 = new JOptionPane();
                                JOptionPane _tmp19 = joptionpane19;
                                JOptionPane.showMessageDialog(this, "Signature Verified", "Message", 1);
                                filecontent.setText("");
                                filecontent.append(s1);
                                tf.setText(presentpath);
                            }
                        } else
                        {
                            JOptionPane joptionpane14 = new JOptionPane();
                            JOptionPane _tmp20 = joptionpane14;
                            JOptionPane.showMessageDialog(this, "Sorry! Your System cannot port this Software", "Unauthorized Porting!", 1);
                        }
                    } else
                    {
                        JOptionPane joptionpane15 = new JOptionPane();
                        JOptionPane _tmp21 = joptionpane15;
                        JOptionPane.showMessageDialog(this, "Sorry! You are not Authorized to use this Software", "Unauthorized User!", 1);
                    }
                }
                isbrowse = false;
            }
            message.setText("");
        } else
        if(s.equals("Send to...") || s.equals("Save"))
            try
            {
                if(filecontent.getText().equals(""))
                {
                    JOptionPane joptionpane3 = new JOptionPane();
                    JOptionPane _tmp22 = joptionpane3;
                    JOptionPane.showMessageDialog(this, "Empty file cannot be saved!", "Empty File", 1);
                } else
                {
                    FileDialog filedialog = new FileDialog(this, "Send to.....", 1);
                    filedialog.setDirectory(ipa.getText());
                    if(senrec.equals("sender"))
                        filedialog.setFile("EncDss");
                    filedialog.setVisible(true);
                    String s3 = filedialog.getDirectory() + filedialog.getFile();
                    if(!filedialog.getFile().equals(null))
                        ipa.setText(filedialog.getDirectory() + filedialog.getFile());
                    if(!filedialog.getFile().equals(null))
                    {
                        FileWriter filewriter = new FileWriter(s3);
                        filewriter.write(filecontent.getText());
                        filewriter.close();
                        JOptionPane joptionpane8 = new JOptionPane();
                        JOptionPane _tmp23 = joptionpane8;
                        JOptionPane.showMessageDialog(this, "File:- " + s3 + " saved ", "Message", 1);
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
            if(!presentfile.equals(null))
                tf.setText(presentfile);
            if(presentfile.equals(null) || presentpath.equals(null))
                tf.setText("");
            Getfiledata getfiledata = new Getfiledata();
            String s6 = new String(getfiledata.doit(presentfile));
            if(senrec.equals("sender"))
            {
                filecontent.setText("");
                filecontent.append(s6);
            } else
            if(senrec.equals("receiver"))
            {
                JOptionPane joptionpane9 = new JOptionPane();
                JOptionPane _tmp24 = joptionpane9;
                JOptionPane.showMessageDialog(this, "You must Verify the Signature", "Message", 1);
                fileverbuffer = s6;
                filecontent.setText("");
                filecontent.append("The file " + presentfile + " must be verified");
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

    JLabel l1;
    JLabel message;
    JButton genver;
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
    boolean isverified;
    String fileverbuffer;
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
    String strgenver;
    String sendtostr;
    char chgenver;
    Container c;
}
