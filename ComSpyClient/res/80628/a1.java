import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class a1{
	public static void main(String[] args) 
	{
	mainframe m1 = new mainframe() ;
	m1.setVisible(true);

	}

	void call()
	{
	 adm a = new adm() ;
	 a.setVisible(true);
	}
}

class mainframe extends JFrame implements WindowListener,ActionListener
	{
		JLabel name ;
		JLabel passwd ;
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;
		JPasswordField jp ;
		a1 aa = new a1() ;
        JPanel jp1 ,jp2 ,jp3;

     mainframe()
		{
		 addWindowListener(this);
		 name = new JLabel("Enter Username");
		 passwd = new JLabel("Enter Password");
		 t1 = new JTextField(10);
		 jp = new JPasswordField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(2,2));
		 jp1.add(name);
         jp1.add(t1);
		 jp1.add(passwd);
		 jp1.add(jp);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,300,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
jp.setText("");// clears the message
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	System.exit(0);
}

else
{

String s1 = t1.getText();
String s2 = jp.getText();
 
 if(s1.equals("admin") && s2.equals("admin"))
	 {
       aa.call() ;
	   this.dispose();
	}

else 
	{
      JOptionPane.showMessageDialog(this,"Invalid user name & password") ;
      t1.setText("");
      jp.setText("");// clears the message
	}
}

}


public void windowClosing(WindowEvent e)
{
  System.exit(0);
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}


class adm extends JFrame implements WindowListener,ActionListener
{
	JRadioButton rt1,rt2,rt3;
	ButtonGroup bg ;
    JButton bt1,bt2,bt3,bt4;
	JLabel jl ;
	JPanel p4,p1 , p2 ,p3;
	lstbs d7;
	lstfw d2 ;
    adfw d1 ;
	delfw d3 ;
	adbs d4 ;
	delsb d5 ;
	addns d12 ;
    deldns d13;
	lstdns d11 ;

