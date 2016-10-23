package eulermath;

public class PrimeFactoriser {

    /*
      Get prime factorisations for each non-negative integer up to and including
      the argument value.
      Return an array of prime-factor arrays. Each prime factor array is an unordered list of
      prime factors corresponding to the integer that equals the index location of the factor array.
     */
    public static int[][] getFactorsTo(int maxInteger) {
	int[][] primeFactors = new int[maxInteger+1][];

	// first do a prime sieve to identify composites and initial pairs of factors (only one of each pair will be prime)
	FactorPair[] factorPairs = new FactorPair[primeFactors.length];
	int maxPrimeFactor = (int)Math.sqrt(maxInteger) + 1;
	for(int prime = 2; prime <= maxPrimeFactor; prime++) {
	    // skip past the composites, otherwise we're spinning our wheels
	    if(factorPairs[prime] != null) {
		continue;
	    } 
	    int multiplier;
	    for(int multiple = 2; (multiplier = prime*multiple) < factorPairs.length;  multiple++) {
		if(factorPairs[multiplier] != null) {
		    continue; // don't need to set another pair of factors
		}
		factorPairs[multiplier] = new FactorPair(prime, multiple);
	    }
	}

	// make the factor trees
	FactorTreeNode[] factorTreeNodes = new FactorTreeNode[primeFactors.length];
	for(int i = 2; i < factorTreeNodes.length; i++) {
	    factorTreeNodes[i] = new FactorTreeNode(i);
	    if(factorPairs[i] != null) {
		// dealing with a composite, so link it to sub-factors
		factorTreeNodes[i].setFirstSubfactor(factorTreeNodes[factorPairs[i].getFirstFactor()]);
		factorTreeNodes[i].setSecondSubfactor(factorTreeNodes[factorPairs[i].getSecondFactor()]);
	    }
	}

	// convert into an array
	for(int i = 2; i < primeFactors.length; i++) {
	    // count how many prime factors
	    int numberOfPrimeFactors = factorTreeNodes[i].numberOfPrimeFactors();
	    primeFactors[i] = new int[numberOfPrimeFactors];
	    // add them to the array
	    factorTreeNodes[i].insertPrimeFactors(primeFactors[i], 0);
	}

	return primeFactors;
    }
}

class FactorPair {

    private int x, y;

    FactorPair(int x, int y) {
	this.x = x;
	this.y = y;
    }

    public int getFirstFactor() {
	return x;
    }

    public int getSecondFactor() {
	return y;
    }
}

class FactorTreeNode {

    private int factor; // factor this node represents
    private FactorTreeNode firstSubfactor; // child nodes
    private FactorTreeNode secondSubfactor;

    FactorTreeNode(int factor) {
	this.factor = factor;
    }

    public int getFactor() {
	return factor;
    }

    public FactorTreeNode getFirstSubfactor() {
	return firstSubfactor;
    }

    public FactorTreeNode getSecondSubfactor() {
	return secondSubfactor;
    }

    public void setFirstSubfactor(FactorTreeNode n) {
	firstSubfactor = n;
    }

    public void setSecondSubfactor(FactorTreeNode n) {
	secondSubfactor = n;
    }

    public int numberOfPrimeFactors() {
	if(firstSubfactor == null) {
	    // this is a leaf/prime
	    return 1;
	} else {
	    return firstSubfactor.numberOfPrimeFactors() + secondSubfactor.numberOfPrimeFactors();
	}
    }

    public int insertPrimeFactors(int[] primeFactors, int indexToInsert) {
	if(firstSubfactor == null) {
	    // this is a leaf/prime
	    primeFactors[indexToInsert] = factor;
	    return indexToInsert+1;
	} else {
	    int nextIndex = firstSubfactor.insertPrimeFactors(primeFactors, indexToInsert);
	    nextIndex = secondSubfactor.insertPrimeFactors(primeFactors, nextIndex);
	    return nextIndex;
	}
    }
}
