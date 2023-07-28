package WheelofFortune;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;



import java.util.*;

public class Main {
	
	public static Stack countries = new Stack(500);
	public static Stack SortedCountries = new Stack(500);
	public static Stack stackLetters = new Stack(26);
	public static Stack s3 = new Stack(100);  //for contestant names
	public static Stack s4 = new Stack(100);  //for contestant scores
	

	public static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static int totalcounter = 0; //to keep a total of how many letters match
	
	public static void main(String[] args) throws IOException {
		
		
		Functions.loadFile("countries.txt"); // countries are read and added to the stack named "countries"
		Functions.loadScoreTable("highscoretable.txt"); // reading the score table and assigning it to two different stacks
		
		
		Main.SortedCountries = Functions.sortingCStack(countries,SortedCountries); //countries are sorted
		Functions.forAlphabet(); // alphabet is assigned to stack = stackLetters
		
		
		String countryname = Functions.QuestionWord(SortedCountries); // A random country is chosen for the competition.
		
		//Functions.displayCountries(SortedCountries); 	
		countryname = countryname.toUpperCase(Locale.ENGLISH); 
		
		CircularQueue word = new CircularQueue(countryname.length()); // for word
		CircularQueue space = new CircularQueue(countryname.length()); // for ------
		
		
		for(int i=0;i<countryname.length();i++) {  // randomly selected country is added to circular queue
			word.enqueue(countryname.charAt(i));
		}
		Functions.displayq(word); // 
		System.out.println();
		
	
		for(int i=0;i<countryname.length();i++) { //filling out the queue that will be shown to the contestant
			space.enqueue("-");
		}
		
		
		int step = 1;  //current step
		int score =0;  //current score
		
			
		
		boolean game = true;
				
		while(game) {
			System.out.print("Word :  ");
			Functions.displayq(space);
			System.out.print("      Step : "+ step+ "   Score : " + score+ "      ");
		    Functions.displayStack(stackLetters);
		    System.out.println();
		    String prize = Functions.forWheel(); //
		    System.out.println("Wheel :  "+ prize);
		    char rand_letter = Functions.randomletter(stackLetters);// generating random letter from stackLetters
			System.out.println("Guess :  "+ rand_letter);
			step++;
			int correct_lettercounter = Functions.checkLetter(rand_letter, space, word, 0,game); //the randomly generated letter is checked on the country		
			score = Functions.calculateScore(prize,score,correct_lettercounter); 
			
			System.out.println();
			
			if(totalcounter==space.size()) { 
				game=false;
				System.out.print("Word :  ");
				Functions.displayq(space);
				System.out.print("      Step : "+ step+ "   Score : " + score+ "      ");
			    Functions.displayStack(stackLetters);
				System.out.println();
				System.out.println("You win "+ score + " !!!");
				System.out.println();
				
			}
	
					
		}
		
		Functions.ScoreTable("You",score,"highscoretable.txt");
		
		
		
	}

	

}
