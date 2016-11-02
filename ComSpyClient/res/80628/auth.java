import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;
import java.sql.*;


public class auth extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  String name=null;
	  String login=null;
	  String pass=null;
	  String password=null;
	  String temp=null;
	  String user_id;

	  int pos;
	  boolean flag=false;

      name = request.getParameter("name");
	  pass = request.getParameter("pass");

	  name=name.toLowerCase();
	  
	  if(!name.equals("")&&!pass.equals(""))
	  {

			FileReader fr=new FileReader("C:/jsdk2.1/webpages/WEB-INF/servlets/unames.txt");
			BufferedReader br1=new BufferedReader(fr);

	   	    while((temp=br1.readLine())!=null)
			{
				pos=temp.indexOf("\t");  /* user name is extracted and verified*/
				login=temp.substring(0,pos);
				password=temp.substring(pos+1);
	
				if(login.equals(name)&&password.equals(pass))
					flag=true;
			}
			fr.close();

	 	    user_id=name+"@project.com";			/* here add the domain name of ur server */

	        if(flag)
			{
			  out.println("<html>");
			  out.println("<FRAMESET COLS=\"15%,*\">");
			  out.println("<FRAME NAME=\"static\" SRC=\"http://10.49.32.212:8080/servlet/staticframe?name="+name+"&pass="+pass+" \" NORESIZE>");
			  out.println("<FRAME NAME=\"frame\" SRC=\"http://10.49.32.212:8080/servlet/welcome?name="+name+"&pass="+pass+"\"  NORESIZE >");
			  out.println("</FRAMESET>");
			  out.println("</html>");
		    }
		    else
		    {
			  out.println("<html><body  bgcolor=\"CECFC5\" text=\"#0000FF\" vlink=\"#0f00F\">");
		      out.println("<p><font size=4 color=\"#ffffff\">Sorry !! Login Failed....RETRY...</font></p><br><br>");
			  out.println("<form method=POST action=http://127.0.0.1:8080/servlet/auth>");
			  out.println(" <p>User Name <input type=text size=20 name=name value="+name+"></p>");
		  	  out.println("<p>Password &nbsp;&nbsp;&nbsp;<input type=password size=20 name=pass></p>");
			  out.println("<p><input type=submit name=B1 value=Login></p>");
			  out.println("</form><p>&nbsp;</p></body></html>");
			}

      }
	  else
	  {
			  out.println("<html><body  bgcolor=\"CECFC5\" text=\"#0000FF\" vlink=\"#0f00F\">");
		      out.println("<p><font size=4 color=\"#ffffff\">Enter the Login name and password both</font></p><br><br>");
			  out.println("<form method=POST action=http://127.0.0.1:8080/servlet/auth>");
			  out.println(" <p>User Name <input type=text size=20 name=name value="+name+"></p>");
		  	  out.println("<p>Password &nbsp;&nbsp;&nbsp;<input type=password size=20 name=pass></p>");
			  out.println("<p><input type=submit name=B1 value=Login></p>");
			  out.println("</form><p>&nbsp;</p></body></html>");
	  }
	  out.close();
    }
	 
}
