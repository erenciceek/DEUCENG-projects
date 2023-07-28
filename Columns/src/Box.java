

public class Box {
	private boolean visible;
	
	public Box(boolean visible) {
		this.visible = false;
	}
	public void mixingBox(SingleLinkedList mixedBox) {
		SingleLinkedList box = new SingleLinkedList();
		for(int i = 0; i < 5; i++) {
			for(int j = 1; j <= 10; j++) {
				box.add(j);
			}
		}
		box.randomForBox(mixedBox);
	}


	public boolean isVisible() {
		return visible;
	}


	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	
}
