// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 5/29/2004 12:27:14 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Dsa.java

import java.math.BigInteger;
import java.util.Random;

class Dsa
{

    Dsa()
    {
    }

    public BigInteger[] doit()
        throws Exception
    {
        String s = "d411a4a0e393f6aab0f08b14d18458665b3e4dbdce2544543fe365cf71c8622412db6e7dd02bbe13d88c58d7263e90236af17ac8a9fe5f249cc81f427fc543f7";
        String s1 = "b20db0b101df0c6624fc1392ba55f77d577481e5";
        Random random = new Random();
        BigInteger biginteger = new BigInteger(s, 16);
        BigInteger biginteger1 = new BigInteger(s1, 16);
        BigInteger biginteger2 = BigInteger.valueOf(random.nextInt(biginteger.intValue()));
        BigInteger biginteger3 = biginteger2.modPow(biginteger.subtract(BigInteger.ONE).divide(biginteger1), biginteger);
        BigInteger biginteger4 = BigInteger.valueOf(random.nextInt(biginteger1.intValue()));
        BigInteger biginteger5 = biginteger3.modPow(biginteger4, biginteger);
        BigInteger biginteger6 = BigInteger.valueOf(random.nextInt(biginteger1.intValue()));
        Dsastore dsastore = new Dsastore();
        dsastore.writein(biginteger, biginteger1, biginteger3, biginteger5);
        BigInteger abiginteger[] = new BigInteger[2];
        abiginteger[0] = biginteger4;
        abiginteger[1] = biginteger6;
        return abiginteger;
    }
}