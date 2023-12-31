package rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class RSA {    
    private static final Random RNG = new Random(); 

    /**
     * @author Christian Walddman. 
     * @param args
     */
    public static void main (String args[])
    { 	
        Person Kassem = new Person(); 
        Person Elaine = new Person(); 

        String soupOrder = "You look exactly like Al Pacino.";
        System.out.println("Elaine's message:" + soupOrder);
        long[] cipher = Elaine.encryptTo(soupOrder, Kassem);
        System.out.println("Elaine sends: " + cipherString(cipher));

        System.out.println("Kassem decrypts: " + Kassem.decrypt(cipher));

        String noSoup = "No soup for you.";
        System.out.println("Kassem's message:" + noSoup);
        cipher = Kassem.encryptTo(noSoup, Elaine);
        System.out.println("Kassem sends: " + cipherString(cipher));

        System.out.println("Elaine decrypts: " + Elaine.decrypt(cipher));
        
    }

    /**
     * @author James Blake and Keegan Kopas
     * @param cipher
     */
    public static String cipherString(long[] cipher){ 
		StringBuilder builder = new StringBuilder(cipher.length * Person.bytesPacked);
		for(int i = 0; i < cipher.length * Person.bytesPacked; i++) {
			char c = (char) (cipher[i / Person.bytesPacked] & (0xFFFF << i % Person.bytesPacked ));
			if(c != 0)
				builder.append(c);
		}

		return builder.toString();
    }


    /**
     * @author Keegan Kopas
=     * @return
     */
    public static long modPower(long base, long  power, long mod){
        if (mod == 1) return 0; 

        long result = 1; 
        base = base % mod; 

        while (power > 0 ) {
            if(power % 2 == 1) result = (result * base) % mod; 
            power = power >> 1; 
            base = (base * base) % mod; 
        }

        return result; 
    }

    /**
     * @author Keegan Kopas and Christian Walddman
     * @param min
     * @param max
     * @return
     */
    public static long randomPrime(long min, long max){
        long nextPrime;
        do{
            nextPrime = RNG.nextLong(min, max + 1); 
        } while (!isPrime(nextPrime));
        return nextPrime; 
    }

    /**
     * @author Keegan Kopas
     * @param randomNumber
     * @return
     */
    public static boolean isPrime(long randomNumber){
        if (randomNumber % 2 == 0) return false;  
        for(long i = 3; i*i <= randomNumber; i = i + 2){
            if (randomNumber % i == 0 ) return false;
        }
        return true; 
    }

    /**
     * @author Keegan Kopas 
     */
    public static long relativePrime(long N){
        long relativePrimeCandidate; 
        do {
            relativePrimeCandidate = Math.abs(RNG.nextLong(0, N));
        } while(gcd(relativePrimeCandidate, N) != 1);
        return relativePrimeCandidate; 
    } 

    /**
     * @author Keegan Kopas
     * @param a
     * @param b
     * @return
     */
    public static long gcd(long a, long b){
        long result = a % b; 
         while(result != 0) {
            a = b;
            b = result; 
            result = a % b; 
        }
        return b;
    }

    /**
     * @author James Blake
     * @return
     */
    public static long inverse(long x, long y) {
        int q = 0; int r = 1; int u = 2; int v = 3;
		// An array to store q, r, u, and v for the extended Euclidean algorithm.
		long[][] qruv = new long[][] {
				{0, y, 0, 1},
				{0, x, 1, 0},
				{0, 0, 0, 0}
		};

		do {
			qruv[2][q] = qruv[0][r] / qruv[1][r];					// Q = R-2 / R-1
			qruv[2][r] = qruv[0][r] % qruv[1][r];					// R = R-2 % R-1
			qruv[2][u] = qruv[0][u] - (qruv[2][q] * qruv[1][u]);	// U = U-2 - (Q * U-1)
			qruv[2][v] = qruv[0][v] - (qruv[2][q] * qruv[1][v]);	// V = V-2 - (Q * V-1)
			qruv[0] = Arrays.copyOf(qruv[1], 4);
			qruv[1] = Arrays.copyOf(qruv[2], 4);
		} while(qruv[1][r] != 1);	// R != 1

		if(qruv[1][u] < 0)
			qruv[1][u] = y + qruv[1][u];

		return qruv[1][u];
	}
}
