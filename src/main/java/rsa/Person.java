package rsa;

import java.util.Arrays;
import java.util.Random;

public class Person {
	public static final int bytesPacked = 4;
	private static final long MAX_PRIME = 997;
	private final long m;
	private final long e;
	private final long d;

	/**
	 * @author James Blake and Christian Waldmann
	 */
	public Person() {
		Random random = new Random();
		long p = RSA.randomPrime(1L, MAX_PRIME);
		long q = RSA.randomPrime(1L, MAX_PRIME);
		m = p * q;
		long N = (p - 1) * (q - 1);
		e = RSA.relativePrime(N);
		d = RSA.inverse(e, N);
	}

	/**
	 * @author James Blake
	 */
	public long getM() {
		return m;
	}


	/**
	 * @author James Blake
	 */
	public long getE() {
		return e;
	}

	/**
	 * @author James Blake 
	 */
	public long[] encryptTo(String msg, Person other) {
		byte[] plaintext = msg.getBytes();
		long[] encrypted = new long[(msg.length() + (bytesPacked - 1)) / bytesPacked];
		for(int i = 0; i < plaintext.length; i++) {
			encrypted[i / bytesPacked] |= plaintext[i] << (i % bytesPacked) * Byte.SIZE;
		}

		for(int i = 0; i < encrypted.length; i++) {
			encrypted[i] = RSA.modPower(encrypted[i], other.getE(), other.getM());
		}

		return encrypted;
	}

	/**
	 * @author James Blake and Christian Waldmann
	 */
	public String decrypt(long[] cipher) {
		long[] encrypted = Arrays.copyOf(cipher,cipher.length);
		for(int i = 0; i < cipher.length; i++) {
			encrypted[i] = RSA.modPower(encrypted[i], d, m);
		}

		StringBuilder builder = new StringBuilder(cipher.length * bytesPacked);
		for(int i = 0; i < cipher.length * bytesPacked; i++) {
			char c = (char) (encrypted[i / bytesPacked] & (0xFFFF << i % bytesPacked ));
			if(c != 0)
				builder.append(c);
		}

		return builder.toString();
	}

}
