/**
 * @author tarun
 * 
 */
import java.util.*;
public class MDP {
	int n,m;
	double gamma = 0.8;
	State[][] grid;
	Vector<Action> actions;
	int numofActions;
	public static final Action UP = new Action(Action.UP);
	public static final Action DOWN = new Action(Action.DOWN);
	public static final Action LEFT = new Action(Action.LEFT);
	public static final Action RIGHT = new Action(Action.RIGHT);
	
	public MDP(int n, int m) {
		this.n = n;
		this.m = m;
		this.numofActions = 4;
		this.grid = new State[n][m];
		this.actions = new Vector<Action>();
		actions.add(UP);
		actions.add(DOWN);
		actions.add(LEFT);
		actions.add(RIGHT);
		generateStates();
	}
	
	public void generateStates() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				grid[i][j] = new State(0.0, null, false, actions, i, j);
			}
		}
		setTerminatingState(grid[1][1], -10);
		setTerminatingState(grid[0][2], 1);
		grid[1][2].setWall(true); //Wall
	}
	
	public void setTerminatingState(State s, double utility) {
		grid[s.row][s.column].setTerminating(true);
		grid[s.row][s.column].setUtility(utility);
		grid[s.row][s.column].setPossibleActions(null);
	}

	public State performUp(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextRow = s.row - 1;
		if(nextRow < 0)
			nextRow = 0;
		return grid[nextRow][nextCol];
	}

	public State performDown(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextRow = s.row + 1;
		if(nextRow > n-1)
			nextRow = n-1;
		return grid[nextRow][nextCol];
	}

	public State performLeft(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextCol = s.column - 1;
		if(nextCol < 0)
			nextCol = 0;
		return grid[nextRow][nextCol];
	}

	public State performRight(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextCol = s.column + 1;
		if(nextCol > m-1)
			nextCol = m-1;
		return grid[nextRow][nextCol];
	}

	public class Pair {
		public State s;
		public double val;
		
		public Pair(State s, double val) {
			this.s = s;
			this.val = val;
		}
	}

	public Vector<Pair> performAction(State s, Action a) {
		double intended = 0.8, alternative = 0.1;
		Vector<Pair> possibleStates = new Vector<Pair>();
		if (a.equals(UP)) {
			possibleStates.add(new Pair(performUp(s), intended));
			possibleStates.add(new Pair(performLeft(s), alternative));
			possibleStates.add(new Pair(performRight(s), alternative));
		} else if (a.equals(DOWN)) {
			possibleStates.add(new Pair(performDown(s), intended));
			possibleStates.add(new Pair(performLeft(s), alternative));
			possibleStates.add(new Pair(performRight(s), alternative));
		} else if (a.equals(LEFT)) {
			possibleStates.add(new Pair(performLeft(s), intended));
			possibleStates.add(new Pair(performUp(s), alternative));
			possibleStates.add(new Pair(performDown(s), alternative));
		} else if (a.equals(RIGHT)) {
			possibleStates.add(new Pair(performRight(s), intended));
			possibleStates.add(new Pair(performUp(s), alternative));
			possibleStates.add(new Pair(performDown(s), alternative));
		}
		return possibleStates;
	}

	public double getTransitions(State s, Action a, State s1) {
		Vector<Pair> possible = performAction(s, a);
		for ( Pair sa : possible ) {
			if (sa.s.equals(s1)) {
				return sa.val;
			}
		}
		return 0.0;
		
	}

	public double getReward(State s, Action a) {
		if (s.isTerminating()) {
			return s.getUtility();
		} else {
			return -0.04;
		}
	}

	public class ActionUtilityPair {
		public double utility;
		public Action best;
		
		public ActionUtilityPair(double val, Action a) {
			this.utility = val;
			this.best = a;
		}
	}

	public void valueIteration() {
		int it=0;
		ActionUtilityPair[][] dummy = new ActionUtilityPair[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				dummy[i][j] = new ActionUtilityPair(grid[i][j].getUtility(), grid[i][j].getAction());
			}
		}
		while(true) {
			System.out.println("Iteration #" + ++it);
			double delta = 0, epsilon = 0.001;
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					grid[i][j].setUtility(dummy[i][j].utility);
					grid[i][j].setAction(dummy[i][j].best);
				}
			}
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					double maxUtility = -2;
					Action best = null;
					if(!grid[i][j].isTerminating && !grid[i][j].isWall) {
						Vector<Action> possibleActions = grid[i][j].getPossibleActions();
						for(Action a : possibleActions) {
							double util = 0.0;
							Vector<Pair> possibleStates = performAction(grid[i][j], a);
							for ( Pair sa : possibleStates ) {
								util = util + (sa.s.getUtility() * sa.val);
							}
							util = util * gamma;
							util = util + this.getReward(grid[i][j], a);
							if(util > maxUtility) {
								maxUtility = util;
								best = a;
							}
						}
						dummy[i][j].utility = maxUtility;
						dummy[i][j].best = best;
						//System.out.println("Limit Value: " + ( epsilon*((double)(1 - gamma) / (gamma))));
						if (Math.abs(dummy[i][j].utility - grid[i][j].getUtility()) > delta) {
							delta = Math.abs(dummy[i][j].utility - grid[i][j].getUtility());
						}
					}
				}
			}
			//System.out.println("Delta: " + delta);
			if (delta <= ( epsilon*((double)(1 - gamma) / (gamma))) ) {
				break;
			}
		}
		return;
	}

	public State[][] getGrid() {
		return grid;
	}
}
