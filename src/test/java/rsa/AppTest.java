package rsa;

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
    }

    @Test
    public void extendedEuclideanTest(){
        assertTrue(RSA.inverse(5L, 39L) == 8L);
    }

    @Test
    public void gcdTest(){
        assertTrue(RSA.gcd(18, 12) == 6);
    }
}
