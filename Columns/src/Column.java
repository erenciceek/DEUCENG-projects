

import java.awt.Color;
import enigma.console.TextAttributes;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import enigma.core.Enigma;
import java.util.*;


public class Column {
	 
		public static enigma.console.Console cn = Enigma.getConsole("Game", 100, 40, 20, 2);
		//public static KeyListener klis; 
		public static int keypr;   // key pressed?
		public static int rkey;    // key   (for press/release)		
		KeyListener klis = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (keypr == 0) {
					keypr = 1;
					rkey = e.getKeyCode();
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		}; // do not touch
		
		static int score = 0;
		static int transfer =0;
		public static int column = 1;
		public static int height = 1;
		public static int orderedCount=0;
		
		public void gamePlay() throws IOException, InterruptedException {//This function is our main game function.
			boolean isPressedZ=false;//This boolean will be true as long as we keep a choosen list.
			SingleLinkedList transferCarrier= new SingleLinkedList();// Carries the list which will be transfered.
			cn.getTextWindow().addKeyListener(klis); 
			TextAttributes color = new TextAttributes(Color.WHITE,Color.BLACK);
			//int column = 1;//column number
			//int height = 1;//height number
			int transferedColumn=1; // for 'Z' key
			int transferedHeight=1;
			enigma.console.Console cn = Enigma.getConsole("Game", 100, 40, 20, 2);
			Box box = new Box(false);
			DoubleLinkedList HighScoreTable = new DoubleLinkedList();
			SingleLinkedList mixedBox = new SingleLinkedList();
			box.mixingBox(mixedBox);
			MLL mll = new MLL();
			mll.addColumn(1);mll.addColumn(2);mll.addColumn(3);mll.addColumn(4);mll.addColumn(5);
			firstGameScreen(mixedBox,mll);//Prints the first times game Screen
			boolean isSelected = false; // This boolean will be used for box.
			
			boolean exitFlag = false;
			while(!mll.endGame() && !exitFlag) {
				
				if(box.isVisible()&&!isSelected) {//To show box element's condition correctly
                    cn.getTextWindow().setCursorPosition(27, 6);
                    System.out.print(mixedBox.headElement());
                }
                else if(box.isVisible()&&isSelected) {//To show box element's condition correctly
                    cn.getTextWindow().setCursorPosition(27, 6);
                    color = new TextAttributes(Color.RED);
                    cn.setTextAttributes(color);
                    System.out.print(mixedBox.headElement());
                    color = new TextAttributes(Color.WHITE);
                    cn.setTextAttributes(color);
                }
				
				
				printGameLines(); // 
				mll.display(column,height);
				color = new TextAttributes(Color.WHITE);
                cn.setTextAttributes(color);
			    if(keypr == 1) {
			    	switch(rkey) {//Cursor Movements and other key probability
					case KeyEvent.VK_LEFT:		//Moving Left				
						mll.horizontalMove(0);	
						clearScreen();
						break;
					case KeyEvent.VK_RIGHT:		//Moving Right			  	
						mll.horizontalMove(1);	
						clearScreen();
						break;
					case KeyEvent.VK_UP:    //Moving Up	
						if(height !=1 ) {
							height--;
						}
						else {
							height = mll.elementSize(column);//If there are no elements on the cursor cursor goes end of the column
						}
						break;
					case KeyEvent.VK_DOWN:  //Moving down	
						if(height != mll.elementSize(column)) {
							height++;
						}
						else {
							height =1;//If there are no elements below the cursor, it goes to first element
						}
						break;
					case KeyEvent.VK_B:
						if(mixedBox.headElement()!=null) {
							if(!box.isVisible()) {//After pressing 'B' if box was not visiable condition
								cn.getTextWindow().setCursorPosition(27, 6);
								System.out.print(mixedBox.headElement());
								box.setVisible(true);
							}
							else if(box.isVisible()&& isSelected == false){//If box is visible and user pressed B one more time Chooses the box element
								isPressedZ=false;
								isSelected = true;
								cn.getTextWindow().setCursorPosition(27, 6);
								color = new TextAttributes(Color.RED);
				                cn.setTextAttributes(color);
				                System.out.print(mixedBox.headElement());			                   
												
							}
							else if(isSelected == true){  // To set box element as not choosed
								isSelected = false;
								cn.getTextWindow().setCursorPosition(27, 6);
				                System.out.print(mixedBox.headElement());
							}
						}
						else {
							cn.getTextWindow().setCursorPosition(36, 10);
							color = new TextAttributes(Color.RED);
			                cn.setTextAttributes(color);
			                System.out.println("There is no element in Box!");
			                
						}
						break;
					case KeyEvent.VK_X:
						if(box.isVisible()&&isSelected==true) {  // transfer for the box element
							int[] columnNumbersToTransfer = mll.transferFromBox(mixedBox.headElement());
							for(int i = 0;i<columnNumbersToTransfer.length;i++){
								if(columnNumbersToTransfer[i]==column) {
									mll.addElement(column, mixedBox.headElement());
									mixedBox.deleteFirstElement();
									cn.getTextWindow().setCursorPosition(27, 6);
									System.out.print("  ");
									isSelected = false;
									box.setVisible(false);
									transfer++;
									break;
								}
							}
							int set10column = mll.isSet10();//This function controlls if there are any 10 set
							if(set10column!=0) {
								score+=1000;
								orderedCount++;
								//Thread.sleep(3000);
								mll.display(column, height);
								Thread.sleep(1500);  //A delay for 1,5 seconds to show the user transfer operation
								mll.deleteElements(set10column);
							}
							
						}
						if(isPressedZ&&!isSelected) {	// So if  we have a choosen part from the 'From column' and if box element was not choosen			
							if(transferCarrier!=null) {					
								if(mll.isTransfer(column, height, transferCarrier)) {
									mll.transferRemover(mll, transferedColumn, transferedHeight);//deletes the transfered elements from the first box
									mll.transferadder(mll, column,transferCarrier);	//adds the choosen part to the 'To column'
									int set10column = mll.isSet10();
									if(set10column!=0) {
										score+=1000;
										orderedCount++;
										//Thread.sleep(3000);
										mll.display(column, height);
										Thread.sleep(1500);
										mll.deleteElements(set10column);
									}									
									transfer++;
									transferCarrier=null;
									isPressedZ=false;
									clearScreen();//Clears the screen 
									cn.getTextWindow().setCursorPosition(33,5);
									System.out.println("Z key: OFF");
									//transfer completed
								}
								else {//Wrong column choosen Transfer Failed!
									cn.getTextWindow().setCursorPosition(25, 25);
									System.out.println("Please Choose a right place to apply the transfer");
								}
							}
							else {//User did not choose a part which can be transfered.
								cn.getTextWindow().setCursorPosition(25, 25);
								System.out.println("Please choose the list first!");
							}
						}
							
						
						break;
					case KeyEvent.VK_Z:
						transferCarrier= mll.transferPartReturner(column, height);//This function Returns the choosen part in a Single Linked List
						transferedColumn=column;//this integer keeps the first column coordinate(from columns)
						transferedHeight=height;//this integer keeps the first height coordinate
						if(transferCarrier!=null) {//If there is a choosen part
							cn.getTextWindow().setCursorPosition(33, 5);							
							System.out.println("Z key: ON ");
							isPressedZ=true;
						}
						else {
							isPressedZ = false;
						}
						break;
					case KeyEvent.VK_E://To exit the game
						exitFlag = true;
						break;
			    }
			    }
			    keypr= 0;
			    rkey=0;
			    
			    					
			}
			if(exitFlag== false) {
				cn.getTextWindow().setCursorPosition(0, 30);			   		    	
				finalHighScore(score);	//printing final high score
			}
			else {
				cn.getTextWindow().setCursorPosition(0, 30);
				highScore(HighScoreTable);
				HighScoreTable.display();
			}
		}
		
		

