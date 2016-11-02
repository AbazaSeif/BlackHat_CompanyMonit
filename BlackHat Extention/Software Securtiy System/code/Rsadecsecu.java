// Source File Name:   Rsadecsecu.java


public class Rsadecsecu
{

    public Rsadecsecu()
    {
    }

    public String doit(String s, String s1, String s2)
        throws Exception
    {
        String s3 = new String();
        String s4 = new String();
        Rsadecsign rsadecsign = new Rsadecsign();
        Rsadecconf rsadecconf = new Rsadecconf();
        s3 = rsadecsign.doit(s, s2);
        s4 = rsadecconf.doit(s1, s3);
        return s4;
    }
}