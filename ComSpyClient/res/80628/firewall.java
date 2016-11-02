import java.io.*;

public class firewall
{

int addblockedsite(String name) throws Exception
{
	String dname;
	int flag=1;
	FileWriter fw;
	FileReader fr=new FileReader("blockedsite.txt");
	BufferedReader br = new BufferedReader(fr);
	while((dname=br.readLine())!=null)
	{
		if(dname.equals(name))
			flag=0;
	}
	fr.close();
	if(flag==1)
	{
		fw=new FileWriter("blockedsite.txt",true);
		fw.write(name+"\n");
		fw.close();
	}
	return(flag);
}


int delblockedsite(String name) throws Exception
{
	String dname;
	int flag=0;
	FileWriter fw;
	FileReader fr=new FileReader("blockedsite.txt");
	BufferedReader br = new BufferedReader(fr);
	name=name.trim();
	while((dname=br.readLine())!=null)
	{
		if(dname.equals(name))
			flag=1;
	}
	fr.close();
	if(flag==1)
	{
		fr=new FileReader("blockedsite.txt");
		br=new BufferedReader(fr);
		fw=new FileWriter("temp.txt");
		while((dname=br.readLine())!=null)
		{
			if(!(dname.equals(name)))
			fw.write(dname+"\n");
		}
		fr.close();
		fw.close();
		fr=new FileReader("temp.txt");
		br=new BufferedReader(fr);
		fw=new FileWriter("blockedsite.txt");
		while((name=br.readLine())!=null)
		fw.write(name+"\n");
		fw.close();
		fr.close();
		fw=new FileWriter("temp.txt");
		fw.close();
	}
	return(flag);
}







int addfilteringwords(String name) throws Exception
{
	String dname;
	int flag=1;
	FileWriter fw;
	FileReader fr=new FileReader("filteringkeywords.txt");
	BufferedReader br = new BufferedReader(fr);
	while((dname=br.readLine())!=null)
	{
		if(dname.equals(name))
			flag=0;
	}
	fr.close();
	if(flag==1)
	{
		fw=new FileWriter("filteringkeywords.txt",true);
		fw.write(name+"\n");
		fw.close();
	}
	return(flag);
}


int delfilteringwords(String name) throws Exception
{
	String dname;
	int flag=0;
	FileWriter fw;
		
	FileReader fr=new FileReader("filteringkeywords.txt");
		BufferedReader br = new BufferedReader(fr);
		while((dname=br.readLine())!=null)
		{
			if(dname.equals(name))
				flag=1;
		}
		fr.close();
		if(flag==1)
		{
			fr=new FileReader("filteringkeywords.txt");
			br=new BufferedReader(fr);
			fw=new FileWriter("temp.txt");
			while((dname=br.readLine())!=null)
			{
				if(!(dname.equals(name)))
				fw.write(dname+"\n");
			}
			fr.close();	
			fw.close();
			fr=new FileReader("temp.txt");
			br=new BufferedReader(fr);
			fw=new FileWriter("filteringkeywords.txt");
			while((name=br.readLine())!=null)
			fw.write(name+"\n");
			fw.close();
			fr.close();
			fw=new FileWriter("temp.txt");
			fw.close();
		}
		return(flag);
	
}



int adddns(String name) throws Exception
{
	String dname,check;
	int flag=1,pos;
	FileWriter fw;
	FileReader fr=new FileReader("dns.txt");
	BufferedReader br = new BufferedReader(fr);
		pos=name.indexOf("\t");
		check=name.substring(0,pos);
	while((dname=br.readLine())!=null)
	{
			//	System.out.println(dname);
		pos=dname.indexOf("\t");
			//	System.out.println(pos);
		dname=dname.substring(0,pos);
			//	System.out.println(dname);
		if(dname.equals(check))
		{
			//System.out.println("hello");
			flag=0;
		}
	}
	fr.close();
	if(flag==1)
	{
		fw=new FileWriter("dns.txt",true);
		fw.write(name+"\n");
		fw.close();
	}
	return(flag);
}


int deldns(String name) throws Exception
{
	String dname,check;
	int flag=0,pos;
	FileWriter fw;
	FileReader fr=new FileReader("dns.txt");
	BufferedReader br = new BufferedReader(fr);
	name=name.trim();
	while((dname=br.readLine())!=null)
	{
		pos=dname.indexOf("\t");
		check=dname.substring(0,pos);
		if(check.equals(name))
			flag=1;
	}
	fr.close();
	if(flag==1)
	{
		fr=new FileReader("dns.txt");
		br=new BufferedReader(fr);
		fw=new FileWriter("temp.txt");
		while((dname=br.readLine())!=null)
		{
			pos=dname.indexOf("\t");
			check=dname.substring(0,pos);
			if(!(check.equals(name)))
			fw.write(dname+"\n");
		}
		fr.close();
		fw.close();
		fr=new FileReader("temp.txt");
		br=new BufferedReader(fr);
		fw=new FileWriter("dns.txt");
		while((name=br.readLine())!=null)
		fw.write(name+"\n");
		fw.close();
		fr.close();
		fw=new FileWriter("temp.txt");
		fw.close();
	}
	return(flag);
}






}