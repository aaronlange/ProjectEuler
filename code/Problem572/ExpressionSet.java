import java.util.*;

public class ExpressionSet {

    public static final int VAR_COUNT = 9;

    private static final int A = 0;
    private static final int B = 1;
    private static final int C = 2; 
    private static final int D = 3;
    private static final int E = 4;
    private static final int F = 5;
    private static final int G = 6;
    private static final int H = 7;
    private static final int I = 8;

    Variable[] v;
    Expression[] e;

    public ExpressionSet(int maxAbsVal) {
	v = new Variable[VAR_COUNT];
	for(int i = 0; i < v.length; i++) {
	    v[i] = new Variable(maxAbsVal, i);
	}
	e = new Expression[VAR_COUNT];
	e[A] = new Expression(v[A],v[A], v[B],v[D], v[C],v[G], v[A]);
	e[B] = new Expression(v[A],v[B], v[B],v[E], v[C],v[H], v[B]);
	e[C] = new Expression(v[A],v[C], v[B],v[F], v[C],v[I], v[C]);
	e[D] = new Expression(v[D],v[A], v[E],v[D], v[F],v[G], v[D]);
	e[E] = new Expression(v[D],v[B], v[E],v[E], v[F],v[H], v[E]);
	e[F] = new Expression(v[D],v[C], v[E],v[F], v[F],v[I], v[F]);
	e[G] = new Expression(v[G],v[A], v[H],v[D], v[I],v[G], v[G]);
	e[H] = new Expression(v[G],v[B], v[H],v[E], v[I],v[H], v[H]);
	e[I] = new Expression(v[G],v[C], v[H],v[F], v[I],v[I], v[I]);
    }

    // copy constructor
    public ExpressionSet(ExpressionSet source) {
	this.v = new Variable[VAR_COUNT];
	for(int i = 0; i < v.length; i++) {
	    v[i] = new Variable(source.v[i]);
	}
	e = new Expression[VAR_COUNT];
	e[A] = new Expression(v[A],v[A], v[B],v[D], v[C],v[G], v[A], source.e[A]);
	e[B] = new Expression(v[A],v[B], v[B],v[E], v[C],v[H], v[B], source.e[B]);
	e[C] = new Expression(v[A],v[C], v[B],v[F], v[C],v[I], v[C], source.e[C]);
	e[D] = new Expression(v[D],v[A], v[E],v[D], v[F],v[G], v[D], source.e[D]);
	e[E] = new Expression(v[D],v[B], v[E],v[E], v[F],v[H], v[E], source.e[E]);
	e[F] = new Expression(v[D],v[C], v[E],v[F], v[F],v[I], v[F], source.e[F]);
	e[G] = new Expression(v[G],v[A], v[H],v[D], v[I],v[G], v[G], source.e[G]);
	e[H] = new Expression(v[G],v[B], v[H],v[E], v[I],v[H], v[H], source.e[H]);
	e[I] = new Expression(v[G],v[C], v[H],v[F], v[I],v[I], v[I], source.e[I]);
    }

    public Variable nextUnknown() {
	for(int i = 0; i < v.length; i++) {
	    if(v[i].isSolved() == false) {
		return v[i];
	    }
	} 
	return null;
    }

    public boolean isSolved() {
	for(int i = 0; i < e.length; i++) {
	    if(e[i].unsolvedCount() > 0) {
		return false;
	    }
	} 
	return true;
    }

    public String solutionString() {
	String solStr = new String("(");
	for(int i = 0; i < v.length; i++) {
	    if(v[i].isSolved()) {
		solStr = solStr.concat((v[i].value() + ", "));
	    } else {
		solStr = solStr.concat("null, ");
	    }
	}
	solStr = solStr.concat(")");
	return solStr;
    }

    public Variable variable(int id) {
	return v[id];
    }

