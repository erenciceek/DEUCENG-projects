

public class ColumnNode {
	private Object data;
	private ColumnNode down;
	private ElementNode right;
	
	public ColumnNode(Object data) {
		this.data=data;
		down = null;
		right = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ColumnNode getDown() {
		return down;
	}

	public void setDown(ColumnNode down) {
		this.down = down;
	}

	public ElementNode getRight() {
		return right;
	}

	public void setRight(ElementNode right) {
		this.right = right;
	}
	
	

}