   adm()
	{
	   //super(ParentFrame,a1,true) ;
   
       rt1=new JRadioButton("List");
       rt2=new JRadioButton("Add");
       rt3=new JRadioButton("Delete");
       bg=new ButtonGroup() ;
       bt1=new JButton("Site Blocking");
       bt2=new JButton("Content Filtering");
       bt3=new JButton("Exit");
	   bt4=new JButton("DNS");
       bg.add(rt1);
       bg.add(rt2);
       bg.add(rt3);
       p1=new JPanel();
       p2=new JPanel();
       p3=new JPanel();
	   p4=new JPanel();
       p1.setLayout(new BorderLayout());
	   p2.setLayout(new BorderLayout());
	   p4.setLayout(new BorderLayout());
       
	   p1.setLayout(new GridLayout(1,3));
	   p2.setLayout(new GridLayout(1,3));
	   p3.setLayout(new GridLayout(1,1));

	   p1.add(rt1);
	   p1.add(rt2);
	   p1.add(rt3);
	   p2.add(bt1);
	   p2.add(bt2);
	   p2.add(bt4);
	   p3.add(bt3);

	   bt1.addActionListener(this);
       bt2.addActionListener(this);
	   bt3.addActionListener(this);
	   bt4.addActionListener(this);
		rt1.addActionListener(this);
		rt2.addActionListener(this);
		rt3.addActionListener(this);
	 p4.add(p2,BorderLayout.CENTER);
	 p4.add(p3,BorderLayout.SOUTH);
	  getContentPane().add(p1,BorderLayout.NORTH);
	  getContentPane().add(p4,BorderLayout.SOUTH);
	  setBounds(50,50,500,200);
	}

public void actionPerformed(ActionEvent e) {

if(e.getSource()==bt1) // if button bt2 is clicked
{
 if(rt1.isSelected()== true)
	{
       d7 = new lstbs(this,"Blocked Sites") ;
	   d7.setVisible(true) ;
	}
else if(rt2.isSelected()== true) 
	{
    	d4 = new adbs(this,"Add in block list") ;
	    d4.setVisible(true) ;
	}
else if(rt3.isSelected()== true) 
	{
	    d5 = new delsb(this,"delete from block list") ;
	    d5.setVisible(true) ;	    
	}
}

if(e.getSource()==bt2) // if button bt2 is clicked
{
 if(rt1.isSelected()== true)
	{
       d2 = new lstfw(this,"Filtered words") ;
	   d2.setVisible(true) ;
	}
else if(rt2.isSelected()== true) 
	{
	   d1 = new adfw(this,"Add Filtered words") ;
	   d1.setVisible(true) ;	   
	}
else if(rt3.isSelected()== true) 
	{
	   d3 = new delfw(this,"delete from filtered list") ;
	   d3.setVisible(true) ;
	
	}
}
if(e.getSource()==bt4) // if button bt2 is clicked
{
 if(rt1.isSelected()== true)
	{
       d11 = new lstdns(this,"DNS") ;
	   d11.setVisible(true) ;
	}
else if(rt2.isSelected()== true) 
	{
    	d12 = new addns(this,"Add in DNS list") ;
	    d12.setVisible(true) ;
	}
else if(rt3.isSelected()== true) 
	{
	    d13 = new deldns(this,"delete from DNS list") ;
	    d13.setVisible(true) ;	    
	}
}

 
if(e.getSource()==bt3)// if button bt3 is clicked
{
	System.exit(0);
}
}

public void windowClosing(WindowEvent e)
{
  System.exit(0);
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

class lstbs extends JDialog implements WindowListener,ActionListener
{

BufferedReader br1,br2;
FileReader fr1,fr2;
String temp1 ;	
JTextArea result ;
JButton jb ;
JPanel jp ;
JScrollPane pan1 ;

lstbs(JFrame ParentFrame , String a1)
	{
super(ParentFrame,a1,true) ;
addWindowListener(this);

result = new JTextArea(20,32) ;
result.setEditable(false);
pan1=new JScrollPane(result);
jb = new JButton("Exit") ;
jb.addActionListener(this);
jp = new JPanel() ;

jp.setLayout(new BorderLayout());

jp.add(result,BorderLayout.NORTH);
jp.add(jb,BorderLayout.SOUTH);

getContentPane().add(jp,BorderLayout.CENTER);

setBounds(25,25,625,500);

try
{
fr1 = new FileReader("blockedsite.txt");
br1 = new BufferedReader(fr1);
result.append("Following sites r blocked \n\n") ;
 while((temp1=br1.readLine())!=null)
 result.append(temp1+"\n") ;	
}
catch(Exception e) 
{
System.out.println(e) ;
}
}

public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb) // if button bt2 is clicked
{
	this.dispose();
}
}
public void windowClosing(WindowEvent e)
{  
	this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}



class lstfw extends JDialog implements WindowListener,ActionListener
{

BufferedReader br1,br2;
FileReader fr1,fr2;
String temp1 ;	
JTextArea result ;
JButton jb ;
JPanel jp ;
JScrollPane pan1 ;

lstfw(JFrame ParentFrame , String a1)
	{
super(ParentFrame,a1,true) ;
addWindowListener(this);

result = new JTextArea(20,32) ;
result.setEditable(false);
pan1=new JScrollPane(result);
jb = new JButton("Exit") ;
jb.addActionListener(this);
jp = new JPanel() ;

jp.setLayout(new BorderLayout());

jp.add(result,BorderLayout.NORTH);
jp.add(jb,BorderLayout.SOUTH);

getContentPane().add(jp,BorderLayout.CENTER);

setBounds(25,25,625,500);

try
{
fr1 = new FileReader("filteringkeywords.txt");
br1 = new BufferedReader(fr1);
result.append("Following keywords r filtered \n\n") ;
 while((temp1=br1.readLine())!=null)
 result.append(temp1+"\n") ;	
}
catch(Exception e) 
{
System.out.println(e) ;
}
}

public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb) // if button bt2 is clicked
{
	this.dispose();
}
}
public void windowClosing(WindowEvent e)
{  
	this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

     
class adfw extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     adfw(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the keyword");
		 t1 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(1,3));
		 jp1.add(name);
         jp1.add(t1);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   wd = wd.trim() ;

	if(wd.length() > 0)
	{
		try
		{
	       ch = fw.addfilteringwords(wd);
		}catch(Exception exc)
		{
			System.out.println("call to addfiltering key words");
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"Keyword successfully added");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"Keyword already exists");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

class adbs extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     adbs(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the Site");
		 t1 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(1,3));
		 jp1.add(name);
         jp1.add(t1);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   wd = wd.trim() ;

	if(wd.length() > 0)
	{
      try{
		  ch = fw.addblockedsite(wd);
		  }catch(Exception exc)
		{
			System.out.println("call to addblockedsite key words");
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"Site successfully blocked");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"Site already blocked");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

class delsb extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     delsb(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the Site");
		 t1 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(1,3));
		 jp1.add(name);
         jp1.add(t1);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   wd = wd.trim() ;

	if(wd.length() > 0)
	{
      try{ch = fw.delblockedsite(wd);
	  }catch(Exception exc)
		{
			System.out.println("call to delblockedsite key words");
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"Site successfully Unblocked");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"Site not present in block list");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

class delfw extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     delfw(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the keyword");
		 t1 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(1,3));
		 jp1.add(name);
         jp1.add(t1);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   wd = wd.trim() ;

	if(wd.length() > 0)
	{
      try{
		  ch = fw.delfilteringwords(wd);
	  }catch(Exception exc)
		{
			System.out.println("call to delfiltering key words");
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"Keyword successfully deleted");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"Keyword does'nt exist");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}


