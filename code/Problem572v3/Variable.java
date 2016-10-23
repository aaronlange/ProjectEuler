public class Variable {

    private int ID;

    private int min, max;
    private Integer value;

    public Variable(int maxAbsoluteValue, int ID) {
	this.min = - maxAbsoluteValue;
	this.max = maxAbsoluteValue;
	this.ID = ID;
	this.value = null;
    }

    // copy constructor
    public Variable(Variable source) {
	this.min = source.min;
	this.max = source.max;
	this.ID = source.ID;
	if(source.value == null) {
	    this.value = null;
	} else {
	    this.value = new Integer(source.value.intValue());
	}
    }

    public int ID() {
	return ID;
    }

    public int max() {
	return max;
    }

    public int min() {
	return min;
    }

    public boolean set(int val) {
	System.out.println("trying to set " + ID + " with val " + val + " and min " + min + " and max " + max);
	if(val >= min && val <= max) {
	    value = new Integer(val);
	    return true;
	} else {
	    return false;
	}
    }

    public boolean isSolved() {
	if(value != null) {
	    return true;
	} else {
	    return false;
	}
    }

    public int value() {
	return value.intValue();
    }

}


