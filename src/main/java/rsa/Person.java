package rsa;

import java.util.Arrays;

public class Person {
	/// Max of 3, otherwise you might get overflow
	public static final int bytesPacked = 1;
	public static final long MIN_PRIME = 0xFFF;
	private static final long MAX_PRIME = 0xFFFF;
	private final long m;
	private final long e;
	private final long d;

	/**
	 * @author James Blake 
	 */
	public Person() {
		long p = RSA.randomPrime(MIN_PRIME, MAX_PRIME);
		long q = RSA.randomPrime(MIN_PRIME, MAX_PRIME);
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
	 * @author James Blake 
	 */
	public String decrypt(long[] cipher) {
		long[] encrypted = Arrays.copyOf(cipher,cipher.length);
		for(int i = 0; i < cipher.length; i++) {
			encrypted[i] = RSA.modPower(encrypted[i], d, m);
		}

		StringBuilder builder = new StringBuilder(cipher.length * bytesPacked);
		for(int i = 0; i < cipher.length * bytesPacked; i++) {
			long value = encrypted[i / bytesPacked];
			int mask = 0xFF << (i % bytesPacked) * Byte.SIZE;
			long result = (value & mask) >> (i % bytesPacked) * Byte.SIZE;
			char c = (char) result;
			if(c != 0)
				builder.append(c);
		}

		return builder.toString();
	}

}
