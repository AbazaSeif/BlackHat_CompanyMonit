import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;

public class registrationform extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

      out.println("<html><body bgcolor=\"#CECFC5\"><br>");
	  out.println("<font size=5 color=\"#0000ff\"><strong>Personal Information</strong></font><br>");
	  out.println("<br><font size=3 color=\"red\"><strong>Login name</strong> must be single word and <strong>Password</strong> must be atleast 5 character long</font><table border=0>");
	  out.println("<form method=POST action=http://127.0.0.1:8080/servlet/registeruser>");
	  out.println("<table border=0>");
	  out.println("<br><tr><td>Login Name</td><td><input type=text size=20 name=user></td></tr>");
	  out.println("<tr><td>User Name</td><td><input type=text size=20 name=name></td></tr>");
	  out.println("<tr><td>Password</td><td><input type=password size=20 name=pass></td></tr>");
	  out.println("<tr><td>Roll Number</td><td><input type=text size=20 name=roll></td></tr>");
	  out.println("<tr><td>Department</td><td><input type=text size=20 name=dep></td></tr>");
	  out.println("<tr><td>Course</td><td><input type=text size=20 name=course></td></tr>");
	  out.println("<tr><td>Year</td><td><input type=text size=20 name=year></td></tr>");
	  out.println("</table><p><input type=submit name=B1 value=Submit></p>");
	  out.println("</form></body></html>");

  }
} 