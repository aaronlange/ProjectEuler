/*
Self powers
Problem 48
The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.

Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000.
*/

/*
Solution:

Rather simple really. Divide by 10^10 after each addition/multiplication to ensure
 that we only play with the last 10 digits. This works because addition digits won't
make a difference to the final result.
*/
import java.util.*;

public class Problem48 {
    public static void main(String[] args) {
	final long N = 10;

	final long BASE = 10;
	final long NUM_DIGITS = 10;
	final long TRUNCATOR = (long)Math.pow(BASE, NUM_DIGITS);

	long lastDigitsOfSum = 0;
	for(long summandBase = 1; summandBase <= N; summandBase++) {
	    long lastDigitsOfSummand = summandBase;
	    for(long power = 1; power <= N; power++) {
		lastDigitsOfSummand *= summandBase;
		lastDigitsOfSummand %= TRUNCATOR;
	    }
	    lastDigitsOfSum += lastDigitsOfSummand;
	    lastDigitsOfSum %= TRUNCATOR;
	}
	System.out.println("Solution is: " + lastDigitsOfSum);
    }
}