		public void menu() throws Exception {//A standard menu part for the game, which includes Game Info, game and high score table
			Scanner scann = new Scanner(System.in);	
			String selection = "1"; // menu selection
			DoubleLinkedList HighScoreTable = new DoubleLinkedList();
	       while (!selection.equals("2")){
	    	   
	       

	    	   System.out.println("1.Game Info\n" +
	                              "2.Start Game\n" +
	                              "3.High Score Table\n" +
	                              "4.Exit\n");

	         
	    	   System.out.print("Enter your choice : ");
	           while(true) {	        	   
	        	   selection=scann.next();
	        	   if(selection.equals("1") || selection.equals("2") || selection.equals("3") || selection.equals("4")) {
	        		   break;
	        	   }
	        	   System.out.print("Please enter a number between 1-4  : ");     	  
	           }          
	           switch(selection){ 

	           case "1" :clearScreen();	           
	           AdditionalThings.HowToPlayGIF();
	           clearScreen();
               break;
	           	    	
	           case "2" : clearScreen();gamePlay(); break;//Main game
	           case "3" :clearScreen();highScore(HighScoreTable); //Score table
	           			HighScoreTable.display();
	           break;
	           case "4" :clearScreen(); //End of the game
	           	System.out.println("Thanks for playing! Bye!"); System.exit(1);
	           default: break;

	           }
	       }
	           
		}
	    public static void clearScreen() {
	            // output spaces to clear the screen
	            // start with one space less than the screen size, so as not to induce scrolling
	            char[] buffer = new char[Math.max(0, cn.getTextWindow().getColumns() * cn.getTextWindow().getRows() - 1)];
	            Arrays.fill(buffer, ' ');
	            cn.getTextWindow().setCursorPosition(0, 0);
	            cn.getTextWindow().output(buffer, 0, buffer.length);
	            // this positional output does not cause scrolling
	            cn.getTextWindow().output(cn.getTextWindow().getColumns() - 1, cn.getTextWindow().getRows() - 1, ' ');
	            // move cursor back to beginning
	            cn.getTextWindow().setCursorPosition(0, 0);
	        }
	    
