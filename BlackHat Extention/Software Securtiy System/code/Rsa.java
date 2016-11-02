// Source File Name:   Rsa.java

import java.util.Random;

class Rsa
{

    Rsa()
    {
    }

    public int[] doit(String s)
        throws Exception
    {
        Random random = new Random();
        int j1 = 0;
        byte byte0 = 20;
        int ai[] = new int[20];
        int ai1[] = new int[20];
        int k1 = 0;
        int l1 = 0;
        boolean flag = true;
        boolean flag1 = true;
        boolean flag3 = true;
        for(int i2 = 28; i2 < 70; i2++)
        {
            for(int j2 = 2; j2 < i2; j2++)
            {
                flag = true;
                if(i2 % j2 != 0)
                    continue;
                flag = false;
                break;
            }

            if(flag)
                ai[k1++] = i2;
        }

        int i = ai[random.nextInt(k1)];
        int j;
        do
            j = ai[random.nextInt(k1)];
        while(i == j);
        int k = i * j;
        int l = (i - 1) * (j - 1);
        for(int k2 = 2; k2 < byte0; k2++)
        {
            boolean flag2 = true;
            for(int l2 = 2; l2 <= k2; l2++)
                if(l % l2 == 0 && k2 % l2 == 0)
                    flag2 = false;

            if(flag2)
                ai1[l1++] = k2;
        }

        int i1 = ai1[random.nextInt(l1)];
        for(int i3 = 0; flag3; i3++)
            if((1 + l * i3) % i1 == 0)
            {
                j1 = (1 + l * i3) / i1;
                flag3 = false;
            }

        int ai2[] = new int[3];
        ai2[0] = j1;
        ai2[1] = i1;
        ai2[2] = k;
        return ai2;
    }
}