

import java.awt.Color;

import enigma.console.TextAttributes;
import enigma.core.Enigma;



public class MLL {
	private ColumnNode head;
	//TextAttributes color = new TextAttributes(Color.WHITE,Color.BLACK);
	
	public MLL() {
		head = null;
	}
	
	
	public void addColumn(int columnNumber) { // adds a new column
		ColumnNode newNode = new ColumnNode(columnNumber);
		if(head == null) {
			head = newNode;
		}
		else{
			ColumnNode temp = head;
			while(temp.getDown()!=null) {
				temp = temp.getDown();
			}
			temp.setDown(newNode);
			
		}
		
	}
	
	
	public void addElement(int columnNumber , Object element) { 
		// Adds an element to the column entered as a parameter
		if(head == null) {
			System.out.println("There is no column");
		}
		else {
			ElementNode newNode = new ElementNode(element);
			ColumnNode temp = head;
			while(temp!=null && columnNumber != (int)temp.getData()) {
				temp = temp.getDown();
			}
			if(temp!=null) {
				if(temp.getRight()==null) {
					temp.setRight(newNode);
				}
				else {
					ElementNode tempE = temp.getRight();
					while(tempE.getNext()!=null) {
						tempE = tempE.getNext();
					}
					tempE.setNext(newNode);
				}
			}
			else {
				System.out.println("The requested column does not exist");
			}
		}		
	}
	
	
	
	public void display(int column,int height) {  // prints columns and heights
		enigma.console.Console cn = Enigma.getConsole("Game", 100, 40, 20, 2);
		TextAttributes color = new TextAttributes(Color.WHITE,Color.BLACK);
		if(head == null) {
			System.out.println("there is no data to display");
		}
		else {
			ColumnNode temp = head;
			int cursorx =2;
			while(temp!=null) {
				if(temp.getRight()!=null) {
					ElementNode tempE = temp.getRight();
					int i=0;
					int count = 1;
					while(tempE!=null) {
						if(tempE.getData()!=null) {
							cn.getTextWindow().setCursorPosition(cursorx, i+2);
							if((int)temp.getData()==column&&count==height) { 
								// to write the point we are on in red
								color = new TextAttributes(Color.RED);
			                    cn.setTextAttributes(color);
								System.out.print(tempE.getData().toString());	
								color = new TextAttributes(Color.WHITE); // to revert to its original color
								cn.setTextAttributes(color);
							}
							else {
								color = new TextAttributes(Color.WHITE);
			                    cn.setTextAttributes(color);
								System.out.print(tempE.getData().toString());
							}
							i++;
							count++;
							tempE = tempE.getNext();
						}
						else
							break;
					}
				}
				cursorx+=4;			
				temp = temp.getDown();
			}
		}
	}
	
	
	public int columnSize() {  // return the number of columns
		if(head == null) {
			System.out.println("there is no column");
			return 0;
		}
		else {
			ColumnNode temp = head;
			int count = 0;
			while(temp!=null) {
				count++;
				temp = temp.getDown();
			}
			return count;
		}
	}
	
	
	public int elementSize(int columnNumber) { // returns the number of elements on column that entered parameter
		if(head == null) {
			System.out.println("there is no column");
			return 0;
		}
		else {
			int count = 0;
			ColumnNode temp = head;
			while(temp!=null && (int)temp.getData()!=columnNumber) {
				temp = temp.getDown();
			}
			if(temp!=null) {
				count =0;
				ElementNode tempE = temp.getRight();
				while(tempE!=null) {
					count++;
					tempE = tempE.getNext();
				}
				temp = temp.getDown();
			}
			else {
				System.out.println("there is no column like : C"+ columnNumber);
			}
			return count;
		}
		
	}
	
