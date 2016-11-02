

/*+---------------------------------Adeel Mahmood---------------------------------+*\

/*+---------------------------------Port Scanner----------------------------------+*\

/*=-------------------------adeelmahmood1982@ukonline.co.uk------------------------+*\

/************************************************************************************/





import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

public class PortScanner extends Frame{



Socket s;
int startPort,endPort;
Label l1;
TextField hostName,fromPort,toPort;
TextArea log;
Label host,from,to,label;
Button scan,reset;
int fp,tp;
String h;
static boolean close=false;
Dialog d;


public PortScanner(){

Frame f=new Frame("Port Scanner");

f.setVisible(true);
f.setLayout(new GridLayout(5,1));

l1=new Label("Port Scanner version 1.0",Label.CENTER);
l1.setForeground(Color.blue);
l1.setFont(new Font("TimesRoman",Font.BOLD,20));
f.add(l1);

Panel p1=new Panel(new GridLayout(3,3));
host=new Label("Host Name:");
host.setForeground(Color.blue);
p1.add(host);
hostName=new TextField(15);
p1.add(hostName);
from=new Label("From Port:");
from.setForeground(Color.blue);
p1.add(from);
fromPort=new TextField(15);
p1.add(fromPort);
to=new Label("To Port:");
to.setForeground(Color.blue);
p1.add(to);
toPort=new TextField(15);
p1.add(toPort);

f.add(p1);

Panel p2=new Panel();
scan=new Button("Scan Now");
scan.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent r){
if(hostName.getText().equals("")){log.setText("Please complete all the required fields");
return;
}
	else if (fromPort.getText().equals("")){log.setText("Please complete all the required fields");
return;}
		else if(toPort.getText().equals("")){log.setText("Please complete all the required fields");
return;}
			
close=false;
Thread run1=new Thread(){
public void run(){

scan.setEnabled(false);
reset.setLabel("Stop");
log.setText("");
log.repaint();

h=hostName.getText();
fp=Integer.parseInt(fromPort.getText());
tp=Integer.parseInt(toPort.getText());

for(int i=fp;i<=tp;i++){
label.setText("Port "+i+" is being tested");
	if(close)
	break;
	try{
	s=new Socket(h,i);
	log.append("Port "+i+" is open."+"\n");
	log.repaint();
	s.close();
	}
	catch(Exception er){continue;}
	
}
scan.setEnabled(true);
reset.setLabel("Reset");
label.setText("Press Scan to start.");
}
};
run1.start();
}
});



reset=new Button("Reset");
reset.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent t){
Thread run2=new Thread(){
public void run(){
close=true;
hostName.setText("");
fromPort.setText("");
toPort.setText("");
}
};
run2.start();
}
});





Label empty=new Label();
p2.add(scan);
p2.add(empty);
p2.add(reset);
f.add(p2);

log=new TextArea();
f.add(log);

label=new Label("Press Scan to start.");
label.setForeground(Color.blue);
f.add(label);

f.setSize(320,460);
//f.show(true);
f.repaint();
f.setResizable(false);

f.addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
System.exit(1);
}
});


}

public static void main(String args[]){

new PortScanner();
}



}// end of class