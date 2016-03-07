import java.util.Scanner;

/**
 * Driver class for running the ValueIteration for MDP Grid World.
 * @author tarun
 */
public class Driver {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter number of rows and columns in the MDP Gridworld, separated by space: ");
		String inp = in.nextLine();
		String[] input = inp.split(" ");
		int row = Integer.parseInt(input[0]);
		int col = Integer.parseInt(input[1]);
		
		System.out.println("Enter the discount factor: ");
		float discount = in.nextFloat();
		
		MDP grdwrld = new MDP(row, col, discount);
	
		float maxPossibleError = (float) 0.1;
		grdwrld.valueIteration(maxPossibleError);
		
		State[][] x = grdwrld.getGrid();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.println(x[i][j]);
			}
		}
	}

}
