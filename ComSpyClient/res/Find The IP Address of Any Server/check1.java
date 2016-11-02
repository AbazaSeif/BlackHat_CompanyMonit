 import java.net.*;

public class check1{

    public static void main (String[] args) {
        try{
            String cacheHostname=args[0];

            if (cacheHostname==""){
                System.err.println("Error: address argument required");
                return;
            }else{
                System.out.println("\tThe initial hostname is:\t"+cacheHostname);
            }

            System.out.println("\tThe IP address is:\t\t"+InetAddress.getByName(cacheHostname).getHostAddress().toString());
	    System.out.println("\tThe reverse lookup hostname is:\t"+InetAddress.getByName(InetAddress.getByName(cacheHostname).getHostAddress().toString()).getHostName());


         }catch (Exception e){
            System.out.println("Error:\t"+e);
        }

    }
}