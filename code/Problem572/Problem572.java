/*
Idempotent matrices
Problem 572
A matrix MM is called idempotent if M2=MM2=M.
Let MM be a three by three matrix : M=⎛⎝⎜adgbehcfi⎞⎠⎟M=(abcdefghi).
Let C(n) be the number of idempotent three by three matrices MM with integer elements such that
−n≤a,b,c,d,e,f,g,h,i≤n−n≤a,b,c,d,e,f,g,h,i≤n.

C(1)=164 and C(2)=848.

Find C(200).
*/

/*
there's a need to be much smarter with the algorithm, and hence perhaps with the data structures
many equations are being repeatedly evaluated when it is already known that they solve for a particular branch of the tree
and there is room to reduce the search space more efficiently by searching for the range of solutions for a variable when there is only one unknown for a given equation

Perhaps in the tree node, keep track of which equations have been solved to avoid resolving them
also keep track of the range of possible solutions for the unnknown variables

In the equation class, write a method that solves for an equation when only one variable in it is unknown, and communicate this back to the tree node so it can update the range

IF A is indempotent then A' (i.e. transpose) is indempotent. Can perhaps use this fact to halve the search space, though I don't know how to do this cleanly yet. 
Perhaps I can create a way to insert a solution into the tree once found. 

It looks like the number of solutions for each seed value is a multiple of 4. There may also be some pattern to the number of solutions for each seed value from which I can deduce
rather than calculate the final answer, though this may be difficult to find?

 */

import java.util.*;

public class Problem572 {

    public static final int MAX_ELEMENT_INDEX = 8;

    public static final int MAX_ELEMENT_VALUE = 1;

    public static long solutionCount = 0;
    public static long nodeCount = 0;

    public static int testCount = 0;

    public static Equation[] eqns;

    public static void main(String[] args) {

	/* SET UP EQUATIONS */
	int A = 0;
	int B = 1;
	int C = 2;
	int D = 3;
	int E = 4; 
	int F = 5;
	int G = 6;
	int H = 7;
	int I = 8;

	eqns = new Equation[9];
	int[] terms;

	terms = new int[]{A,A, B,D, C,G, A};
	eqns[A] = new Equation(terms, A);

	terms = new int[]{A,B, B,E, C,H, B};
	eqns[B] = new Equation(terms, B);

	terms = new int[]{A,C, B,F, C,I, C};
	eqns[C] = new Equation(terms, C);

	terms = new int[]{D,A, E,D, F,G, D};
	eqns[D] = new Equation(terms, D);

	terms = new int[]{D,B, E,E, F,H, E};
	eqns[E] = new Equation(terms, E);

	terms = new int[]{D,C, E,F, F,I, F};
	eqns[F] = new Equation(terms, F);

	terms = new int[]{G,A, H,D, I,G, G};
	eqns[G] = new Equation(terms, G);

	terms = new int[]{G,B, H,E, I,H, H};
	eqns[H] = new Equation(terms, H);

	terms = new int[]{G,C, H,F, I,I, I};
	eqns[I] = new Equation(terms, I);


	/* FIND SOLUTIONS */

	int[] solution = new int[MAX_ELEMENT_INDEX+1]; // seed solution for matrix tree
	boolean[] known = new boolean[MAX_ELEMENT_INDEX+1]; // starts off all false for seed
	boolean[] equationSolved = new boolean[MAX_ELEMENT_INDEX+1];

	long time = System.currentTimeMillis();
	long prevSolCount = 0;
	for(int val = -MAX_ELEMENT_VALUE; val <= MAX_ELEMENT_VALUE; val++) {
	    System.out.println("\nseed value: " + val);
	    time = System.currentTimeMillis();
	    prevSolCount = solutionCount;
	    MatrixTreeNode seedNode = new MatrixTreeNode(solution, known, equationSolved, 0, val);
	    //System.out.println("solutions for that val: " + (solutionCount-prevSolCount));
	    //System.out.println("total so far: " + solutionCount);
	    //System.out.println("time taken: " + (System.currentTimeMillis()-time));

	}

	System.out.println("Total solutions: " + solutionCount);

    }

    public static void incrementSolutionCount() {
	solutionCount++;
    }

    // check whether this is possible for all nine equations
    public static boolean isPossible(int[] partialSolution, boolean[] known, boolean[] equationSolved, int testVariable, int testValue) {
	for(int i = 0; i < eqns.length; i++) {
	    if((equationSolved[i] == false) && (eqns[i].hasSolution(partialSolution, known, equationSolved, testVariable, testValue) == false)) {
		return false;
	    }
	}
	return true;
    }
}

class Equation {

    private int[] terms;
    private int ID;

    public Equation(int[] t, int ID) {
	terms = t.clone();
	this.ID = ID;
    }

    /*public int[] solutionRange(int[] partialSolution, boolean[] known, int testVariable) {
	int[] solutionRange = new int[2]; // max and min of solution range
	// binary search for minimum x, i.e. where x-1 has no solution and x does
	// first use recursive binary search to find one value that has a solution
	boolean solutionFound = false;

	}*/

