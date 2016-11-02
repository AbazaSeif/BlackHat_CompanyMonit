// Source File Name:   Keys.java


public class Keys
{

    public Keys()
    {
    }

    public String[] generate()
    {
        Table table = new Table();
        char ac[] = new char[29];
        char ac1[] = new char[29];
        char ac2[] = new char[57];
        String as[] = new String[17];
        char ac3[] = new char[65];
        String s = "";
        long l1 = 0x133457799bbcdff1L;
        for(s = Long.toBinaryString(l1); s.length() < 64; s = "0" + s);
        s.getChars(0, 64, ac3, 1);
        for(int i = 1; i < 57; i++)
            ac2[i] = ac3[table.PC[i]];

        for(int j = 1; j < 29; j++)
            ac[j] = ac2[j];

        int l = 1;
        for(int k = 29; k < 57;)
        {
            ac1[l] = ac2[k];
            k++;
            l++;
        }

        Oneleftshift oneleftshift = new Oneleftshift();
        for(int i1 = 1; i1 < 17; i1++)
            if(i1 == 1 || i1 == 2 || i1 == 9 || i1 == 16)
                oneleftshift.leftshift(1, i1, ac, ac1, ac2, as);
            else
                oneleftshift.leftshift(2, i1, ac, ac1, ac2, as);

        return as;
    }
}