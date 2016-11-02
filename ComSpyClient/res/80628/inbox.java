import javax.servlet.*;
import javax.servlet.http.*;  
import java.net.* ;
import java.io.*;

public class inbox extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

		String user_id,name,temp,sub="",from,date,data="",filename,pass;
	    int i,pos,count=0;
		String folder=null;
        response.setContentType("Text/html");
	  	PrintWriter out = response.getWriter();
		
		name= request.getParameter("name");
		pass= request.getParameter("pass");
		filename= request.getParameter("fname");
		
		user_id=name+"@project.com";	
			
				
			/* now make a connection to smtp server and send the mail */ 

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
			s=bin.readLine();			/* first line is hand shake */
			
			bout.println("user "+name);
			s=bin.readLine();
			reply=s.substring(0,1);

			if(reply.equals("+"))
			{
				bout.println("pass "+pass);
				s=bin.readLine();
				reply=s.substring(0,1);
				if(reply.equals("+"))
				{	
					bout.println("FOLD "+filename);
					//System.out.println(filename);
					bin.readLine();

					bout.println("stat");
					s=bin.readLine();
					if(s.substring(4,5).equals("0"))
					{
						out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
					  out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
					  out.println("<font size=4 color=\"red\"><strong>No mails in this folder</font></strong>");
					  out.println("</body></html>");
						bout.println("quit");
					   out.close();	
					}

					folder=filename.toUpperCase();
					out.println("<html><body bgcolor=\"#CECFC5\">");
					out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp;</p>");
					out.println("<center> <table border=1 ><caption><font size=4 color=\"red\"><b>"+folder+"</b></caption><tr><th width=25%><font size=4 color=\"blue\" >Sender</font></th>");
					out.println("<th width=15%><font size=4 color=\"blue\">Date</font></th>");
					out.println("<th width=30%><font size=4 color=\"blue\">Subject</font></th>");

					bout.println("exls");
					while(!(s=bin.readLine()).equals("."))
					{
						count++;
						i=s.indexOf("\t");
						from=s.substring(0,i);
						s=s.substring(i).trim();
						i=s.indexOf("\t");
						date=s.substring(0,i);
						sub=s.substring(i).trim();
										
						out.println("<tr><td align=\"center\"><font size=3 color=\"red\">"+from+"</font></th>");
					    out.println("<td align=\"center\"><font size=3 color=\"red\">"+date+"</font></th>");
						out.println("<td><a href=\"http://127.0.0.1:8080/servlet/read_messg?name="+name+"&pass="+pass+"&messg_id="+count+"&fname="+filename+"\"><font size=3 color=purple>"+sub+"</font></a></th>");
						
				    }
					out.println("</tr></table>");
					bout.println("quit");
				}
				else
				{
					out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
				out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
				out.println("<font size=4 color=\"red\"><strong>Connection reset by the server.</font></strong>");
				out.println("</body></html>");

			    }
			}
			else
	        {
				out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
				out.println("Username/Passrord	Incorrect.");
			    out.println("</body></html>");
			}
		out.close();
	
	
   }
 }
