import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Date.*;
//import Timer.*;

public class A_Side extends JFrame
{
	private JTextField enter,lossP,crptP, Delay, RSeed;
	private JTextArea display;
	ObjectOutputStream output;
	ObjectInputStream input;
	private Label CrptF, DelayT;
	private Label LossF, LSeed;
	private JPanel topPanel, rightPanel;
	String payload= " ";
	String msgFromB = " ";
	String checkSum = " ";
	int Timer;
	String DT, RS, LP, CP;
	int delayTime,t, yourSeed, LPacket, CPacket;
	String bufferData = "";
	int seed;

	public A_Side()
	{
		
		super("TCP A-Side");
		Container c=getContentPane();
		c.setLayout(new FlowLayout() );

		


		enter=new JTextField(60);
		enter.setEnabled(true);
		enter.addActionListener(
		
		new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			sendData(e.getActionCommand());
			enter.setText("");
		}

	}

		
	);

		topPanel = new JPanel();
		topPanel.setBackground(new Color(200,200,255));
		rightPanel = new JPanel();
		rightPanel.setBackground(new Color(200,200,255));
		DelayT = new Label("average time delay (> 0): [1000]");
		Delay = new JTextField(4);
		Delay.setEnabled(true);
		LossF = new Label("loss probability (0 for no)");
		lossP = new JTextField(4);
		lossP.setEnabled(true);
		CrptF = new Label("corruption probability (0 for no)");
		crptP = new JTextField(4);
		crptP.setEnabled(true);
		topPanel.add(DelayT );
		topPanel.add( Delay);
		topPanel.add(LossF );
		topPanel.add( lossP );
		topPanel.add(CrptF);
		topPanel.add(crptP );
		LSeed = new Label("Enter rendom seed: [random]");
		RSeed = new JTextField(4);
		RSeed.setEnabled(true);
		//rightPanel.add(LSeed);
		//rightPanel.add(RSeed);
		topPanel.add(LSeed);
		topPanel.add(RSeed);
		//Delay.setText(1000);
		
		
		display=new JTextArea(12,60);
		display.setEnabled(true);
		
		c.add(topPanel, BorderLayout.NORTH);	
		//c.add(rightPanel, BorderLayout.EAST);
		c.add(new JScrollPane(display),BorderLayout.CENTER);
		c.add(enter, BorderLayout.CENTER);

		setSize(950,350);
		
		show();

		
}

	public void runA_Side()
	{

		


		Socket A_Socket;

		try {
		
		display.append("Attempting connection\n");
		A_Socket=new Socket(InetAddress.getByName("192.168.0.92"),5000);
		
		display.append("Connected to "+A_Socket.getInetAddress().getHostName());

		output=new ObjectOutputStream(A_Socket.getOutputStream());

		output.flush();
		input=new ObjectInputStream(A_Socket.getInputStream());

		display.append("\nGot I/O stream\n");
		enter.setEnabled(true);

		//A_Socket.setSoTimeout(5000);

		do{
			
		    try {
			
			msgFromB=(String)input.readObject();
			String nack = msgFromB.substring(0,4);
			//String ack = msgFromB.substring(0,3);
			if( t <= delayTime && (!nack.equals("NACK")) ) {
			
			//display.append("\nReceived From B_Side@ "+ t);
			display.append("\n"+msgFromB);
			}
			else if(nack.equals("NACK") )
			{
				display.append("\nNACK FROM B_Side "+msgFromB);
				
				sendData(bufferData);
			}
			else {
				
			display.append("\nResending "+bufferData);
			sendData(bufferData);
			}
			//display.setCaretPosition(display.getText().length());
			}
		catch(ClassNotFoundException cnfex){
			display.append("\nUnknown object type recieved");
			}
		} while(!msgFromB.equals("B_Side : TERMINATE"));

		display.append("Closing connection\n");
		input.close();
		output.close();
		A_Socket.close();
		}
		catch(EOFException eof){
			System.out.println("B_Side terminated connection");
		}
		
		catch(IOException e) {
		e.printStackTrace();
		}

	}

	
	


		private void sendData(String s)
		{
			bufferData = s;
			checkSum = s.toUpperCase();

			CP = crptP.getText();
				CPacket = Integer.parseInt(CP);
			if(CPacket < 0)
				CPacket = 0;
			
			LP = lossP.getText();
				LPacket = Integer.parseInt(LP);
			if(LPacket < 0)
				LPacket = 0;
			
			DT = Delay.getText();	
			 delayTime=Integer.parseInt(DT);	
			RS = RSeed.getText();
			yourSeed = Integer.parseInt(RS);
			if(yourSeed <= 0)
			yourSeed = 5;

			seed = (int)(yourSeed * Math.random());
			
			display.append("\nSEED "+ seed);
			
			try {	
				if(delayTime <= 0)
				delayTime = 1000;
				
				
				Timer =0;
				
				Timer = (int) ( Math.random() * delayTime );
				payload=s;

				display.append("\nDelay Timer "+ delayTime );
				if( (yourSeed/2  ) <  ((LPacket/100) * (seed) ) ) // loss (simulation) a packet randomly
				{ 
					display.append("\n A_Side: "+ payload+ " send ");
					display.append("\npacket being lost");
					display.append("\nResending.."); // a simulation dummy
					sendData(bufferData);
				}

				else if ((CPacket/100) * seed > (yourSeed/2) ) //corrupt packet randomly
				{

				
				
				int half = s.length()/2;
				payload = s.substring(0,half);  	//corrupts the payload
				String packet = payload.concat(checkSum);
				 t = Timer;				
				display.append("\n A_Side: "+ payload+ " send @ "+t);
				display.append("\n Packet has been corrupted");
				output.writeObject(packet);
				output.flush();
				}
					
				else {	// if the data is not corrupted and no loss		

				
				bufferData = s;
				//checkSum = s.toUpperCase();
				String packet = payload.concat(checkSum);
				 t = Timer;				
				display.append("\n A_Side: "+ payload+ " send @ "+t);
				output.writeObject(packet);
				output.flush();
				}
				//display.append("\nTimer " + Timer);
				t =(int) ( Math.random() * 1000 );
				
			     }
		
			catch(IOException cnfex) {
			display.append("\nError writing object ");
			}

		}
	
		public static void main(String arge[])
		{
			
			A_Side app=new A_Side();
			app.addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				//System.exit(0);
			}
		}

		);
			app.runA_Side();
		}


	}


