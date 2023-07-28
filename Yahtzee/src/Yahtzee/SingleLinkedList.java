package Yahtzee;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class SingleLinkedList{
	
	private Node head;
	
	
	public void add(Object data) { //appends to the end without a check
		Node newNode = new Node(data);
		
		if(head == null) {
			head = newNode;
		}
		else {
			Node temp = head;
			while(temp.getLink()!=null) {
				temp = temp.getLink();
			}
			temp.setLink(newNode);
		}	
	}
	
	
	public void addwithsort(Player data) {  //adds in order
		Node newNode = new Node(data); 
		
		if(head == null) {  // boþsa
			head = newNode;
		}
		else {
			Player p = (Player)newNode.getData();
		 
			if((((Player)data).getScore() > ((Player)head.getData()).getScore()) || (((Player)data).getScore() == ((Player)head.getData()).getScore())) { //öne ekleme
				newNode.setLink(head);
				head = newNode;
			}
			else if(data.getScore() > p.getScore()) { // sona 
				Node temp = head;
				while(temp.getLink()!=null) {
					temp = temp.getLink();
				}
				temp.setLink(newNode);
				
			}
			else {  
				Node temp = head;
				Node previous = null;
				while(temp!=null && (((Player)data).getScore() < ((Player)temp.getData()).getScore())) {
					previous = temp;
					temp = temp.getLink();
				}
				if(temp == null) {
					previous.setLink(newNode);
		
				}
				else {
					newNode.setLink(temp);
					previous.setLink(newNode);
				
				}
			}
		}
	}

	
	public int size() {
		int count = 0 ;
		if(head == null) {
			System.out.println("Linked list is empty");
		}
		else{
			Node temp = head;
			while(temp != null) {
				count++;
				temp= temp.getLink();
			}
		}
		return count;
	}
	
	
	public void display() {
		if(head == null) {
			System.out.println("Linked list is empty");
		}
		else {
			Node temp = head;
			while(temp!=null) {
				System.out.print(temp.getData()+" ");
				temp=temp.getLink();
			}
		}
	}
	
	
	public String displayLine() {
		String line = "";
		if(head == null) {
			System.out.println("Linked list is empty");
			return " ";
		}
		else {
			Node temp = head;
			while(temp!=null) {
				if(temp.getData() == null) {
					continue;
				}
				line += temp.getData()+" ";
				temp=temp.getLink();
			}
		}
		return line;
	}
	
	
	public void displayScoreTable(String filename) throws IOException { // prints score table to console and txt file 
		PrintWriter outputStream = null;
		outputStream = new PrintWriter(new FileWriter(filename));
		if(head == null) {
			System.out.println("Linked list is empty");
		}
		else {
			Node temp = head;
			int count = 0;
			while(temp!=null&&count<10) {  //top 10 players are printed
				Player p= (Player) temp.getData();
				
				System.out.println(p.getName()+ " " + String.valueOf(p.getScore()));
				outputStream.println(p.getName()+ " " + String.valueOf(p.getScore()));
				temp = temp.getLink();
				count++;
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}
	
	
	public void delete(Object data) { // completely deletes data from linked list
		if(head==null) {
			System.out.println("Linked list is empty");
		}
		else {
			while((int)head.getData()==(int)data) {
				head = head.getLink();
			}
			Node previous = null;
			Node temp = head;
			
			while(temp != null) {
				if((int)temp.getData()==(int)data) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
	}
	
	
	
	public void deleteforStraight(Object data) { //Deletes the first element it sees equal to data for Large Straight
        boolean isDeleted = false;
        if(head == null)
            System.out.println("List is empty");
        else {
            while((Integer)head.getData() == (Integer)data) {
                head = head.getLink();
                isDeleted= true;
                break;
            }


            Node temp = head;
            Node previous = null;

            while(temp != null && !isDeleted) {
                if((Integer)temp.getData() == (Integer)data) {
                    previous.setLink(temp.getLink());
                    temp = previous;
                    isDeleted = true;
                }
                previous = temp;
                temp = temp.getLink();
            }


        }
    }
	
	
	public void remove4element(Object dataToDelete) { // Deletes the data entered as a parameter for yahtzee 4 times
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
				if(count == 4)
					break;
			}
		}
	}
	
	
	
	public void remove1element(Object dataToDelete) { //Deletes the first element that is equal to data
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
	
	

	
	public boolean search(Object data) {
		if(head == null) {
			System.out.println("Linked list is empty");
			return false;
		}
		else {
			Node temp = head;
			while(temp!=null) {
				if((int)data == (int)temp.getData()) {
					return true;
				}
				temp = temp.getLink();
			}
			return false;
		}
	}
	
	
	
	
	public int count(Object data) { //Returns the number of data in the linked list
		int count = 0;
		if(head == null) {
			System.out.println("Linked list is empty");
			return 0 ;
		}
		else {
			Node temp = head;
			while(temp!=null) {
				if((int)data  == (int)temp.getData())
					count++;
				temp = temp.getLink();
			}
		}
		return count;
	}

	

	
	
	public boolean Yahtzee() { // checks for yahtzee
		boolean flag= false;
		if(head==null) {
			System.out.println("Linked list is empty");
			return false;
		}
		else {
			Node temp = head;
			while(temp!=null) {
				int countforcurrent = count(temp.getData());
				if(countforcurrent>=4) {
					flag=true;
					break;
				}
			
				temp=temp.getLink();
			}
			return flag;
		}
		
	}
	
	
	
	public int YahtzeeDeleter() { // When yahtzee it deletes the required elements and returns how many yahtzees were generated
		int Yahtzeecounter = 0;
		boolean flag= false;
		if(head==null) {
			System.out.println("Linked list is empty");
			return  Yahtzeecounter;
		}
		else {
			Node temp = head;
			while(temp!=null) {
				int countforcurrent = count(temp.getData());
				if(countforcurrent>=4) {
					Yahtzeecounter++;					
					remove4element(temp.getData());	
				}			
				temp=temp.getLink();
			}
			return Yahtzeecounter;
		}
		
	}
	
	
	
	public boolean LargeStraight() { //large straight control
		if(head==null) {
			System.out.println("Linked list is empty");
			return false;
		}
		else {
			if(search(1)&&search(2)&&search(3)&&search(4)&&search(5)&&search(6)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
}
