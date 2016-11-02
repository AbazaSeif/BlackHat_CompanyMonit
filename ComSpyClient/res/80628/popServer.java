import java.io.* ;
import java.net.* ;
import java.util.Date;



//Authorization State************************************************
//*******************************************************************

class auth_state{
public String uname;
private Socket s;
private BufferedReader bin;
private PrintWriter bout;


//constructor ********************

auth_state(Socket p) throws java.io.IOException
{
s=p;
bin= new BufferedReader(new InputStreamReader(s.getInputStream())) ;
bout = new PrintWriter(s.getOutputStream(),true ) ;      

}

//verify username function ***************
private int vrfy_uname(String username)
throws Exception{
FileReader fr=new FileReader("unames.txt");
BufferedReader br=new BufferedReader(fr);
String s;
String match=username;
int a=0;
int pos;
while((s=br.readLine())!=null)
	{
	pos=s.indexOf('\t');
	match=s.substring(0,pos);
	if (match.equals(username))
		a=1;
	}
fr.close();
return a;
}

//verify password function ***************

private int vrfy_passwd(String passwd)
throws Exception{
FileReader fr=new FileReader("unames.txt");
BufferedReader br=new BufferedReader(fr);
String s;
int a=0;
String match=uname+"\t"+passwd;
while((s=br.readLine())!=null)
	{
		if (match.equals(s))
		a=1;
	}
fr.close();
return a;
}


//no_cmd function ******************

private void no_cmd() throws Exception
{
bout.println("-ERR Unknown command in AUTHORIZATION state");
}




//enter function *******************

public int enter() throws Exception
{

String cmd;
String temp;
char ch='0';
int flg=0;
int state=1;
while(ch!='4')
	{
	temp=bin.readLine();
	temp=temp.trim();
	if(temp.length()<4)
	no_cmd();
	else 
		{
		cmd=temp.substring(0,4);
		if(cmd.equalsIgnoreCase("user"))
		ch='1';
		else if(cmd.equalsIgnoreCase("pass"))
		ch='2';
		else if(cmd.equalsIgnoreCase("quit"))				
		ch='3';
		else ch='5';
		switch(ch)
			{
			case '1':if(temp.length()==4)
				bout.println("-ERR Missing UserName Argument.");
				else
				{
				if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing UserName Argument.");
				else
				{
				flg=vrfy_uname(cmd);
				if(flg==0)
				bout.println("-ERR Sorry, no mailbox for "+cmd+" here.");
				else
				{
				uname=cmd;
				state=2;
				bout.println("+OK UserName accepted, Password please.");
				}
				}
				}
				}
				break;

			case '2':
			if(temp.length()>4 && (temp.substring(4,5)).equals(" ") && state !=2)
				{
				bout.println("-ERR Need Username before Password");
				break;
				}
				if(temp.length()==4 && state !=2)
				{
				bout.println("-ERR Need Username before Password");
				break;
				}
				if(temp.length()==4)
				bout.println("-ERR Missing Password Argument.");
				else
				{
				if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing Password Argument.");
				else
				{
				flg=vrfy_passwd(cmd);
				if(flg==0)
				bout.println("-ERR Bad Login.");
				else
				{
					FileReader fr=new FileReader(uname+"/lock.txt");
					BufferedReader br=new BufferedReader(fr);
					cmd=br.readLine();
					fr.close();
					if(cmd.equals("0"))
					{
						FileWriter fe=new FileWriter(uname+"/lock.txt");
						fe.write("1");
						fe.close();
					bout.println("+OK mailbox for "+uname+" locked."); 
					ch='4';
				return 1;
					}
					else
					bout.println("-ERR mailbox for "+uname+" already locked.Try again later."); 

					
				
				}
				}
				}
				}
				break;
			case '3':if(temp.length()!=4)
				no_cmd();
				else
				return 0;
				
			default:no_cmd();			
			}     //switch ends
		}   //else of wrong cmd check ends
	}//while ends
	return 0;
} //enter function ends
}//class ends





//Transaction State**************************************************
//*******************************************************************

