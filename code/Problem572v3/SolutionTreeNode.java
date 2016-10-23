/*
Brute force:
n=1 -> 29524 nodes
n=2 -> 2441406 nodes
*/

public class SolutionTreeNode {

    public static final int A = 0;
    public static final int B = 1;
    public static final int C = 2;
    public static final int D = 3;
    public static final int E = 4;
    public static final int F = 5;
    public static final int G = 6;
    public static final int H = 7;
    public static final int I = 8;

    public static final int VAR_COUNT = 9;

    private Variable[] v;

    private static long solutionCount = 0;
    private static long nodeCount = 0;

    // root node constructor
    public SolutionTreeNode(int maxAbsoluteValue) {
	nodeCount++;
	v = new Variable[VAR_COUNT];
	for(int i = 0; i < v.length; i++) {
	    v[i] = new Variable(maxAbsoluteValue, i);
	}
	// procreate
	int nextUnsolved = 0;
	int min = v[nextUnsolved].min();
	int max = v[nextUnsolved].max();
	for(int i = min; i <= max; i++) {
	    new SolutionTreeNode(this.v, nextUnsolved, i);
	}
	System.out.println("Solutions found = " + solutionCount);
    }
    
    public SolutionTreeNode(Variable[] parentV, int variableID, int value) {
	nodeCount++;
	// copy parent's variables
	v = new Variable[VAR_COUNT];
	for(int i = 0; i < v.length; i++) {
	    v[i] = new Variable(parentV[i]);
	}

	// set new variable 
	v[variableID].set(value);
	System.out.println("before tying to solve: " + solutionToString());
	// solve any extra variables, if an expression has only on uknown left
	boolean validSolution = tryToCompleteExpression();

	System.out.println(nodeCount + "th node is " + solutionToString());
		if(nodeCount > 20) System.exit(0);

	if(validSolution) {
	    // check for indempotency
	    boolean allVariablesSolved = true;
	    int nextUnsolved = -1;
	    for(int i = 0; i < v.length; i++) {
		if(!v[i].isSolved()) {
		    allVariablesSolved = false; // not indempotent if one of the variables doesn't yet have a value
		    nextUnsolved = i;
		    break;
		}
	    }
	    if(allVariablesSolved) { // all variables have been solved, so check the squared matrix element expressions
		boolean solutionFound = true;
		if(valueOfExpression(v[A],v[A], v[B],v[D], v[C],v[G], v[A]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[A],v[B], v[B],v[E], v[C],v[H], v[B]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[A],v[C], v[B],v[F], v[C],v[I], v[C]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[D],v[A], v[E],v[D], v[F],v[G], v[D]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[D],v[B], v[E],v[E], v[F],v[H], v[E]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[D],v[C], v[E],v[F], v[F],v[I], v[F]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[G],v[A], v[H],v[D], v[I],v[G], v[G]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[G],v[B], v[H],v[E], v[I],v[H], v[H]) != 0) {
		    solutionFound = false;
		} else if(valueOfExpression(v[G],v[C], v[H],v[F], v[I],v[I], v[I]) != 0) {
		    solutionFound = false;
		}
		// it passed all the tests
		if(solutionFound) { 
		    System.out.println("Found solution: " + solutionToString());
		    solutionCount++;
		}
	    } else { // not all solutions solved, so have children to create
		int min = v[nextUnsolved].min();
		int max = v[nextUnsolved].max();
		for(int i = min; i <= max; i++) {
		    new SolutionTreeNode(this.v, nextUnsolved, i);
		}
	    }
	}
    }
    
