/*
Class to keep track of the prime factors of a particular number.

It's a partial list because at any given time it may not contain all of the factors, perhaps only
some or even none of them.
*/

public class PartialPrimeFactorList {

    private int number; // the number composed of the (completed) prime factors
    private Vector[]<Integer> factors; // prime factors
    private int factorProduct; // product of the factors
    private boolean isComplete; // whether or not the prime factor list is complete, i.e. is factorProduct equal to number?

    /*
      Constructor to create a partial prime factor list for the integer n    
    */
    public PartialPrimeFactorList(int n) {
	this.number = n;
	factors = new Vector[]<Integer>;
	factorProduct = 1;
	isComplete = false;
    }

    public int getNumber() {
	return number;
    }

    /*
      Returns a copy of the factors.
    */
    public Vector getFactors() {
	int[] factorArray = new int[factors.size()];
	for(...) {
	    factorArray[i] = factors.elementAt(i).intValue();
	}
	return factorArray;
    }

    /*
      No checks are done, assumes correct input.
    */
    public void addFactor(int f) {
	assert number % f == 0;
	assert isComplete == false;

	factors.add(new Integer(f));
	factorProduct *= f;
	if(factorProduct == number) {
	    isComplete == true;
	}
    }
}
