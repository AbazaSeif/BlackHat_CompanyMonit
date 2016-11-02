// Source File Name:   Rsadecsign.java

import java.io.FileWriter;
import java.math.BigInteger;

public class Rsadecsign
{

    public Rsadecsign()
    {
    }

    public String doit(String s, String s1)
        throws Exception
    {
        String s2 = "";
        int ai[] = new int[3];
        String s3 = s1.substring(s1.length() - 30, s1.length());
        s1 = s1.substring(0, s1.length() - 30);
        Rsastore rsastore = new Rsastore();
        ai = rsastore.getinteger(s3);
        int i = ai[0];
        int j = ai[1];
        int k = ai[2];
        FileWriter filewriter = new FileWriter("cipher.dat");
        filewriter.write(s1);
        filewriter.close();
        String s4 = new String();
        while(s1.length() > 0) 
        {
            String s5 = s1.substring(0, 8);
            s1 = s1.substring(8);
            long l = Long.parseLong(s5);
            BigInteger biginteger = BigInteger.valueOf(l);
            BigInteger biginteger1 = BigInteger.valueOf(k);
            BigInteger biginteger2 = biginteger.pow(j).mod(biginteger1);
            long l1 = biginteger2.longValue();
            s2 = s2 + (char)(int)l1;
        }
        return s2;
    }
}