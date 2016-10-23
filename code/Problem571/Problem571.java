/*

Super Pandigital Numbers
Problem 571
A positive number is pandigital in base b if it contains all digits from 0 to b - 1 at least once when written in base b.

A n-super-pandigital number is a number that is simultaneously pandigital in all bases from 2 to n inclusively.
For example 978 = 11110100102 = 11000203 = 331024 = 124035 is the smallest 5-super-pandigital number.
Similarly, 1093265784 is the smallest 10-super-pandigital number.
The sum of the 10 smallest 10-super-pandigital numbers is 20319792309.

What is the sum of the 10 smallest 12-super-pandigital numbers?

*/

import eulermath.*;

public class Problem571 {
    public static void main(String[] args) {

	final int TARGET_SUPER_PANDIGITAL_NUMBER_COUNT = 50;
	final int MAX_BASE = 12;
	final int MIN_BASE = 2;

	// first test number needs to be (in MAX_BASE) written as (MAX_BASE-1)...43210
	int[] seedTestDigits = new int[MAX_BASE];
	for(int i = MAX_BASE-1; i >= 0; i--) {
	    seedTestDigits[i] = MAX_BASE - 1 - i;
	}
	long seedTestNumber = 0; // compute base 10 version
	for(int i = 0; i < seedTestDigits.length; i++) {
	    seedTestNumber = seedTestNumber*MAX_BASE + seedTestDigits[i];
	    System.out.println("seed is " + seedTestNumber);
	}

	int superPandigitalNumberCount = 0;
	long superPandigitalNumberSum = 0;

	BasedLong basedNumberOriginal = new BasedLong(1, 2);
	for(long testNumber = seedTestNumber; testNumber != -1; testNumber = Numbers.nextSmallestPandigital(basedNumberOriginal)) { 
	    //System.out.print("testing " + testNumber);
	    basedNumberOriginal = new BasedLong(testNumber, MAX_BASE);
	    for(int base = MAX_BASE; base >= 2; base--) {
		BasedLong basedNumberNew = new BasedLong(testNumber, base);
		if(Numbers.isPandigital(basedNumberNew) == false) {
		    break;
		} else if(base == 2) {
		    superPandigitalNumberSum += testNumber;
		    superPandigitalNumberCount++;
		    System.out.println("super pandigital found: " + testNumber);
		}
	    }
	}
	System.out.println("Sum is " + superPandigitalNumberSum);
    }
}
