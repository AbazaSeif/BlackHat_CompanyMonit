import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;

public class compose extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
      String name=null;
	  String user_id=null;	
	  	
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  name= request.getParameter("name");
	  user_id=name+"@project.com";

      out.println("<html><head><body bgcolor=\"#CECFC5\">");
	  out.println("<font size=4 color=\"red\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
	  out.println("<font size=4 color=\"blue\">Compose Mail</font> <br><br>");
	  out.println("<FORM action=http://10.49.32.212:8080/servlet/composeaction method=post><TABLE border=0>");
	  out.println("<TR><TD>Mail to</TD><TD><INPUT name=mail_to size=50></TD></TR>");
	  out.println("<TR><TD>cc</TD><TD><INPUT name=addon size=50></TD></TR>");
	  out.println("<TR><TD>Subject</TD><TD><INPUT name=subject size=50></TD></TR>");
	  out.println("<TR><TD></TD><TD><TEXTAREA cols=60 name=data rows=15></TEXTAREA></TD></TR>");
	  out.println("<TR><TD><INPUT name=name type=hidden value="+name+"><INPUT type=submit value=Send></TD></TR>");
	  out.println("</TABLE><form></BODY></HTML>");
	  out.close();
  }
} 