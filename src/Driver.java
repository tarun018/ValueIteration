import java.util.Scanner;

/**
 * Driver class for running the ValueIteration for MDP Grid World.
 * @author tarun
 */
public class Driver {
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("\nCorrect Usage: \'java Driver (#ofrows) (#ofcolumns) (discountfactor)\' ");
			System.out.println("Example Correct Usage: \'java Driver 3 4 1\' \n");
			System.exit(1);
		}
		
		int row = Integer.parseInt(args[0]);
		int col = Integer.parseInt(args[1]);
		
		System.out.print("Enter the discount factor: ");
		float discount = Float.parseFloat(args[2]);
		
		MDP grdwrld = new MDP(row, col, discount);
	
		float maxPossibleError = (float) 0.1;
		grdwrld.valueIteration(maxPossibleError);
		
		System.out.println("\n\n\n---------------FINAL OUTPUT---------------");
		State[][] x = grdwrld.getGrid();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.println(x[i][j]);
			}
		}
	}

}
