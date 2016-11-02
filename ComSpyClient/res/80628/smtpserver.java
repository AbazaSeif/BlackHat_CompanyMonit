import java.io.* ;
import java.net.* ;
import java.util.Date;

class multi implements Runnable{
	
	String name;
	Thread t;
	Socket s;


multi(String threadname,Socket p)throws Exception{		/* constructor */
	
	name=threadname;
	t=new Thread(this,name);
	System.out.println("New thread: "+t);
	t.start();
	s=p;
}


private int domainverify(String s)throws java.lang.Exception /* verify the E-mail address*/
{	
	int a=-1;
	int b=-1;
	if(((a=s.indexOf('@'))!=-1)&&(a!=0)&&(!s.substring(a-1,a).equalsIgnoreCase(" "))&&(!s.substring(a+1,a+2).equalsIgnoreCase(" ")))			
	{									/* return 2 for not specifing correct address 1 for incorrect domain name*/
		if(a==0)						/* 0 for correct */
			return 2;
		else if(((b=s.indexOf('.'))!=-1)&&(b!=1)&&((b-a)>1)&&(b!=s.length()-1)&&(!s.substring(b-1,b).equalsIgnoreCase(" "))&&(!s.substring(b+1,b+2).equalsIgnoreCase(" ")))
			return 0;
		else
			return 1;
	}
	else
		return 2;
}





private String addressverify(String s,String sub)throws java.lang.Exception  
{
	int len,pos2,pos3,startindex;
	String part1,part2;
	
	int check; 
	if(s.trim().indexOf(sub)==0)		/* to check and strip the Email add. from the mail and rcpt command*/
	{
		if(sub.equalsIgnoreCase("from"))
		{
			startindex=4;
		}
		else if(sub.equalsIgnoreCase("to"))
			startindex=2;
		else
			return "notok";

		part1=s.substring(startindex).trim();
	
		if(part1.indexOf(':')==0)
		{

			part2=part1.substring(1).trim();
			check=domainverify(part2);			/* to check the validity of domain address*/

			if(check==0)
			{
				len=part2.length();
				pos2=part2.indexOf("@");
				pos3=part2.indexOf(".");
				if(pos2==0)
					return "notok";
	
						/* if E-mail add is enclosed in'<  >'*/
		
				if(part2.substring(0,1).equalsIgnoreCase("<")&&part2.substring(len-1).equalsIgnoreCase(">"))       
				{
							/*   when enclosed <  > check for space in b/w sender's id , domain name */
		
					if((part2.substring(1,pos2).indexOf(" ")==-1)&&(part2.substring(pos3+1,len-2).indexOf(" ")==-1))
						return part2.substring(1,len-1);
	
					else
						return "notok";		/* spaces in name or domain */
				}
				else if(part2.substring(0,1).equalsIgnoreCase("<"))
					return "notok";
	
				else if(part2.substring(len-1).equalsIgnoreCase(">"))
					return "notok";

				else
				{						/*   when not enclosed in <  > check for space in b/w sender's id , domain name */
					if((part2.substring(0,pos2).indexOf(" ")==-1)&&(part2.substring(pos3+1).indexOf(" ")==-1))
						return part2.substring(0,len);
					else
						return "notok";      /* spaces in names or domain*/
				}
			}
			else
				return "notokdomain";   /* domain invalid*/
			
		}
		else   /* does not contain ':'*/
		{
			
			return "notok";
		}

	}
	else	/* not contains from or to*/
	{
		return "notok";
	}
}






public void run(){              /* override run() method of runnable */

	int messgid;
	String command="";
	String temp="";
	String fulldata="";
	String authent="";
	String from="";
	String to="";
	String receptent[]=new String[20];       /* for multiple receptents */
	for (int i=0;i<20; i++ )
	{
		receptent[i]="";				/* initialize receptent */
	}
	int number=0;
	String address="";
	int state;
	char ch;

	firewallcheckup wall=new firewallcheckup();

	try{
			int pos;
			InetAddress IA=s.getInetAddress();  /* to get the ip of the client*/
			address=IA.toString();
			pos=address.indexOf('/');   /* to extract only the ip striping the host name*/
			address=address.substring(pos+1);
			System.out.println(IA);
	   }catch(Exception ie)
		{
		    System.out.println(ie);
		}
	
	try
	{
		
		ch='1';
		state=0;
		int check;
		int pos=0;
		Date date=new Date();
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(s.getInputStream())) ;
		PrintWriter bout = new PrintWriter(s.getOutputStream(),true ) ;      

		bout.println("220 project.com Email Server 1.0 ;"+date);


		while(ch!='4')
		{
			command="";
			temp="";
						
			temp=bin.readLine();
			temp=temp.trim();
			if(temp.length()<4)
				ch='8';
			else
			{
				command=temp.substring(0,4);
			
				if (command.equalsIgnoreCase("mail"))
					ch='1';
				else if(command.equalsIgnoreCase("rcpt"))
					ch='2';
				else if (temp.equalsIgnoreCase("data"))
					ch='3';
				else if (temp.equalsIgnoreCase("quit"))
					ch='4';
				else if (temp.equalsIgnoreCase("rset"))
					ch='5';
				else if (temp.equalsIgnoreCase("noop"))
					ch='6';
				else if(command.equalsIgnoreCase("helo"))
					ch='0';
				else if(temp.equalsIgnoreCase("help"))
					ch='7';
				else
					ch='8';
			}
			try{
				switch(ch)
				{

					case '0':	if(state!=0)
									bout.println("503 project.com Duplicate HELO/EHLO ");
								else if(temp.length()==4)
								{
									bout.println("501 helo requires Domain address ");
									continue;
								}
								else if(!temp.substring(4,5).equalsIgnoreCase(" ") || temp.length()<6)
								{
									bout.println("541 Invalid choice.");
									continue;
								}
								else
								{
									authent=temp.substring(5);
									bout.println("250 project.com Hello "+authent+"["+address+"], pleased to meet you");
									state=1;
								}
								break;

					
					case '1':   if(state>1)							/* for MAIL */
								{
							 		bout.println("503 Sender already specified.");
									continue;
								}
								command=temp.substring(4);
								if(!command.substring(0,1).equalsIgnoreCase(" "))
								{
									bout.println("501 Syntax error in parameters scanning  "+command);
									continue;
								}
								authent=addressverify(command.trim(),"from");
					
								if(authent.equalsIgnoreCase("notokdomain"))
								{
									pos=command.trim().indexOf(":");  /* to get the mail id*/
			
			
									if(pos==-1)
									{
										bout.println("501 Syntax error in parameters scanning  "+command);
										continue;
									}
									else
									{
										check=domainverify(command.trim().substring(pos+1));    /* redundent but used to pass appropriate mesg. */
										System.out.println(command.trim().substring(pos+1));
										System.out.println(pos);
									}									
									if(check==1)
										bout.println("501 "+command.trim().substring(pos+1)+"... Sender domain must exist");
									else if(check==2)
										bout.println("553 "+command.trim().substring(pos+1)+"... Correct domain name required ");
									continue;

								}
								else if(authent.equalsIgnoreCase("notok"))
								{
									bout.println("501 Syntax error in parameters scanning  "+command);
									continue;
								}
								else
								{
										bout.println("250 "+authent+" ... Sender ok"); 
										state=2;
										from=authent;
								}
								break;

					
					case '2':	if(state!=2&&state!=3)							/* for RCPT */
								{
									bout.println("503 Need Mail before RCPT...");
									continue;
								}
								command=temp.substring(4);
								if(!command.substring(0,1).equalsIgnoreCase(" "))
								{
									bout.println("501 Syntax error in parameters scanning  "+command);
									continue;
								}
								authent=addressverify(command.trim(),"to");
									
								if(authent.equalsIgnoreCase("notokdomain"))
								{
									pos=command.trim().indexOf(":");  /* to get the mail id*/

									if(pos==-1)
									{
										bout.println("501 Syntax error in parameters scanning  "+command);
										continue;
									}
									else
										check=domainverify(command.substring(pos+1));    /* redundent but used to pass appropriate mesg. */

									if(check==1)
										bout.println("501 "+command.trim().substring(pos+1)+"... Sender domain must exist");
									else if(check==2)
										bout.println("553 "+command.trim().substring(pos+1)+"... Correct domain name required ");
									continue;
								}
								else if(authent.equalsIgnoreCase("notok"))
								{
									bout.println("501 Syntax error in parameters scanning  "+command);
									continue;
								}
								else
								{
										bout.println("250 "+authent+" ... Receptent ok"); 
										state=3;
										receptent[number]=authent;
										number++;
								}
								break;
								
								

					case '3':	if(state!=3)
								{
									bout.println("503 Need RCPT before data....");
								}
								else
								{
									bout.println("354 Enter mail, end with . on a line by itself");

									while((temp=bin.readLine()).compareTo(".")!=0)
										fulldata=fulldata+temp+'\n';
									
									fulldata=wall.contentfiltering(fulldata);/* full data now filtered */

									FileReader fr=new FileReader("msgid.txt");
									BufferedReader br=new BufferedReader(fr);
									temp=br.readLine();
									messgid=Integer.parseInt(temp);
									System.out.println(messgid);
									fr.close();
									FileWriter f2=new FileWriter("spool.txt",true);
									for(int i=0;i<number;i++)
									{
										++messgid;

										to=receptent[i];
										for(int j=0;j<number;j++)
										{
											if(i!=j)							/* to add multiple receptents in the data part */ 
												to=to+","+receptent[j];
										}

										f2.write("Message ID:"+messgid+"\n");
										f2.write("From: "+from+"\nTo: "+to+"\n"+"Date: "+date+"\n");
										f2.write(fulldata);
										f2.write(".\n");
									}
										temp=Integer.toString(messgid);
										FileWriter f1=new FileWriter("msgid.txt");
										f1.write(temp);
										f1.close();
										f2.close();
										bout.println("250 Message accepted for delivery..");
										state=1;
										for (int i=0;i<20; i++ )
										{
											receptent[i]="";				/* initialize receptent */
										}
										number=0;
								}	
								break;

					case '4':	break;          /* quit */

					case '5':	state=1;		/* rset */
								bout.println("250 Reset state   ");
								break;

					case '6':	bout.println("250 OK");   /* noop */
								break;

                    case '7':   bout.println("214-This is project.com Email Server version 1.0");
								bout.println("214-Topics:");
								bout.println("214-    HELO    MAIL    RCPT    DATA");
								bout.println("214-    RSET    NOOP    QUIT    HELP");
								bout.println("214-To report bugs in the implementation send email to");
								bout.println("214-deepakchudhary02@yahoo.com");
								bout.println("214-For local information send email to Postmaster at your site.");
								bout.println("214 End of HELP info");
						        break;

					case '8':  
					default :	bout.println("500 Command unrecognized:  "+temp);
				}
			}catch(Exception ee)
					    {
						   bout.println("541 Invalid choice. ");
						   System.out.println(ee);
					    }

		}
		bout.println("221 project.com closing connection.");
		s.close();
		
	}catch(Exception e)
	{
		System.out.println(e);
	}
}

}



class smtpserver {

public static void main(String[] args)throws Exception
{
	
	int i=-1;
	ServerSocket a=new ServerSocket(25);
	Socket s=null;
	Socket p=s;
	while (true)
	{
		try
		{	
			p=a.accept();
			if(p!=null)
			{
				i=(i+1)%3;
				s=p;
				p=null;
			}
			if(i==0)
			{
				new multi("One",s);
			}
			if(i==1)
			{
				new multi("Two",s);
			}
			if(i==2)
			{
				new multi("Three",s);
			}
		}catch(InterruptedException e)
		{
			System.out.println("main interrupted");
		}
	}

}
}