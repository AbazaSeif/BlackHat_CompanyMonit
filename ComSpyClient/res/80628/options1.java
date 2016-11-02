import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;



public class options extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  String user_id,name,temp,filename;
	  int i;
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();
		name= request.getParameter("name");
		pass= request.getParameter("pass");
		//filename= request.getParameter("fname");
		i=user_id.indexOf("@");
		user=user_id.substring(0,i);


			FileReader fr=new FileReader("C://jsdk2.1/webpages/WEB-INF/servlets/"+user+"/files.txt");
			//System.out.println("FileReader opened.");
			BufferedReader br1=new BufferedReader(fr);

			out.println("<html><head><body bgcolor=\"#CECFC5\">");
			  out.println("<table border=0><tr><td ROWSPAN=2><strong><font size=4 color=\"red\">Mail Box&nbsp;&nbsp;&nbsp;&nbsp;</font></strong></td>");
			  out.println("<td ROWSPAN=2><font size=4 color=\"blue\">"+user_id+"</font></td></tr>");
			  out.println("<td><form method=post action=\"http://127.0.0.1:8080/servlet/mailserver1\"><input type=submit value=\"logout\" ></form></td>");
			 out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/inbox\"><input type=hidden name=\"fname\" value=\"inbox\"><input type=submit value=\"Inbox&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" >");
			 out.println("<input type=hidden name=\"user_id\" value="+user_id+"></form>");
			  out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/compose\"><input type=submit value=\"Compose\" ><input type=hidden name=\"user_id\" value="+user_id+"></form></td>");
			  out.println("<td><font size=4 color=\"red\">Welcome to your Options Area.</font> </td>");
			  out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/options\"><input type=submit value=\"Options&nbsp;&nbsp;&nbsp;&nbsp;\" >");	
			  out.println("<input type=hidden name=\"user_id\" value="+user_id+"></form></td></tr>");
			  out.println("<tr><td ROWSPAN=19></td>");
			  out.println("<td ROWSPAN=19></table><HR><form method=POST action=http://127.0.0.1:8080/servlet/make_new_folder><P> ");
			  out.println("<input type=hidden name=\"user_id\" value="+user_id+"><center> Create New Folder<center><BR> Folder Name:<input type=text size=20 name=name>");
			  out.println("<input type=submit value=Submit><input type=reset value=Clear></form><P><HR><P>");
			  out.println("<center><font face=\"Monotype Corsiva\" size =4 color = \"blue\">The list of available folders for "+user_id+" are:<P><font face=\"Arial\" size =3 color = \"indigo\">");
			  	   	   try{
						while((temp=br1.readLine())!=null)
						{		
							out.println("<a href=\"http://127.0.0.1:8080/servlet/inbox?user_id="+user_id+"&fname="+temp+"\">"+ temp+"</a><BR>");
					   }
					   }

			catch(Exception sa)
						   {
				System.out.println(sa);
						   }
			out.println("</body></html>");
			fr.close();
}
}