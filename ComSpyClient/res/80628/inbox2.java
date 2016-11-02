import javax.servlet.*;
import javax.servlet.http.*;            
import java.io.*;



public class inbox extends HttpServlet
{
   public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
   {
	  String user_id,user,temp,sub="",from,date,data="",filename;
	  int i,pos,count=0;
      response.setContentType("Text/html");
      PrintWriter out = response.getWriter();
		user_id= request.getParameter("user_id");
		filename= request.getParameter("fname");
		i=user_id.indexOf("@");
		user=user_id.substring(0,i);


			FileReader fr=new FileReader("C://jsdk2.1/webpages/WEB-INF/servlets/"+user+"/"+filename+".txt");
			System.out.println("FileReader opened.");
			BufferedReader br1=new BufferedReader(fr);

			out.println("<html><head><body bgcolor=\"#CECFC5\">");
			  out.println("<table border=0><tr><td ROWSPAN=2><strong><font size=4 color=\"red\">Mail Box&nbsp;&nbsp;&nbsp;&nbsp;</font></strong></td>");
			  out.println("<td ROWSPAN=2><font size=4 color=\"blue\">"+user_id+"</font></td></tr>");
			  out.println("<td><form method=post action=\"http://127.0.0.1:8080/servlet/mailserver1\"><input type=submit value=\"logout\" ></form></td>");
			 out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/inbox\"><input type=hidden name=\"fname\" value=\"inbox\"><input type=submit value=\"Inbox&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\" >");
			 out.println("<input type=hidden name=\"user_id\" value="+user_id+"></form>");
			  out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/compose\"><input type=submit value=\"Compose\" ><input type=hidden name=\"user_id\" value="+user_id+"></form></td>");
			  out.println("<td><font size=4 color=\"red\">Welcome to your mail box</font> </td>");
			  out.println("<tr><td><form method=post action=\"http://127.0.0.1:8080/servlet/options\"><input type=submit value=\"Options&nbsp;&nbsp;&nbsp;&nbsp;\" >");			  out.println("<input type=hidden name=\"user_id\" value="+user_id+"></form></td></tr>");
			  out.println("<tr><td ROWSPAN=19></td>");
			  out.println("<td ROWSPAN=19><table border=1><tr><th><font size=4 color=\"blue\">Sender</font></th>");
			 out.println("<th><font size=4 color=\"blue\">Date</font></th>");
			 out.println("<th><font size=4 color=\"blue\">Subject</font></th>");
			  	   	   try{
						while((temp=br1.readLine())!=null)
			{		
					temp=br1.readLine();
					temp=br1.readLine();
					pos=temp.indexOf(":");
					temp=temp.substring(++pos);
					from=temp.trim();
					temp=br1.readLine();
					temp=br1.readLine();
					temp=temp.substring(10,17);
					date=temp.trim();
					temp=br1.readLine();
					temp=temp.trim();
					if((temp.substring(0,8)).equals("Subject:"))
				{
						if(temp.length()>29)
							sub=temp.substring(9,29);
						else sub=temp.substring(8);

				}
				else
				{
						if(temp.length()>20)
						sub=temp.substring(0,20);
						else sub=temp;

				}
						while(!(temp=br1.readLine()).equals("."));
						
			out.println("<tr><td><font size=3 color=\"red\">"+from+"</font></th>");
			 out.println("<td><font size=3 color=\"red\">"+date+"</font></th>");
			 out.println("<td><a href=\"http://127.0.0.1:8080/servlet/read_messg?user_id="+user_id+"&messg_id="+count+"&fname="+filename+"\"><font size=3 color=purple>"+sub+"</font></a></th>");
					   }
					   out.println("</tr></table>");
					   }

			catch(Exception sa)
						   {
				System.out.println(sa);
						   }
			out.println("</body></html>");
			fr.close();
}
}