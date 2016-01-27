
public class Driver {
	public static void main(String[] args) {
		MDP grdwrld = new MDP(3, 4);
		grdwrld.valueIteration();
		State[][] x = grdwrld.getGrid();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				System.out.println(x[i][j]);
			}
		}
	}

}
