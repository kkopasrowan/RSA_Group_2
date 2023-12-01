package rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

public class RSA {    
    private static final Random RNG = new Random(); 


    public static void main (String args[])
    { 	
        Person Alice = new Person();
        Person Bob = new Person();
    
        String msg = new String ("Bob, let's have lunch."); 	// message to be sent to Bob
        long []  cipher;
        cipher =  Alice.encryptTo(msg, Bob);			// encrypted, with Bob's public key
    
        System.out.println ("Message is: " + msg);
        System.out.println ("Alice sends:");
        show (cipher);
    
        System.out.println ("Bob decodes and reads: " + Bob.decrypt (cipher));	// decrypted,
                                    // with Bob's private key.
        System.out.println ();
    
        msg = new String ("No thanks, I'm busy");
        cipher = Bob.encryptTo (msg, Alice);
        
        System.out.println ("Message is: " + msg);
        System.out.println ("Bob sends:");
        show (cipher);
    
        System.out.println ("Alice decodes and reads: " + Alice.decrypt (cipher));
    }

    /**
     * @author James Blake and Keegan Kopas
     * @param cipher
     */
    public static void show(long[] cipher){ 
		StringBuilder builder = new StringBuilder(cipher.length * Person.bytesPacked);
		for(int i = 0; i < cipher.length * Person.bytesPacked; i++) {
			char c = (char) (cipher[i / Person.bytesPacked] & (0xFFFF << i % Person.bytesPacked ));
			if(c != 0)
				builder.append(c);
		}

		System.out.println( builder.toString());
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
     * @author Keegan Kopas
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
        for(long i = 3; i*i < randomNumber; i = i + 2){
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
     * @author James Blake, refactored by Keegan Kopas
     * @return
     */
    public static long inverse(long x, long y) {
        ArrayList<Long> r = new ArrayList<Long>(Arrays.asList(y, x)); 
        ArrayList<Long> u = new ArrayList<Long>(Arrays.asList(0L,1L)); 
        ArrayList<Long> v = new ArrayList<Long>(Arrays.asList(1L, 0L)); 

        long r_i = -1; 
        long u_result = -1; 
        long q = 0; 
        for(int i = 2; r_i != 1L; i++){
            q = r.get(i -2) / r.get(i- 1);

            r_i = r.get(i - 2).longValue() % r.get(i - 1).longValue();
            r.add(r_i);

            Long u_i = u.get(i -2) - (q * u.get(i - 1));
            u.add(u_i);
            
            Long v_i = v.get(i - 2) - (q * v.get(i - 1));
            v.add(v_i);

            Long r_check = u.get(i) * x + v.get(i) * y; 

            if (r.get(i) != r_check) {System.err.println("Checksum doesn't match!");}
            if (r_i ==  1L) {
                u_result = u_i;
                break; 
            } 
        }
        if (u_result < 0L ) u_result += y; 
        return u_result;   
	}
}
