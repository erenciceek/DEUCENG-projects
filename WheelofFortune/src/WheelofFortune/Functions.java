package WheelofFortune;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Functions {
	public static void loadFile(String file_name) { // the file containing the countries is read
		File txtfile = new File(file_name);         // and assigned to the stack
		Scanner text = null; 
		try {
			text = new Scanner(txtfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		while (text.hasNextLine()) { 

			String line = text.nextLine();
			Main.countries.push(line);
			
		}
		text.close();
	}
	
	public static void loadScoreTable(String filename) {
		File txtfile = new File(filename);
		Scanner text = null;
		try {
			text = new Scanner(txtfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		while(text.hasNextLine()) {
			String line = text.nextLine();
			String[] arr = line.split(" ");
			Main.s3.push(arr[0]);  // name
			Main.s4.push(Integer.parseInt(arr[1])); // score
		}
	}
	
	
	public static String forWheel() { // randomly selected prize according to the wheel
		Random randint = new Random();
		int num=randint.nextInt(8)+1;
		String s = "";
		if(num==1)
			s = "10";
		else if(num==2)
			s = "50";
		else if(num==3)
			s = "100";
		else if(num==4)
			s = "250";
		else if(num==5)
			s = "500";
		else if(num==6)
			s = "1000";
		else if(num==7)
			s = "Double Money";
		else
			s = "Bankrupt";
		return s;
			
	}
	
	
	public static int calculateScore(String s,int score,int correctcount) {                                                  
		if(s.equals("10")) {                 // The score is calculated according to the result
			score = 10*correctcount + score; // from the wheel and the number of correct letters.
		}			
		else if(s.equals("50")) {
			score = 50*correctcount + score;
		}
			
		else if(s.equals("100")) {
			score = 100*correctcount + score;
		}
		else if(s.equals("250")) {
			score = 250*correctcount + score;
		}
		else if(s.equals("500")) {
			score = 500*correctcount + score;
		}
		else if(s.equals("1000")) {
			score = 1000*correctcount + score;
		}
		else if(s.equals("Double Money")&&correctcount>=1)
			score = score*2;
		else if(s.equals("Bankrupt"))
			score = 0;
		
		return score;
			
	}
	
	public static String QuestionWord(Stack s) {  //a random country is selected from the stack 
		String country="";                        //where the countries are located
		Random randint = new Random();
		int num = randint.nextInt(s.size())+1;
		System.out.println("Randomly generated number :  " + num);
		Stack tempstack = new Stack(s.size());
		int numberofelements= s.size();          // if the randomly generated number in the stack with 30 elements is 18, 
		while(true) {                                // the pop() command is used 12 times and 
			if(numberofelements==num) {              // the value in the top becomes the 18th element.
				country = (String) s.peek();         // This element is kept at another value.
				break;
			}
			for(int i=0;i<numberofelements-num;i++) {
				tempstack.push(s.pop());
				country = (String)s.peek();
				
			}
			while(!tempstack.isEmpty()) {
				s.push(tempstack.pop());
			}
			break;
		}
		return country;
	}
	
	public static char randomletter(Stack letterstack) {  //a random letter is generated with a logic similar to the function above
		Random randint = new Random();
		int num= randint.nextInt(letterstack.size())+1;
		Stack tempstack = new Stack(letterstack.size());
		char ch='þ';
		int limit=letterstack.size();
		while(true) {
			if(limit==num) {
				ch = (char)letterstack.peek();
				letterstack.pop();
				break;
			}
			for(int i=0;i<limit-num;i++) {
				tempstack.push(letterstack.pop());
				ch = (char)letterstack.peek();			
			}
			letterstack.pop();
			while(!tempstack.isEmpty()) {
				letterstack.push(tempstack.pop());
			}
			break;
		}
		return ch;
	}
	
	public static Stack forAlphabet() {
		for(int i=0;i<Main.alphabet.length();i++) {
			Main.stackLetters.push(Main.alphabet.charAt(i));
		}
		return Main.stackLetters;
	}
	
	

	
	public static int checkLetter(char randomletter,CircularQueue space,CircularQueue word,int counter,boolean game) {
		
		for(int i=0;i<word.size();i++) {  //Spell check is done by browsing at the same time in queues named space and word.
			if(randomletter==(char)word.peek()) {
				space.dequeue();
				space.enqueue(randomletter);
				word.enqueue(word.dequeue());				
				counter++;
				Main.totalcounter++;
			}
			
			else {
				word.enqueue(word.dequeue());
				space.enqueue(space.dequeue());
			}
		}
		return counter;
		
	}
	
	

	public static void displayCountries(Stack sorted_countries) { //to print sorted countries (when necessary)
		Stack tempstack = new Stack(500);
		while(!(sorted_countries.isEmpty())) {
            tempstack.push(((String)sorted_countries.peek()).toUpperCase(Locale.ENGLISH));
            sorted_countries.pop();
        }
        while(!(tempstack.isEmpty())) {
            System.out.println(tempstack.pop());

        }
	}
	
	public static void displayq(CircularQueue cq) {
		for(int i=0;i<cq.size();i++) {
			System.out.print(cq.peek());
			cq.enqueue(cq.dequeue());
		}
	}
	
	
	public static void displayStack(Stack s) {
		Stack tempstack = new Stack(1000);
		while(!s.isEmpty()) {
			tempstack.push(s.pop());
		}
		while(!tempstack.isEmpty()) {
			System.out.print(tempstack.peek());
			s.push(tempstack.pop());
		}
	}
	
	
	
	
	
	public static Stack sortingCStack(Stack countries,Stack sortedstack) { //countries are sorted
		while(!countries.isEmpty()) {
			String temp = (String)countries.pop();
			
			while(!sortedstack.isEmpty()&&sortedstack.peek().toString().compareToIgnoreCase(temp)>0) {
				countries.push(sortedstack.pop());
			}
			
			sortedstack.push(temp);
		}
	
        return sortedstack;
	}
	
	
	
	
	
	public static void ScoreTable(String currentname,int score,String filename) throws IOException { //scores are sorted and printed in tabular form
		PrintWriter outputStream = null;
		outputStream = new PrintWriter(new FileWriter(filename));
		Stack sortedscores = new Stack(100);
		Stack sortednames = new Stack(100);

		Main.s3.push(currentname);
		Main.s4.push(score);
		
		while(!Main.s4.isEmpty()) {
			int temp_score = (int)Main.s4.pop();
			String temp_name = (String)Main.s3.pop();
			while(!sortedscores.isEmpty()&&(int)sortedscores.peek()>temp_score) {
				Main.s4.push(sortedscores.pop());
				Main.s3.push(sortednames.pop());
			}
			
			sortedscores.push(temp_score);
			sortednames.push(temp_name);
		}
		String space = " ";
		System.out.println(" HIGH SCORE TABLES");
		outputStream.println(" HIGH SCORE TABLES");
		while(!sortednames.isEmpty()) {
			
			int spacecounter = 15 - sortednames.peek().toString().length();
			String scoretable_line=sortednames.peek()+space.repeat(spacecounter)+sortedscores.peek();
			System.out.println(scoretable_line);
			outputStream.println(scoretable_line);
			sortednames.pop();
			sortedscores.pop();
		}
		if (outputStream != null) {
			outputStream.close();
		}
		
	}
	
	public static boolean isGameOver(boolean game,CircularQueue q) {
		int count=0;
		for(int i=0;i<q.size();i++) {
			if(!q.peek().equals("-")) {
				count++;
				q.enqueue(q.dequeue());
			}
			if(count==q.size()) {
				game=false;
				break;
			}
			else
				q.enqueue(q.dequeue());
		}
		
		return game;
	}
	
	
	
	
	
	
	
	
	
}
