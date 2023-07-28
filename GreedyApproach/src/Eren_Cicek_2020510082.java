import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Eren_Cicek_2020510082 {

	public static void main(String[] args) {

		ArrayList<Integer> demand_list = new ArrayList<Integer>(); // arraylist is created for txt files
		ArrayList<Integer> salary_list = new ArrayList<Integer>();
		loadFile("yearly_player_demand.txt", demand_list); // calls loadFile function
		loadFile("players_salary.txt", salary_list); // calls loadFile function

		int p = 5, c = 10, n = 4;
		System.out.println(Greedy(p,n,demand_list,salary_list,c));
	}

	public static int Greedy(int p, int n, ArrayList<Integer> demand_list, ArrayList<Integer> salary_list, int c) {
		int totalCost = 0; // initial value for total cost
		 
		for (int i = 1;i<=n;i++) {  
			int demand_val = demand_list.get(i);
			int required_player = demand_val - p;
		
			if (i != n) {
				int next_required_player = demand_list.get(i+1) - p; 
				
			
				if(next_required_player > 0) { // if you need a player next year
					if(required_player > 0) 
						totalCost += c * required_player;
					
					// checks are made between paying the player a salary and keeping them on hold for the next year
					// and hiring a coach and raising players
					int diff = next_required_player - Math.abs(required_player);
					int cost;
					if(diff > 0) {
						if(salary_list.get(Math.abs(required_player)) > c * Math.abs(required_player)) 
							cost = c * next_required_player ; 						
						else 
							cost = salary_list.get(Math.abs(required_player)) + diff * c;					
					}
					else {
						cost =Math.min(salary_list.get(next_required_player), next_required_player * c);
					}
					totalCost += cost;
					i++;
				}
				else if(required_player > 0){	// if there is no need for a player in the next year, and there is a need for a player in the controlled year			
					totalCost += required_player * c;
				}
						
			}
			else if(required_player > 0){				
				totalCost += required_player * c;
			}
		}
		return totalCost;
	}
	

	    
	public static void loadFile(String file_name, ArrayList<Integer> list) {
		File file = new File(file_name);
		Scanner text = null;
		list.add(0); // the first index of the arraylist is assigned 0
		try {
			text = new Scanner(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		text.nextLine();
		while (text.hasNextLine()) { // for example, in a line such as "10 29" in a file named players_salary, the
										// tenth index of the arraylist is assigned value of 29
			String[] arr = text.nextLine().split("\t"); //
			list.add(Integer.parseInt(arr[1]));
		}
	}
}