    /*
      Try to set given variable to the given value. Return true if successful, false otherwise.     
    */
    public boolean set(Variable var, int val) {
	System.out.println("trying to set " + var.ID() + " with val " + val);
	// check whether the given value is consistent with possible values for the variable
	if(var.set(val)) {
	    // communicate variable setting to expressions and check for consistency with them
	    int varID = var.ID();
	    switch(varID) {
	    case A: 
		if(!e[A].decrementUnsolvedCount() || !e[B].decrementUnsolvedCount() || !e[C].decrementUnsolvedCount() || !e[D].decrementUnsolvedCount() || !e[G].decrementUnsolvedCount()) {
		    return false;
		} 
		break;
	    case B:
		if(!e[A].decrementUnsolvedCount() || !e[B].decrementUnsolvedCount() || !e[C].decrementUnsolvedCount() || !e[E].decrementUnsolvedCount() || !e[H].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case C: 
		if(!e[A].decrementUnsolvedCount() || !e[B].decrementUnsolvedCount() || !e[C].decrementUnsolvedCount() || !e[F].decrementUnsolvedCount() || !e[I].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case D: 
		if(!e[D].decrementUnsolvedCount() || !e[E].decrementUnsolvedCount() || !e[F].decrementUnsolvedCount() || !e[A].decrementUnsolvedCount() || !e[G].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case E:
		if(!e[D].decrementUnsolvedCount() || !e[E].decrementUnsolvedCount() || !e[F].decrementUnsolvedCount() || !e[B].decrementUnsolvedCount() || !e[H].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case F:
		if(!e[D].decrementUnsolvedCount() || !e[E].decrementUnsolvedCount() || !e[F].decrementUnsolvedCount() || !e[C].decrementUnsolvedCount() || !e[I].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case G:
		if(!e[G].decrementUnsolvedCount() || !e[H].decrementUnsolvedCount() || !e[I].decrementUnsolvedCount() || !e[A].decrementUnsolvedCount() || !e[D].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case H:
		if(!e[G].decrementUnsolvedCount() || !e[H].decrementUnsolvedCount() || !e[I].decrementUnsolvedCount() || !e[B].decrementUnsolvedCount() || !e[E].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    case I:
		if(!e[G].decrementUnsolvedCount() || !e[H].decrementUnsolvedCount() || !e[I].decrementUnsolvedCount() || !e[C].decrementUnsolvedCount() || !e[F].decrementUnsolvedCount()) {
		    return false;
		}
		break;
	    default:
		System.err.println("Problem with switch in EquationSet.set(..): " + var.ID());
		System.exit(0);		
	    }
	    // solve expressions further, if possible
	    if(this.reduceSolutionSpace()) {
		System.out.println("val 1 is solved? " + var.isSolved());
		System.out.println("reduced solution space successfully: " + this.solutionString());
		return true;
	    } else {
		return false;
	    }
	} else {
	    return false;
	}
    }

    /*
      Find expressions that have only one unknown. 
      Solve for the unknown.
      Check for validity with other (relevant) expressions.
      If all good, then 

      !!! HAVEN'T YET ADDED IN REDUCTION IN RANGE WHEN TWO UNKNOWNS ARE PRESENT

      Returns false if reduction leads to an inconsistency, true otherwise.
    */
    private boolean reduceSolutionSpace() {
	boolean haveReduced = false;
	for(int i = 0; i < e.length; i++) {
	    if(e[i].unsolvedCount() == 1) {
		boolean variableSolved = false;
		try{
		    variableSolved = e[i].solveFinalVariable();
		} catch(MultipleSolutionsException e) {
		    // ignore, do nothing more with this variable
		    continue;
		}
		if(variableSolved) {
		    haveReduced = true;
		    break; // we only want to solve one at a time
		} else {
		    return false;
		}
	    }
	}
	if(haveReduced) {
	    return this.reduceSolutionSpace(); // keep going until we can't reduce any further
	}
	return true;
    }
}

class Expression {

    private static final int T1V1 = 0;
    private static final int T1V2 = 1;
    private static final int T2V1 = 2;
    private static final int T2V2 = 3;
    private static final int T3V1 = 4;
    private static final int T3V2 = 5;
    private static final int T4 = 6;

    public static final int VAR_COUNT = 7;

    public static final int UNIQUE_VARS = 5;

    private Variable[] v;
    private int unsolvedVars = UNIQUE_VARS;

    private Integer value;

    public Expression(Variable t1v1, Variable t1v2, Variable t2v1, Variable t2v2, Variable t3v1, Variable t3v2, Variable t4) {
	v = new Variable[VAR_COUNT];
	v[T1V1] = t1v1;
	v[T1V2] = t1v2;
	v[T2V1] = t2v1;
	v[T2V2] = t2v2;
	v[T3V1] = t3v1;
	v[T3V2] = t3v2;
	v[T4] = t4;

	this.value = null;
    }

    public Expression(Variable t1v1, Variable t1v2, Variable t2v1, Variable t2v2, Variable t3v1, Variable t3v2, Variable t4, Expression source) {
	v = new Variable[VAR_COUNT];
	v[T1V1] = t1v1;
	v[T1V2] = t1v2;
	v[T2V1] = t2v1;
	v[T2V2] = t2v2;
	v[T3V1] = t3v1;
	v[T3V2] = t3v2;
	v[T4] = t4;

	this.value = source.value;
	this.unsolvedVars = source.unsolvedVars;
    }

    /* 
       Only checks whether fully solved expression is zero. If unsolved, doesn't check for solution within unknowns
    */
    public boolean couldEqualZero() {
	if(unsolvedVars > 0) {
	    return true;
	} else {
	    return (value.intValue() == 0);
	}
    }

    private void evaluate() {
	this.value = new Integer((v[T1V1].value()*v[T1V2].value() + v[T2V1].value()*v[T2V2].value() + v[T3V1].value()*v[T3V2].value() - v[T4].value()));
    }

    public int unsolvedCount() {
	return unsolvedVars;
    }

    // returns true if unique valid solution was found, false if no solution possible
    // throws exception if solvable but with multiple possible solutions
    public boolean solveFinalVariable() throws MultipleSolutionsException {
	// find the variable
	for(int i = 0; i < v.length; i++) {
	    if(!v[i].isSolved()) {
		// solve for it
		return this.solveFor(i);
	    }
	}
	// should never get here
	System.err.println("Something went wrong solving for final variable: couldn't find an unsolved one");
	return false;
    }

    private boolean solveFor(int vIndicator) throws MultipleSolutionsException {
	int divisor = 0;
	int numerator = 0;
	switch(vIndicator) {
	case T1V1:
	    divisor = v[T1V2].value();
	    numerator = v[T4].value() - v[T2V1].value()*v[T2V2].value() - v[T3V1].value()*v[T3V2].value();
	    break;
	case T1V2:
	    divisor = v[T1V1].value();
	    numerator = v[T4].value() - v[T2V1].value()*v[T2V2].value() - v[T3V1].value()*v[T3V2].value();
	    break;
	case T2V1:
	    divisor = v[T2V2].value();
	    numerator = v[T4].value() - v[T1V1].value()*v[T1V2].value() - v[T3V1].value()*v[T3V2].value();
	    break;
	case T2V2:
	    divisor = v[T2V1].value();
	    numerator = v[T4].value() - v[T1V1].value()*v[T1V2].value() - v[T3V1].value()*v[T3V2].value();
	    break;
	case T3V1:
	    divisor = v[T3V2].value();
	    numerator = v[T4].value() - v[T2V1].value()*v[T2V2].value() - v[T1V1].value()*v[T1V2].value();
	    break;
	case T3V2:
	    divisor = v[T3V1].value();
	    numerator = v[T4].value() - v[T2V1].value()*v[T2V2].value() - v[T1V1].value()*v[T1V2].value();
	    break;
	case T4:
	    divisor = 1;
	    numerator = v[T1V1].value()*v[T1V2].value() + v[T2V1].value()*v[T2V2].value() + v[T3V1].value()*v[T3V2].value();
	default:
	    System.err.println("Something went wrong when solving for individual variable in equation");
	    System.exit(0);
	}
	if(divisor == 0) {
	    throw new MultipleSolutionsException("Any solution possible due to division by zero");
	}
	if((numerator%divisor) != 0) {
	    return false; // can't solve it as an integer value
	} else {
	    if(v[vIndicator].set(numerator/divisor)) {
		return true;
	    } else {
		// the solution does not belong in the variables range
		return false;
	    }
	}
    }

    public boolean isEvaluated() {
	if(value == null) {
	    for(int i = 0; i < v.length; i++) {
		if(!v[i].isSolved()) {
		    return false;
		}
	    }
	    // all values are solved, so we can evaluate the expression
	    this.evaluate();
	} else {
	    return true;
	}
    }
}


