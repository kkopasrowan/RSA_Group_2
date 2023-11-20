package rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class RSA {    

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
