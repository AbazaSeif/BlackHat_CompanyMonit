// Source File Name:   Dsamethod.java

import java.io.PrintStream;

public class Dsamethod
{

    public Dsamethod()
    {
    }

    public String f(int i, String s, String s1, String s2)
    {
        Dsamethod dsamethod = new Dsamethod();
        if(i > 79)
            System.out.println("INVALID");
        if(i >= 0 && i <= 19)
            return dsamethod.or(dsamethod.and(s, s1), dsamethod.and(dsamethod.not(s), s2));
        if(i >= 20 && i <= 39)
            return dsamethod.exor(dsamethod.exor(s, s1), s2);
        if(i >= 40 && i <= 59)
            return dsamethod.or(dsamethod.or(dsamethod.and(s, s1), dsamethod.and(s, s2)), dsamethod.and(s1, s2));
        else
            return dsamethod.exor(dsamethod.exor(s, s1), s2);
    }

    public String K(int i)
    {
        String s = "";
        if(i >= 0 && i <= 19)
            s = Long.toBinaryString(0x5a827999L);
        else
        if(i >= 20 && i <= 39)
            s = Long.toBinaryString(0x6ed9eba1L);
        else
        if(i >= 40 && i <= 59)
            s = Long.toBinaryString(0x8f1bbcdcL);
        else
        if(i >= 60 && i <= 79)
            s = Long.toBinaryString(0xca62c1d6L);
        for(; s.length() < 32; s = "0" + s);
        return s;
    }

    public String S(int i, String s)
    {
        Dsamethod dsamethod = new Dsamethod();
        return dsamethod.or(dsamethod.lshift(s, i), dsamethod.rshift(s, i - 32));
    }

    public String not(String s)
    {
        String s1 = "";
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) == '0')
                s1 = "1" + s1;
            else
                s1 = "0" + s1;

        return s1;
    }

    public String or(String s, String s1)
    {
        String s2 = "";
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) == '0' && s1.charAt(i) == '0')
                s2 = "0" + s2;
            else
                s2 = "1" + s2;

        return s2;
    }

    public String and(String s, String s1)
    {
        String s2 = "";
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) == '1' && s1.charAt(i) == '1')
                s2 = "1" + s2;
            else
                s2 = "0" + s2;

        return s2;
    }

    public String exor(String s, String s1)
    {
        String s2 = "";
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) == s1.charAt(i))
                s2 = "0" + s2;
            else
                s2 = "1" + s2;

        return s2;
    }

    public String exor(String s, String s1, String s2, String s3)
    {
        String s4 = "";
        Dsamethod dsamethod = new Dsamethod();
        s4 = dsamethod.exor(s, s1);
        s4 = dsamethod.exor(s4, s2);
        s4 = dsamethod.exor(s4, s3);
        return s4;
    }

    public String lshift(String s, int i)
    {
        for(int j = 0; j < i; j++)
        {
            s = s.substring(1);
            s = s + "0";
        }

        return s;
    }

    public String rshift(String s, int i)
    {
        for(int j = 0; j < i; j++)
        {
            s = s.substring(0, s.length() - 1);
            s = "0" + s;
        }

        return s;
    }

    public String add(String s, String s1)
    {
        String s2 = new String();
        String s3 = "0";
        for(int i = s.length() - 1; i >= 0; i--)
            if(s.charAt(i) == '0' && s1.charAt(i) == '0')
            {
                if(s3 == "0")
                {
                    s2 = "0" + s2;
                } else
                {
                    s2 = "1" + s2;
                    s3 = "0";
                }
            } else
            if(s.charAt(i) == '1' && s1.charAt(i) == '1')
            {
                if(s3 == "0")
                {
                    s2 = "0" + s2;
                    s3 = "1";
                } else
                {
                    s2 = "1" + s2;
                    s3 = "1";
                }
            } else
            if(s3 == "0")
            {
                s2 = "1" + s2;
                s3 = "0";
            } else
            {
                s2 = "0" + s2;
                s3 = "1";
            }

        return s2;
    }

    public String add(String s, String s1, String s2, String s3, String s4)
    {
        String s5 = "";
        Dsamethod dsamethod = new Dsamethod();
        s5 = dsamethod.add(s, s1);
        s5 = dsamethod.add(s5, s2);
        s5 = dsamethod.add(s5, s3);
        s5 = dsamethod.add(s5, s4);
        return s5;
    }
}