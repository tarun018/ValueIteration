
public class Driver {
	public static void main(String[] args) {
		MDP grdwrld = new MDP(4, 3);
		grdwrld.valueIteration();
		State[][] x = grdwrld.getGrid();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.println(x[i][j]);
			}
		}
	}

}
