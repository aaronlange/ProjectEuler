import java.io.*;
import java.util.*;

public class Problem42 {

    public static int A_VALUE = 0;

    public static void main(String[] args) {

	if(args.length == 0) {
	    System.out.println("You need to specify a file to read.");
	    System.exit(0);
	}

	A_VALUE = Character.getNumericValue('A');

	/* generate triangle numbers; use formula t_n = 0.5*(n+1)*(n+2) 
	   since we're starting from t_0 rather than t_1 as in the problem description 
	   triangle numbers may also be defined recursively, i.e. t_0 = 1 and t_n = t_(n-1) + n+1 */

	int numOfTriNumbers = 30;
	int[] triangleNumbers = new int[numOfTriNumbers]; // !!! if errors, check no triangle numbers are greater than biggest one in the array
	triangleNumbers[0] = 1;
	System.out.println("Triangle number 0 is " + triangleNumbers[0]);
	for(int i=1; i<triangleNumbers.length; i++) {
	    triangleNumbers[i] = triangleNumbers[i-1] + i + 1; 
	    System.out.println("Triangle number " + i + " is " + triangleNumbers[i]);
	}

	    
	String data = new String();
	String line = new String();

	/* read in file with words from which to generate the "word values" */
	try {
	    BufferedReader  br = new BufferedReader(new FileReader(args[0]));
	    
	    while((line = br.readLine()) != null) {
		data = data + line;	    
	    } 
	} catch(FileNotFoundException e) {
	    System.out.println("File not found");
	    System.exit(0);
	} catch(IOException e) { 
	    System.out.println("Oops, IO Exception"); 
	    System.exit(0);
	}



		  
	/* organise words into an array */
	String[] words = data.split(",");

	System.out.println(words.length + " words found in the file");

	/* trim the leading and trailing quote characters */
	for(int i = 0; i < words.length; i++) {
	    words[i] = words[i].substring(1, words[i].length()-1);
	}
	
	/* compute word values corresponding to each word and count how many are triangle numbers */
	int count = 0;

	for(int i = 0; i < words.length; i++) {
	    int wordValue = getWordValue(words[i]);

	    // sanity check: it shouldn't be bigger than the biggest triangle number
	    if(wordValue > triangleNumbers[triangleNumbers.length-1]) {
		System.out.println("Need more triangle numbers since is doesn't go up to " + wordValue);
	    }

	    // count it if it's a triangle number
	    if(Arrays.binarySearch(triangleNumbers, wordValue) >= 0) count++;
	}

	/* the count of triangle numbers is the solution */
	System.out.println("I counted " + count + " triangle numbers");
    }

    /* assumes only upper case Engish alphabet letters */
    public static int getWordValue(String word) {
	int wordValue = 0;
	for(int i = 0; i < word.length(); i++) {
	    char c = word.charAt(i);
	    int charValue = Character.getNumericValue(c) - A_VALUE + 1;
	    wordValue += charValue;
	    //System.out.println("char val " + charValue);
	}
	return wordValue;
    }

 }
