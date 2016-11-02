import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;

public class registeruser extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {

		String user=null;
	    String name=null;
	    String pass=null;
	    String roll=null;
		String dep=null;
	    String course=null;
		String year=null;
		String reply=null;
		String statement=null;
	  
		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();
		addnewuser add = new addnewuser();

		user = request.getParameter("user");
		name= request.getParameter("name");
		pass = request.getParameter("pass");
		roll= request.getParameter("roll");
		dep= request.getParameter("dep");
		course= request.getParameter("course");
		year= request.getParameter("year");

		if(!user.equals("")&&!pass.equals(""))
		{
			reply=add.adduser(user,pass);

			if(reply.equals("ok"))
			{
				out.println("<html><body bgcolor=\"#CECFC5\"><br>");
				out.println("<p><font size=4 color=\"#0000ff\"><strong>Congratulation You are now a registered user of our server </strong></font></p><br>");
				out.println("<form method=POST action=http://127.0.0.1:8080/servlet/mailserver1>");
				out.println("<input type=submit name=B1 value=login>");
			    out.println("</form></body></html>");
			    out.close();		
			}
			else
			{
				if(reply.equals("namenotok"))
					statement="Login name incorrect";
				else if(reply.equals("passnotok"))
					statement="Password incorrect";
				else if(reply.equals("passlennotok"))
					statement="Password short";
				else if(reply.equals("userexist"))
					statement="User already exists";
				else if(reply.equals("other"))
					statement="Some problem occured in processing information PLEASE re-enter";
				
				out.println("<html><body bgcolor=\"#CECFC5\"><br>");
				out.println("<font size=5 color=\"#ffffff\"><strong>"+statement+"</strong></font><br>");
				out.println("<br><font size=3 color=\"red\"><strong>User name</strong> must be single word and <strong>Password</strong> must be atleast 5 character long</font><table border=0>");
  	            out.println("<form method=POST action=http://127.0.0.1:8080/servlet/registeruser>");
				out.println("<table border=0>");
			    out.println("<br><tr><td>Login Name</td><td><input type=text size=20 name=user></td></tr>");
			    out.println("<tr><td>User Name</td><td><input type=text size=20 name=name></td></tr>");
			    out.println("<tr><td>Password</td><td><input type=password size=20 name=pass ></td></tr>");
			    out.println("<tr><td>Roll Number</td><td><input type=text size=20 name=roll value="+roll+"></td></tr>");
			    out.println("<tr><td>Department</td><td><input type=text size=20 name=dep value="+dep+"></td></tr>");
			    out.println("<tr><td>Course</td><td><input type=text size=20 name=course value="+course+"></td></tr>");
			    out.println("<tr><td>Year</td><td><input type=text size=20 name=year value="+year+"></td></tr>");
  			    out.println("</table><p><input type=submit name=B1 value=Submit></p>");
			    out.println("</form></body></html>");
			
			}
		}
		else
		{
			    out.println("<html><body bgcolor=\"#CECFC5\"><br>");
				out.println("<FONT size=3 align=centercolor=\"#0000ff\"><strong>Enter user name and password both </FONT>");
				out.println("<form method=POST action=http://127.0.0.1:8080/servlet/registeruser>");
			    out.println("<p><font size=4 color=\"#0000ff\"><strong>Personal Information</strong></font></p><br>");
			    out.println("<table border=0>");
		        out.println("<tr><td>Login Name</td><td><input type=text size=20 name=user value="+user+"></td></tr>");
			    out.println("<tr><td>User Name</td><td><input type=text size=20 name=name value="+name+"></td></tr>");
			    out.println("<tr><td>Password</td><td><input type=password size=20 name=pass ></td></tr>");
			    out.println("<tr><td>Roll Number</td><td><input type=text size=20 name=roll value="+roll+"></td></tr>");
			    out.println("<tr><td>Department</td><td><input type=text size=20 name=dep value="+dep+"></td></tr>");
			    out.println("<tr><td>Course</td><td><input type=text size=20 name=course value="+course+"></td></tr>");
			    out.println("<tr><td>Year</td><td><input type=text size=20 name=year value="+year+"></td></tr>");
  			    out.println("</table><p><input type=submit name=B1 value=Submit></p>");
			    out.println("</form></body></html>");		
		}
   }
 }
