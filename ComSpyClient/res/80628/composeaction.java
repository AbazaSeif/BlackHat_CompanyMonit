import javax.servlet.*;
import javax.servlet.http.*;  
import java.net.* ;
import java.io.*;

public class composeaction extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

		String mail_to=null;
		String to=null;
	    String addon=null;
	    String subject=null;
	    String data=null;
		String name=null;
		String user_id=null;
		String data1=null;
		String data2=null;
		String data3=null;
		String recipients=null;
		int posdot,pos_comma;
		
	  
		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();
		
		mail_to = request.getParameter("mail_to");
		addon= request.getParameter("addon");
		subject = request.getParameter("subject");
		data= request.getParameter("data");
		name=request.getParameter("name");		
		
		user_id=name+"@project.com";

		if(!mail_to.equals(""))
		{

			if(!addon.equals(""))   /* more than one receptents */
			{
				mail_to=mail_to+","+addon;
			}
			
			recipients=mail_to;

			while((posdot=data.indexOf("\n."))!=-1)  /* to handle '\n.' in the data part*/
			{
				data1=data.substring(0,posdot);
				data2=data.substring(posdot+3);
				data3=data.substring(posdot+2,posdot+3);
				data=data1+"\n ."+data3+data2;
			}
				
			if(!subject.equals(""))			/* add subject to data */
				data="Subject: "+subject+"\n"+data;

			/* now make a connection to smtp server and send the mail */ 

			String s,reply;
			s="";
			reply="";

			Socket sock=null;
			boolean flag=false;

			try
			{
				sock=new Socket("10.49.32.212",25);  /* if connection refused by destination or not vavailable*/
			}catch(Exception e)
			{
				flag=false;
			}

			BufferedReader bin = new BufferedReader(new InputStreamReader(sock.getInputStream())) ;
			PrintWriter bout = new PrintWriter(sock.getOutputStream(),true ) ;  
			s=bin.readLine();			/* first line is hand shake */
			
			bout.println("mail from:"+user_id);

			
			s=bin.readLine();
			reply=s.substring(0,3);

			if(reply.equals("250"))
			{
				while((pos_comma=mail_to.indexOf(","))!=-1)
				{
					to=mail_to.substring(0,pos_comma);
					mail_to=mail_to.substring(++pos_comma);
					bout.println("rcpt to:"+to);
					s=bin.readLine();
					reply=s.substring(0,3);
					if(!reply.equals("250"))
					{
						flag=false;
						break;
					}
				}
							
				
				bout.println("rcpt to:"+mail_to);
				s=bin.readLine();
				reply=s.substring(0,3);
				if(reply.equals("250"))
				{
					bout.println("data");
					s=bin.readLine();
					reply=s.substring(0,3);
					if(reply.equals("354"))
					{
						bout.println(data);
						bout.println(".\n");
						s=bin.readLine();
						reply=s.substring(0,3);
					
						if(reply.equals("250"))
						{
							flag=true;
							bout.println("quit");
						}
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

			if(flag)
			{
				out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
				out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
				out.println("<font size=4 color=\"red\"><strong>Your mail has been sent to : </strong>"+recipients+"</font>");
				out.println("</body></html>");

			}
			else
			{
				out.println("<html><head><body bgcolor=\"#CECFC5\">");
				out.println("<font size=4 color=\"red\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
				out.println("<font size=4 color=\"red\">Unable to deliver Mail</font><p></p>");
	            out.println("<FORM action=http://10.49.32.212:8080/servlet/composeaction method=post><TABLE border=0>");
	            out.println("<TR><TD>Mail to</TD><TD><INPUT name=mail_to size=50></TD></TR>");
	            out.println("<TR><TD>cc</TD><TD><INPUT name=addon size=50></TD></TR>");
	            out.println("<TR><TD>Subject</TD><TD><INPUT name=subject size=50></TD></TR>");
	            out.println("<TR><TD></TD<TD><TEXTAREA cols=60 name=data rows=15></TEXTAREA></TD></TR>");
	            out.println("<TR>><TD><INPUT name=name type=hidden value="+name+"><INPUT type=submit value=Send></TD></TR>");
	            out.println("</TABLE><form></BODY></HTML>");
			}


		out.close();
		}
		else
		{
				
			out.println("<html><head><body bgcolor=\"#CECFC5\">");
	        out.println("<font size=4 color=\"red\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
			out.println("<font size=4 color=\"red\">Destination address must be provided</font> <p></p>");
     	    out.println("<FORM action=http://10.49.32.212:8080/servlet/composeaction method=post><TABLE border=0>");
	        out.println("<TR><TD>Mail to</TD><TD><INPUT name=mail_to size=50></TD></TR>");
	        out.println("<TR><TD>cc</TD><TD><INPUT name=addon size=50></TD></TR>");
	        out.println("<TR><TD>Subject</TD><TD><INPUT name=subject size=50></TD></TR>");
	        out.println("<TR><TD></TD<TD><TEXTAREA cols=60 name=data rows=15></TEXTAREA></TD></TR>");
	        out.println("<TR><TD><INPUT name=name type=hidden value="+name+"><INPUT type=submit value=Send></TD></TR>");
	        out.println("</TABLE><form></BODY></HTML>");
			out.close();
		    	
		}
   }
 }
