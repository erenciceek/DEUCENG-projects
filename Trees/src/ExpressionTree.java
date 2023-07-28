public class ExpressionTree extends BinaryTree<String> implements ExpressionTreeInterface {
	public ExpressionTree() {
	}

	public ExpressionTree(String rootData) {
		super(rootData);
	}

	public double evaluate() {
		return evaluate(getRootNode());
	}

	private double evaluate(BinaryNode<String> rootNode) {
		double result = 0;

		if (rootNode == null)
			result = 0;
		else if (rootNode.isLeaf()) {
			String variable = rootNode.getData();
			result = Integer.parseInt(variable);
		} else {
			double firstOperand = evaluate(rootNode.getLeftChild());
			double secondOperand = evaluate(rootNode.getRightChild());
			String operator = rootNode.getData();
			result = compute(operator, firstOperand, secondOperand);
		}

		return result;
	}

	private double compute(String operator, double firstOperand, double secondOperand) {
		double result = 0;

		if (operator.equals("+"))
			result = firstOperand + secondOperand;
		else if (operator.equals("-"))
			result = firstOperand - secondOperand;
		else if (operator.equals("*"))
			result = firstOperand * secondOperand;
		else if (operator.equals("/"))
			result = firstOperand / secondOperand;

		return result;
	}

	public void displayPrefixExpression() {
		BinaryNode<String> rootNode = (BinaryNode<String>) getRootNode();
		System.out.println("preorder:");
		preorder(rootNode);
		System.out.println();
	}

	public void displayPostfixExpression() {
		BinaryNode<String> rootNode = (BinaryNode<String>) getRootNode();
		System.out.println("postorder:");
		rootNode = (BinaryNode<String>) getRootNode();
		postorder(rootNode);
		System.out.println();
	}

	public void display(BinaryNode<String> tree) { //preorder yedek
		
		   if(tree == null)
			   return;
		   System.out.print(tree.getData() + " ");
		   display(tree.getLeftChild());
		   display(tree.getRightChild());
	   }
	private void postorder(BinaryNode<String> rootNode) {
		if (rootNode != null) {
			postorder(rootNode.getLeftChild());
			postorder(rootNode.getRightChild());
			System.out.print(rootNode.getData() + " ");
		}
	}

	private void preorder(BinaryNode<String> rootNode) {
		if (rootNode != null) {
			System.out.print(rootNode.getData() + " ");
			preorder(rootNode.getLeftChild());
			preorder(rootNode.getRightChild());
		}
	}
}
