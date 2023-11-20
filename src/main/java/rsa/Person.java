package rsa;

import java.util.Arrays;
import java.util.Random;

public class Person {
	/** 
	private static final int bytesPacked = 4;
	private final long m;
	private final long e;
	private final long d;

	public Person() {
		Random random = new Random();
		long p = RSA.randPrime(1, Integer.MAX_VALUE, random);
		long q = RSA.randPrime(1, Integer.MAX_VALUE, random);
		m = p * q;
		long N = (p - 1) * (q - 1);
		e = RSA.relPrime(N, random);
		d = RSA.extendedEuclidean(e, N);
	}

	public long getM() {
		return m;
	}

	public long getE() {
		return e;
	}

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

	*/
}
