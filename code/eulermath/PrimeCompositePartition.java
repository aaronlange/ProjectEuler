/* 
   A prime-composite sieve partition object partitions natural numbers into primes and composites (via a sieve method).

   Implementation notes: the prime and composite arrays are not constructed until *after* the
   first call to getPrimes(..) and getComposites(..) respectively. So any new methods that use
   these arrays should access them via these methods. 
*/

package eulermath;

public class PrimeCompositePartition {

    public static final boolean IS_PRIME = false;
    public static final boolean IS_COMPOSITE = true;

    private int limit; // determines the upper limit of the partition (i.e. all natural numbers below limit are partitioned
    private boolean[] partition; 
    private int[] composites;
    private int[] primes;
    private int numberOfComposites; 
    private int numberOfPrimes; 

    /*
      Constructor partitions the natural numbers (integers 0 and above) up to but not including
      the given limit. 
    */
    public PrimeCompositePartition(int limit) {
	this.limit = limit;
	partition = new boolean[limit];
	numberOfComposites = 0;
	int largestMultiplier = (int)Math.ceil(Math.sqrt(partition.length-1));
	for(int multiplier = 2; multiplier <= largestMultiplier; multiplier++) {
	    for(int multiple = multiplier*2; multiple < partition.length; multiple += multiplier) {
		if(!partition[multiple]) {
		    partition[multiple] = IS_COMPOSITE;
		    numberOfComposites++;
		}
	    }
	}
	numberOfPrimes = partition.length - numberOfComposites - 2; // subtract 2 for the base cases 0 and 1
    }

    /* 
       Returns the (cloned) partition. Primality/compositeness can be determined by the IS_PRIME and IS_COMPOSITE constants.
    */
    public boolean[] getPartition() {
	return partition.clone();
    }

    /* 
       Returns all the (cloned) primes in the partition.
    */
    public int[] getPrimes() {
	if(primes == null) {
	    primes = new int[numberOfPrimes];
	    int primeIndex = 0;
	    /* start from 2 to avoid the non-applicable cases */
	    for(int i = 2; i < partition.length; i++) {
		if(partition[i] == IS_PRIME) {
		    primes[primeIndex] = i;
		    primeIndex++;
		}
	    }
	}
	return primes.clone();
    }

    /*
      Returns all the (cloned) composites in the partition.
    */
    public int[] getComposites() {
	if(composites == null) {
	    composites = new int[numberOfComposites];
	    int compositeIndex = 0;
	    /* start from 2 to avoid the non-applicable cases */ 
	    for(int i = 2; i < partition.length; i++) {
		if(partition[i] == IS_COMPOSITE) {
		    composites[compositeIndex] = i;
		    compositeIndex++;
		}
	    }
	}
	return composites.clone();
    }
    /*
      Returns the primes separated by the given delimiter as a string.
    */
    public String primesToString(String delimiter) {
	if(primes == null) {
	    this.getPrimes();
	}
	String primeString = new String();
	for(int i = 0; i < primes.length; i++) {
	    primeString = primeString.concat(Integer.toString(primes[i])).concat(delimiter);
	}
	return primeString;
    }

    /*
      Returns the composites separated by the given delimiter as a string.
    */
    public String compositesToString(String delimiter) {
	if(composites == null) {
	    this.getComposites();
	}
	String compositeString = new String();
	for(int i = 0; i < composites.length; i++) {
	    compositeString = compositeString.concat(Integer.toString(composites[i])).concat(delimiter);
	}
	return compositeString;
    }

}