    public boolean stillValid(int[] partialSolution, boolean[] known) {
	// still valid if there are unsolved variables
	for(int i = 0; i < terms.length; i++) {
	    if(known[terms[i]] == false) {
		return true;
	    }
	}

	// all variables are solved, so check the solution fits.
	int LHS = partialSolution[terms[0]]*partialSolution[terms[1]] + partialSolution[terms[2]]*partialSolution[terms[3]] + partialSolution[terms[4]]*partialSolution[terms[5]] - partialSolution[terms[6]];
	if(LHS == 0) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean hasSolution(int[] partialSolution, boolean[] known, boolean[] equationSolved, int testVariable, int testValue) {
	// first check if it's a solution if all (relevant) variables are known /** COULD BE MORE EFFICIENT?? **/
	int unknownCount = 0;
	int unknownIndex = -1;
	int unknownIndex2 = -1;
	for(int i = 0; i < terms.length; i++) {
	    if(known[terms[i]] == false) {
		unknownIndex = i; 
		unknownCount++; 
	    }
	}
	if(unknownCount == 0) {
	    equationSolved[this.ID] = true;
	    int LHS = partialSolution[terms[0]]*partialSolution[terms[1]] + partialSolution[terms[2]]*partialSolution[terms[3]] + partialSolution[terms[4]]*partialSolution[terms[5]] - partialSolution[terms[6]];
	    // System.out.println("has been solved, so testing validity of solution. LHS = " + LHS);

	    if(LHS == 0) {
		return true;
	    } else {
		return false;
	    }
	}
	
	if(unknownCount == 1) {
	    // solve for unknown
	    Integer unknownInteger =  solveFor(unknownIndex, partialSolution);
	    if(unknownInteger == null) {
		return false;
	    } 
	    if(unknownInteger.intValue() == Problem572.MAX_ELEMENT_VALUE*1000) {
		return true;// this is when divisor is zero so any value is possible
	    }
	    int unknownValue = unknownInteger.intValue();
	    if((unknownValue >= -Problem572.MAX_ELEMENT_VALUE) && (unknownValue <= Problem572.MAX_ELEMENT_VALUE)) {
		partialSolution[terms[unknownIndex]] = unknownValue;
		known[terms[unknownIndex]] = true;
		// But if we set a value, we need to make sure it doesn't stuff up any other equations
		for(int i = 0; i < Problem572.eqns.length; i++) {
		    if(Problem572.eqns[i].stillValid(partialSolution, known) == false) {
			return false;
		    }
		}
		// passed all the tests
		return true;
	    } else {
		return false;
	    }
	}

	// below happens only if we have two or more unsolved variables in this equation (except when a variable appears more than once !!! CAN FIX THIS)
	/* Eqn is term1 + term2 + term3 - term4 = 0 */
	// check whether can make LHS of equation >= 0 by making each term as positively large as possible
	int term1 = maximiseProduct(partialSolution[terms[0]], partialSolution[terms[1]], known[terms[0]], known[terms[1]]);
	int term2 = maximiseProduct(partialSolution[terms[2]], partialSolution[terms[3]], known[terms[2]], known[terms[3]]);
	int term3 = maximiseProduct(partialSolution[terms[4]], partialSolution[terms[5]], known[terms[4]], known[terms[5]]);
	int term4 = known[terms[6]] ? partialSolution[terms[6]] : -Problem572.MAX_ELEMENT_VALUE; // need to try to minimise this as it's subtracted from others
	if(term1 + term2 + term3 - term4 < 0) {
	    return false;
	}

	// check whether can make LHS of equation <= 0
	term1 = minimiseProduct(partialSolution[terms[0]], partialSolution[terms[1]], known[terms[0]], known[terms[1]]);
	term2 = minimiseProduct(partialSolution[terms[2]], partialSolution[terms[3]], known[terms[2]], known[terms[3]]);
	term3 = minimiseProduct(partialSolution[terms[4]], partialSolution[terms[5]], known[terms[4]], known[terms[5]]);
	term4 = known[terms[6]] ? partialSolution[terms[6]] : Problem572.MAX_ELEMENT_VALUE; // need to try to maximise this as it's subtracted from others
	if(term1 + term2 + term3 - term4 > 0) {
	    return false;
	}

	return true;
    }

    private Integer solveFor(int termIndex, int[] partialSolution) {
	int divisor;
	int separateSum;
	switch(termIndex) {
	case 0: // first product, first variable
	    divisor = partialSolution[terms[1]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[2]]*partialSolution[terms[3]] - partialSolution[terms[4]]*partialSolution[terms[5]];
	    break;

	case 1: // first product, second variable
	    divisor = partialSolution[terms[0]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[2]]*partialSolution[terms[3]] - partialSolution[terms[4]]*partialSolution[terms[5]];
	    break;

	case 2: // second product, first variable
	    divisor = partialSolution[terms[3]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[0]]*partialSolution[terms[1]] - partialSolution[terms[4]]*partialSolution[terms[5]];
	    break;

	case 3: // second product, 2nd variable
	    divisor = partialSolution[terms[2]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[0]]*partialSolution[terms[1]] - partialSolution[terms[4]]*partialSolution[terms[5]];
	    break;

	case 4: // third product, 1st variable
	    divisor = partialSolution[terms[5]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[0]]*partialSolution[terms[1]] - partialSolution[terms[2]]*partialSolution[terms[3]];
	    break;

	case 5: // fourth product, 2nd variable
	    divisor = partialSolution[terms[4]];
	    separateSum = partialSolution[terms[6]] - partialSolution[terms[0]]*partialSolution[terms[1]] - partialSolution[terms[2]]*partialSolution[terms[3]];
	    break;

	case 6: // stand alone variable
	    divisor = 1;
	    separateSum = partialSolution[terms[0]]*partialSolution[terms[1]] + partialSolution[terms[2]]*partialSolution[terms[3]] + partialSolution[terms[4]]*partialSolution[terms[5]];
	    break;

	default:
	    System.out.println("solve switch failure:" + terms[termIndex]);
	    divisor = 0; separateSum = 0;
	    System.exit(0);
	}
	if(divisor == 0) {
	    return new Integer(Problem572.MAX_ELEMENT_VALUE*1000); // a hack... to indicate any solution possible
	}
	if((separateSum%divisor) != 0) {
	    return null;
	} else {
	    //System.out.println("divisor = " + divisor + " and sum = " + separateSum);
	    return new Integer(separateSum/divisor);
	}
    }
    
    public int maximiseProduct(int x, int y, boolean xKnown, boolean yKnown) {
	if(xKnown) {
	    if(yKnown) {
		return x*y;
	    } else {
		return x > 0 ? x*Problem572.MAX_ELEMENT_VALUE : -x*Problem572.MAX_ELEMENT_VALUE;
	    }
	} else {
	    if(yKnown) {
		return y > 0 ? y*Problem572.MAX_ELEMENT_VALUE : -y*Problem572.MAX_ELEMENT_VALUE;
	    } else {
		return Problem572.MAX_ELEMENT_VALUE*Problem572.MAX_ELEMENT_VALUE;
	    }
	}
    }
 
    public int minimiseProduct(int x, int y, boolean xKnown, boolean yKnown) {
	if(xKnown) {
	    if(yKnown) {
		return x*y;
	    } else {
		return x < 0 ? x*Problem572.MAX_ELEMENT_VALUE : -x*Problem572.MAX_ELEMENT_VALUE;
	    }
	} else {
	    if(yKnown) {
		return y < 0 ? y*Problem572.MAX_ELEMENT_VALUE : -y*Problem572.MAX_ELEMENT_VALUE;
	    } else {
		return -Problem572.MAX_ELEMENT_VALUE*Problem572.MAX_ELEMENT_VALUE;
	    }
	}
    }


}

class MatrixTreeNode {

