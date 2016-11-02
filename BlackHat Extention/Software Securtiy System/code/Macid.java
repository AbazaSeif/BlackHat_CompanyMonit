// Source File Name:   Macid.java

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.StringTokenizer;

public class Macid
{

    public Macid()
    {
    }

    private static final String getMacAddress()
        throws IOException
    {
        try
        {
        String s = System.getProperty("os.name");
        if(s.startsWith("Windows"))
            return windowsParseMacAddress(windowsRunIpConfigCommand());
            throw new IOException("unknown operating system: " + s);
        }
        catch(ParseException parseexception)
        {
            parseexception.printStackTrace();
            throw new IOException(parseexception.getMessage());
        }
    }

    private static final String windowsParseMacAddress(String s)
        throws ParseException
    {
        String s1 = null;
        try
        {
            s1 = InetAddress.getLocalHost().getHostAddress();
        }
        catch(UnknownHostException unknownhostexception)
        {
            unknownhostexception.printStackTrace();
            throw new ParseException(unknownhostexception.getMessage(), 0);
        }
        StringTokenizer stringtokenizer = new StringTokenizer(s, "\n");
        String s2 = null;
        while(stringtokenizer.hasMoreTokens()) 
        {
            String s3 = stringtokenizer.nextToken().trim();
            if(s3.endsWith(s1) && s2 != null)
                return s2;
            int i = s3.indexOf(":");
            if(i > 0)
            {
                String s4 = s3.substring(i + 1).trim();
                if(windowsIsMacAddress(s4))
                    s2 = s4;
            }
        }
        ParseException parseexception = new ParseException("cannot read MAC address from [" + s + "]", 0);
        parseexception.printStackTrace();
        throw parseexception;
    }

    private static final boolean windowsIsMacAddress(String s)
    {
        return s.length() == 17;
    }

    private static final String windowsRunIpConfigCommand()
        throws IOException
    {
        Process process = Runtime.getRuntime().exec("ipconfig /all");
        BufferedInputStream bufferedinputstream = new BufferedInputStream(process.getInputStream());
        StringBuffer stringbuffer = new StringBuffer();
        do
        {
            int i = bufferedinputstream.read();
            if(i != -1)
            {
                stringbuffer.append((char)i);
            } else
            {
                String s = stringbuffer.toString();
                bufferedinputstream.close();
                return s;
            }
        } while(true);
    }

    String showid()
    {
        String s = "";
        try
        {
            s = getMacAddress();
        }
        catch(Throwable throwable)
        {
            throwable.printStackTrace();
        }
        return s;
    }
}