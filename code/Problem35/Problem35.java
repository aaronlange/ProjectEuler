/* 
Circular primes
Problem 35

The number, 197, is called a circular prime because all rotations of the digits: 197, 971, and 719, are themselves prime.

There are thirteen such primes below 100: 2, 3, 5, 7, 11, 13, 17, 31, 37, 71, 73, 79, and 97.

How many circular primes are there below one million?
*/

public class Problem35 {
    public static void main(String[] args) {
	final int PRIME_CEILING = 1000000;
	boolean[] isComposite = new boolean[PRIME_CEILING];
	System.out.println("Marking composites...");
	markComposites(isComposite);

	/* check circularity of each prime */
	System.out.println("Checking circularity of primes...");
	boolean[] isNotCircularPrime = new boolean[PRIME_CEILING];
	for(int x = 0; x < isComposite.length; x++) {
	    if(isComposite[x] == false) {
		// assume is not circular prime, try to prove otherwise
		int[] rotation = digitsToArray(x);
		for(int r = 1; r < rotation.length; r++) {
		    rotate(rotation);
		    if(isComposite[digitArrayToInt(rotation)]) {
			isNotCircularPrime[x] = true;
			break;
		    }
		}
	    } else {
		// if it's not a prime, it's not a circular prime
		isNotCircularPrime[x] = true;
	    }
	}

	/* count circular primes */
	int numCircularPrimes = 0;
	for(int x = 0; x < isNotCircularPrime.length; x++) {
	    if(isNotCircularPrime[x] == false) {
		System.out.println(x + " is a circular prime.");
		numCircularPrimes++;
	    }
	}
	System.out.println("Number of cicular primes = " + numCircularPrimes);
    }

    public static void markComposites(boolean[] isComposite) {
	isComposite[0] = true;
	isComposite[1] = true;
	int maxDivisor = (int)Math.sqrt(isComposite.length);
	for(int divisor = 2; divisor <= maxDivisor; divisor++) {
	    int divisorMultiple = divisor*2;
	    while(divisorMultiple < isComposite.length) {
		isComposite[divisorMultiple] = true;
		divisorMultiple += divisor;
	    }
	}
    }

    public static int numberOfDigits(int x) {
	int diminishing = x;
	int count = 0;
	while(diminishing > 0) {
	    count++;
	    diminishing /= 10;
	}
	return count;
    }

    /*
      Split digits of x into array. Most significant digit comes first.
     */
    public static int[] digitsToArray(int x) {
	int numDigits = numberOfDigits(x);
	int[] digitized = new int[numDigits];
	int diminishing = x;
	for(int i = numDigits-1; i >= 0; i--) {
	    digitized[i] = diminishing%10;
	    diminishing /= 10;
	}
	return digitized;
    }

    /*
      Convert digit array into an int
    */
    public static int digitArrayToInt(int[] digits) {
	int x = 0;
	for(int i = 0; i < digits.length; i++) {
	    x *= 10;
	    x += digits[i];
	}
	return x;
    }

    /* 
       rotate digits one place towards the left 
     (most significant digit goes to the end)
    */
    public static void rotate(int[] digits) {
	int mostSignificant = digits[0];
	for(int i = 1; i < digits.length; i++) {
	    digits[i-1] = digits[i];
	}
	digits[digits.length-1] = mostSignificant;
    }
}