	public int[] transferFromBox(Object data) {
		int[] columnNumberstoTransfer = new int[5];
		columnNumberstoTransfer[0]=0;
		if(head == null) {
			System.out.println("there is no column");
			return null;
		}
		else {
			int a = 0;
			ColumnNode temp = head;
			int columnNum = 1;
			while(temp!=null) {	 
				if(temp.getRight()!=null && temp.getRight().getData().equals("*")) {
					deleteElements(columnNum);  // 	If there is a "*" element in the column we are in 
									// and we want to move an element from the box here, we first delete the "*" element.
				}
				if(temp.getRight()==null&&((int)data == 10||(int)data == 1)) {
					columnNumberstoTransfer[a] = (int)temp.getData(); // if column is empty and element from box is 1 or 10
					a++;
				}
				
				else{
					if(temp.getRight()!=null) {
						ElementNode tempE= temp.getRight();
						while(tempE.getNext()!=null) {
							tempE = tempE.getNext();
						}
						int difference = (int)tempE.getData()-(int)data;
						if(Math.abs(difference)==1||difference ==0){
							columnNumberstoTransfer[a] = (int)temp.getData();
							a++;					
						}
					}
				}
				temp = temp.getDown();
				columnNum++;
			}
			return columnNumberstoTransfer;
		}
	}
	
	
	
	public void deleteElements(int column) { // Deletes all elements in the column entered as a parameter
		if(head == null) {
			System.out.println("There is no column");
		}
		else {
			ColumnNode temp = head;
			while(temp!=null) {
				if((int)temp.getData()==column) {
					temp.setRight(null);
					break;
				}
				temp = temp.getDown();
			}
		}
	}
	
	
	
	
	public Object columnheadElement(int column) { // Returns the head element of the column
		if(head == null) {
			System.out.println("There is no column");
			return null;
		}
		else {
			Object firstelement = null;
			ColumnNode temp = head;
			while(temp!=null) {
				if((int)temp.getData()==column&&temp.getRight()!=null) {
					firstelement =  temp.getRight().getData();
				}
				temp = temp.getDown();
			}
			return firstelement;
		}
	}
	
	
	
	
	
	public void horizontalMove(int direction) { // 0-left , 1-right
		enigma.console.Console cn = Enigma.getConsole("Game", 100, 40, 20, 2);
		while(true) {	
			
			if(elementSize(Column.column)!=0&&columnheadElement(Column.column).equals("*")) { 
				deleteElements(Column.column);	// If there is a "*" element in the column we are on and 	
			}									//we are going to make a horizontal movement, the "*" element is deleted first	
			
			if(Column.column!=5 && direction == 1) {
				if(elementSize(Column.column+1)>= Column.height) {
					Column.column++;
					break;
				}
				else if(elementSize(Column.column+1)==0){
					Column.column++;  // // If the column to go to is empty, the "*" element is added to that column.
					addElement(Column.column,"*");
					Column.height = elementSize(Column.column);
					break;
				}
				else {
					Column.height = elementSize(Column.column+1);
					Column.column++;
					break;
				}
			}
			else if(Column.column == 5 && direction == 1) {
				Column.column = 1;
				if(elementSize(Column.column)!=0) {
					if(elementSize(Column.column)<Column.height) {
						Column.height = elementSize(Column.column);
					}	
					break;
				}
				else {					
					addElement(Column.column,"*");
					Column.height = elementSize(Column.column);
					break;
				}
			}
			else if(Column.column!=1 && direction == 0) {
				if(elementSize(Column.column-1)>= Column.height) {
					Column.column--;
					break;
				}
				else if(elementSize(Column.column-1)==0){
					Column.column--;
					addElement(Column.column,"*"); // If the column to go to is empty, the "*" element is added to that column.
					Column.height = elementSize(Column.column);
					break;
				}
				else {
					Column.height = elementSize(Column.column-1);
					Column.column--;
					break;
				}
			}
			else if(Column.column == 1 && direction == 0) {
				Column.column = 5;
				if(elementSize(Column.column)!=0) {
					if(elementSize(Column.column)<Column.height) {
						Column.height = elementSize(Column.column);					
					}				
					break;
				}
				else { 
					// If the column to go to is empty, the "*" element is added to that column.
					addElement(Column.column,"*");
					Column.height = elementSize(Column.column);
					break;
				}
			}
		}
				
	}
	
	
		
