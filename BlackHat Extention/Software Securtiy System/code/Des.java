// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 5/29/2004 12:23:43 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Des.java

import java.io.*;

public class Des
{

    public Des()
    {
    }

    public String Desalg(String s)
        throws Exception
    {
        Table table = new Table();
        String s1 = new String();
        char ac[] = new char[65];
        String as[] = new String[17];
        char ac1[] = new char[57];
        char ac2[] = new char[29];
        char ac3[] = new char[29];
        char ac4[] = new char[65];
        String as1[] = new String[17];
        for(; s.length() < 64 || s.length() % 64 == 0; s = s + " ");
        String s2 = "";
        boolean flag = false;
        String s3 = "";
        String as3[] = new String[9];
        char ac5[] = new char[33];
        char ac6[] = new char[65];
        char ac7[] = new char[65];
        char ac8[] = new char[65];
        char ac9[] = new char[65];
        char ac10[] = new char[49];
        char ac11[] = new char[33];
        String as4[] = new String[17];
        String as5[] = new String[17];
        char ac12[] = new char[33];
        char ac13[] = new char[65];
        byte byte0 = 7;
        String s5 = "";
        int l2 = 0;
        FileWriter filewriter = new FileWriter("desplain.dat");
        filewriter.write(s);
        filewriter.close();
        try
        {
            FileReader filereader = new FileReader("desplain.dat");
            int j;
            while((j = filereader.read()) != -1) 
            {
                l2++;
                for(s5 = Integer.toBinaryString(j); s5.length() < 8; s5 = "0" + s5);
                s2 = s2 + s5;
            }
        }
        catch(IOException ioexception)
        {
            System.out.println(ioexception);
        }
        s5 = "";
        int i3 = 0;
        int j3 = 0;
        int l3 = l2 / 8;
        int k3 = l2 % 64;
        while(i3 < l3) 
        {
            String s4 = "";
            for(int k = 0; k < 17; k++)
            {
                as5[k] = " ";
                as4[k] = " ";
            }

            if(l2 - j3 == k3)
            {
                String s6 = s2.substring(j3, j3 + k3);
                s6.getChars(0, k3, ac6, 1);
            } else
            {
                String s7 = s2.substring(j3, j3 + 64);
                s7.getChars(0, 64, ac6, 1);
            }
            for(int l = 1; l < 65; l++)
                ac7[l] = ac6[table.IP[l]];

            as5[0] = " ";
            for(int i1 = 1; i1 < 33; i1++)
                as5[0] += ac7[i1];

            as4[0] = " ";
            for(int j1 = 33; j1 < 65; j1++)
                as4[0] += ac7[j1];

            Exor exor = new Exor();
            Keys keys = new Keys();
            String as2[] = keys.generate();
            SBox1 sbox1 = new SBox1();
            for(int i = 1; i < 17; i++)
            {
                as2[i].getChars(1, 49, ac10, 1);
                exor.xor('l', i, ac10, as4, as, table.E);
                int k1 = 1;
                int k2 = 1;
                for(; k1 < 9; k1++)
                {
                    as3[k1] = as[i].substring(k2, k2 + 6);
                    k2 += 6;
                }

                sbox1.Sbox1(1, as3, table.S1);
                sbox1.Sbox1(2, as3, table.S2);
                sbox1.Sbox1(3, as3, table.S3);
                sbox1.Sbox1(4, as3, table.S4);
                sbox1.Sbox1(5, as3, table.S5);
                sbox1.Sbox1(6, as3, table.S6);
                sbox1.Sbox1(7, as3, table.S7);
                sbox1.Sbox1(8, as3, table.S8);
                sbox1.Sub.getChars(0, 32, ac11, 1);
                sbox1.Sub = "";
                for(int l1 = 1; l1 < 33; l1++)
                    ac5[l1] = ac11[table.PF[l1]];

                as5[i - 1].getChars(1, 33, ac12, 1);
                as4[i] = " ";
                as5[i] = " ";
                for(int i2 = 1; i2 < 33; i2++)
                {
                    as4[i] += ac5[i2] - 48 ^ ac12[i2] - 48;
                    as5[i] = as4[i - 1];
                }

            }

            as5[16].getChars(1, 33, ac4, 1);
            as4[16].getChars(1, 33, ac4, 33);
            for(int j2 = 1; j2 < 65; j2++)
                s1 = s1 + ac4[j2];

            i3++;
            j3 += 64;
        }
        return s1;
    }
}