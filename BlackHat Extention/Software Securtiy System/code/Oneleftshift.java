// Source File Name:   Oneleftshift.java


public class Oneleftshift
{

    public Oneleftshift()
    {
    }

    public void leftshift(int i, int j, char ac[], char ac1[], char ac2[], String as[])
    {
        Table table = new Table();
        char ac3[] = new char[49];
        char c = '\0';
        char c1 = '\0';
        int j1 = 0;
        for(int k1 = 0; k1 < i; k1++)
        {
            for(int k = 1; k < 29; k++)
            {
                if(k == 1)
                {
                    c = ac[k];
                    c1 = ac1[k];
                } else
                if(k <= 28)
                {
                    ac[k - 1] = ac[k];
                    ac1[k - 1] = ac1[k];
                }
                if(k == 28)
                {
                    ac[k] = c;
                    ac1[k] = c1;
                }
            }

        }

        j1 = 1;
        for(int l = 1; l < 57; l++)
            if(l > 28)
            {
                ac2[l] = ac1[j1];
                j1++;
            } else
            {
                ac2[l] = ac[l];
            }

        as[j] = " ";
        for(int i1 = 1; i1 < 49; i1++)
            as[j] += ac2[table.PC2[i1]];

    }
}