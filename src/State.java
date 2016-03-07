/**
 * @author tarun
 * Class denoting a State in MDP GridWorld.
 */
import java.util.Vector;
public class State {
	float utility; //Utility of state.
	Action bestAction; //Best Action in the state corresponding to defined MDP.
	boolean isTerminating; //Whether state is terminating or not.
	boolean isWall; //Whether state is a wall or not.
	Vector<Action> possibleActions;
	public final int row, column; //Row and column number of the state in the grid.
	
	/**
	 * Generates a state from row and column.
	 * @param row
	 * @param column
	 */
	public State(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Copy constructor.
	 * Generates a state by making copy of other state.
	 * @param State s
	 */
	public State(State s) {
		this.utility = s.utility;
		this.bestAction = s.bestAction;
		this.isTerminating = s.isTerminating;
		this.possibleActions = s.possibleActions;
		this.row = s.row;
		this.column = s.column;
	}

	/**
	 * Generate state using parameters.
	 * @param utility
	 * @param action
	 * @param isTerminating
	 * @param possibleActions
	 * @param row
	 * @param column
	 */
	public State(float utility, Action action,
			boolean isTerminating, Vector<Action> possibleActions, int row,
			int column) {
		this.utility = utility;
		this.bestAction = action;
		this.isTerminating = isTerminating;
		this.possibleActions = possibleActions;
		this.row = row;
		this.column = column;
	}

	/**
	 * @return Utility of the state, Float.
	 */
	public float getUtility() {
		return utility;
	}

	/**
	 * Sets the utility of this state.
	 * @param utility
	 */
	public void setUtility(float utility) {
		this.utility = utility;
	}

	/**
	 * @return Best Action of the state.
	 */
	public Action getAction() {
		return bestAction;
	}
	
	/**
	 * Set the best action for the corresponding state.
	 * @param action
	 */
	public void setAction(Action action) {
		this.bestAction = action;
	}
	
	/**
	 * @return Whether the present state is terminating or not.
	 */
	public boolean isTerminating() {
		return isTerminating;
	}
	
	/**
	 * Set the state as terminating ot non-terminating.
	 * @param isTerminating
	 */
	public void setTerminating(boolean isTerminating) {
		this.isTerminating = isTerminating;
	}
	
	/**
	 * @return Get the list of all possible actions for the state.
	 */
	public Vector<Action> getPossibleActions() {
		return possibleActions;
	}

	/**
	 * Sets the possible actions for this state.
	 * @param possibleActions
	 */
	public void setPossibleActions(Vector<Action> possibleActions) {
		this.possibleActions = possibleActions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + (isTerminating ? 1231 : 1237);
		result = prime * result + row;
		return result;
	}

	/**
	 * Two states are equal, if their corresponding actions are avaialable.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof State)) {
			return false;
		}
		State other = (State) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "State [utility=" + utility + ", bestAction=" + bestAction
				+ ", isTerminating=" + isTerminating + ", isWall=" + isWall
				+ ", possibleActions=" + possibleActions + ", row=" + row
				+ ", column=" + column + "]";
	}

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
		this.possibleActions = null;
	}
}
