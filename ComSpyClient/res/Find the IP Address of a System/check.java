import java.io.*;
import java.net.*;

public class check
{
    public static void main(String args[])
    {
//        InetAddress ia=new InetAddress();
try{
        InetAddress ia=InetAddress.getLocalHost();
        System.out.println(ia.getHostAddress().toString());
//        System.out.println(InetAddress.getHostAddress());
}catch(Exception e)
{
System.out.println("Error");
}
    }
}