class trans_state{
private Socket s;
private BufferedReader bin;
private PrintWriter bout;
String username;
String filename="inbox";

//constructor trans_state()********************

trans_state(Socket p,String name) throws java.io.IOException
{
s=p;
bin= new BufferedReader(new InputStreamReader(s.getInputStream())) ;
bout = new PrintWriter(s.getOutputStream(),true ) ;      
username=name;
}

//error function ********************
private void no_cmd() throws Exception
{
bout.println("-ERR Unknown command in TRANSACTION state");
}



//help function ********************
private void help() throws Exception
{
//bout.println("+OK  Commands recognized by the server are:-");
bout.println("+OK This is project.com POP Server version 1.0");
bout.println("+OK Topics:");
bout.println("+OK   FOLD    STAT    LIST    DELE    RETR");
bout.println("+OK   RSET    NOOP    QUIT    HELP    MOVE");
bout.println("+OK   MAKE    LSTF    DELF	EXLS");
bout.println("+OK To report bugs in the implementation send email to");
bout.println("+OK piyush@project.com");
bout.println("+OK For local information send email to Postmaster at your site.");
bout.println("+OK End of HELP info");
}


//retr function ********************
private void retr(String msgid) throws Exception
	{
	int sno,len=0;
	String str;
	FileReader frr;
BufferedReader brr;
	try{
		sno=Integer.parseInt(msgid);
		//System.out.println("MsgId to be retrieved="+sno);
		if(sno<1)
		bout.println("-ERR Invalid Message Number");
		else
		{
		frr=new FileReader(username+"/"+filename+".txt");
		brr=new BufferedReader(frr);
		for(int i=1;i<sno;i++)
			{
				str=brr.readLine();
			if(str.equals("1"))
				{
				i--;
				while(!(str=brr.readLine()).equals("."));
				}
				else
				while(!(str=brr.readLine()).equals("."));
			}

		
		while(true)
			{
			str=brr.readLine();
		if(str.equals("0"))
			{
			//bout.println("cant reach here");
		do{
			bout.println(str);
		}while(!((str=brr.readLine()).equals(".")));
			bout.println(".");
			break;
			}
			else 
				{
			while(!((str=brr.readLine()).equals(".")));
			//bout.println("piyush");
				}
			}
		frr.close();
		
		}
	}
	catch(Exception ex)
		{
		bout.println("-ERR Invalid Message Number");
		}
	}
		




//rset function ********************
private void rset() throws Exception
{
FileReader frr;
BufferedReader brr;
String s;
int start=1;
FileWriter F;


frr=new FileReader(username+"/"+filename+".txt");
brr=new BufferedReader(frr);
F=new FileWriter(username+"/temp.txt");
while((s=brr.readLine())!=null)
	{
	if (start==1)
		{
		F.write("0\n");
		start=0;
		}
		else if(s.equals("."))
		{
			F.write(s+"\n");
			start=1;
		}
		else
			F.write(s+"\n");
	}
frr.close();
F.close();



frr=new FileReader(username+"/temp.txt");
			brr=new BufferedReader(frr);
			F=new FileWriter(username+"/"+filename+".txt");
			//start=1;
			while((s=brr.readLine())!=null)
			F.write(s+"\n");
			frr.close();
			F.close();
			F=new FileWriter(username+"/temp.txt");
			F.close();
			//bout.println("+OK Successfully Reset the State!");
}


//list() function *******************

private void list() throws Exception
{
	int sno=0,len=0;
	String str;
	FileReader frr;
	BufferedReader brr;
		frr=new FileReader(username+"/"+filename+".txt");
		brr=new BufferedReader(frr);
		bout.println("+OK Mailbox Scan Listing Follows.");
		while((str=brr.readLine())!=null)
	{
			if(str.equals("0"))
		{
			len+=str.length();
			while(!((str=brr.readLine()).equals(".")))
				len+=str.length();
			sno++;
			bout.println("+OK "+sno+" "+len);
			len=0;

		}
		else
			while(!(str=brr.readLine()).equals("."));

	}
		frr.close();
		
}



//list(msgid) function ********************
private void list(String msgid) throws Exception
	{
	int sno,len=0;
	String str;
	FileReader frr;
BufferedReader brr;
	try{
		sno=Integer.parseInt(msgid);
		//System.out.println("MsgId to be retrieved="+sno);
		if(sno<1)
		bout.println("-ERR Invalid Message Number");
		else
		{
		frr=new FileReader(username+"/"+filename+".txt");
		brr=new BufferedReader(frr);
		for(int i=1;i<sno;i++)
			{
				str=brr.readLine();
			if(str.equals("1"))
				{
				i--;
				while(!(str=brr.readLine()).equals("."));
				}
				else
				while(!(str=brr.readLine()).equals("."));
			}

			
			
			
			while(true)
			{
			str=brr.readLine();
			if(str.equals("0"))
			{
		do{
			len+=str.length();
		}while(!(str=brr.readLine()).equals("."));		
			bout.println("+OK "+msgid+" "+len);
			break;
			}
			else
			{
				while(!(str=brr.readLine()).equals("."));		
			}
			}
			frr.close();
		}
	}
	catch(Exception ex)
		{
		bout.println("-ERR Invalid Message Number");
		}
	}





//mark_del(msgid) function **************************

private void mark_del(String msgid) throws Exception
	{
	FileReader frr;
	BufferedReader brr;
	String s;
	int start=1,m_id,count=1;
	FileWriter F;
	try{
		m_id=Integer.parseInt(msgid);
		if(m_id<1)
		bout.println("-ERR Invalid Message Number");
		else
			{
			frr=new FileReader(username+"/"+filename+".txt");
			brr=new BufferedReader(frr);
			F=new FileWriter(username+"/temp.txt");
			while(true)
				{
				s=brr.readLine();
				if(start==1 && count==m_id && s.equals("0"))
					{
						F.write("1\n");
					//	F.write(s+"\n");
						while((s=brr.readLine())!=null)
							F.write(s+"\n");
					break;
						}
						//else ;
				/*while(!((s=brr.readLine()).equalsIgnoreCase(".")));
				start=0;
				count++;*/
					
				else if(start==1 && s.equals("0"))
					{
					F.write("0\n");
					count++;
					start=0;
					}
					else
					{
					F.write(s+"\n");
					if(s.equals("."))	
						{
						//	count++;
							start=1;
						}
					else start=0;
					}
				}
	
			frr.close();
			F.close();
			frr=new FileReader(username+"/temp.txt");
			brr=new BufferedReader(frr);
			F=new FileWriter(username+"/"+filename+".txt");
			//start=1;
			while((s=brr.readLine())!=null)
			F.write(s+"\n");
			frr.close();
			F.close();
			F=new FileWriter(username+"/temp.txt");
			F.close();
			bout.println("+OK Message "+msgid+" deleted successfully!");
			/*F=new FileWriter(username+"/temp.txt");
			F.close();	*/
			}
	}
		catch(Exception ex){
		bout.println("-ERR Invalid Message Number");
		
	}
}




//stat() function *******************

private void stat() throws Exception
{
	int sno=0,len=0;
	String str;
	FileReader frr;
	BufferedReader brr;
		frr=new FileReader(username+"/"+filename+".txt");
		brr=new BufferedReader(frr);
		while((str=brr.readLine())!=null)
		{
			if(str.equals("1"))
			{
				while(!(str=brr.readLine()).equals("."));
			}
			else 
			{
				sno++;
				len++;
				while(!(str=brr.readLine()).equals("."))
			{
		len+=str.length();
		}
		}
		}
		frr.close();
			bout.println("+OK "+sno+" "+len);
		
	}


//change_fol() function

private void change_fol(String folname) throws Exception
	{
	FileReader filecheck;
	try
		{
if(folname.equalsIgnoreCase("temp")||folname.equalsIgnoreCase("lock")||folname.equalsIgnoreCase("files"))
			{
			bout.println("-ERR no such folder exists");
			}
			else if(folname.equals(filename))
			{
			bout.println("-ERR Already in the folder \""+folname+"\".");
			}
else
			{
		filecheck=new FileReader(username+"/"+folname+".txt");
		filename=folname;
		bout.println("+OK Folder changed to "+filename+".");
		}
		}
	catch(Exception ex)
		{
		bout.println("-ERR no such folder exists");
		}
	}


//make_fol() function

private void make_fol(String folname) throws Exception
	{
	FileReader filecheck;
	FileWriter F;
	BufferedReader br;
	String str;
	int flag=1;
	filecheck=new FileReader(username+"/files.txt");
	br=new BufferedReader(filecheck);
	if(folname.equalsIgnoreCase("temp")||folname.equalsIgnoreCase("lock")||folname.equalsIgnoreCase("files"))
		{
		bout.println("-ERR Folder Name \""+folname+"\" reserved.");
		}
	else{
	while((str=br.readLine())!=null)
		{
		if(str.equals(folname))
			{
			bout.println("-ERR The folder with the name specified already exists.");
			flag=0;
			}
		}
		if(flag==1)
		{
			F=new FileWriter(username+"/"+folname+".txt");
			F.close();
			F=new FileWriter(username+"/files.txt",true);
			F.write(folname+"\n");
			F.close();
			bout.println("+OK New Folder "+folname+" created.");
		}
		
	}

	}


	
//del_fol() function

private void del_fol(String folname) throws Exception
	{
	FileReader filecheck;
	FileWriter F;
	BufferedReader br;
	String str;
	int flag=0;
	
	if(folname.equalsIgnoreCase("inbox"))
		{
		bout.println("-ERR Cant delete Inbox.");
		}
	else{
		filecheck=new FileReader(username+"/files.txt");
		br=new BufferedReader(filecheck);
	while((str=br.readLine())!=null)
		{
		if(str.equals(folname))
			{
				flag=1;
			}
		}
		filecheck.close();
		if(flag==0)
			bout.println("-ERR No folder with the name specified exists.");
		else
		{
			F=new FileWriter(username+"/temp.txt");
			filecheck=new FileReader(username+"/files.txt");
			br=new BufferedReader(filecheck);
			while((str=br.readLine())!=null)
			{
				if(!(str.equalsIgnoreCase(folname)))
					F.write(str+"\n");
			}
			F.close();
			filecheck.close();
			F=new FileWriter(username+"/files.txt");
			filecheck=new FileReader(username+"/temp.txt");
			br=new BufferedReader(filecheck);
			while((str=br.readLine())!=null)
				F.write(str+"\n");
			F.close();
			filecheck.close();
			F=new FileWriter(username+"/temp.txt");
			F.close();
			bout.println("+OK Folder "+folname+" deleted.");
		}
		
	}

	}






//exls() function

private void exls() throws Exception
	{
		//System.out.println(filename);
		FileReader fr=new FileReader(username+"/"+filename+".txt");
		BufferedReader br1=new BufferedReader(fr);
		String temp,from,date,sub;
		String fromfile,subject;
		subject="";
		int pos,pos_sub;
			try
			{
				while((temp=br1.readLine())!=null)
				{		
					temp=br1.readLine();
					temp=br1.readLine();

					pos=temp.indexOf(":");
					temp=temp.substring(++pos);
					from=temp.trim();

					temp=br1.readLine();
					
					temp=br1.readLine();
					temp=temp.substring(10,17);
					date=temp.trim();

					
					while(!(fromfile=br1.readLine()).equals("."))
					{
						fromfile=fromfile.trim();
						if(fromfile.substring(0,8).equalsIgnoreCase("Subject:"))
						{
							subject=fromfile.substring(8).trim();
							break;
						}
					}
					while(!(fromfile=br1.readLine()).equals("."));
					if(subject.length()>20)
						sub=subject.substring(0,20);
					else
						sub=subject;

					bout.println(from+"\t"+date+"\t"+sub+"\t");
			        System.out.println(from+"\n"+date+"\n"+sub+"\n");
					//break;
			   }
			   //System.out.println("");
			   fr.close();
			   bout.println(".");

		  }catch(Exception sa)
		  {
				System.out.println(sa);
    	   }
	}


