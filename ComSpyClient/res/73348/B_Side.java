import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class B_Side extends JFrame
{
	private JTextField enter;
	private JTextArea display;
	ObjectOutputStream B_Output;
	ObjectInputStream B_Input;
	private JPanel topPanel;
	String FromLayer3= " ";
	String dataFromA;
	String checksum = " ";
	int ACKnum = 0;
	int NACKnum = 0;

	public B_Side()
	{
		super("TCP B-side");

		Container c=getContentPane();

		enter=new JTextField();
		enter.setEnabled(false);
		enter.addActionListener(
		
		new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			sendData(e.getActionCommand());
		}

	}
	);
		topPanel = new JPanel();
		topPanel.setBackground(new Color(200,200,255));
		c.add(enter, BorderLayout.SOUTH);
		display=new JTextArea();
		c.add(new JScrollPane(display),BorderLayout.CENTER);
		c.add(topPanel, BorderLayout.NORTH);

		setSize(600,350);
		show();
	}

	public void runB_Side()
	{
		ServerSocket B_Socket;
		Socket connection;
		int counter = 1;

		try {
			B_Socket=new ServerSocket(5000,100);
		while(true) {
		display.setText("waiting for connection\n");
		connection=B_Socket.accept();
		
		//client=new Socket(InetAddress.getByName("127.0.0.1"),5000);
		
 		display.append("Connection "+counter+ 
			"recieved from "+ connection.getInetAddress().getHostName());


		B_Output=new ObjectOutputStream(connection.getOutputStream());

		B_Output.flush();

		B_Input=new ObjectInputStream(connection.getInputStream());

		display.append("\nGot I/O stream\n");
		String message="B_Side: connection successfull";
		B_Output.writeObject(message);
		B_Output.flush();


		enter.setEnabled(true);

		do{
		    try {
			FromLayer3=(String)B_Input.readObject();  // From layer3
			int length = FromLayer3.length();
			int half = FromLayer3.length()/2;
			dataFromA = FromLayer3.substring(0,half); //seperate checksum and data
			checksum = FromLayer3.substring(half,length); //checksum

			if ((checksum.toLowerCase()).equals(dataFromA) ){
			ACKnum++;
			display.append("\nDATA From A_Side : "+dataFromA); //display the data from layer3
			display.append("\nAcknowledged number "+ ACKnum+ " Send to A_Side");
			B_Output.writeObject("ACK : "+ ACKnum);
			B_Output.flush();
			display.setCaretPosition(display.getText().length());
			} 
			else {
			NACKnum++;
			B_Output.writeObject("NACK "+ NACKnum);
			}
				
			}
		catch(ClassNotFoundException cnfex){

			display.append("\nUnknown object type recieved");
			}
		} while(!FromLayer3.equals("A_Side >>> TERMINATE"));


		display.append("User terminated connection\n");
		enter.setEnabled(false);
		B_Output.close();
		B_Input.close();
		connection.close();
		++counter;
			}
		}
		catch(EOFException eof){
			System.out.println("A_Side terminated connection");
		}
		
		catch(IOException io) {
		io.printStackTrace();
		}

	}
		private void sendData(String s)
		{
			try {
				//message=s;
				
				B_Output.writeObject("B_Side: "+s);
				B_Output.flush();
				display.append("\n B_Side: "+s);

			     }
		
			catch(IOException cnfex) {
			display.append("\nError writing object ");
			}

		}
	
		public static void main(String arge[])
		{
			B_Side app=new B_Side();
			app.addWindowListener( new WindowAdapter(){
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		}

		);
			app.runB_Side();
		}

	}
						

	

