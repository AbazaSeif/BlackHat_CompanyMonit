import java.io.*;
import java.net.* ;
import java.util.Date;

class sending
{



private boolean verifyuser(String uname)throws java.lang.Exception{
	
	FileReader fr=new FileReader("unames.txt");  /* verify the user name from unames.txt file */
	BufferedReader br=new BufferedReader(fr);    /*in every line user names passwords are seperated by '\t' in that file */
	
	String s;	
	String match="";

	boolean flag=false;
	int pos;

	while((s=br.readLine())!=null)
		{
			pos=s.indexOf('\t');
			match=s.substring(0,pos);
			if (match.equals(uname))
				flag=true;
		}
	fr.close();
	return flag;
}



private String verifydns(String domain)throws java.lang.Exception{
	
	FileReader fr=new FileReader("dns.txt");  /* verify the user name from unames.txt file */
	BufferedReader br=new BufferedReader(fr);    /* user names are seperated by '\t' in that file */
	
	String s;	
	String match="";

	int pos=0;
	boolean flag=false;
	
	while((s=br.readLine())!=null)
		{
			pos=s.indexOf('\t');
			match=s.substring(0,pos);
			System.out.println(match);
			if (match.equals(domain))
			{
				flag=true;
				break;
			}
		}
	fr.close();
	
	if(flag)
		return s.substring(pos+1).trim();
	else
		return "notfound";

}





private boolean delivermail(String IP,int messageid)throws Exception
{
	
	String s,temp,infile,reply;
	String from_name,to_name,to;
	from_name="";
	to_name="";
	to="";
	s="";
	temp="";
	infile="";
	reply="";

	int pos1;
	Socket sock;
	
	boolean flag=false;
	try
	{
		sock=new Socket(IP,25);  /* if connection refused by destination or not vavailable*/
	}catch(Exception e)
	{
		flag=false;
		return flag;
	}

	BufferedReader bin = new BufferedReader(new InputStreamReader(sock.getInputStream())) ;
	PrintWriter bout = new PrintWriter(sock.getOutputStream(),true ) ;  
	s=bin.readLine();			/* first line is hand shake */
	
	FileReader f=new FileReader("spool.txt");
	BufferedReader br=new BufferedReader(f);

	temp="Message ID:"+messageid;	

	while((infile=br.readLine())!=null)
	{	
		if(infile.equals(temp))	
				{
					System.out.println("inside deliver");
					infile=br.readLine();
					from_name=infile.substring(6);
					
					infile=br.readLine();		/* next line from spool*/

					to=infile.substring(4);	
					pos1=infile.indexOf(",");   /* receptent name is till first ',' in this line */

					if(pos1==-1)				/* if single receptent then delimiter is '\n' */
						to_name=to;
					else							
						to_name=to.substring(0,pos1);

					System.out.println("inside deliver"+from_name);
					bout.println("mail from:"+from_name);

					s=bin.readLine();
					reply=s.substring(0,3);
					System.out.println("inside deliver"+reply);
					//System.out.println(name);
					if(reply.equals("250"))
					{
						bout.println("rcpt to:"+to_name);
						System.out.println("inside deliver"+to_name);
						s=bin.readLine();
						reply=s.substring(0,3);
						if(reply.equals("250"))
						{
							System.out.println("inside deliver"+reply);
							bout.println("data");
							s=bin.readLine();
							reply=s.substring(0,3);
							if(reply.equals("354"))

							{
								System.out.println("inside deliver"+reply);

								if(pos1!=-1)					/* if multiple receptents then data part will*/
									bout.println("To: "+to);   /* start from To: specifing the names of all recipients*/

								while((infile=br.readLine()).compareTo(".")!=0)
										bout.println(infile);

								System.out.println("data sent");
								bout.println(".\n");
								s=bin.readLine();
								reply=s.substring(0,3);
								System.out.println("inside deliver"+reply);
								if(reply.equals("250"))
									flag=true;
								else
									flag=false;
							}
							else
								flag=false;
						}
						else
							flag=false;
					}
					else
						flag=false;
				
				}/* end if(infile.equals(tem))	*/

	} /* end while((infile=br.readLine())!=null)*/
bout.println("quit");
sock.close();	
return flag;
}




private void daemondelivery(int messageid,String statusmessage)throws Exception
{
	String infile="";
	String from_name="";
	String to_name="";
	String data="";
	String fulldata="";
	String dom="";
	String user="";
	String IP="";
	String temp="";
	
	Date date=new Date();
	sending sendingclass=new sending();

	int pos1,pos2,pos3,id,total;
	
	System.out.println(statusmessage);	

	FileReader f=new FileReader("spool.txt");
	BufferedReader br=new BufferedReader(f);

	temp="Message ID:"+messageid;	
	System.out.println("inside daemondelivery");
	while((infile=br.readLine())!=null)
	{	
		if(infile.equals(temp))	
				{
					infile=br.readLine();
					from_name=infile.substring(6);
	
					infile=br.readLine();		/* next line from spool*/
					pos1=infile.indexOf(",");   /* receptent name is till first ',' in this line */
					if(pos1==-1)				/* if single receptent then delimiter is '\n' */
						pos1=infile.indexOf("\n");
					to_name=infile.substring(4);


					System.out.println(from_name);
					System.out.println(to_name);
					while((infile=br.readLine()).compareTo(".")!=0)  /* reads data part */
							data=data+infile+'\n';
					data+=".\n";

					fulldata  = "Subject: Mailer non-delivery notification -Unable to deliver the mail\n";

					if(statusmessage.equalsIgnoreCase("usernotfound")||statusmessage.equalsIgnoreCase("IPnotexistindnsfile")||statusmessage.equalsIgnoreCase("undeliveredtoIP"))
								fulldata += "Orignated From:"+from_name+"\n To:"+to_name+"\nReason : Either destination server down or invalid user\n";
					else if(statusmessage.equalsIgnoreCase("siteblocked"))	
								fulldata += "Orignated From:"+from_name+"\n To:"+to_name+"\nReason : Target domain is either inassessible to user from inside or is not allowed to provide service to this domain's user\n";

					fulldata += "Mail content:"+data;

					pos2=from_name.indexOf('@');

					user=from_name.substring(0,pos2).trim();
					dom=from_name.substring(++pos2).trim();

					System.out.println(user);

					if(dom.equalsIgnoreCase("project.com")) /* to a user in this server */ 
					{
						if(sendingclass.verifyuser(user))/* check the user in user file */
										/* user not exist now mailerdelivery is to be made to senders account*/
						{

							System.out.println("now sending to"+user);/* deliverinh message to user's inbox*/
							FileWriter f3=new FileWriter(user+"/inbox.txt",true);
							f3.write("0\n"+temp+"\n");
							f3.write("From: mailerdaemon@project.com\nTo: "+from_name+"\n"+"Date: "+date+"\n");
						    f3.write(fulldata);
							f3.close();
							
							
						}
						else
							System.out.println("mail dropped as invalid user on this server");
					}
					else					/* user in another server */
					{
						IP = sendingclass.verifydns(dom);
						if(!IP.equals("notfound"))			/* deliver to spool*/
						{
							FileReader f1=new FileReader("msgid.txt");
							BufferedReader b1=new BufferedReader(f1);
							temp=b1.readLine();
							f1.close();

							id=Integer.parseInt(temp);
							id++;
					//		System.out.println(id);
							temp=Integer.toString(id);

							FileWriter f11=new FileWriter("msgid.txt");
							f11.write(temp);
							f11.close();

							
							FileWriter f3=new FileWriter("spool.txt",true);
							f3.write("Message ID:"+id+"\n");
							f3.write("From: mailerdaemon@project.com\nTo: "+from_name+"\n"+"Date: "+date+"\n");
							f3.write(fulldata);
							f3.write(".\n");
							f3.close();
						}
						else
							System.out.println("mail dropped as invalid domain");			
						
					}


				} /* end if(infile.equals(temp))*/

	}/* end while((infile=br.readLine())!=null)*/
		
}




public static void main(String args[])throws Exception
{
	Socket sock;
	int total,sent,pos1,pos2,pos3;

	String s1="";
	String s2="";
	String temp="";
	String name="";
	
	String to_name="";			/* to get the first recipient(to whom the mail is to be delivered) */
	String from_name="";
	String IP="";
	String infile="";
	String to="";				/* for getting all recipients */
	String to_name_user="";     
	String to_name_dom="";
	String from_name_user="";
	String from_name_dom="";
	
	

	sending sendingclass=new sending();
	firewallcheckup wall=new firewallcheckup();
	
	FileReader f,f1,f2;

	while(true)
	{
		f1=new FileReader("msgid.txt");
		BufferedReader br=new BufferedReader(f1);
		s1=br.readLine();
		total=Integer.parseInt(s1);
		f1.close();

		f2=new FileReader("sent.txt");
		br=new BufferedReader(f2);
		s2=br.readLine();
		sent=Integer.parseInt(s2);
		f2.close();
	
		if(total-sent>0)
		{
			System.out.println("Entered sending");
			sent++;
			f=new FileReader("spool.txt");
			br=new BufferedReader(f);

			
			while((infile=br.readLine())!=null)
			{	
				temp="Message ID:"+sent;		/* since  now sent is the message id of first undelivered message*/
				if(infile.equals(temp))	
				{
					infile=br.readLine();
					from_name=infile.substring(6);
					
					infile=br.readLine();		/* next line from spool*/
					to=infile.substring(4);

					pos1=to.indexOf(",");   /* receptent name is till first ',' in this line */
					if(pos1==-1)				/* if single receptent then delimiter is '\n' */
						to_name=to;
					
					else
						to_name=to.substring(0,pos1);
			

					pos2=to_name.indexOf('@');
					to_name_user=to_name.substring(0,pos2).trim();
					to_name_dom=to_name.substring(++pos2).trim();
			

					pos3=from_name.indexOf('@');
					System.out.println(from_name);
					System.out.println(pos3);
					from_name_user=from_name.substring(0,pos3).trim();
					from_name_dom=from_name.substring(++pos3).trim();
					System.out.println("2");

					if((!wall.siteblocking(to_name_dom))||(!wall.siteblocking(from_name_dom)))
					{
						if(to_name_dom.equalsIgnoreCase("project.com")) /* to a user in this server */ 
						{
					
							if(!sendingclass.verifyuser(to_name_user))/* check the user in user file */
							{
								System.out.println("user not found");
								sendingclass.daemondelivery(sent,"usernotfound");	/* user not exist now mailerdelivery is to be made to senders account*/
							}
							else
							{
								System.out.println("user found");
								System.out.println(to_name_user);
								FileWriter f3=new FileWriter(to_name_user+"/inbox.txt",true);
								f3.write("0\n"+temp+"\n");
								f3.write("From: "+from_name+"\nTo: "+to+"\n");
							
								while((infile=br.readLine()).compareTo(".")!=0)
									f3.write(infile+"\n");


								f3.write(".\n");
								f3.close();
	
								
							}
						}
						else					/* user in another server */
						{
							System.out.println(to_name_dom);
							IP = sendingclass.verifydns(to_name_dom);
							System.out.println(IP);
							if(IP.equals("notfound"))  /* domain not in dns now mailerdelivery is to be made to senders account*/
							{
								sendingclass.daemondelivery(sent,"IPnotexistindnsfile");							
							}
							else
							{
								System.out.println("sending");
								if(!sendingclass.delivermail(IP,sent))			/*first deliver to external server */
								{
									System.out.println("undelivered to"+IP);
									sendingclass.daemondelivery(sent,"undeliveredtoIP");		/* if unsucessful then mailerdelivery */
								}
							}
						}/* end else user in another server*/

					}/* end if wall */

					else
					{
						sendingclass.daemondelivery(sent,"siteblocked");
					}

				}/* end if(infile.equals(temp))	*/

			}	/* end while((infile=br.readLine())!=null) */ 

			FileWriter f3=new FileWriter("sent.txt");
			temp=Integer.toString(sent);
			f3.write(temp);
			f3.close();
		}/* end if(total-sent>o) */
	
	}/* end while */

}/* end main */

}/* end sending*/