    private boolean tryToCompleteExpression() {
	int[] solvedCount = new int[VAR_COUNT];
	for(int i = 0; i < v.length; i++) {
	    if(v[i].isSolved()) {
		int vGroupLeader = (i / 3) * 3;
		int vOffset = i % 3;
		solvedCount[vGroupLeader]++;
		solvedCount[vGroupLeader +1]++;
		solvedCount[vGroupLeader +2]++;
		switch(vGroupLeader) {
		case A:
		    solvedCount[D + vOffset]++;
		    solvedCount[G + vOffset]++;
		    break;
		case D:
		    solvedCount[A + vOffset]++;
		    solvedCount[G + vOffset]++;
		    break;
		case G:
		    solvedCount[A + vOffset]++;
		    solvedCount[D + vOffset]++;
		    break;
		}
	    }
	}
	for(int i = 0; i < v.length; i++) {
	    if(solvedCount[i] == 4) { // one unsolved variable
		return solveFinalVariable(i);
	    }
	}
	return true;
    }

    private boolean solveFinalVariable(int exprID) {
	int vGroupLeader = (exprID / 3) * 3;
	int vOffset = exprID % 3;

	// get the expression's variables and find the unsolved one
	int unsolvedLocation = -1;
	Variable[] exprV = new Variable[7];
	exprV[6] = v[exprID];
	for(int i = 0; i <= 2; i++) {
	    exprV[i*2] = v[vGroupLeader + i];
	    exprV[i*2 + 1] = v[i*3 + vOffset];
	} 
	int unsolvedCount = 0;
	for(int i = 0; i < exprV.length; i++) {
	    if(exprV[i].isSolved() == false) {
		unsolvedLocation = i;
		unsolvedCount++;
	    }
	}
	// if it's the repeated variable, ignore (possible optimisation for later)
	if(unsolvedCount == 1) {
	    int num = 10000000;
	    int div = 50000000;
	    boolean canSolve = false;
	    switch(unsolvedLocation) {
	    case 0:
		div = exprV[1].value();
		if(div != 0) {
		    num = exprV[2].value()*exprV[3].value() + exprV[4].value()*exprV[5].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 1:
		div = exprV[0].value();
		if(div != 0) {
		    num = exprV[2].value()*exprV[3].value() + exprV[4].value()*exprV[5].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 2:
		div = exprV[3].value();
		if(div != 0) {
		    num = exprV[0].value()*exprV[1].value() + exprV[4].value()*exprV[5].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 3:
		div = exprV[2].value();
		if(div != 0) {
		    num = exprV[0].value()*exprV[1].value() + exprV[4].value()*exprV[5].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 4:
		div = exprV[5].value();
		if(div != 0) {
		    num = exprV[0].value()*exprV[1].value() + exprV[2].value()*exprV[3].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 5:
		div = exprV[4].value();
		if(div != 0) {
		    num = exprV[0].value()*exprV[1].value() + exprV[2].value()*exprV[3].value() - exprV[6].value() ;
		    if(num % div == 0) {
			canSolve = true;
		    }
		}
		break;
	    case 6:
		return true; // not trying to solve it
	    }
	    if(canSolve) {
		boolean isValidValue = exprV[unsolvedLocation].set(num/div);
		//		System.out.println("Solved for " + exprV[unsolvedLocation].ID() + " which now has value " + exprV[unsolvedLocation].value());
		return isValidValue;
	    } else {
		return false;
	    }
	}
	return false; // not trying
    }

    private String solutionToString() {
	String solution = new String("(");
	for(int i = 0; i < v.length-1; i++) {
	    if(v[i].isSolved()) {
		solution = solution.concat(v[i].value() + ", ");
	    } else {
		solution = solution.concat("?, ");
	    }
	}
	if(v[v.length-1].isSolved()) {
	    solution = solution.concat(v[v.length-1].value() + ")");
	} else {
	    solution = solution.concat("?)");
	}
	return solution;
    }

    private int valueOfExpression(Variable x0, Variable x1, Variable x2, Variable x3, Variable x4, Variable x5, Variable x6) {
	return x0.value()*x1.value() + x2.value()*x3.value() + x4.value()*x5.value() - x6.value();
    }



}
