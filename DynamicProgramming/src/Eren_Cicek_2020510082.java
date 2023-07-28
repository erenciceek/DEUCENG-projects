import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Eren_Cicek_2020510082 {

	public static void main(String[] args) {
		
		/*
		ArrayList<Integer> demand_list = new ArrayList<Integer>(); // arraylist is created for txt files
		ArrayList<Integer> salary_list = new ArrayList<Integer>();
		loadFile("yearly_player_demand.txt", demand_list); //calls loadFile function
		loadFile("players_salary.txt", salary_list);  //calls loadFile function
		
		int p = 5 , c = 10 , n = 6; 
	 
		System.out.println("Minimum cost : " + DB(p, demand_list,salary_list, c, n));
		*/
		
	
		int m = 3, p = 2;
        long r = possiblePlacements(m, p);
        System.out.println("The number of placements are: " + r);



		
	}
	public static long possiblePlacements(int m, int p) {
        long[][] dp = new long[m+1][3];
        // dp[i][0]: i chairs with last two occupied by different teams
        // dp[i][1]: i chairs with last two occupied by same team
        // dp[i][2]: i chairs with last three occupied by two same and one different team

        dp[1][0] = p;
        dp[2][0] = p * p;
        dp[2][1] = p * (p - 1);

        for(int i = 3; i <= m; i++){
            dp[i][0] = dp[i-1][0] * p + dp[i-1][1] * p;
            dp[i][1] = dp[i-1][0] * (p - 1);
            dp[i][2] = dp[i-1][1];
        }

        return dp[m][0] + dp[m][1] + dp[m][2];
    }

	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static int DB(int p, ArrayList<Integer> demand_list, ArrayList<Integer> salary_list, int c, int n) {
		int total_Demand = 0;  // the total number of football players requested to be hired in as many as n years
		
		for (int i = 1; i <= n; i++)  // 
			total_Demand += demand_list.get(i);

		int[][] dp = new int[n + 1][total_Demand + 1]; // the matrix in which the earnings calculations are made
		dp[0][0] = 0;
		for (int i = 1; i <= total_Demand; i++) // 
			dp[0][i] = salary_list.get(i); 
		
		//Salary values are assigned to the first line of the matrix, 
		//assuming that there are promoted football players left over from the previous year in the first year.


		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= total_Demand; j++) {
				
				int min_cost = 999999999;
				if (demand_list.get(i) > p) {   //if we can produce fewer football players 
					// than the number of football players who are asked to be hired by clubs
					int required_player = demand_list.get(i) + j - p;
					for (int y = 0; y <= required_player; y++) {
						if (y > total_Demand)
							break;
						int temp = dp[i - 1][y] + (c * (required_player - y));
						if (temp < min_cost)  //min cost is determined
							min_cost = temp;
					}
					dp[i][j] = min_cost + dp[0][j]; //there is a cumulative cost calculation between the lines

				} else {  //if we can produce more football players  
					// than the number of football players who are asked to be hired by clubs
					int required_player = j - (p - demand_list.get(i));
					if (required_player >= 0) {
						for (int x = 0; x <= required_player; x++) {
							if (x > total_Demand)
								break;
							int temp = dp[i - 1][x] + (c * (required_player - x));
							if (temp < min_cost) // min cost is determined
								min_cost = temp;
						}
						dp[i][j] = min_cost + dp[0][j]; // there is a cumulative cost calculation between the lines
					} else {
						dp[i][j] = dp[i - 1][j];
					}

				}
			}
		}
		return dp[n][0];
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
		while (text.hasNextLine()) { //for example, in a line such as "10 29" in a file named players_salary, the tenth index of the arraylist is assigned value of 29
			String[] arr = text.nextLine().split("\t"); //
			list.add(Integer.parseInt(arr[1])); 
		}
	}
}
