import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;
import java.net.* ;



public class createnewfolder extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  String user_id,user,foldername;
	  int i;
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();

	  String name=null;
	  String pass=null;

	  name = request.getParameter("name");
	  pass = request.getParameter("pass");
	  foldername = request.getParameter("foldname");
	  
	  user_id= name+"@project.com";

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
			s=bin.readLine();	

		
		bout.println("user "+name);
		s=bin.readLine();
		reply=s.substring(0,1);

		if(reply.equals("+"))
		{
			bout.println("pass "+pass);
			s=bin.readLine();
			reply=s.substring(0,1);
			//System.out.println("pass reply "+reply+" "+pass);
			if(reply.equals("+"))
			{	
				bout.println("make "+foldername);
				s=bin.readLine();
				reply=s.substring(0,1);

				if(reply.equals("+"))
				{
					
					out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
					 out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
					out.println("<font size=4 color=\"red\"><strong>Folder "+foldername+" created successfully.</font></strong>");
					out.println("</body></html>");
				}
			
				else
				{
					out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
					 out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
					out.println("<font size=4 color=\"red\"><strong>Folder "+foldername+" cannot be created.</font></strong>");
					out.println("</body></html>");
				}
			}
			else
			{
				out.println("<html><body bgcolor=\"#CECFC5\"><p></p>");
				out.println("<font size=4 color=\"blue\"><strong>"+user_id+"</font></strong><p>&nbsp</p>");
				out.println("<font size=4 color=\"red\"><strong>Connection reset by the server.</font></strong>");
				out.println("</body></html>");
			}

		}
		bout.println("quit");
		out.close();
		
   }
}
