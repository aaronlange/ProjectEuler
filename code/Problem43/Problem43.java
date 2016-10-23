
import java.util.*;

public class Problem43 {
    public static void main(String[] args) {
	/* we need to track the sum of substring divisible pandigitals */
	long divisiblePandigitalSum = 0;

	int substringDivisibilityFailurePoint;

	/* set up first pandigital number 0123456789 */
	int[] p = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

	/* check its substring divisibility */
	if((substringDivisibilityFailurePoint = substringDivisibilityFailure(p)) == -1) {
	    System.out.println(digitsToLong(p) + " is substring divisible.");	 
	    divisiblePandigitalSum += digitsToLong(p);
	}

	/* test the substring divibility of all of the 0-9 pandigital numbers */
	while((p = nextPandigital(p, substringDivisibilityFailurePoint)) != null) {
	if((substringDivisibilityFailurePoint = substringDivisibilityFailure(p)) == -1) {
		System.out.println(digitsToLong(p) + " is substring divisible."); 
		divisiblePandigitalSum += digitsToLong(p);	   
	    } 
	}
	System.out.println("Sum of substring divisible pandigitals is " + divisiblePandigitalSum);
    }
    
    /* Compute the long-value corresponding to the given pandigital number 
       Element 0 is the most significant digit */
    public static long digitsToLong(int[] p) {
	long pandigitalValue = (long)p[0];
	for(int i = 1; i < p.length; i++) {
	    pandigitalValue *= (long)10;
	    pandigitalValue += (long)p[i];
	}
	return pandigitalValue;
    }
    
    /* Check whether the given pandigital number meets all the substring division conditions
       as defined by the Problem 43 definiton. I'll use d_(i-1) instead of d_i in reference to
       the i-th digit, for consistency with array indices.
       Will return the index of the first digit whose condition is not met, or -1 if all conditions are met */
    public static int substringDivisibilityFailure(int[] p) {
	/* condition: d1d2d3 is divisible by 2, i.e. 3rd digit is divisible by 2*/
	if((p[3] & 1) == 1) return 3;

	/* condition: d2d3d4 is divisible by 3, i.e. digits sum to a multiple of 3*/
	if((p[2] + p[3] + p[4]) % 3 != 0) return 4;

	/* condition: d3d4d5 is divisible by 5, i.e. 5th digit is 0 or 5 */
	if((p[5] != 0) && (p[5] != 5)) return 5;

	/* condition: d4d5d6 is divisible by 7 */
	if((p[4]*100 + p[5]*10 + p[6]) % 7 != 0) return 6;

	/* condition: d5d6d7 is divisible by 11 */
	if((p[5]*100 + p[6]*10 + p[7]) % 11 != 0) return 7;

	/* condition: d6d7d8 is divisible by 13 */
	if((p[6]*100 + p[7]*10 + p[8]) % 13 != 0) return 8;

	/* condition d6d7d8 is divisible by 17 */
	if((p[7]*100 + p[8]*10 + p[9]) % 17 != 0) return 9;

	/* if we got this far, then all the conditions must have been met */
	return -1;
    }

    /* Return the next highest pandigital number, or null if no more. */
    public static int[] nextPandigital(int[] p, int failurePoint) {
	/* the place to try to increment is the failure point */
	int incrementPoint = failurePoint;

	/* but if there was no failure (i.e. failure point is -1), then try to increment at the final digit */
	if(failurePoint == -1) incrementPoint = p.length - 1;

	/* but now we only know where to stat looking. What we need now is to find the least significant digit 
	   (no less significant than the current increment point) where it is actually possible to increment. 
	   It is possible if and only if there is a digit in a less significant position that is greater than the current one */
	int nextLargestLocation = -1;
	int nextLargestDigit;
	for(nextLargestDigit = 10; incrementPoint >= 0; incrementPoint--) { // next largest digit set to something impossibily large so we know it's not yet set
	    /* look for the least digit in a less significant spot that is greater than the current increment point */
	    for(int i = incrementPoint + 1; i < p.length; i++) {
		if(p[i] > p[incrementPoint] && p[i] < nextLargestDigit) {
		    nextLargestDigit = p[i];
		    nextLargestLocation = i;
		}
	    }
	    /* this is a valid increment point if we found a larger digit */
	    if(nextLargestLocation >= 0) break;
	}

	/* increment the pandigital */
	if(nextLargestLocation >= 0) {
	    /* first swap the increment location with the next largest digit */
	    p[nextLargestLocation] = p[incrementPoint];
	    p[incrementPoint] = nextLargestDigit;

	    /* then sort all digits following the incremented one from lowest to highest */
	    Arrays.sort(p, incrementPoint + 1, p.length);

	    return p;
	} else {
	    return null;
	}

    }
}