	public SingleLinkedList transferPartReturner(int column,int height) { 
		if(head == null) {  // When z is pressed, it adds a single linked list  the elements under it along with the element we are on.
			System.out.println("There is no column");
			return null;				
		}
		else {
			SingleLinkedList transferList=new SingleLinkedList();
			ColumnNode temp = head;
			while(temp!=null) {
				if(column == (int)temp.getData()) {
					if(temp.getRight()!=null) {
						if(temp.getRight().getData().equals("*")) {
							transferList=null;
							break;
						}
						ElementNode tempE = temp.getRight();
						int count = 1;
						while(tempE !=null) {
							if(count == height) {
								while(tempE!=null) {
									transferList.add(tempE.getData());
									tempE = tempE.getNext();
								}
								break;
							}
							count++;
							tempE = tempE.getNext();
						}
					}
				}
				temp = temp.getDown();
			}
			return transferList;
		}
	}
	

	
	public void transferRemover(MLL mll,int column,int height) {
		//we know where to delete exactly because  we only call this function if we transfer is confirmed.
		ColumnNode temp = mll.head;
		ElementNode tempE=null;
		boolean isChoosenRight=true;
		for(int i=0;i<column-1;i++) {//we should reach the column which cursor is on.
			temp=temp.getDown();
		}
		tempE=temp.getRight();
		for(int i=0;i<height-2;i++) {
			tempE=tempE.getNext();
		}
		tempE.setNext(null);
		if(elementSize(column)==1&&height==1)
			temp.setRight(null);
		
	}
	
	
	public void transferadder(MLL mll,int column,SingleLinkedList sll) { 
		Node temp= sll.head; ////Adds the elements in the single linked list to the column to go to by pressing Z
		while(temp!=null) {
			mll.addElement(column, temp.getData());
			if(temp.getLink()!=null)
				temp=temp.getLink();
			else break;
		}
		
	}
	
	
	
	public boolean isTransfer(int column,int height,SingleLinkedList sll) { // transfer controls
		if(head == null) {
			System.out.println("There is no column");
			return false;
		}
		else {
			boolean flag = false;
			ColumnNode temp = head;
			while(temp!=null && (int)temp.getData()!= column) {
				temp = temp.getDown();
			}
			if(temp.getRight()!=null && temp.getRight().getData().equals("*")) {
				deleteElements(column); // if there is a star in the column to go to
				flag = false;
				
			}
			if(temp.getRight()==null) {
				if((int)sll.headElement()==10 || (int)sll.headElement()==1) {
					flag = true;
				}
			}
			else{
				ElementNode tempE = temp.getRight();
				while(tempE.getNext()!=null) {
					tempE = tempE.getNext();
				}
				int difference = (int)tempE.getData() - (int)sll.headElement();
				if(Math.abs(difference)== 1 || difference == 0) {
					flag = true;
				}
			}
			return flag;		
		}
	}
	
	
	
	public int isSet10() { // it checks if there is a set of 10 by traversing all the columns
		if(head == null) {
			System.out.println("there is no column");
			return 0;
		}
		else { 
			boolean flag = false;
			ColumnNode temp = head;
			int column_num = 1;
			int setColumnNum=0;
			while(temp!=null) {
				
				if(elementSize(column_num)==10) {
					ElementNode tempE = temp.getRight();
					ElementNode previous = null;
					
					int count = 1;
					boolean flagfordecreasing = true;
					boolean flagforincreasing = true;
					while(tempE.getNext()!=null) {
						previous = tempE;
						tempE  = tempE.getNext();
						int difference = (int)previous.getData()-(int)tempE.getData();
						if(difference==1 && flagforincreasing==true) {
							flagfordecreasing = false;
							count++;  
						}
						else if(difference == -1 && flagfordecreasing == true) {
							count++;
							flagforincreasing = false;
						}
						if(count == 10) {
							flag  =true;
							break;
						}					
						
					}
					
				}
				if(flag==true) {
					setColumnNum = column_num;
					break;
				}
				temp = temp.getDown();
				column_num++;
			}
			return setColumnNum;
		}
	}
	
	
	
	
	
	public boolean endGame() { // the game is over if the elements in the columns run out
		boolean flag = true;
		for(int i=1;i<=5;i++) {
			if(elementSize(i)!=0) {
				flag=false;
			}
		}
		return flag;
	}
	
	
		
	
	
}
