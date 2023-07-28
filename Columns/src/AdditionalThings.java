

import java.awt.Color;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class AdditionalThings {
		public static enigma.console.Console cn = Enigma.getConsole("COLUMNS",50,15,40,7);
		public static void LoginScreen() throws InterruptedException{
		   cn.getTextWindow().setCursorPosition(0, 0);
		  int i=0;
		   while(i<5) {
			  TextAttributes color = new TextAttributes(Color.WHITE,Color.BLACK);			  
			  if(i==0) color = new TextAttributes(Color.RED);
			  else if(i==1) color=new TextAttributes(Color.MAGENTA);
			  else if(i==2) color=new TextAttributes(Color.PINK);
			  else if(i==3) color=new TextAttributes(Color.WHITE);
			  else if(i==4) color=new TextAttributes(Color.YELLOW);			  
		   cn.setTextAttributes(color);
    	   System.out.print("   _____ ____  _     _    _ __  __ _   _  _____ \r\n"
    	   		+ "  / ____/ __ \\| |   | |  | |  \\/  | \\ | |/ ____|\r\n"
    	   		+ " | |   | |  | | |   | |  | | \\  / |  \\| | (___  \r\n"
    	   		+ " | |   | |  | | |   | |  | | |\\/| | . ` |\\___ \\ \r\n"
    	   		+ " | |___| |__| | |___| |__| | |  | | |\\  |____) |\r\n"
    	   		+ "  \\_____\\____/|______\\____/|_|  |_|_| \\_|_____/ \r\n"
    	   		+ "                                                \r\n"
    	   		+ "                                                ");
    	   System.out.println(i);
    	   i++;
    	   //System.out.println(color);
    	   Thread.sleep(1000);
    	   Column.clearScreen();    	  
    	   color = new TextAttributes(Color.WHITE);
    	   cn.setTextAttributes(color); 	  
    	   } 
		   Column.clearScreen();
      }
		public static void HowToPlayGIF() throws InterruptedException {
			
			cn.getTextWindow().setCursorPosition(0, 0);
			TextAttributes color = new TextAttributes(Color.WHITE,Color.BLACK);
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			System.out.print("Box");
			color = new TextAttributes(Color.WHITE);
			
			cn.getTextWindow().setCursorPosition(0, 1);
			System.out.println("+--+");
			cn.getTextWindow().setCursorPosition(0, 2);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(3, 2);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(0, 3);
			System.out.println("+--+");
			cn.setTextAttributes(color);
			System.out.println("Game box containing these playing cards");
			Thread.sleep(1500);
			Column.clearScreen();
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			System.out.println("All Cards");
			System.out.println("1 2 3 4 5 6 7 8 9 10 ");
			System.out.println("1 2 3 4 5 6 7 8 9 10 ");
			System.out.println("1 2 3 4 5 6 7 8 9 10 ");
			System.out.println("1 2 3 4 5 6 7 8 9 10 ");
			System.out.println("1 2 3 4 5 6 7 8 9 10 ");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			System.out.println("This game box contains 5 each of the numbers 1to10\nAlso, these cards are mixed in the box.\nMixed cards in the box ");						
		
			color = new TextAttributes(Color.GREEN);
			cn.setTextAttributes(color);
			System.out.println("1 3 2 6 8 4 9 10 7 5 ");
			System.out.println("3 10 8 1 4 2 6 9 5 7 ");
			System.out.println("8 4 2 5 1 7 9 10 6 3 ");
			System.out.println("10 1 5 2 4 8 3 7 6 9 ");
			System.out.println("3 4 10 1 2 8 6 7 9 2");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			Thread.sleep(2500);
			Column.clearScreen();
			cn.getTextWindow().setCursorPosition(0, 0);
			//System.out.println("These cards are then randomly distributed to the columns in the game, 6 in each column.");
			//System.out.println();
			System.out.println("C1 C2 C3 C4 C5 ");
			System.out.println("-- -- -- -- -- ");
			System.out.print("1  3  2  6  8\n4  9  10 7  5 \n3  10 ");
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			System.out.print("8 ");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			System.out.print(" 1  4 \n2  6  9  5  7 \n8  4  2  5  1 ");		
			System.out.println();System.out.println();System.out.println();
			System.out.println("Competitor can direct cursor by pressing W-A-S-D keys");			
			Thread.sleep(2500);
			Column.clearScreen();
			System.out.println("C1 C2 C3 C4 C5 ");
			System.out.println("-- -- -- -- -- ");
			System.out.print("1  3  2  6  8\n4  9  10 7  5 \n3  10 8  ");						
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			System.out.print("1 ");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			System.out.print(" 4 \n2  6  9  5  7 \n8  4  2  5  1 ");				
			cn.getTextWindow().setCursorPosition(0, 0);

			Thread.sleep(3500);
			Column.clearScreen();
			cn.getTextWindow().setCursorPosition(0, 0);
			System.out.println("C1 C2 C3 C4 C5 ");
			System.out.println("-- -- -- -- -- ");
			System.out.print("1  3  2  6  8\n4  9  10 ");
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			System.out.print("7 ");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			System.out.print(" 5 \n3  10 8  1  4 \n2  6  9  5  7 \n8  4  2  5  1 ");		
			System.out.println();System.out.println();System.out.println();
			System.out.println("Competitor can direct cursor by pressing W-A-S-D keys");				
			Thread.sleep(3000);
			Column.clearScreen();
			
			System.out.println("C1 C2 C3 C4 C5 ");
			System.out.println("-- -- -- -- -- ");
			System.out.println("1  3  2  6  8\n4  9  10 7  5 \n3  10 8  1  4 \n2  6  9  5  7 \n8  4  2  5  1 ");
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			cn.getTextWindow().setCursorPosition(28, 3);
			System.out.print("Box");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			cn.getTextWindow().setCursorPosition(28, 4);
			System.out.println("+--+");
			cn.getTextWindow().setCursorPosition(28, 5);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(31, 5);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(28, 6);
			System.out.println("+--+");
			
			
			System.out.println("After the competitor presses the 'B' key, a card begins to appear from the box.");
			Thread.sleep(3500);
			Column.clearScreen();			
			System.out.println("C1 C2 C3 C4 C5 ");
			System.out.println("-- -- -- -- -- ");
			System.out.println("1  3  2  6  8\n4  9  10 7  5 \n3  10 8  1  4 \n2  6  9  5  7 \n8  4  2  5  1 ");
			color = new TextAttributes(Color.RED);
			cn.setTextAttributes(color);
			cn.getTextWindow().setCursorPosition(28, 3);
			System.out.print("Box");
			color = new TextAttributes(Color.WHITE);
			cn.setTextAttributes(color);
			cn.getTextWindow().setCursorPosition(28, 4);
			System.out.println("+--+");
			cn.getTextWindow().setCursorPosition(28, 5);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(31, 5);
			System.out.println("|");
			cn.getTextWindow().setCursorPosition(28, 6);
			System.out.println("+--+");
			cn.getTextWindow().setCursorPosition(29, 5);
			System.out.println("7");
			
			
			
			
			
			
		}
		}


