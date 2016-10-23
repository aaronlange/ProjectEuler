/* 
PROBLEM: 
Distinct primes factors
Problem 47
The first two consecutive numbers to have two distinct prime factors are:

14 = 2 × 7
15 = 3 × 5

The first three consecutive numbers to have three distinct prime factors are:

644 = 2² × 7 × 23
645 = 3 × 5 × 43
646 = 2 × 17 × 19.

Find the first four consecutive integers to have four distinct prime factors. What is the first of these numbers?
*/

/*
SOLUTION:
Solution is as follows: 
- use a basic sieve method to identify composite numbers
- as we go, we'll add each prime factor for each composite number to a list containing the other prime factors for that composite
- when we find a composite number with 4 (and no more) prime factors, we'll check to see if enough neighbouring numbers also have four identified prime factors
If they do, then we stop, otherwise we keep going.

The complexity of this solution is O(n^2) for n = size of integer set to search, when n is small
Complexity approaches O(n) as n approaches infinity, due to ratio of primes : composites approaching 0 as n approaches infinity. I think? Rough in-the-head visualisation to estimate this...
*/

/*
maybe create a separate quick factorisation class/method to factorise all numbers in a range, as I expect it will be used again and it may be a good exercise anyway
sieve as usual, except stick to primes for the first factor to save time
and also save the first product you find for each composite (ie. identify two factors, one may not be prime)
then run through again and for every composite with a factor that is also a composite, recursively replace the composite factor with its two factors
this way we end up with a prime factorisation for each number

once we've got the prime factorisation, it's a matter of iterating through the number until we find 4 in a row with at least 4 prime factors
 */

import eulermath.*;
import java.util.*;

public class Problem47 {
    public static void main(String[] args) {
	final int MAX_INTEGER_PLUS_1 = 1000000;
	final int TARGET_PRIME_FACTOR_LIST_SIZE = 4;
	final int IN_A_ROW_TARGET = 4;

	int[][] primeFactors = PrimeFactoriser.getFactorsTo(MAX_INTEGER_PLUS_1);

	int targetPrimeListCount = 0;
	for(int i = 2; i < primeFactors.length; i++) {
	    //System.out.print("Factors for " + i + " are ");
	    int uniqueFactorCount = 0;
	    Arrays.sort(primeFactors[i]);
	    for(int j = 0; j < primeFactors[i].length; j++) {
		//System.out.print(primeFactors[i][j] + ", ");
		if(Arrays.binarySearch(primeFactors[i], 0, j, primeFactors[i][j]) < 0) {
		    uniqueFactorCount++;
		}
	    }
	    //System.out.println("");
	    if(uniqueFactorCount >= TARGET_PRIME_FACTOR_LIST_SIZE) {
		targetPrimeListCount++;
		if(targetPrimeListCount == IN_A_ROW_TARGET) {
		    System.out.println(i);
		    System.exit(0);
		}
	    } else { 
		targetPrimeListCount = 0;
	    }
	}

    }
}

