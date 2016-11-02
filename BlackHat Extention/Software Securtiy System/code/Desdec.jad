// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 5/29/2004 12:24:53 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Desdec.java

import java.io.*;

class Desdec
{

    Desdec()
    {
    }

    public String Decrypt1(String s)
    {
        Table table = new Table();
        Exor exor = new Exor();
        char ac[] = new char[49];
        String as[] = new String[8];
        String s1 = new String();
        String as1[] = new String[17];
        String as2[] = new String[17];
        String as3[] = new String[9];
        char ac1[] = new char[33];
        String as4[] = new String[17];
        char ac2[] = new char[33];
        char ac3[] = new char[33];
        char ac4[] = new char[65];
        String as5[] = new String[17];
        String s2 = new String();
        try
        {
            FileWriter filewriter = new FileWriter("cipherdes.dat");
            filewriter.write(s);
            filewriter.close();
        }
        catch(Exception exception) { }
        Keys keys = new Keys();
        as5 = keys.generate();
        try
        {
            FileReader filereader = new FileReader("cipherdes.dat");
            int j2;
            while((j2 = filereader.read()) != -1) 
                s2 = s2 + (char)j2;
            filereader.close();
        }
        catch(IOException ioexception) { }
        for(int k2 = 0; k2 < s2.length(); k2 += 64)
        {
            String s3 = "";
            for(int i = 0; i < 17; i++)
            {
                as1[i] = " ";
                as4[i] = " ";
            }

            as1[16] += s2.substring(k2, k2 + 32);
            as4[16] += s2.substring(k2 + 32, k2 + 64);
            for(int k1 = 16; k1 > 0; k1--)
            {
                as5[k1].getChars(1, 49, ac, 1);
                exor.xor('r', k1, ac, as1, as2, table.E);
                int j = 1;
                int l1 = 1;
                for(; j < 9; j++)
                {
                    as3[j] = as2[k1].substring(l1, l1 + 6);
                    l1 += 6;
                }

                SBox1 sbox1 = new SBox1();
                sbox1.Sbox1(1, as3, table.S1);
                sbox1.Sbox1(2, as3, table.S2);
                sbox1.Sbox1(3, as3, table.S3);
                sbox1.Sbox1(4, as3, table.S4);
                sbox1.Sbox1(5, as3, table.S5);
                sbox1.Sbox1(6, as3, table.S6);
                sbox1.Sbox1(7, as3, table.S7);
                sbox1.Sbox1(8, as3, table.S8);
                sbox1.Sub.getChars(0, 32, ac2, 1);
                sbox1.Sub = "";
                for(int k = 1; k < 33; k++)
                    ac1[k] = ac2[table.PF[k]];

                as4[k1].getChars(1, 33, ac3, 1);
                for(int l = 1; l < 33; l++)
                {
                    as1[k1 - 1] += ac1[l] - 48 ^ ac3[l] - 48;
                    as4[k1 - 1] = as1[k1];
                }

            }

            as1[0].getChars(1, 33, ac4, 1);
            as4[0].getChars(1, 33, ac4, 33);
            for(int i1 = 1; i1 < 65; i1++)
                s3 = s3 + ac4[table.IPI[i1]];

            int j1 = 0;
            for(int i2 = 0; i2 < s3.length(); i2 += 8)
            {
                as[j1] = "";
                as[j1] = s3.substring(i2, i2 + 8);
                j1++;
            }

            for(int i3 = 0; i3 < j1; i3++)
            {
                int l2 = Integer.parseInt(as[i3], 2);
                s1 = s1 + (char)l2;
            }

        }

        return s1;
    }
}