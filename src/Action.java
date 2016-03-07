/**
 * @author tarun
 * Class denoting the actions possible in the MDP GridWorld.
 * Presently, Actions UP, DOWN, LEFT, RIGHT are supported.
 */
public class Action {
	int action;
	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	
	/**
	 * Constructor of Action class from a Integer.
	 * @param a
	 */
	public Action(int a) {
		this.action = a;
	}
	
	/**
	 * Constructor of Action class from a String.
	 * @param a
	 * @throws IllegalArgumentException, if string is not recognized.
	 */
	public Action(String a) {
		if (a.equals("UP"))
			this.action = 0;
		else if (a.equals("DOWN"))
			this.action = 1;
		else if (a.equals("LEFT"))
			this.action = 2;
		else if (a.equals("RIGHT"))
			this.action = 3;
		else
			throw new IllegalArgumentException("String " + a + "is not a possible action");
	}

	@Override
	/**
	 * Checks whether two actions are equal or not.
	 */
	public boolean equals(Object a) {
		if ((a != null) && (a instanceof Action)) {
			Action b = (Action)a;
			if (b.action == this.action)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		switch(this.action) {
		case 0: return "ACTION UP";
		case 1: return "ACTION DOWN";
		case 2: return "ACTION LEFT";
		case 3: return "ACTION RIGHT";
		default: return "UNKNOWN";
		}
	}
}
