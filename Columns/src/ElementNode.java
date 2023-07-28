

public class ElementNode {
	private Object data;
	private ElementNode next;
	
	public ElementNode(Object data) {
		this.data = data;
		next = null;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public ElementNode getNext() {
		return next;
	}

	public void setNext(ElementNode next) {
		this.next = next;
	}
	
}
