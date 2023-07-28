

import java.util.Random;

public class SingleLinkedList {

	Node head;	

	//For adding dices
	public void add(Object dataToAdd) {
		if(head == null) {
			Node newNode = new Node(dataToAdd);
			head = newNode;
		}
		else {
			Node temp = head;
			while(temp.getLink() != null)
				temp = temp.getLink();
			Node newNode = new Node(dataToAdd);
			temp.setLink(newNode);
		}
	}
	public Object headElement() {
        if(head != null)
            return head.getData();
        else
            return null;
    }
	public void randomForBox(SingleLinkedList sll) {
		Random rnd = new Random();
		int sllSize = 50;
		
		for(int i = 0; i < 50; i++) {
			Node temp = head;
			Node previous = null;
			int rndNumber = rnd.nextInt(sllSize)+1;
			for(int j = 1; j < rndNumber; j++) {
				previous = temp;
				temp = temp.getLink();
			}
			sll.add(temp.getData());
			if(previous == null) 
				head = head.getLink();
			else {
				previous.setLink(temp.getLink());
				temp = previous;
				previous = temp;
				temp = temp.getLink();
			}
			sllSize--;
		}
	}
	public int size() {
		if(head == null)
			return 0;
		else {
			int count = 0;
			Node temp = head;
			while(temp != null) {
				temp = temp.getLink();
				count++;
			}
			return count;
		}
	}

	public void display() {
		if(head == null)
			System.out.println("List is empty!");
		else {
			Node temp = head;
			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
	}
	
	
	
	public int count(Object data) {
		if(head == null) {
			System.out.println("List is empty!");
			return 0;
		}
		else {
			Node temp = head;
			int count = 0;
			while(temp != null) {
				if((Integer) temp.getData() == (Integer) data)
					count++;
				temp = temp.getLink();
			}
		return count;
		}
	}

	public boolean search(Object data) {
		if(head == null) {
			System.out.println("List is empty!");
			return false;
		}
		else {
			Node temp = head;
			while(temp != null) {
				if((Integer) temp.getData() == (Integer) data)
					return true;
				temp = temp.getLink();
			}
			return false;
		}
	}
	
	public void remove(Object dataToDelete) {
		if(head == null)
			System.out.println("List is empty");
		else {
			int count = 0;
			while((Integer) head.getData() == (Integer) dataToDelete) {
				head = head.getLink();
				count++;
			}
			Node temp = head;
			Node previous = null;
			while(temp != null) {
				if((Integer) temp.getData() == (Integer) dataToDelete) {
					previous.setLink(temp.getLink());
					temp = previous;
					count++;
				}
				previous = temp;
				temp = temp.getLink();
				if(count == 1)
					break;
			}
		}
	}
	
	public void deleteFirstElement() {
		if(head == null) {
			System.out.println("list is empty");
		}
		else {
			head = head.getLink();
		}
	}

	public void deleteLastElement() {
		if(head == null)
			System.out.println("List is empty");
		else {
			Node temp = head;
			Node previous = null;
			while(temp.getLink() != null) {
				previous = temp;
				temp = temp.getLink();
			}
			previous.setLink(null);
		}
	}
	
	
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		else
			return false;
	}
	
	
}
