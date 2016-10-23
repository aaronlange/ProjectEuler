
public class Problem572v3 {
    public static void main(String[] args) {
	if(args.length == 0) {
	    System.out.println("please input n");
	    System.exit(0);
	}
	int maxAbsValue = Integer.parseInt(args[0]);
	SolutionTreeNode root = new SolutionTreeNode(maxAbsValue);
    }

}
