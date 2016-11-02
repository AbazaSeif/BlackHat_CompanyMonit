import java.io.*;

public class user
{
		
	
public void add(String name,String password)throws java.lang.Exception
{
	
	FileWriter fr=new FileWriter("unames.txt",true);  /* user names and passwords are seperated by '\t' in that file */
	
	fr.write(name+"\t"+password+"\n");
	fr.close();
			
}

public static void main(String args[])
{

	String name,password,temp;
	boolean flag=false;
	int pos;
	user usr=new user();
	try
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the user name :");
		name=br.readLine();
		
		FileReader fr=new FileReader("unames.txt");
		BufferedReader br1=new BufferedReader(fr);
		while((temp=br1.readLine())!=null)
		{
			pos=temp.indexOf("\t");  /* user name is extracted and verified*/
			temp=temp.substring(0,pos);
	
			if(temp.equalsIgnoreCase(name))
				flag=true;
		}
		fr.close();

		if(!flag)
		{		
			System.out.println("Enter the password :");
			password=br.readLine();

			File f1=new File(name);		/*create the directory for user */
			f1.mkdir();

			FileWriter fw=new FileWriter(name+"/noofmessg.txt",true);
			fw.write("0");
			fw.close();
		
			usr.add(name,password);
		}
		else
			System.out.println("user already exists");
			
	}catch(Exception e)
	{
		System.out.println(e);
	}
}

};
