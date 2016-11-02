// Source File Name:   Rsaencsecu.java


public class Rsaencsecu
{

    public Rsaencsecu()
    {
    }

    public String doit(String s, String s1, String s2)
        throws Exception
    {
        String s3 = new String();
        String s4 = new String();
        Rsaencconf rsaencconf = new Rsaencconf();
        Rsaencsign rsaencsign = new Rsaencsign();
        s3 = rsaencconf.doit(s1, s2);
        s4 = rsaencsign.doit(s, s3);
        return s4;
    }
}