	    public void firstGameScreen(SingleLinkedList mixedBox,MLL mll) { //distributes elements from the box into columns
			Node temp = mixedBox.head;	
			int columnNum = 1;
			for(int i =0;i<5;i++){
				for(int j=0;j<6;j++) {
					mll.addElement(columnNum, temp.getData());//adds the element to the MLL 
					mixedBox.deleteFirstElement();//deletes the element from the mixedBox
					temp = temp.getLink();
				}
				columnNum++;
				
			}
	    }
	   
	    
	    public void printGameLines() { // prints the constant game lines
	    	cn.getTextWindow().setCursorPosition(1,0);
	    	System.out.println("C1  C2  C3  C4  C5");
	    	cn.getTextWindow().setCursorPosition(1,1);
			System.out.println("--  --  --  --  --");
			cn.getTextWindow().setCursorPosition(25, 0);
			System.out.println("Transfer  : " + transfer);
			cn.getTextWindow().setCursorPosition(25, 1);
			System.out.println("Score     : " + score);
	    	cn.getTextWindow().setCursorPosition(26, 4);
			System.out.print("Box");
			cn.getTextWindow().setCursorPosition(26, 5);
			System.out.println("+--+");
			cn.getTextWindow().setCursorPosition(26, 6);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(29, 6);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(26, 7);
			System.out.println("+--+");
	    }
	    
	    
	    public void highScore(DoubleLinkedList DLL) throws IOException {
            //enigma.console.Console cn = Enigma.getConsole("COLUMNS",50,10,40,7);
            BufferedReader objReader = new BufferedReader(new FileReader("highscore.txt"));
            String row;
            Object[] highScore = new Object[2];
            row = objReader.readLine();
            while (row != null) {
                if(!row.equals("")) {
                	//adds each row to the array 
                    highScore = row.split("     ");
                }
                //sets the cont's name and score from the highScore array
                Contestant cont = new Contestant((String) highScore[0], Double.parseDouble((String)highScore[1]));
                DLL.add(cont);
                row = objReader.readLine();
            }
            objReader.close();
        }
	    
	    //adds the new player to the high score table
	    public void finalHighScore(int score) throws IOException {
            DoubleLinkedList HighScoreTable = new DoubleLinkedList();
            Column highScore = new Column();
            highScore.highScore(HighScoreTable);
            Contestant player = new Contestant("Player", calculateScore());
            HighScoreTable.add(player);
            HighScoreTable.display();
        }
	    
	    public double calculateScore() {
            double endGameScore = (100*orderedCount + ((double)score / (double)transfer));
            endGameScore = (double) (Math.round(endGameScore*100.0)/100.0);
            return endGameScore;
        }
	   
	    
	  
}