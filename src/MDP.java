/**
 * @author tarun
 * MDP class.
 */
import java.util.*;
public class MDP {
	int n,m; //n corresponds to number of rows in MDP GridWorld and m corresponds to number of columns in MDP GridWorld.
	float gamma; //Gamma value to be used for discounting.
	State[][] grid;
	Vector<Action> actions;
	int numofActions; //Total number of actions available. Corresponds to the number of actions available in Action class. 

	/**
	 * Action objects corresponding to the action.
	 */
	public static final Action UP = new Action(Action.UP);
	public static final Action DOWN = new Action(Action.DOWN);
	public static final Action LEFT = new Action(Action.LEFT);
	public static final Action RIGHT = new Action(Action.RIGHT);
	
	/**
	 * MDP Initialization. Adds the actions supported by the MDP to the list of possible actions.
	 * @param n, number of rows
	 * @param m, number of columns
	 * @param gamma, discount factor.
	 */
	public MDP(int n, int m, float gamma) {
		this.n = n;
		this.m = m;
		this.gamma = gamma;
		this.numofActions = 4;
		this.grid = new State[n][m];
		this.actions = new Vector<Action>();
		actions.add(UP);
		actions.add(DOWN);
		actions.add(LEFT);
		actions.add(RIGHT);
		generateStates();
	}
	
