package whowantstomillionaire;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import enigma.core.Enigma;
import enigma.event.TextMouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class wwtbmgame {
	public enigma.console.Console cn = Enigma.getConsole("WWTBM");
	public TextMouseListener tmlis; 
	public KeyListener klis; 
	public int keypr;   // key pressed?
	public int rkey;    // key   (for press/release)
	
	public wwtbmgame() throws InterruptedException {
		enigma.console.Console cn = Enigma.getConsole("Who wants to be a millionarie");
		Scanner input = new Scanner(System.in);

		File correctanswer_effect = new File("dogrucevap.WAV"); // Audio Files for sound effects
	    File wronganswer_effect = new File("yanliscevap.WAV");
		
		//Stop_file read and add to array
		String[] stop_words = new String[851];  
		File stopfile = new File("stop_words.txt");
		Scanner stopreader = null;
		try {
			stopreader = new Scanner(stopfile); 
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		int a = 0;
		while (stopreader.hasNextLine()) {    // each line is read and assigned to the array for stop_words
			stop_words[a] = stopreader.nextLine();
			a++;
		}
		
		//Dict_file read and add to array
		String[] dictionary = new String[370103];
		File dictfile = new File("dictionary.txt");
		Scanner dictreader = null;
		try {
			dictreader = new Scanner(dictfile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		a = 0;
		while (dictreader.hasNextLine()) {   //each line is read and assigned to the array for dictionary
			dictionary[a] = dictreader.nextLine();
			a++;
		}
		
		
		Question[] questions = new Question[176]; 
		Participant[] participants = new Participant[21];
		ContestantStatistic[] css = new ContestantStatistic[21];  // created for contestantstatistics to work
		CategoryStatistic[] cts = new CategoryStatistic[10];    // created for categorystatistics to work
		String[] categoryarray = new String[126]; // created for the category table
		String[] difficultyarray = new String[126]; // created for the difficulty of degree table
		boolean flag1= true, flag2 = true, flag3 = true,cont=true;
		
		String choice = "";
		while(!choice.equals("5")){
			                                   // Printing the menu 
			System.out.print("** Menu **\n"
					+ "1.Load questions\n"
					+ "2.Load participants\n"
					+ "3.Start competition \n"
					+ "4.Show statistics\n"
					+ "5.Exit\n"
					+ "\n"
					+ "> Enter your choice:");
			
			choice = input.next();
			// prevents error for string or invalid number
			if(!(choice.equals("1")||choice.equals("2")||choice.equals("3")||choice.equals("4")||choice.equals("5"))) {
				System.out.println("Please enter a number between 1-5.");
				continue;
			}
			
			
			switch (choice) {
			
			case "1": 
				System.out.print("Enter file name to load: ");
	            String filenameq = input.next();
				
				try {
					File fileq = new File(filenameq);
					Scanner filetextq = new Scanner(fileq);
					System.out.println("The file loaded.");
					String line_q;
				
					int count = 0, countcatogery = 0;
					while (filetextq.hasNextLine()) {   // An object is created from the question class and 
						line_q = filetextq.nextLine();  // each object is added to the elements of the questions array.
						String[] questionforclass = line_q.split("#");// the properties of the problem are assigned to this array	
						Question current_question = new Question(questionforclass[0],questionforclass[1],questionforclass[2],questionforclass[3],questionforclass[4],questionforclass[5],questionforclass[6],questionforclass[7],false);
						CategoryStatistic currentstatistic = new CategoryStatistic(questionforclass[0],0);  // taking only category info and setting the correct counter to zero 
						addQuestion(questions ,current_question, count);   
						forTables(questionforclass[0],categoryarray); //calling the function for the category table
						forTables(questionforclass[7],difficultyarray); // calling the function for the difficulty level table
						countcatogery = addCatogery(currentstatistic,countcatogery,cts);    // adding the category to cts array
						count++;
						
					}	
					filetextq.close();
					
				}
				catch (FileNotFoundException e) {
					System.out.println("There is no such a file.");
				} //printing tables
				System.out.println("Category           Number of Questions");				
				displayarray(categoryarray); // 
				System.out.println();
				System.out.println("Difficulty Level       Number of Questions");
				displayarray(difficultyarray);
				
				choice="";
				flag1 = false;
				break;
			
			case "2":
	            System.out.print("Enter file name to load: ");
	            String filenamep = input.next();
	            try {
	            	File filep = new File(filenamep);
		            Scanner filetextp = new Scanner(filep);
		
		            System.out.println("The file loaded.");
		            String line_p;
		            
		            int count = 0;
		            while (filetextp.hasNextLine()) {
		                line_p = filetextp.nextLine();
		                //System.out.print(line_p);
		                String[] participantsInfo = line_p.split("#");
		                Participant current_participant = new Participant(participantsInfo[0],participantsInfo[1],participantsInfo[2],participantsInfo[3],false);
		                addParticipant(current_participant, count, participants); //An object is created from the participant class, and each object is added to an element of the participants array
		                int ageofcontestant = participants[count].findAge(11, 03, 2022); //the function that calculates the age of the participants is called 
		                String[] contestantcity = participantsInfo[3].split(";");   // for statistics taking only the city info as string
		                ContestantStatistic currentcontestant = new ContestantStatistic(participantsInfo[0],0,0,ageofcontestant,contestantcity[3]); // taking participant name and its city from files
		                addContestant(currentcontestant,count,css); 
		                count++;
		            }
		            
		            
		            filetextp.close();
	            }
	            catch (FileNotFoundException e) {
					System.out.println("There is no such a file.");
				}
	            choice="";
	            flag2 = false;
	            break;
			case "3": 
				if (flag1 || flag2) {
					System.out.println("Please first enter the data files.");
					break;
				}
				while(cont) {
					// current player info (contestant)  ##############
					Random rand1 = new Random();
					
					int contestantint = rand1.nextInt(21); // for choosing random contestant
					
					
					
					String contestantname = participants[contestantint].getName();  //getting the name of choosen contestant
					participants[contestantint].setJoined(true);  // setting its value in the participants arrey to isjoined for knowing if he/she played the game
					System.out.println("\n" + "Contestant: " + contestantname + "\n");
					
					  
					
					
					
					// current player info (contestant)  ##############
					
					//word cloud
					String[][] questionWords = new String[176][];
					for(int b = 0; b < 176; b++) {
						questionWords[b] = splitQuestionWords(questions, b);
					}
					int count = 0;
					int level = 1; 
					boolean game = true;
	
					int count50per = 0, doubledipcount = 0; //for each lifeline to be used only once
	
					
					// game 
					while (game) {
						String[] word_cloud = wordcloud(questionWords, stop_words, dictionary, level, questions); //for word cloud preparation
						
						boolean flagfordoubledip = false; //Necessary variables
						Boolean correcttimesflag = false;     // at the and of the while(game) cycle these flags counts the correct answer times for category and also participants --
						Boolean falsetimesflag = false;   // for statistics
						
						String money=""; 
						for(int b = 0; b < word_cloud.length; b++) { //write word cloud
							if (count == 6) {
								System.out.print("\n"); //if the same line has 6 word than go to other line
								count = 0;
							}
							
							if (word_cloud[b] != null) {
								System.out.print(word_cloud[b] + "   "); //write word cloud array's words
								count += 1;
							}
						}
						System.out.println();
						
						//from word cloud, word selection
						System.out.print("Enter your selection : ");
						String userword = input.next();
						
						System.out.println();
						System.out.println();
						
						while (!(Arrays.asList(word_cloud).contains(userword.toLowerCase()))) {
	                        userword=spell_check(userword,dictionary); //If the user word is not in word cloud, 
	                        if((Arrays.asList(word_cloud).contains(userword.toLowerCase()))) //,the spell_check function works and checked again.
	                            break;
	                        System.out.print("Please enter a word within the word cloud:  ");
	                        userword = input.next();
	
	                    }
						int questionNumber = 0;
						
						//questions write
						for(int b = 0; b < questions.length; b++) { 
							if (word_cloud[b] == null) {
								continue;
							}
							if (word_cloud[b].equals(userword.toLowerCase())) {
                                System.out.println(getQuestionText(questionWords, b, dictionary)); // this function check every word in question text
                                System.out.println();												
                                System.out.println(displayAnswers(questions, b)); // this function writes answers
                                questionNumber = b;
                                questions[questionNumber].setIstried(true); //to not print the printed question again
                                break;	
							}
						}
						boolean flag = true;
						keypr = 0;
	                    rkey = 0;
	
						klis=new KeyListener() { //game class variables 
						public void keyTyped(KeyEvent e) {}
				        public void keyPressed(KeyEvent e) {
				        	if(keypr==0) {
				        		keypr=1;
				        		rkey=e.getKeyCode();
				        	}
				        }
				        public void keyReleased(KeyEvent e) {}
						};
						cn.getTextWindow().addKeyListener(klis);
						// ----------------------------------------------------
						
						
						Timer timer = new Timer(); // get class timer 
						long startTime = timer.getStartTime(); //set start time
						
						//input controls
						while(flag) {
							if(keypr==1) {    // if keyboard button pressed
								if(rkey==KeyEvent.VK_1 && count50per!=1) {  //50% lifeline is called
									Space();
									System.out.println(percent50(questions, questionNumber, questionWords, dictionary)); 
									count50per++;
								}
								else if(rkey==KeyEvent.VK_2 && doubledipcount != 1) {
									System.out.print("Double Dip");
									doubledipcount++;
									flagfordoubledip = true;
								}
								else if(rkey==KeyEvent.VK_A) { // if 'A' is pressed
									if(getCorrectAnswer(questions, questionNumber).equalsIgnoreCase("A")) {
										PlaySound(correctanswer_effect);
										System.out.println();
										System.out.println("A is Correct Answer.");
										System.out.println();
										game = true;
										level += 1;
										if (level == 6) { // if user made 5 level then end the contest
											System.out.println();
											System.out.println("Well done. You are successfully finished the game.");
											System.out.println();
											System.out.println(calculateMoney(level)); // write money s/he earned
											game=false;
											level = 0;
											break;
										}
										Thread.sleep(1000);
										keypr = 0;
										correcttimesflag = true;  // checks if the category answered correctly for statistics
										break;
									}
									else if (flagfordoubledip){
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("A is not the correct answer.");
										flagfordoubledip = false;
										keypr = 0;
										continue;
									}
									else {
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("A is Wrong Answer. Game over.");
										System.out.println();
										System.out.println(calculateMoney(level));
										game = false;
										falsetimesflag = true;  // checks if the category answered wrongly for statistics
										break;
									}
								}
								else if(rkey==KeyEvent.VK_B) { // if 'B' is pressed
									if(getCorrectAnswer(questions, questionNumber).equalsIgnoreCase("B")) {
										PlaySound(correctanswer_effect);
										System.out.println();
										System.out.println("B is Correct Answer.");
										System.out.println();
										game = true;
										
										level += 1;
										if (level == 6) {
											System.out.println();
											System.out.println("Well done. You are successfully finished the game.");
											System.out.println();
											System.out.println(calculateMoney(level));
											game=false;
											level = 0;
											correcttimesflag = true;
											break;
										}
										Thread.sleep(1000);
										keypr = 0;
										correcttimesflag = true;
										break;
									}
									else if (flagfordoubledip){
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("B is not the correct answer.");
										flagfordoubledip = false;
										keypr = 0;
										continue;
									}
									else {
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("B is Wrong Answer. Game over.");
										System.out.println();
										System.out.println(calculateMoney(level));
										game = false;
										falsetimesflag = true; 
										break;
									}
								}
								else if(rkey==KeyEvent.VK_C) { // if 'C' is pressed
									if(getCorrectAnswer(questions, questionNumber).equalsIgnoreCase("C")) {
										PlaySound(correctanswer_effect); // the audio is played for the correct answer
										System.out.println();
										System.out.println("C is Correct Answer.");
										System.out.println();
										game = true;
									
										level += 1;
										if (level == 6) {
											System.out.println();
											System.out.println("Well done. You are successfully finished the game.");
											System.out.println();
											System.out.println(calculateMoney(level));
											game=false;
											level = 0;
											correcttimesflag = true;
											break;
										}
										Thread.sleep(1000);
										keypr = 0;
										correcttimesflag = true;
										break;
									}
									else if (flagfordoubledip){
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("C is not the correct answer.");
										flagfordoubledip = false;
										correcttimesflag = true;
										keypr = 0;
										continue;
									}
									else {
										PlaySound(wronganswer_effect); //the audio is played for the wrong answer
										System.out.println();
										System.out.println("C is Wrong Answer. Game over.");
										System.out.println();
										System.out.println(calculateMoney(level));
										game = false;
										falsetimesflag = true;
										break;
									}
								}
								else if(rkey==KeyEvent.VK_D) { // if 'D' is pressed
									if(getCorrectAnswer(questions, questionNumber).equalsIgnoreCase("D")) {
										PlaySound(correctanswer_effect);
										System.out.println();
										System.out.println("D is Correct Answer.");
										System.out.println();
										game = true;
										correcttimesflag = true;
										level += 1;
										if (level == 6) {
											System.out.println();
											System.out.println("Well done. You are successfully finished the game.");
											System.out.println();
											System.out.println(calculateMoney(level));
											game=false;
											level = 0;
											correcttimesflag = true;
											break;
										}
										Thread.sleep(1000);
										keypr = 0;
										correcttimesflag = true;
										break;
									}
									else if (flagfordoubledip){
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("D is not the correct answer.");
										flagfordoubledip = false;
										keypr = 0;
										continue;
									}
									else {
										PlaySound(wronganswer_effect);
										System.out.println();
										System.out.println("D is Wrong Answer. Game over.");
										System.out.println();
										System.out.println(calculateMoney(level));
										game = false;
										falsetimesflag = true;
										break;
									}
								}
								else if(rkey==KeyEvent.VK_E) {
									System.out.println(calculateMoney(level));
									game = false;
								}
								else {
									keypr=0;
								}
								if(game==false)
									break;
							}
							Thread.sleep(1000);
							GameTable(level, timer, startTime, doubledipcount, count50per); // GameTable function is called
							game = timer.getFlag();  //flag get function called
							if(game==false)
								break;
						}
						if(correcttimesflag == true) {  // if contestant answered the question correctly
							
							css[contestantint].setLevel(level);  // its keeps the contestans' latest level for statistic(1)
							
							int crtimes = css[contestantint].getCorrecttimes();  // and also adds their correct times counter for age statistic
							css[contestantint].setCorrecttimes(crtimes + 1);
							
							
							for (int i = 0; i < 9; i++) {   // this one searches the category answered correctly and adds one to its counter
								if(cts[i].getCatogery().equalsIgnoreCase(questions[questionNumber].getCategory())) {
									int ctscounter = cts[i].getCategoryCounter();
									cts[i].setCategoryCounter(ctscounter+1);
								}
								
							}
							
							correcttimesflag = false;
						}
						if(falsetimesflag == true) {  // this one searches the category answered correctly and subtracts one from its counter
							for (int i = 0; i < 9; i++) {
								if(cts[i].getCatogery().equalsIgnoreCase(questions[questionNumber].getCategory())) {
									int ctscounter = cts[i].getCategoryCounter();
									cts[i].setCategoryCounter(ctscounter-1);
								}
								
							}
							falsetimesflag = false;
						}
					}
					System.out.println("Do you want to continue compatition? (Y/N)"); //checking for replay
                    choice = input.next();
                    while (!choice.equalsIgnoreCase("N") && !choice.equalsIgnoreCase("Y")) {
                        System.out.println("Please enter Y or N: ");
                    }
                    if (choice.equalsIgnoreCase("N")) {
                        cont = false;
                    }
                }
				flag3=false;
				break;
				
			case "4":
				if (flag1 && flag2 && flag3) {
					System.out.println("Please first enter data files and play the game.");
					break;
				}
				//Statistics
				// the most succesful contestant
				int mostsuccessful = 0;   
				String name = "";
				for (int i = 0; i < 21; i++) {    // searches for contestant with biggest level value
					if(css[i].getLevel() > mostsuccessful) {
						mostsuccessful = css[i].getLevel();
						name = css[i].getName();
					}
					
				}
				System.out.println("�	The most successful contestant: " + name);
				
				// the category with most correctly answered
				int mostcorrect = 0;
				String mostcategory = "";
				for (int i = 0; i < 10; i++) {   // searches for most correctly answered category 
					if(cts[i].getCategoryCounter() > mostcorrect) {
						mostcorrect=cts[i].getCategoryCounter();
						mostcategory = cts[i].getCatogery();
						
					}
					
				}
				System.out.println("�	The category with the most correctly answered: " + mostcategory);
				// the category with most badly answered
				int mostbad = 0;
				String mostbadcategory = "";
				for (int i = 0; i < 10; i++) {  // searches for most badly answered category 
					if(cts[i].getCategoryCounter() < mostcorrect) {
						mostcorrect=cts[i].getCategoryCounter();
						mostbadcategory = cts[i].getCatogery();
						
					}
					
				}
				System.out.println("�	The category with the most badly answered: " + mostbadcategory);
				//age percentage
				
				double age30 = 0,age3050 = 0,age50 = 0;   // How many times that age group answered correctly
				int age30contestant= 0 ,age3050contestant= 0 , age50contestant = 0;
				double age30perc = 0,age3050perc = 0,age50perc = 0;
				
				for (int i = 0; i < 21 ; i++) {   // going through the loop and sets the participants values for their age gap
					if(participants[i].isJoined()== true && css[i].getAge() <= 30) {
						age30 += css[i].getCorrecttimes();
						age30contestant++;
						
					}
					else if(participants[i].isJoined()== true && css[i].getAge() > 30 && css[i].getAge() <= 50) {
						age3050 += css[i].getCorrecttimes();
						age3050contestant++;
					}
					else if(participants[i].isJoined()== true && css[i].getAge() > 50) {
						
						age50 += css[i].getCorrecttimes();
						age50contestant++;
						
					}
					
				}
				// calculating the avarage values
				age30perc = age30 / age30contestant;
				if(age30contestant == 0) { age30perc = 0.0 ; }
				
				
				age3050perc = age3050 / age3050contestant;
				if(age3050contestant == 0) { age3050perc = 0.0 ; }
				
				
				age50perc = age50 / age50contestant;
				if(age50contestant == 0) { age50perc = 0.0 ; }
				
				
				System.out.println("�	age<=30 " + String.format("%.2f", age30perc) + " 30<age<50 " + String.format("%.2f", age3050perc) + " age>50 " + String.format("%.2f", age50perc));
				
				
				
				
				
				// city with highest number of participants
				//			I created an object 2D aray for setting string and integers at the same time
				Object[][] cityStatistic = new Object[21][2];  // it counts the cities that participants belong to
				Boolean cityflag = false;
				for(int i = 0; i < cityStatistic.length; i++) {
					cityStatistic[i][0] = "";
					cityStatistic[i][1] = 1;
				}
				
				
				int citycounter = 0;
				for (int i = 0; i < 21; i++) {
					for (int j = 0; j < cityStatistic.length; j++) {  // this part for eliminating duplicates and only taking odd cities
						if(cityStatistic[j][0] != null && !css[i].getCityadress().equalsIgnoreCase((String)cityStatistic[j][0])) {
							cityflag = true;
							
						} // if the cityStatistics array aready has the city it adds into the counter
						else if(cityStatistic[j][0] != null && css[i].getCityadress().equalsIgnoreCase((String)cityStatistic[j][0])) {
							 int xx1 = (Integer)(cityStatistic[j][1]);
							 xx1++;
							 cityStatistic[j][1] = xx1;
							 cityflag = false;
							 break;
						}
						
						
						
					}
					if(cityflag == true) {
						cityStatistic[citycounter][0] = css[i].getCityadress();
						citycounter++;
						cityflag = false;
					}
					
				}
				
				int highestcitycounter = 0;
				String highestcity = "";
				for (int i = 0; i < cityStatistic.length; i++) {
					if((Integer)(cityStatistic[i][1]) > highestcitycounter) {
						highestcitycounter = (Integer)(cityStatistic[i][1]);
						highestcity = (String)cityStatistic[i][0];
												
						
					}
					
				}
				
				System.out.println("�	The city with the highest number of participants: " + highestcity);
				
				
				
				break;
			case "5":
				// exit
				break;
			default:
				System.out.println("Invalid number.");
			
			}
		}
		System.out.println("Bye!!");
	}
	
	
	public static void addQuestion(Question[] questions ,Question q, int count) { 
		questions[count] = q;  // To add all objects from the question class to the array
	}		
	public void displayAllQuestions(Question[] questions , int count) {
		for(int i =0; i < count; i++)
			System.out.println(questions[i].displayQuestion());
	}
	public String[] splitQuestionWords(Question[] questions, int count) { //it is divided into text words before word checks are performed
			return questions[count].getText().split(" ");
	}
	public String getQuestions (Question[] questions, int a) {
		return questions[a].getText();
	}
	public String getCorrectAnswer (Question[] questions, int a) {
		return questions[a].getCorrectAnswer();
	}
	public static int getLevels (Question[] questions, int a) {
		return questions[a].getDifficultydegree();
	}
	public String displayAnswers(Question[] questions, int a) {
		return "A:" + questions[a].getAnswer1() + "  B:"+ questions[a].getAnswer2() 
				+ "  C:"+ questions[a].getAnswer3() + "  D:"+questions[a].getAnswer4()+"\n";
	}
	
	// this function write question with 2 answers
	public String percent50(Question[] questions, int a, String[][] questionWords, String[] dictionary) {
		int count=0;
		Random r = new Random(); // make random chose to choice wrong 2 answers
		int fora = 0,forb=0,forc=0,ford=0;
		while(count<2) {			
			int randint = r.nextInt(4) + 1;
			if(!questions[a].getCorrectAnswer().equalsIgnoreCase("A")&&randint==1 && fora!=1) {
				questions[a].setAnswer1("    ");
				count++;
				fora=1;
			}
			if(!questions[a].getCorrectAnswer().equalsIgnoreCase("B")&&randint==2 && forb!=1) {
				questions[a].setAnswer2("    ");
				count++;
				forb=1;
			}
			if(!questions[a].getCorrectAnswer().equalsIgnoreCase("C")&&randint==3 && forc!=1) {
				questions[a].setAnswer3("    ");
				count++;
				forc=1;
			}
			if(!questions[a].getCorrectAnswer().equalsIgnoreCase("D")&&randint==4 && ford!=1) {
				questions[a].setAnswer4("    ");
				count++;
				ford=1;
			}
			
			
		}
		//return question with 2 answers
		return getQuestionText(questionWords, a, dictionary) + "\n" + "A:" + questions[a].getAnswer1() + "  B:"+ questions[a].getAnswer2() 
				+ "  C:"+ questions[a].getAnswer3() + "  D:"+questions[a].getAnswer4()+"\n";
	}
	
	
	
	public void addParticipant(Participant player, int count, Participant[] participants) { 
		participants[count]=player;   //To add all objects consisting of the Participant class to the array
		count++;
	}
	
	public void addContestant(ContestantStatistic name, int counter, ContestantStatistic[] css) { // adding contestant info to contestantstatistics(css) array
		css[counter] = name;
		
		counter++;
	}
	public static int addCatogery(CategoryStatistic cat,int counter,CategoryStatistic[] cts) { // adding category info to categorystatistics(cts) array
		Boolean flag1 = false;
		
		if(counter == 0) {
			cts[counter] = cat;
			counter++;
		}
		else {
			for (int i = 0; i < counter; i++) {
				if(cts[i].getCatogery().equalsIgnoreCase(cat.getCatogery())) {
					flag1 = true;
					break;
				}
				
				
			}
			if(flag1 == false) {
				cts[counter] = cat;
				counter++;
			}
		} 
					
		return counter;
	}
	
	
	public static boolean isalpha(char character) { //checks whether the letter is alphabetical
			
        if(character >= 'a' && character <= 'z')
        	return true;
        else
            return false;   
	}                         
	public static String replace(String word) { //to remove punctuation marks at the beginning and end of a word
		String new_word = "";
		word = word.toLowerCase();
		for(int a = 0; a < word.length(); a++) {
			if (isalpha(word.charAt(a))) {  
				new_word += word.charAt(a);
			}
			else if (a != 0 && a != word.length() - 1) {
				new_word += word.charAt(a);
			}
		}
		word = new_word;
		return word;
	}
	public static String spell_check(String word, String[] dict) { 
		int count = 0, temp1 = 30, temp2 = 30;
		word = word.toLowerCase();
		if (word.length() < 3) return word; //if word length less than 3 than make no difference
		if (Arrays.asList(dict).contains(word.toLowerCase())) return word; // if word in dictionary return word
		String new_word = "";
		for(int a = 0; a < dict.length; a++) {
			if ( dict[a].length() == word.length()) { // if they are same length
				for(int b = 0; b < word.length(); b++) {
					if (word.charAt(b) == dict[a].charAt(b)) { //count every same character
						count += 1;  
					} 
					else if (temp1 == 30) {  // change 2 letter
						temp1 = b;
					}
					else {                    
						temp2 = b; //
					}
				}
				if (count == word.length() - 1) { // if count + 1 equals to length of word return word
					word = dict[a];
					return word;
				}
				else if (count == word.length() - 2) { // if length - 2 equals to length
					for(int b = 0; b < word.length(); b++) {
						if (b == temp1) {
							new_word += word.charAt(temp2); // change 2 chars which not the same character with dictionary word
						}
						else if (b == temp2) {
							new_word += word.charAt(temp1);
						}
						else {
							new_word += word.charAt(b);
						}
					}
					if (new_word.equals(dict[a])) { // if same word acquired then return dictionary word
						word = dict[a];
						return word;
					}
				}
				
				count = 0;
			}
		}
		return word;
	}
	public static boolean isword(String word) { // check the word if its word or not 
		int count = 0;
		for(int a = 0; a < word.length(); a++) {
			if(isalpha(word.charAt(a))) {
				count += 1;
			}
		}
		if (count < 1) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static String[] forTables(String category,String[] array) { //write category table
		int a;
		
		
		for(int i = 0;i<array.length;i+=2) {
			
			if(array[i]== null) {
				array[i]=category;
				array[i+1]="1";
				break;
							
			}
			else if(array[i].equals(category)){
				a =Integer.parseInt(array[i+1])+1;
				array[i+1]=Integer.toString(a);
				break;
			}
			else {
				
			}
			
			
		}
		
		return array;
	}
	
	public static String[] wordcloud(String[][] questionWords, String[] stop_words, String[] dictionary, int level,	Question[] questions) {
		String temp;
		String[] word_cloud = new String[176];
		for(int b = 0; b < questions.length; b++) { 
			if(questions[b].getIstried()==false) {
				if (level == getLevels(questions, b)) {
					for(int c = 0; c < questionWords[b].length; c++) { // check every word in the questions
						// if the word is not in stop word and first letter is not upper case than enter the if statement
						if (!(Arrays.asList(stop_words).contains(questionWords[b][c].toLowerCase())) && !(Character.isUpperCase(questionWords[b][c].charAt(0)))) {
							//if dictionary has that word and word cloud hasn't then add that word
							if (Arrays.asList(dictionary).contains(questionWords[b][c].toLowerCase()) && !(Arrays.asList(word_cloud).contains(questionWords[b][c].toLowerCase()))) {
								word_cloud[b] = questionWords[b][c];
								break;
							}
							// if first and last character is not a word than add that word
							else if (!(isalpha(questionWords[b][c].charAt(0))) || !(isalpha(questionWords[b][c].charAt(questionWords[b][c].length() - 1)))) {
								temp = replace(questionWords[b][c]);
								if (!(Arrays.asList(stop_words).contains(temp.toLowerCase())) && isword(temp) && !(Arrays.asList(word_cloud).contains(temp.toLowerCase()))) { 
									word_cloud[b] = temp;
									break;
								}
							}
							else {
								// make spell check than add that word
								if (!(Arrays.asList(stop_words).contains(spell_check(questionWords[b][c], dictionary).toLowerCase()))
										&& !(Arrays.asList(word_cloud).contains(spell_check(questionWords[b][c], dictionary).toLowerCase()))) { // 
									word_cloud[b] = spell_check(questionWords[b][c], dictionary);
									questionWords[b][c] = spell_check(questionWords[b][c], dictionary);
									break;
								}
							}
						}
					}
				}
			}
			
		}
		return word_cloud;
	}
	
	
	public static void displayarray(String[] array) { // for printing tables of categories and degrees of difficulty
		
        for(int b = 0; b< array.length;b+=2)
        {
        	if(array[b]==null) {
        		
        	}
        	else{
        		String space = " ".repeat(28 - array[b].length());
        		System.out.print(""+array[b]+ space + array[b+1] + "\n");
        	}
        }
    }
	
	public static void PlaySound(File sound) { //for sound play
	 	  try {
	 		  Clip voice= AudioSystem.getClip();
	 		  voice.open(AudioSystem.getAudioInputStream(sound));
	 		  voice.start();
	 		  
	 		  Thread.sleep(voice.getMicrosecondLength()/1000);
	 	  }
	 	  catch(Exception e){
	 		  
	 	  }
	}
	
	public static String MoneyForScreen(int level) { //money table
		String money="";
		if(level==1) {
			money= "$0";
		}
		else if(level==2) {
			money= "$20000";
		}
		else if(level==3) {
			money= "$100000";
		}
		else if(level==4) {
			money= "$250000";
		}
		else if(level==5)
			money= "$500000";
		
		return money;
	}
	
	public static String calculateMoney(int level) { // calculates the contestant's net winnings for each level
		if(level==1)
			return "You won $0";
		else if(level==2) {
			return "You won $0";
		}
		else if(level==3) {
			return "You won $100,000 !";
		}
		else if(level==4) {
			return "You won $100,000 !";
		}
		else if(level==5) {
			return "You won $500,000 !";
		}
		else
			return "You won $1,000,000 !";
	}
	public static void GameTable(int level, Timer timer, long startTime, int dd, int fif) {// that is game table
		String table =  "----------------------------";
		String tablevoid = "|                          |";
		String table1 = "";
		String table2 =  "| Time (20s):              |";
		String table3, table4;
		
		if (level == 1) { // change length of string by money large
			table1 =   "| "+ MoneyForScreen(level) +"                       |";
		}
		else if (level == 2) {
			table1 =   "| "+ MoneyForScreen(level) +"                   |";
		}
		else if (level == 3) {
			table1 =   "| "+ MoneyForScreen(level) +"                  |";
		}
		else if (level == 4) {
			table1 =   "| "+ MoneyForScreen(level) +"                  |";
		}
		else if (level == 5) {
			table1 =   "| "+ MoneyForScreen(level) +"                  |";
		}
		if (fif == 0) {
			table3 =  "| %50                      |";
		}
		else {
			table3 =  "| --                       |";
		}
		if (dd == 0) {
			table4 =  "| Double Dip               |";
		}
		else {
			table4 =  "| --                       |";
		}
		int cursorx =  Enigma.getConsole().getTextWindow().getCursorX(), cursory =  Enigma.getConsole().getTextWindow().getCursorY();
		
		//write that table specific coordinates
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 10);
		System.out.print(table);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 9);
		System.out.print(table1);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 8);
		System.out.print(tablevoid);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 7);
		System.out.print(table2);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 6);
		System.out.print(tablevoid);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 5);
		System.out.print(table3);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 4);
		System.out.print(tablevoid);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 3);
		System.out.print(table4);
		Enigma.getConsole().getTextWindow().setCursorPosition(78, cursory - 2);
		System.out.print(table);
		Enigma.getConsole().getTextWindow().setCursorPosition(96, cursory - 7);
		timer.timer(startTime);
		Enigma.getConsole().getTextWindow().setCursorPosition(cursorx, cursory);
	}
	public static void Space() {// add space
		int count = 0;
		while (count <= 20) {
			System.out.println();
			System.out.println();
			count++;
		}
	}
	
	
	public static String getQuestionText(String[][] questionWords, int b, String[] dictionary) {
        String question = "";  // Returns the corrected version of the problem contains typo
        for (int d = 0; d < questionWords[b].length; d++) {
            if (d == questionWords[b].length - 1) {  
                question += questionWords[b][d];
            }
            else if (!isalpha(questionWords[b][d].charAt(questionWords[b][d].length() - 1))) {
                question += questionWords[b][d] + " ";
            }
            else if (Character.isUpperCase(questionWords[b][d].charAt(0))) {
                question += questionWords[b][d] + " ";
            }
            else if (Arrays.asList(dictionary).contains(questionWords[b][d].toLowerCase())) {
                question += questionWords[b][d] + " ";
            }
            else {
                question += spell_check(questionWords[b][d], dictionary) + " ";
            }
        }
        return question;
    }
}