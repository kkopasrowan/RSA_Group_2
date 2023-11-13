package rsa;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Person {
	private long m;
	private long e;
	private long d;

	public Person() {
		Random random = new Random();
		long p = RSA.randPrime(1, Integer.MAX_VALUE, random);
		long q = RSA.randPrime(1, Integer.MAX_VALUE, random);
		m = p * q;
		long N = (p - 1) * (q - 1);
		e = RSA.relPrime(N, random);
		d = extendedEuclidean(e, N);
	}

	public long getM() {
		return m;
	}

	public long getE() {
		return e;
	}

	public long[] encryptTo(String msg, Person other) {
		return null;
	}

	public String decrypt(long[] cipher) {
		return "";
	}

	private static long extendedEuclidean(long x, long y) {
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
