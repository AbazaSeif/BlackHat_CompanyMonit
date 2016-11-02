// Source File Name:   Dsastore.java

import java.io.*;
import java.math.BigInteger;

public class Dsastore
{

    public Dsastore()
    {
    }

    public void writein(BigInteger biginteger, BigInteger biginteger1, BigInteger biginteger2, BigInteger biginteger3)
        throws Exception
    {
        String s = biginteger.toString();
        String s1 = biginteger1.toString();
        String s2 = biginteger2.toString();
        String s3 = biginteger3.toString();
        FileWriter filewriter = new FileWriter("public.dat");
        filewriter.write("p");
        filewriter.write(s);
        filewriter.write("q");
        filewriter.write(s1);
        filewriter.write("g");
        filewriter.write(s2);
        filewriter.write("y");
        filewriter.write(s3);
        filewriter.close();
    }

    public BigInteger[] readout()
        throws Exception
    {
        FileReader filereader = new FileReader("public.dat");
        String s = "";
        String s1 = "";
        String s2 = "";
        String s3 = "";
        String s4 = "";
        System.out.println();
        int i;
        while((i = filereader.read()) != -1) 
        {
            char c = (char)i;
            s = s + c;
        }
        filereader.close();
        boolean flag1;
        boolean flag2;
        boolean flag3;
        boolean flag = flag1 = flag2 = flag3 = false;
        for(int j = 0; j < s.length(); j++)
        {
            char c1 = s.charAt(j);
            if(c1 == 'p')
            {
                flag = true;
                flag1 = flag2 = flag3 = false;
            } else
            if(c1 == 'q')
            {
                flag1 = true;
                flag = flag2 = flag3 = false;
            } else
            if(c1 == 'g')
            {
                flag2 = true;
                flag = flag1 = flag3 = false;
            } else
            if(c1 == 'y')
            {
                flag3 = true;
                flag = flag1 = flag2 = false;
            }
            if(flag)
                s1 = s1 + c1;
            else
            if(flag1)
                s2 = s2 + c1;
            else
            if(flag2)
                s3 = s3 + c1;
            else
            if(flag3)
                s4 = s4 + c1;
        }

        s1 = s1.substring(1);
        s2 = s2.substring(1);
        s3 = s3.substring(1);
        s4 = s4.substring(1);
        BigInteger biginteger = new BigInteger(s1, 10);
        BigInteger biginteger1 = new BigInteger(s2, 10);
        BigInteger biginteger2 = new BigInteger(s3, 10);
        BigInteger biginteger3 = new BigInteger(s4, 10);
        BigInteger abiginteger[] = new BigInteger[4];
        abiginteger[0] = biginteger;
        abiginteger[1] = biginteger1;
        abiginteger[2] = biginteger2;
        abiginteger[3] = biginteger3;
        return abiginteger;
    }
}