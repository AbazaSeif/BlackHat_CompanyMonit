// Source File Name:   Exor.java


public class Exor
{

    public Exor()
    {
    }

    public void xor(char c, int i, char ac[], String as[], String as1[], int ai[])
    {
        char ac1[] = new char[33];
        char ac2[] = new char[49];
        int ai1[] = new int[49];
        if(c == 'l')
            as[i - 1].getChars(1, as[i - 1].length(), ac1, 1);
        else
            as[i].getChars(1, as[i].length(), ac1, 1);
        for(int j = 1; j < 49; j++)
            ac2[j] = ac1[ai[j]];

        as1[i] = " ";
        for(int k = 1; k < 49; k++)
        {
            ai1[k] = ac2[k] - 48 ^ ac[k] - 48;
            as1[i] += ai1[k];
        }

    }
}