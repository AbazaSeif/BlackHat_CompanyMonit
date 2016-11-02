import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;



public class welcome extends HttpServlet
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
		
	  out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
	  out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
	  out.println("<font size=4 color=\"red\"><strong>Welcome to your mail-box</font></strong>");
	  out.println("</body></html>");
	  out.close();
   }

}
	  
