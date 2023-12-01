package rsa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void modPowerTest()
    {
        assertTrue( RSA.modPower(4, 13, 497) == 445 );
        assertTrue(RSA.modPower(23, 99, 46) == 23);
    }

    @Test
    public void inverseTest(){
        assertTrue(RSA.inverse(5L, 39L) == 8L);
        assertTrue(RSA.inverse(5L, 33L) == 20L);
        assertTrue(RSA.inverse(70L, 99L) == 58L);

    }

    @Test
    public void testRandomPrime(){
        long testPrime = RSA.randomPrime(1, 999);

        assertTrue(RSA.isPrime(testPrime));
    }

    @Test
    public void testIsPrime(){
        long testPrime = 13;

        assertTrue(RSA.isPrime(testPrime));

        testPrime = 7;

        assertTrue(RSA.isPrime(testPrime));
    }

    @Test
    public void testIsNotPrime(){
        long testNotPrime = 14; 
        assertTrue(!RSA.isPrime(testNotPrime));
        testNotPrime = 777; 
        assertTrue(!RSA.isPrime(testNotPrime));
    }


    @Test
    public void relativePrimeTest(){
        long testNum = 999;
        long relPrime = RSA.relativePrime(testNum); 
        assertTrue(RSA.gcd(relPrime, testNum) == 1);

        testNum = 575;
        relPrime = RSA.relativePrime(testNum); 
        assertTrue(RSA.gcd(relPrime, testNum) == 1);

        testNum = 651;
        relPrime = RSA.relativePrime(testNum); 
        assertTrue(RSA.gcd(relPrime, testNum) == 1);

    }

    @Test
    public void gcdTest(){
        assertTrue(RSA.gcd(18, 12) == 6);
        assertTrue(RSA.gcd(75, 15) == 15);
    }
}
