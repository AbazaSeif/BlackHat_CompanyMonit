import java.io.*;

class firewallcheckup
{

public String contentfiltering(String data)throws Exception
{
	FileReader fr=new FileReader("filteringkeywords.txt");  
	BufferedReader br=new BufferedReader(fr); 
	int length,pos;
	int found;
	String s;
	String substr1="";
	String substr2="";
	String replacement="";

	while((s=br.readLine())!=null)
	{
		if ((found=data.indexOf(s))!=-1)
		{
			pos=data.indexOf(s);
			length=s.length();
			
			substr1=data.substring(0,pos);
			substr2=data.substring(pos+length);
			
			for(int i=0;i<length;i++)
				replacement+="*";
				
			data=substr1+replacement+substr2;	
		}
		
	}
	return data;
}



public boolean siteblocking(String sitename)throws Exception
{
	FileReader fr=new FileReader("blockedsite.txt");  
	BufferedReader br=new BufferedReader(fr); 
	
	String s;	
	boolean flag=false;
	
	while((s=br.readLine())!=null)
	{
		if (s.equals(sitename))
			flag=true;
	}
	fr.close();
	return flag;
}


};