	/**
	 * Initialize the states generated. Initially all states are set to have utility as 0 and non terminating, with bestAction being null.
	 */
	public void generateStates() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				grid[i][j] = new State((float) 0.0, null, false, actions, i, j);
			}
		}
		
		//Set the terminating states with the corresponding utility.
		setTerminatingState(grid[0][3], 1);
		setTerminatingState(grid[1][3], -1);
		
		//Set the walls in MDP GridWorld.
		grid[1][1].setWall(true);
	}
	
	/**
	 * Sets the given state s as terminating, and set the utilities as given utility and possible actions as null, since no action is possible in a terminating state.
	 * @param State s
	 * @param utility
	 */
	public void setTerminatingState(State s, float utility) {
		grid[s.row][s.column].setTerminating(true);
		grid[s.row][s.column].setUtility(utility);
		grid[s.row][s.column].setPossibleActions(null);
	}

	/**
	 * Performs the up action, Goes up row up.! If wall is encountered, then remain at the same state.
	 * @param State s
	 * @return State after corresponding UP action.
	 */
	public State performUp(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextRow = s.row - 1;
		if(nextRow < 0)
			nextRow = 0;
		if (grid[nextRow][nextCol].isWall())
			return s;
		else
			return grid[nextRow][nextCol];
	}

	/**
	 * Performs the down action, Goes one row down.! If wall is encountered, then remain at the same state.
	 * @param State s
	 * @return State after corresponding DOWN action.
	 */
	public State performDown(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextRow = s.row + 1;
		if(nextRow > n-1)
			nextRow = n-1;
		if (grid[nextRow][nextCol].isWall())
			return s;
		else
			return grid[nextRow][nextCol];
	}

	/**
	 * Performs the left action, Goes left one column.! If wall is encountered, then remain at the same state.
	 * @param State s
	 * @return State after corresponding LEFT action.
	 */
	public State performLeft(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextCol = s.column - 1;
		if(nextCol < 0)
			nextCol = 0;
		if (grid[nextRow][nextCol].isWall())
			return s;
		else
			return grid[nextRow][nextCol];
	}

	/**
	 * Performs the right action, Goes right one column.! If wall is encountered, then remain at the same state.
	 * @param State s
	 * @return State after corresponding RIGHT action.
	 */
	public State performRight(State s) {
		int nextRow = s.row, nextCol = s.column;
		nextCol = s.column + 1;
		if(nextCol > m-1)
			nextCol = m-1;
		if (grid[nextRow][nextCol].isWall())
			return s;
		else
			return grid[nextRow][nextCol];
	}

	/**
	 * Pair class to accommodate State and their corresponding probabilities to reach them together.
	 * @author tarun
	 *
	 */
	public class Pair {
		public State s;
		public float val;
		
		public Pair(State s, float val) {
			this.s = s;
			this.val = val;
		}
	}

	/**
	 * Given a state s, and an action a, returns the possible states due to non deterministic movements along with their probabilities.
	 * Agent can go the inteneded state with 'intended' probability and can deviate with 'alternative' probability.
	 * @param State s
	 * @param Action a
	 * @return List of the possible states due to non deterministic movements along with their probabilities.
	 */
	public Vector<Pair> performAction(State s, Action a) {
		float intended = (float) 0.8, alternative = (float) 0.1;
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

	/**
	 * Given a state s, and an action a, it returns the probability to transition to next state s1.
	 * Transition Probabilities.
	 * @param State s
	 * @param Action a
	 * @param State s1
	 * @return Transition probability to s1 when action a is taken in state s.
	 */
	public float getTransitions(State s, Action a, State s1) {
		Vector<Pair> possible = performAction(s, a);
		for ( Pair sa : possible ) {
			if (sa.s.equals(s1)) {
				return sa.val;
			}
		}
		return (float) 0.0;
		
	}

	/**
	 * Given a state s and an action a, returns the reward R(s,a).
	 * R(s,a) is the utility, if state s is terminating, otherwise can be set.
	 * @param State s
	 * @param Action a
	 * @return Reward R(s,a) when taking action a in state s.
	 */
	public float getReward(State s, Action a) {
		if (s.isTerminating()) {
			return (float) s.getUtility();
		} else {
			return (float) -0.04;
		}
	}

	/**
	 * Class to pair up Utilities and the best action for ValueIteration.
	 * @author tarun
	 *
	 */
	public class ActionUtilityPair {
		public float utility;
		public Action best;
		
		public ActionUtilityPair(float val, Action a) {
			this.utility = val;
			this.best = a;
		}
	}

	/**
	 * ValueIteration on this MDP.
	 * @param epsilon, the maximum error allowed in the utility of any state.
	 */
	public void valueIteration(float epsilon) {
		int it=0; //Number of iteration, initially 0.
		
		/**
		 * Copying existing utility values (initially 0 for all non terminating states) in a dummy variable with corresponding bestActions for each of the states.
		 */
		ActionUtilityPair[][] dummy = new ActionUtilityPair[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				dummy[i][j] = new ActionUtilityPair(grid[i][j].getUtility(), grid[i][j].getAction());
			}
		}

		/**
		 * Value Iteration begins.
		 */
		while(true) {
			System.out.println("------------Iteration--------- # " + ++it);
			float delta = 0; //Initialize delta (the maximum change in the utility of any state in an iteration) to zero.

			/**
			 * For all states, print out the state information (i.e. utility, bestAction, etc. ) at current iteration.
			 * 
			 */
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					grid[i][j].setUtility(dummy[i][j].utility);
					grid[i][j].setAction(dummy[i][j].best);
					System.out.println(grid[i][j]);
				}
			}
			
			/**
			 * Apply Bellman equation updates to all states. 
			 * If state is terminating or is a wall, then no updates.
			 * From all possible actions, 
			 */
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < m; j++) {
					System.out.println("Row: " + i + " Column: " + j);
					float maxUtility = -2;
					Action best = null;
					if(!grid[i][j].isTerminating && !grid[i][j].isWall) {
						
						//All possible actions for state grid[i][j].
						Vector<Action> possibleActions = grid[i][j].getPossibleActions();
						
						//Returns the maximum possible utility after trying out all possible actions, and the corresponding action.
						for(Action a : possibleActions) {
							System.out.println("Action Selected: " + a);
							float util = (float) 0.0;
							Vector<Pair> possibleStates = performAction(grid[i][j], a);

							//For a given state, and an action selected, get the expected value of utility of this state by iterating over all possible states due to non-determinsitic actions.
							for ( Pair sa : possibleStates ) {
								System.out.println("State: " + sa.s.row + " " + sa.s.column + "Utility: " + sa.s.getUtility() + "Probability: " + sa.val);
								util = util + (sa.s.getUtility() * sa.val);
							}
	
							util = util * gamma;
							util = util + this.getReward(grid[i][j], a);
							System.out.println("Utility: " + util);
							
							if(util > maxUtility) {
								maxUtility = util;
								best = a;
							}
						}
						
						//Prints best action, and max utility at current iteration.
						System.out.println("MAX : Action: " + best + "  " + "Utility: " + maxUtility);
						dummy[i][j].utility = maxUtility; //Updates dummy values to accomodate the current values.
						dummy[i][j].best = best;
						
						//Getting the value of maximum delta for all states and corresponding actions for the current iteration.
						if (Math.abs(dummy[i][j].utility - grid[i][j].getUtility()) > delta) {
							delta = Math.abs(dummy[i][j].utility - grid[i][j].getUtility());
						}
					}
				}
			}
			//If maximum change in utility of any state in this iteration (delta), is within the maximum error allowed range (epsilon), then we stop the value iteration.
			if (delta <= ( epsilon*((float)(1 - gamma) / (gamma))) ) {
				break;
			}
		}
		return;
	}

	/**
	 * @return grid containing all the states.
	 */
	public State[][] getGrid() {
		return grid;
	}
}