	//move_messg() function
	
	private void move_messg(String msgid,String destn_folder) throws Exception
	{
		String temp;
		boolean flag=false;
		int messg_id;
		msgid=msgid.trim();
		destn_folder=destn_folder.trim();
		if(destn_folder.equalsIgnoreCase(filename))
			bout.println("-ERR Destination folder same as source folder.");
		else
		{
			FileReader fr=new FileReader(username+"/files.txt");
			BufferedReader br=new BufferedReader(fr);
			while((temp=br.readLine())!=null)
			{
				if(temp.equals(destn_folder))
					flag=true;
			}
			fr.close();
			if(flag==false)
				bout.println("-ERR Destination folder not present in user domain.");
			else
			{
				try{
				fr=new FileReader(username+"/"+filename+".txt");
				br=new BufferedReader(fr);
				FileWriter F1=new FileWriter(username+"/temp.txt");
				FileWriter F2=new FileWriter(username+"/"+destn_folder+".txt",true);
				messg_id=Integer.parseInt(msgid);
				for(int k=1;k<messg_id;k++)
					{
					while(!(temp=br.readLine()).equals("."))
					F1.write(temp+"\n");
					F1.write(".\n");
					}
					while(!(temp=br.readLine()).equals("."))
					F2.write(temp+"\n");
					F2.write(".\n");
					F2.close();
					while((temp=br.readLine())!=null)
					F1.write(temp+"\n");
					fr.close();
					F1.close();
					F1=new FileWriter(username+"/"+filename+".txt");
					fr=new FileReader(username+"/temp.txt");
					br=new BufferedReader(fr);
					while((temp=br.readLine())!=null)
						F1.write(temp+"\n");
					fr.close();
					F1.close();
					F1=new FileWriter(username+"/temp.txt");
					F1.close();
					bout.println("+OK Message "+messg_id+" moved successfully to folder "+destn_folder+".");
				}catch(Exception ex)
				{
					bout.println("-ERR The message number provided does not exist.");
				}
			}
		}
	}

//trans function *******************

public int trans() throws Exception
{

String cmd;
String temp;
char ch='0';
int index1,index2;
while(ch!='8')
	{
	temp=bin.readLine();
	temp=temp.trim();
	if(temp.length()<4)
	no_cmd();
	else 
		{
		cmd=temp.substring(0,4);
		if(cmd.equalsIgnoreCase("fold"))
		ch='1';
		else if(cmd.equalsIgnoreCase("retr"))
		ch='2';
		else if(cmd.equalsIgnoreCase("rset"))				
		ch='3';
		else if(cmd.equalsIgnoreCase("noop"))				
		ch='4';
	else if(cmd.equalsIgnoreCase("stat"))				
		ch='5';
	else if(cmd.equalsIgnoreCase("list"))				
		ch='6';
	else if(cmd.equalsIgnoreCase("dele"))				
		ch='7';
	else if(cmd.equalsIgnoreCase("quit"))				
		ch='8';
	else if(cmd.equalsIgnoreCase("make"))				
		ch='9';
	else if(cmd.equalsIgnoreCase("move"))				
		ch='a';
	else if(cmd.equalsIgnoreCase("lstf"))				
		ch='b';
	else if(cmd.equalsIgnoreCase("help"))				
		ch='c';
	else if(cmd.equalsIgnoreCase("delf"))				
		ch='d';
	else if(cmd.equalsIgnoreCase("EXLS"))				
		ch='e';
	
	else
			{
		no_cmd();
		continue;
			}


		switch(ch)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			{
			case '1':if(temp.length()==4)
				bout.println("-ERR Missing Folder Name Argument.");
				else {
					if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing Folder Name Argument.");
				else
				change_fol(cmd);				
				}
				}
				
				break;

			case '2':if(temp.length()==4)
				bout.println("-ERR Missing Message Number Argument.");
				else
				{
				if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing Message Number Argument..");
				else
				retr(cmd);				
				}
				}
				break;
			
			case '3':if(temp.length()==4)
				{
				rset();
				bout.println("+OK Reset State.");
				}
				else
					no_cmd();
				break;

			case '4':if(temp.length()==4)
				bout.println("+OK NOOP done");
				else 
					no_cmd();
				break;

			case '5':if(temp.length()==4)
				stat();
				else 
					no_cmd();
				break;

			case '6':if(temp.length()==4)
				list();
				else
				{
				if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				list();
				else
				list(cmd);				
				}
				}
				break;

			case '7':if(temp.length()==4)
				bout.println("-ERR Missing Message Number Argument.");
				else
				{
				if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing Message Number Argument..");
				else
				mark_del(cmd);				
				}
				}
				break;

