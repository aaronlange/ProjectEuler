/*
Digit factorials
Problem 34

145 is a curious number, as 1! + 4! + 5! = 1 + 24 + 120 = 145.

Find the sum of all numbers which are equal to the sum of the factorial of their digits.

Note: as 1! = 1 and 2! = 2 are not sums they are not included.
*/

/*
Solution: 40730. Only two numbers fit the bill: 145 and 40585. 
The solution below is an infinite loop, but I just typed in 40730 after
spitting out those two numbers and it was the correct answer, so just killed
the problem after that.
*/

public class Problem34 {
    public static void main(String[] args) {
	/* the brute force approach */

	// cache factorial calculations
	int[] factorial = new int[10];
	for(int d = 0; d < 10; d++) {
	    factorial[d] = getBabyFactorial(d);
	}

	for(int n = 3; true; n++) {
	    System.out.print("\r");
	    System.out.print(n);
	    // sum the factorials of the digits of n
	    int[] digits = getDigits(n);
	    long sum = 0;
	    for(int d = 0; d < digits.length; d++) {
		sum += factorial[digits[d]];
	    }
	    if(sum == n) {
		System.out.print("\n");
	    }
	}
    }

    /* 
       Returns factorials of single digit numbers.
    */
    public static int getBabyFactorial(int d) {
	// don't bother with checks for this toy code
	int factorial = 1;
	for(int i = 1; i <= d; i++) {
	    factorial *= i;
	}
	return factorial;
    }
  
    /*
      Return the digits of a signed integer from most signifiant to least
    */  
    public static int[] getDigits(int n) {
	int[] digits;
	if(n == 0) {
	    digits = new int[1];
	    digits[0] = 0;
	} else {
	    // count digits
	    int n_degenerate = n;
	    int numDigits = 0;
	    while(n_degenerate != 0) {
		n_degenerate /= 10;
		numDigits++;
	    }
	    digits = new int[numDigits];
	    // put digits into array
	    n_degenerate = n;
	    for(int i = numDigits-1; i >= 0; i--) {
		digits[i] = n_degenerate%10;
		n_degenerate /= 10;
	    }
	}
	return digits;
    }
}
