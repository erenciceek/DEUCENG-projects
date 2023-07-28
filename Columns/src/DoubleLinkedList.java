


public class DoubleLinkedList {
	private DoubleNode head;
	private DoubleNode tail;
	
	public DoubleLinkedList() {
		head = null;
		tail = null;
	}
	
	public void addToTheEnd(Object dataToAdd) {
		if(head == null && tail == null) {
			DoubleNode newNode = new DoubleNode(dataToAdd);
			head = newNode;
			tail = newNode;
		}
		else {
			DoubleNode newNode = new DoubleNode(dataToAdd);
			newNode.setPrev(tail);
			tail.setNext(newNode);
			tail = newNode;
		}
	}
	
	public void add(Contestant cont) {
		if(head == null && tail == null) {
			DoubleNode newNode = new DoubleNode(cont);
			head = newNode;
			tail = newNode;
		}
		else {
			DoubleNode newNode = new DoubleNode(cont);
			if(cont.getScore() > ((Contestant) head.getData()).getScore()) {
				newNode.setNext(head);
				head.setPrev(newNode);
				head = newNode;
			}
			else {
				DoubleNode temp = head;
				while(temp.getNext() != null && cont.getScore() < ((Contestant)temp.getNext().getData()).getScore()) {
					temp = temp.getNext();
				}
				newNode.setPrev(temp);
				newNode.setNext(temp.getNext());
				if(temp.getNext() != null)
					temp.getNext().setPrev(newNode);
				else
					tail = newNode;
				temp.setNext(newNode);
			}
		}
		
	}
	public int size(){
		 int count = 0;
		 DoubleNode temp = head;
		 while(temp != null){
		 count++;
		 temp = temp.getNext();
		 }
		 return count;
		}
	
	public void display() {
		if(head == null)
			System.out.println("List is empty!");
		else {
			DoubleNode temp = head;
			String space = " ";
			while(temp != null) {
				int spacenum = 15 - ((Contestant)temp.getData()).getName().length();				
				System.out.print(((Contestant)temp.getData()).getName() + space.repeat(spacenum) + ((Contestant)temp.getData()).getScore() + "\n");
				temp = temp.getNext();
			}
		}
	}
	
}