			case '8':if(temp.length()==4)
				return 1;
				else 
					no_cmd();
				break;
			case '9':if(temp.length()==4)
				bout.println("-ERR Missing Folder Name Argument.");
				else {
					if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
				cmd=temp.substring(5);
				cmd=cmd.trim();
				if(cmd.equals(""))
				bout.println("-ERR Missing Folder Name Argument.");
				else
				make_fol(cmd);				
				}
				}
				
				break;


			case 'a':if(temp.length()==4)
				bout.println("-ERR Missing Message Number & Folder Name Arguments.");
				else if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				{
					index1=temp.indexOf(" ");
					index2=temp.lastIndexOf(" ");
					if(index1==index2)
						bout.println("-ERR Either Message Number or Folder Name not provided.");
					else
						move_messg(temp.substring(++index1,index2),temp.substring(++index2));
				}
				break;

			case 'b':if(temp.length()!=4)
				no_cmd();
				else
				{
					bout.println("+OK Folder Listing follows.");
					FileReader fr=new FileReader(username+"/files.txt");
					BufferedReader br=new BufferedReader(fr);
					String tem;
					while((tem=br.readLine())!=null)
						bout.println(tem);
					bout.println(".");
					fr.close();
				}
				break;

			case 'c':if(temp.length()!=4)
				no_cmd();
				else
				help();
				break;

			case 'd':if(temp.length()==4)
				bout.println("-ERR Folder Name to be deleted required.");
				else if(!((temp.substring(4,5)).equals(" ")))
				no_cmd();
				else
				del_fol(temp.substring(5));
				break;


			case 'e':  if(temp.length()!=4)
				              no_cmd();
				else
				{
					System.out.println("hiii"+filename);
				    exls();
				}
				break;
			}
		}
	}
	return 0;
}
}

