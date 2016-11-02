// Source File Name:   Dsasha.java


class Dsamsgdig
{

    Dsamsgdig()
    {
    }

    public String doit(String s)
    {
        String as[] = new String[5];
        as[0] = Long.toBinaryString(0x67452301L);
        as[1] = Long.toBinaryString(0xefcdab89L);
        as[2] = Long.toBinaryString(0x98badcfeL);
        as[3] = Long.toBinaryString(0x10325476L);
        as[4] = Long.toBinaryString(0xc3d2e1f0L);
        for(int i = 0; i < 5; i++)
            while(as[i].length() < 32) 
                as[i] = "0" + as[i];

        String as1[] = new String[80];
        for(int l = 0; l < 16; l++)
        {
            as1[l] = s.substring(0, 32);
            s = s.substring(32);
        }

        Dsamethod dsamethod = new Dsamethod();
        for(int j = 16; j <= 79; j++)
        {
            String s6 = dsamethod.exor(as1[j - 3], as1[j - 8], as1[j - 14], as1[j - 16]);
            as1[j] = dsamethod.S(1, s6);
        }

        String s1 = as[0];
        String s2 = as[1];
        String s3 = as[2];
        String s4 = as[3];
        String s5 = as[4];
        String s7 = new String();
        for(int k = 0; k < 79; k++)
        {
            String s8 = dsamethod.add(dsamethod.S(5, s1), dsamethod.f(k, s2, s3, s4), s5, as1[k], dsamethod.K(k));
            s5 = s4;
            s4 = s3;
            s3 = dsamethod.S(30, s2);
            s2 = s1;
            s1 = s8;
        }

        as[0] = dsamethod.add(as[0], s1);
        as[1] = dsamethod.add(as[1], s2);
        as[2] = dsamethod.add(as[2], s3);
        as[3] = dsamethod.add(as[3], s4);
        as[4] = dsamethod.add(as[4], s5);
        s = as[0] + as[1] + as[2] + as[3] + as[4];
        return s;
    }
}