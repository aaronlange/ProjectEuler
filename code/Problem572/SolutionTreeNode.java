public class SolutionTreeNode {

    private static long solutionCount = 0;

    private ExpressionSet exprSet;

    static int count = 0;

    SolutionTreeNode(ExpressionSet e, Variable var, int val) {
	this.exprSet = e;
	System.out.println("solution so far before set " + exprSet.solutionString());
	System.out.println("exprset ref: " + e.toString());

	if(exprSet.set(var, val)) {
	    System.out.println("solution so far after set " + exprSet.solutionString());
	    if(exprSet.isSolved()) {
		System.out.println("found solution: " + exprSet.solutionString());
		solutionCount++;
	    } else {
		// procreate
		ExpressionSet exprSetCopy = new ExpressionSet(exprSet);
		Variable nextVarToSet = exprSetCopy.nextUnknown();
		int minVal = nextVarToSet.min();
		int maxVal = nextVarToSet.max();
		for(val = minVal; val < maxVal; val++) {
		    count++;
		    if(count > 10) System.exit(0);
		    System.out.println("going to set var " + nextVarToSet.ID() + " with val " + val);
		    new SolutionTreeNode(exprSetCopy, nextVarToSet, val);
		}
	    }
	}
    }
}
