/**
 * @author tarun
 *
 */
import java.util.Vector;
public class State {
	float utility;
	Action bestAction;
	boolean isTerminating;
	boolean isWall;
	Vector<Action> possibleActions;
	public final int row, column;
	
	public State(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public State(State s) {
		this.utility = s.utility;
		this.bestAction = s.bestAction;
		this.isTerminating = s.isTerminating;
		this.possibleActions = s.possibleActions;
		this.row = s.row;
		this.column = s.column;
	}

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

	public float getUtility() {
		return utility;
	}
	public void setUtility(float utility) {
		this.utility = utility;
	}
	public Action getAction() {
		return bestAction;
	}
	public void setAction(Action action) {
		this.bestAction = action;
	}
	public boolean isTerminating() {
		return isTerminating;
	}
	public void setTerminating(boolean isTerminating) {
		this.isTerminating = isTerminating;
	}
	public Vector<Action> getPossibleActions() {
		return possibleActions;
	}
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
