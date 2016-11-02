
import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;
import java.net.* ;



public class options extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  String user_id,user,filename;
	  int i;
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  String name=null;
	  String pass=null;

	  name = request.getParameter("name");
	  pass = request.getParameter("pass");
	  
	  user_id= name+"@project.com";
		
	  out.println("<html><head><body bgcolor=\"#CECFC5\">");
	  out.println("<font size=4 color=\"red\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
	  out.println("<font size=4 color=\"blue\">Options</font> <br><br>");	

	  out.println("<form method=post action=\"http://10.49.32.212:8080/servlet/createnewfolder\">");
	  
	  out.println("<input type=hidden name=\"name\" value="+name+"><input type=hidden name=\"pass\" value="+pass+">");
	  out.println("<center> Create New Folder<center><BR> Folder Name:<input type=text size=20 name=foldname>");	
	  out.println("<input type=submit value=Submit><input type=reset value=Clear></form><P><HR><P>");	
	 

	  
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
					bout.println("lstf");
					bin.readLine();

					out.println("<center><font face=\"Monotype Corsiva\" size =4 color = \"blue\">The list of available folders for <font  size =4 color = \"red\">"+user_id+"</font> are:<P><font face=\"Arial\" size =3 color = \"indigo\">");	
					while(!(s=bin.readLine()).equals("."))
					{
						out.println("<a href=\"http://10.49.32.212:8080/servlet/inbox?name="+name+"&pass="+pass+"&fname="+s+"\">"+s+"</a><BR>");
					}		 
				}

			}
		out.println("<font size=4 color=red ><B>Connection reset by the server.</B>");
			bout.println("quit");
			
			out.println("</body></html>");
			out.close();
   }
}