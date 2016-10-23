/*
It was proposed by Christian Goldbach that every odd composite number can be written as the sum of a prime and twice a square.

9 = 7 + 2×12
15 = 7 + 2×22
21 = 3 + 2×32
25 = 7 + 2×32
27 = 19 + 2×22
33 = 31 + 2×12

It turns out that the conjecture was false.

What is the smallest odd composite that cannot be written as the sum of a prime and twice a square?
*/

import eulermath.*;

public class Problem46 {
    public static void main(String[] args) {
	int MAX = 1000000;
	PrimeCompositePartition partition = new PrimeCompositePartition(MAX);
	boolean[] primeCompositeMarkers = partition.getPartition();
	int[] primes = partition.getPrimes();
	boolean[] goldbachComposites = new boolean[primeCompositeMarkers.length];
	for(int i = 1; i < primes.length; i++) { // want to skip 2, since it plus twice a square will never be an _odd_ composite
	    int primePlusTwiceSquare;
	    for(int n = 1; (primePlusTwiceSquare = 2*n*n + primes[i]) < goldbachComposites.length; n++) {
		if(primeCompositeMarkers[primePlusTwiceSquare] == PrimeCompositePartition.IS_COMPOSITE) {
		    goldbachComposites[primePlusTwiceSquare] = true;
		}
	    }
	}
	
	/* find the smallest odd composite that is not a goldbach composite */
	int[] composites = partition.getComposites();
	for(int i = 0; i < composites.length; i++) {
	    if((composites[i]%2 == 1) && (goldbachComposites[composites[i]] == false)) {
		System.out.println(composites[i] + " is not a goldbach composite");
	    }
	}

    }
}
