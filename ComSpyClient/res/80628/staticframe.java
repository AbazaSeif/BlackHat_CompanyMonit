import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;



public class staticframe extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  String name=null;
	  String pass=null;
	  String user_id=null;

	  name = request.getParameter("name");
	  pass = request.getParameter("pass");
	  
	  user_id=name+"@project.com";			/* here add the domain name of ur server */
		
	  out.println("<html><body bgcolor=\"#CECFC5\">");
	  out.println("<img src=\"C:/jsdk2.1/webpages/WEB-INF/servlets/1.bmp\" alt=\"IIT\"  BORDER=3><br><br>");
	  out.println("<font size=4 color=\"red\"><strong>Mail Box</font></strong><p>&nbsp</p>");
	  out.println("<a href=\"http://10.49.32.212:8080/servlet/inbox?name="+name+"&pass="+pass+"&fname=inbox \" TARGET=\"frame\">Inbox<br></a>");
	  out.println("<a href=\"http://10.49.32.212:8080/servlet/compose?name="+name+"&pass="+pass+" \" TARGET=\"frame\">Compose<br></a>");
	  out.println("<a href=\"http://10.49.32.212:8080/servlet/options?name="+name+"&pass="+pass+" \" TARGET=\"frame\">Options<br></a>");
	  out.println("<a href=\"http://10.49.32.212:8080/servlet/logout\" TARGET=\"_top\">Logout<br></a>");
	  out.println("</body></html>");
	  out.close();
   }
  
}
	  
