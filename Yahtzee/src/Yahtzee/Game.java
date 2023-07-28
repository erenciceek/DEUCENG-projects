package Yahtzee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	public Game () throws IOException {
		SingleLinkedList sll_1 = new SingleLinkedList(); //Linked list where player 1's numbers are added
		SingleLinkedList sll_2 = new SingleLinkedList(); //Linked list where player 2's numbers are added
		SingleLinkedList score_table = new SingleLinkedList(); //Linked list for score table
		loadScoreTable("highscoretable.txt",score_table); //
		
		Player player1 = new Player("Player1",0);
		Player player2 = new Player("Player2",0);
		
		// large straight example: 1 2 2 4 3 5 6     >>> finding different six numbers
		// yahtzee example:  2 5 3 6 2 1 2 2         >>> finding four identical numbers 
		
		int turn = 1;
		System.out.println("Turn: " + String.valueOf(turn));
		while(true) {
			//displayRound(sll_1,sll_2,player1,player2);
			addNumbers(sll_1);
			addNumbers(sll_2);
			displayRound(sll_1,sll_2,player1,player2);
			System.out.println();
			
			int yahtzee= 0; // To reprint the round in cases of yahtzee or large straight
			int large_straight= 0;
			
			if(sll_1.Yahtzee()==true) {  // if Yahtzee happens in player1
				yahtzee++;
				int yahtzeecount1 = sll_1.YahtzeeDeleter();
				player1.setScore(player1.getScore()+10*yahtzeecount1);	// uploads the score		
			}
			if(sll_2.Yahtzee()==true) {  //If Yahtzee happens in player2
				yahtzee++;
				int yahtzeecount2 = sll_2.YahtzeeDeleter();
				player2.setScore(player2.getScore()+10*yahtzeecount2);	// uploads the score
						
			}							
			if(sll_1.LargeStraight()) { //if large straight is observed in player 1
				large_straight++;
				player1.setScore(player1.getScore()+30);	 // uploads the score			
				delete6numbers(sll_1);	
			}
			
			if(sll_2.LargeStraight()) { //if large straight is observed in player 2
				large_straight++;
				player2.setScore(player2.getScore()+30);	 // uploads the score			
				delete6numbers(sll_2);		 
			}
			if(yahtzee>0||large_straight>0)
				displayRound(sll_1,sll_2,player1,player2);
			turn++;
			if(turn==11)
				break;
			System.out.println();
			System.out.println("Turn: " + String.valueOf(turn));
		
			
		}
		System.out.println();
		System.out.println("Game over...");
		if(player1.getScore()>player2.getScore())
			System.out.println("Winner is Player1!");
		else if(player2.getScore()>player1.getScore())
			System.out.println("Winner is Player2!");
		else {
			System.out.println("Tie!");
		}
		System.out.println();
		System.out.println("High Score Table");
		score_table.addwithsort(player2);   
		score_table.addwithsort(player1);
		
		score_table.displayScoreTable("highscoretable.txt");
		
		player1.setScore(0);
		player2.setScore(0);

	}
	
	
	
	public static void loadScoreTable(String filename,SingleLinkedList scoretable) {
		File txtfile = new File(filename);
		Scanner text = null;
		try {
			text = new Scanner(txtfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while(text.hasNextLine()) {
			String line = text.nextLine();
			int spaceindex = line.indexOf(" ");
			String current_name = line.substring(0, spaceindex);
			String current_score = line.substring(spaceindex+1);
			Player current_player = new Player(current_name,Integer.parseInt(current_score));
			scoretable.addwithsort(current_player);
			
		}
	}
	
	
	
	
	public static void displayRound(SingleLinkedList sll_1,SingleLinkedList sll_2,Player p1,Player p2) {
		String space = " ";                    //Prints linked lists and score of players for each round
		String line1 = sll_1.displayLine() ;
		String line2 = sll_2.displayLine();
		int spacecounter1 = 80 - line1.length();
		int spacecounter2 = 80 - line2.length();
		System.out.println("Player1: " + line1+ space.repeat(spacecounter1) + "Score:"+ p1.getScore());
		System.out.println("Player2: " + line2+ space.repeat(spacecounter2) + "Score:"+ p2.getScore());
	}
	
	
	public static void addNumbers(SingleLinkedList sll) { //Generates 3 numbers between 1-6 and adds a linked list
		Random randint = new Random();
		int count = 0;
		while(count<3) {
			int num=randint.nextInt(6)+1;
			sll.add(num);
			count++;
		}
	}
	
	public static void delete6numbers(SingleLinkedList sll) { //In case of large straight all 6 elements are deleted from the list
		sll.deleteforStraight(1);   	
		sll.deleteforStraight(2);
		sll.deleteforStraight(3);
		sll.deleteforStraight(4);
		sll.deleteforStraight(5);
		sll.deleteforStraight(6);
	}
	
	
	

	

}
