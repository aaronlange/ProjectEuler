/*

Long number represented in a certain base.

*/

package eulermath;

public class BasedLong {

    private long number;
    private int base;
    private int[] digits; // as per the base. First index is most significant.

    public BasedLong(long number, int base) {
	this.number = number;
	this.base = base;
	// create digit representation of the number in the given base. First index is the most significant
	digits = new int[(int)(Math.log(this.number) / Math.log(base)) + 1];
	long leftOver = number;
	for(int index = digits.length - 1; index >= 0; index--) {
	    int remainder = (int) (leftOver % base);
	    digits[index] = remainder;
	    leftOver /= base;
	}
	// sanity check
	assert leftOver == 0;
    }

    // return digits in the appropriate base (e.g. for base 12 each digit can be any one of 0, 1, 2, ... 10 or 11)
    // returns a clone to protect integrity of the object
    public int[] getDigits() {
	return digits.clone();
    }

    public int getBase() {
	return base;
    }

    /* 
       Returns base 10 version
    */
    public long getNumber() {
	return number;
    }

}