class lstdns extends JDialog implements WindowListener,ActionListener
{

BufferedReader br1,br2;
FileReader fr1,fr2;
String temp1 ;	
JTextArea result ;
JButton jb ;
JPanel jp ;
JScrollPane pan1 ;

lstdns(JFrame ParentFrame , String a1)
	{
super(ParentFrame,a1,true) ;
addWindowListener(this);

result = new JTextArea(20,32) ;
result.setEditable(false);
pan1=new JScrollPane(result);
jb = new JButton("Exit") ;
jb.addActionListener(this);
jp = new JPanel() ;

jp.setLayout(new BorderLayout());

jp.add(result,BorderLayout.NORTH);
jp.add(jb,BorderLayout.SOUTH);

getContentPane().add(jp,BorderLayout.CENTER);

setBounds(25,25,625,500);

try
{
fr1 = new FileReader("dns.txt");
br1 = new BufferedReader(fr1);
result.append("Following DNS entries are present \n\n") ;
 while((temp1=br1.readLine())!=null)
 result.append(temp1+"\n") ;	
}
catch(Exception e) 
{
System.out.println(e) ;
}
}

public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb) // if button bt2 is clicked
{
	this.dispose();
}
}
public void windowClosing(WindowEvent e)
{  
	this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}

class deldns extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1 ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     deldns(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the keyword");
		 t1 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(1,3));
		 jp1.add(name);
         jp1.add(t1);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   wd = wd.trim() ;

	if(wd.length() > 0)
	{
      try{
		  ch = fw.deldns(wd);
	  }catch(Exception exc)
		{
			System.out.println("call to delfiltering key words");
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"DNS successfully deleted");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"DNS does'nt exist");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}



class addns extends JDialog implements WindowListener,ActionListener
	{
		JLabel name ;		
		JTextField t1,t2 ;
		JLabel ip ;
		JButton jb1 ,jb2,jb3 ;		
        JPanel jp1 ,jp2 ,jp3;
		int ch;
		firewall fw = new firewall();

     addns(JFrame ParentFrame , String a1)
	   {
        super(ParentFrame,a1,true) ;
		 addWindowListener(this);
		 name = new JLabel("Enter the Site");
		 ip = new JLabel("Enter the IP");
		 t1 = new JTextField(10);
		 t2 = new JTextField(10);
		 jp1 = new JPanel() ;
 		 jp2 = new JPanel() ;
		 jp3 = new JPanel() ;
		 jp3.setLayout(new BorderLayout());
    	 //jp2.setLayout(new BorderLayout());
		 jb1 = new JButton("Submit");
		 jb2 = new JButton("Clear");
		 jb3 = new JButton("Exit");
         jp1.setLayout(new GridLayout(2,2));
		 jp1.add(name);
         jp1.add(t1);
         jp1.add(ip);
		 jp1.add(t2);
		 jp2.add(jb1);
		 jp2.add(jb2);
		 jp2.add(jb3);


		 jb1.addActionListener(this);
		 jb2.addActionListener(this);
		 jb3.addActionListener(this);
		 jp3.add(jp1,BorderLayout.NORTH);
		 jp3.add(jp2,BorderLayout.SOUTH);
//		 getContentPane().add(jp3,BorderLayout.NORTH);
		 getContentPane().add(jp3,BorderLayout.CENTER);
         setBounds(200,150,250,200);
	}

	public void actionPerformed(ActionEvent e) {

if(e.getSource()==jb2) // if button bt2 is clicked
{
t1.setText("");
t2.setText("");
}

if(e.getSource()==jb3)// if button bt3 is clicked
{
	this.dispose();
}

else if(e.getSource()==jb1)
{
   String wd = t1.getText();
   String wd2 = t2.getText();
   wd = wd.trim() ;
   wd2 = wd2.trim() ;
   wd=wd+"\t"+wd2;
  // System.out.println("Entered text is: "+wd);

	if(wd.length() > 0 && wd2.length() > 0)
	{
      try{
		  ch = fw.adddns(wd);
		  }catch(Exception exc)
		{
			System.out.println("call to add dns "+exc);
		}

	  if(ch==1)
		{
		  JOptionPane.showMessageDialog(this,"DNS successfully added");
	      this.dispose();
		}

	  else
          JOptionPane.showMessageDialog(this,"DNS already added");
    }
	else
        JOptionPane.showMessageDialog(this,"Enter some text");
}

}


public void windowClosing(WindowEvent e)
{
  this.dispose();
}

public void windowClosed(WindowEvent e)
{
}
public void windowOpened(WindowEvent e)
{
}
public void windowIconified(WindowEvent e)
{
}
public void windowDeiconified(WindowEvent e)
{
}
public void windowActivated(WindowEvent e)
{
}
public void windowDeactivated(WindowEvent e)
{
}
}
