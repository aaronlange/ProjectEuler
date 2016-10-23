
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		long sumPalindromes = 0;
		for(int testCase = 500000; testCase < 1000000; testCase++) {
			// test decimal palindrome
			int decimalDigits = 1;
			int testCaseDegen = testCase;
			while(testCaseDegen >= 10) {
				testCaseDegen /= 10;
				decimalDigits++;
			}
			testCaseDegen = testCase;
			boolean isDecimalPalindrome = true; // try to prove otherwise
			for(int d = 1; d < decimalDigits; d+=2) {
				// compare big and littles digits
				int exponentComponent = (int)Math.pow(10, decimalDigits-d);
				//System.out.println("testCaseDegen = " + testCaseDegen+"; expCom = "+exponentComponent);
				int big = testCaseDegen / exponentComponent;
				int small = testCaseDegen % 10;
				//System.out.println("big = " + big + "; small = " + small);
				if(big != small) {
					isDecimalPalindrome = false;
					break;
				} else {
					// cut off end digits
					testCaseDegen -= big*exponentComponent;
					testCaseDegen /= 10;
				}
			}

			// test the binary case
			boolean isBinaryPalindrome = true;
			int[] binaryCase = decimalToBinary(testCase);
			for(int d = 0; d <= binaryCase.length/2 - 1; d++) {
				if(binaryCase[d] != binaryCase[binaryCase.length-1-d]) {
					isBinaryPalindrome = false;
					break;
				}
			}
			if(isDecimalPalindrome && isBinaryPalindrome) {
				sumPalindromes += testCase;
				System.out.print(testCase + "; ");
				for(int i = 0; i < binaryCase.length; i++) {
					System.out.print(binaryCase[i]);
				}
				System.out.println("");
			}
			
		}
		System.out.println("sum palindromes = " + sumPalindromes);
	}
	
	public static int[] decimalToBinary(int decimalNumberParam) {
		// count binary digits
		int digits = 0;
		int decimalNumber = decimalNumberParam;
		if(decimalNumber == 0) {
			digits = 1;
		} else {
			while(decimalNumber > 0) {
				decimalNumber /= 2;
				digits++;
			}
		}
		// make array representing binary form of the decimal number
		// big endian format is used
		int[] binary = new int[digits];
		decimalNumber = decimalNumberParam;
		if(decimalNumber == 0) {
			binary[0] = 0;
		} else {
			for(int digitLocation = digits-1; digitLocation >= 0; digitLocation--) {
				if(decimalNumber == 0) {
					binary[digits-digitLocation-1] = 0;
					continue;
				}
				int exponentComponent = (int)Math.pow(2,digitLocation);
				//System.out.println("decimalNumber="+decimalNumber+"; exponentComponent="+exponentComponent);
				if(decimalNumber >= exponentComponent) {
					binary[digits-digitLocation-1] = 1;
					decimalNumber -= exponentComponent;
					//System.out.println("stored 1");
				} else {
					binary[digits-digitLocation-1] = 0;
					//System.out.println("stored 0");
				}
			}
		}
		return binary;
	
	}
}
