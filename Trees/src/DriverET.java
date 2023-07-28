public class DriverET 
{
	public static void main(String[] args) 
	{		
		ExpressionTree a = new ExpressionTree("5");
		ExpressionTree b = new ExpressionTree("2");
		ExpressionTree c = new ExpressionTree("5");
		ExpressionTree d = new ExpressionTree("2");
		ExpressionTree e = new ExpressionTree("4");
		ExpressionTree f = new ExpressionTree("2");
		ExpressionTree g = new ExpressionTree("9");
		ExpressionTree h = new ExpressionTree("3");
		
		
		ExpressionTree _5Times2 = new ExpressionTree();
		_5Times2.setTree("*", a, b);

		ExpressionTree minus5 = new ExpressionTree();
		minus5.setTree("-", _5Times2, c);
		
		ExpressionTree times2 = new ExpressionTree();
		times2.setTree("*", minus5, d);
		
		ExpressionTree _4Divide2 = new ExpressionTree();
		_4Divide2.setTree("/", e, f);
		
		ExpressionTree minus = new ExpressionTree();
		minus.setTree("-", times2, _4Divide2);
		
		ExpressionTree _9Divide3 = new ExpressionTree();
		_9Divide3.setTree("/", g, h);
				
		ExpressionTree myTree = new ExpressionTree();
		myTree.setTree("+", minus, _9Divide3);
	

		System.out.println("evaluate() returns " + myTree.evaluate());	
		myTree.displayPostfixExpression();
		myTree.displayPrefixExpression();
		System.out.println("display:");
		BinaryNode<String> rootNode = (BinaryNode<String>) myTree.getRootNode();
		myTree.display(rootNode);
	}   
}  

     