    private int[] solution;
    private boolean[] known;
    private boolean[] equationSolved; // corresponds to equations

    public MatrixTreeNode(int[] s, boolean[] k, boolean[] es, int nextUnknown, int nextValue) {
	Problem572.nodeCount++;
	solution = s.clone();
	known = k.clone();
	equationSolved = es.clone();
	solution[nextUnknown] = nextValue;
	known[nextUnknown] = true;
	if(Problem572.isPossible(solution, known, equationSolved, nextUnknown, nextValue)) {
	    // find new next unknown
	    boolean foundAnotherUnknown = false;
	    for(int i = nextUnknown + 1; i <= (Problem572.MAX_ELEMENT_INDEX); i++) {
		if(known[i] == false) {
		    foundAnotherUnknown = true;
		    // create child nodes
		    /*** POSSIBLE BIG IMPROVEMENT IF YOU USE BINARY SEARCH HERE FOR THE MINIMUM VIABLE VAL ***/
		    /*** ALSO BIG IMPROVEMENT IF YOU SET VARIABLES THAT HAVE ONLY ONE POSSIBLE VALUE IN AN EQUATION ***/
		    // use binary search to find the minimum value of the next unknown for which a solution is possible
		    //int testValue = 0;
		    //int minSearchValue = -MAX_ELEMENT_VALUE;
		    //boolean testValueFound = false;
		    for(int val = -Problem572.MAX_ELEMENT_VALUE; val <= Problem572.MAX_ELEMENT_VALUE; val++) {
			//System.out.println("\ncreating child with unknown index " + i + " and val=" + val + " while solution is " + Arrays.toString(solution) + " and k="+Arrays.toString(known));
			MatrixTreeNode childNode = new MatrixTreeNode(solution, known, equationSolved, i, val);
		    }
		    // we only create child nodes for a single unknown variable for any given node
		    break;
		}
	    }
	    if(foundAnotherUnknown == false) {
		// all variables solved for, so add to solution count
		Problem572.incrementSolutionCount();
		System.out.println("found solutions: " + Problem572.solutionCount + " with solution " + Arrays.toString(solution) + " and k="+Arrays.toString(known) + " and eqns solved " + Arrays.toString(equationSolved));
		System.out.println("nodes traversed: " + Problem572.nodeCount);
		/*if(Problem572.solutionCount >= 8) {
		    System.exit(0);
		    }*/
	    }
	} 
    }
}


