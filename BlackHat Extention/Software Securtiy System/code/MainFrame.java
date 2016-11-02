// Source File Name:   Sss.java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

class MainFrame extends JFrame
    implements ActionListener
{

    MainFrame(String s)
    {
        super(s);
        c = getContentPane();
        p = new JPanel();
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();
        p5 = new JPanel();
        addWindowListener(new MyWindowAdapter(this));
        setSize(550, 450);
        sender = new JButton("Sender");
        receiver = new JButton("Receiver");
        exit = new JButton("Exit");
        sender.setBackground(new Color(130, 200, 220));
        receiver.setBackground(new Color(130, 200, 220));
        exit.setBackground(new Color(130, 200, 220));
        sender.setMnemonic('S');
        receiver.setMnemonic('R');
        exit.setMnemonic('X');
        p1.setLayout(new FlowLayout(1));
        p2.setLayout(new FlowLayout(1));
        p3.setLayout(new FlowLayout(1));
        p4.setLayout(new FlowLayout(1));
        p5.setLayout(new FlowLayout(1));
        l3 = new JLabel(new ImageIcon("net.jpg"));
        footer = new JLabel(new ImageIcon("secufooter.jpg"));
        p1.add(l3);
        p2.add(sender);
        p3.add(receiver);
        p4.add(exit);
        p5.add(footer);
        p.setLayout(new GridLayout(5, 1));
        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
        c.add(p);
        show();
        sender.addActionListener(this);
        receiver.addActionListener(this);
        exit.addActionListener(this);
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        String s = actionevent.getActionCommand();
        SenderReceiverFrame senderreceiverframe;
        if(s.equals("Sender"))
            senderreceiverframe = new SenderReceiverFrame("Sender", "sender");
        else
        if(s.equals("Receiver"))
            senderreceiverframe = new SenderReceiverFrame("Receiver", "receiver");
        else
        if(s.equals("Exit"))
            setVisible(false);
    }

    JLabel l3;
    JLabel footer;
    JButton sender;
    JButton receiver;
    JButton exit;
    Container c;
    JPanel p;
    JPanel p1;
    JPanel p2;
    JPanel p3;
    JPanel p4;
    JPanel p5;
}