//Updation State*****************************************************
//*******************************************************************

class update_state{
private Socket s;
private BufferedReader bin;
private PrintWriter bout;
private String username;

//constructor ********************

update_state(Socket p,String name) throws java.io.IOException
{
s=p;
bin= new BufferedReader(new InputStreamReader(s.getInputStream())) ;
bout = new PrintWriter(s.getOutputStream(),true ) ;      
username=name;
}






//update(fname) function *************************

private void update(String fname) throws Exception
	{
String s;
FileReader frr=new FileReader(username+"/"+fname+".txt");
BufferedReader brr=new BufferedReader(frr);
FileWriter F=new FileWriter(username+"/temp.txt");
while((s=brr.readLine())!=null)
				{
				if(s.equals("1"))
					{
				while(!((s=brr.readLine()).equalsIgnoreCase(".")));
					}
				else
					{
					F.write(s+"\n");
					while(!((s=brr.readLine()).equalsIgnoreCase(".")))
					F.write(s+"\n");
					F.write(".\n");
					}
				}
	
			frr.close();
			F.close();
			frr=new FileReader(username+"/temp.txt");
			brr=new BufferedReader(frr);
			F=new FileWriter(username+"/"+fname+".txt");

			while((s=brr.readLine())!=null)
			F.write(s+"\n");
			frr.close();
			F.close();


	}

//del_mails() function ***************************

void del_mails() throws Exception
	{
	FileReader fr;
	BufferedReader br;
	fr=new FileReader(username+"/files.txt");
	br=new BufferedReader(fr);
	String fname;
	while((fname=br.readLine())!=null)
		update(fname);
	FileWriter F=new FileWriter(username+"/temp.txt");
	F.close();
	F=new FileWriter(username+"/lock.txt");
	F.write("0");
	F.close();

	bout.println("+OK project.com POP Server 1.0 closing connection.");
	}

}









