import javax.servlet.*;
import javax.servlet.http.*;          
import java.net.* ;
import java.io.*;



public class read_messg extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  String user_id,name,temp,message_id,filename,pass;
	  int i,pos,count=0;
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

		name= request.getParameter("name");
		pass= request.getParameter("pass");
		message_id= request.getParameter("messg_id");
		filename= request.getParameter("fname");
		
		user_id=name+"@project.com";
		i=Integer.parseInt(message_id);

		String s,reply;
			s="";
			reply="";

			Socket sock=null;
			boolean flag=false;
			
			try
			{
				sock=new Socket("10.49.32.212",110);
				/* if connection refused by destination or not available*/
				
			}catch(Exception e)
			{
				System.out.println(e);
				flag=false;
			
			}

			BufferedReader bin = new BufferedReader(new InputStreamReader(sock.getInputStream())) ;
			PrintWriter bout = new PrintWriter(sock.getOutputStream(),true ) ;  
			s=bin.readLine();	

		out.println("<html><body bgcolor=\"#CECFC5\">");
		out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp;</p>");

		bout.println("user "+name);
		s=bin.readLine();
		reply=s.substring(0,1);

		if(reply.equals("+"))
		{
			bout.println("pass "+pass);
			s=bin.readLine();
			reply=s.substring(0,1);
			//System.out.println("pass reply "+reply+" "+pass);
			if(reply.equals("+"))
			{	
                    bout.println("FOLD "+filename);
				//	System.out.println(filename);
					bin.readLine();


				System.out.println(message_id);
			   	bout.println("retr "+message_id);   
				s=bin.readLine();
				s=bin.readLine();
				System.out.println(s+"\n");
				//while(!(s=bin.readLine()).equals("."))
				//{
					out.println("<font face=\"Monotype Corsiva\" size=4 color=\"blue\">");
					s=s.trim();
					while(!((s.substring(0,4)).equalsIgnoreCase("date")))
				{
						out.println(s+"<BR>");
						s=bin.readLine();
						System.out.println(s+"\n");
						
				}
					if(s.length()>4 && ((s.substring(0,4)).equalsIgnoreCase("date")))
				{
						out.println(s+"<BR>");
						s=bin.readLine();
				}

					if(s.length()>4 && ((s.substring(0,4)).equalsIgnoreCase("date")))
				{
						out.println(s+"<BR>");
						s=bin.readLine();
				}

					 if(s.length()>8 &&((s.substring(0,8)).equalsIgnoreCase("subject:")))
					{
						out.println(s+"<BR><P><BR>");
						out.println("<font face=\"Arial\" size=3 color=\"black\">");
					}
					else
					{
						out.println("Subject: None<BR><P><BR>");
						out.println("<font face=\"Arial\" size=3 color=\"black\">");
						out.println(s+"<BR>");
					}
					while(!(s=bin.readLine()).equals("."))
						out.println(s+"<BR>");
			}
		}
		out.close();
		bout.println("quit");
   }
}

						

				