

public class Problem572v2 {
    public static void main(String[] args) {
	ExpressionSet eqset = new ExpressionSet(Integer.parseInt(args[0]));
	Variable v = eqset.variable(0);
	SolutionTreeNode root = new SolutionTreeNode(eqset, v, v.min());
    }
}