//to run multiple threads********************************************
//*******************************************************************
class multi1 implements Runnable{
	
	String name;
	Thread t;
	Socket s;


multi1(String threadname,Socket p)throws Exception{		/* constructor */
	
	name=threadname;
	t=new Thread(this,name);
	System.out.println("New thread: "+t);
	t.start();
	s=p;
}




//release_lock() method***********************************************************************
//*********************************************************************************

 private void release_lock(String user_name) throws Exception
	{
	 try{
		FileWriter F=new FileWriter(user_name+"/lock.txt");
		F.write("0");
		F.close();
	 }catch(Exception ee)
		{
		System.out.println(ee);
		}
	}



//run method***********************************************************************
//*********************************************************************************



public void run(){              /* override run() method of runnable */

	int messgid;
	int state;
	char ch;
	int flg=0,flg1;
	String user_name="";
	
	try
	{
		auth_state authorize=new auth_state(s);
	BufferedReader bin = new BufferedReader(new InputStreamReader(s.getInputStream())) ;
	PrintWriter bout = new PrintWriter(s.getOutputStream(),true ) ;      

		bout.println("+OK project.com POP3 Server 1.0 ready.");
		flg=authorize.enter();
		if(flg==1)
		{
			user_name=authorize.uname;
			trans_state transaction=new trans_state(s,user_name);
			flg1=transaction.trans();
			if(flg1==1)
			{
				update_state update=new update_state(s,user_name);
				update.del_mails();
			}
		}

		
//		bout.println("+OK Deepak-Piyush.edu POP Server 1.0 closing connection.");
		s.close();
		
	}catch(Exception e)
	{						
		try{
		//if connection reset by client abruptly
			if(flg==1)
			release_lock(user_name);
		}catch(Exception a){
		System.out.println(a);
		}
		

	}
}

}



class popServer {

public static void main(String[] args)throws Exception
{
	
	int i=-1;
	ServerSocket a=new ServerSocket(110);
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
				new multi1("One",s);
			}
			if(i==1)
			{
				new multi1("Two",s);
			}
			if(i==2)
			{
				new multi1("Three",s);
			}
		}catch(InterruptedException e)
		{
			System.out.println("main interrupted");
		}
	}

}
}