import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;



public class logout extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  out.println("<html>");
      out.println("<head></head>");
	  out.println("<body bgcolor=\"#CECFC5\" text=\"#0000FF\" vlink=\"#0f00F\">");
	  out.println("<img src=\"C:/jsdk2.1/webpages/WEB-INF/servlets/1.bmp\" width=74 height=110 ALIGN=\"center\" BORDER=3 alt=\"IIT\">");
	  out.println("<FONT size=6><STRONG>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Welcome To project.com Mail Server</STRONG></FONT>");
	  out.println("<BR><FONT size=1>&nbsp;&nbsp;IIT-Kharagpur </FONT>");
	  out.println("<BR><FONT size=3 color=\"red\">&nbsp;&nbsp;&nbsp;&nbsp;You are sucessfully logged out</FONT>");
	  out.println("<form method=POST action=http://127.0.0.1:8080/servlet/auth >");
	  out.println("<font size=3 color=\"#0000ff\"><strong>Existing Users</strong></font><br><br>");
	  out.println("<table border=0><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;User Name</td><td><input type=text size=20 name=name></td><td>@project.com</td></tr>");
	  out.println("<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;Password</td><td><input type=password size=20 name=pass></td><td><input type=submit name=B1 value=login></td></tr>");
	  out.println("</table></form><br>");
	  out.println("<form method=POST action=http://127.0.0.1:8080/servlet/registrationform><font size=3 color=\"#0000ff\"><strong>New Users&nbsp;&nbsp;</strong></font>");
	  out.println("<input type=submit name=B1 value=Signup>");
	  out.println("</form></body></html>");
	  out.close();	
	    
   }
}
	  
