package eulermath;

/*
Class for common number routines
*/

public class Numbers {

    private static int primeCount = -1;
    private static int compositeCount = -1;
    
    /*
      Get the number of digits of the given integer.
    */
    public static int numberOfDigits(int x) {
	int numberOfDigits = 0;
	int diminishing = (int)Math.abs(x);
	if(diminishing < 10) {
	    numberOfDigits = 1;
	} else {
	    while(diminishing > 0) {
		diminishing /= 10;
		numberOfDigits++;
	    }
	}
	return numberOfDigits;
    }

    /* 
       Returns true if the based long number is pandigital in its given base (each digit appears at least once), false otherwise.
    */
    public static boolean isPandigital(BasedLong b) {
	int[] digits = b.getDigits();
	int base = b.getBase();
	boolean[] digitsFound = new boolean[base];
	int digitsFoundCount = 0;
	for(int i = 0; i < digits.length; i++) {
	    int digit = digits[i];
	    if(digitsFound[digit] == false) {
		digitsFound[digit] = true;
		digitsFoundCount++;
	    }
	}
	if(digitsFoundCount == base) {
	    return true;
	} else {
	    return false;
	}
    }

    /* 
       Returns the next smallest pandigital number (each digit appears exactly once in the given base)
       Returns -1 if not possible (i.e. at the biggest already or too many digits or not enough digits )

       !!! need to fix in case input is not pandigital

       !!! need to fix this so it can handle repeated digits!
    */
    public static long nextSmallestPandigital(BasedLong b) {
	int[] digits = b.getDigits();
	/*System.out.println("old digits");
	for(int i = 0; i < digits.length; i++) {
	    System.out.println("digit " + i + " is " + digits[i]);
	    }*/
	int base = b.getBase();

	if(digits.length != base) {
	    System.out.println("digit problem with " + b.getNumber() + " with " + digits.length + " digits and base " + base);
	    return -1; // too many or not enough digits
	}

	int pivot = locationBeforeRightHandSideIncreasingSequence(digits);

	if(pivot < 0) {
	    System.out.println("max problem with " + b.getNumber());

	    return -1; // can't get a bigger pandigital without increasing the number of digits
	}

	boolean[] inIncreasingSequence = new boolean[digits.length];

	// clear sequence markers
	for(int i = 0; i < digits.length; i++) {
	    inIncreasingSequence[i] = false;
	}
	// mark the digits in the increasing sequence
	for(int i = pivot+1; i < digits.length; i++) {
	    inIncreasingSequence[i] = true;
	}

	// find max digit in incr seq below pivot value
	int pivotValue = digits[pivot];
	int candidateValue = -1;
	int candidateIndex = -1;
	for(int i = pivot+1; i < digits.length; i++) {
	    if(digits[i] < pivotValue && digits[i] > candidateValue) {
		candidateValue = digits[i];
		candidateIndex = i;
	    }
	}
	// swap pivot with new value
	digits[candidateIndex] = pivotValue;
	digits[pivot] = candidateValue;
	
	// reorder incr. seq digits into decreasing sequence
	for(int i = pivot+1; i < digits.length; i++) {
	    int currentDigit = digits[i];
	    int currentMax = currentDigit;
	    int currentMaxIndex = i;
	    for(int j = i+1; j < digits.length; j++) {
		if(digits[j] > currentMax) {
		    currentMax = digits[j];
		    currentMaxIndex = j;
		}
	    }
	    digits[i] = currentMax;
	    digits[currentMaxIndex] = currentDigit;
	}
	/*System.out.println("new digits");
	for(int i = 0; i < digits.length; i++) {
	    System.out.println("digit " + i + " is " + digits[i]);
	    }*/
	// compute the long value 
	long nextPandigital = 0;
	for(int i = 0; i < digits.length; i++) {
	    nextPandigital = nextPandigital*base + digits[i];
	}
	return nextPandigital;
    }

    /*
      Find the largest sequence of increasing numbers bound to the right hand 
      side of the full sequence. Returns the index before the start of the
      sequence (so may return -1)
    */
    private static int locationBeforeRightHandSideIncreasingSequence(int[] numbers) {
	int index = numbers.length-1;
	int previousValue = numbers[index];
	boolean stillIncreasing = true;
	while(stillIncreasing) {
	    index--;
	    if(index < 0) break;
	    int newValue = numbers[index];
	    if(newValue < previousValue) {
		previousValue = newValue;
	    } else {
		stillIncreasing = false;
	    }
	}
	return index;
    }

    /*
      Generate primes up to but not including the given integer limit, using a sieve method.
    
    public static int[] getPrimesToLimit(int limit) {
	boolean[] sieve = Numbers.primeCompositeSieve(limit);
	int[] primes = new int[compositeCount];
	int primeIndex = 0;
	for(int i = 0; i < sieve.length; i++) {
	    if(!sieve[i]) {
		primes[primeIndex] = i;
		primeIndex++;
	    }
	}
	return primes;
	}*/

    /* 
       Generate composites up to but not including the given integer limit, using a sieve method.
    
    public static int[] getCompositesToLimit(int limit) {
	boolean[] sieve = Numbers.primeCompositeSieve(limit);
	int[] composities = new int[compositeCount];
	int primeIndex = 0;
	for(int i = 0; i < sieve.length; i++) {
	    if(!sieve[i]) {
		primes[primeIndex] = i;
		primeIndex++;
	    }
	}
	return primes;
    }

    
       Generate a boolean array indicating which integers are prime and which are
       composite up to but not including the given integer limit.
       Prime p is indicated by false at index location p in the array.
       Conversely, composite c is indicated by a true value at the corresponding location in the array.
    
    private static boolean[] primeCompositeSieve(int limit) {
	sieve[0] = true; // take care of the base cases
	sieve[1] = true;
	int compositeCount = 0;
	int largestMultiplier = (int)Math.ceil(Math.sqrt(sieve.length-1));
	for(int multiplier = 2; multiplier <= largestMultiplier; multiplier++) {
	    for(int multiple = multiplier*2; multiple < sieve.length; multiple += multiplier) {
		if(!sieve[multiple]) {
		    sieve[multiple] = true;
		    compositeCount++;
		}
	    }
	}
	return sieve;
    }
    */
}
