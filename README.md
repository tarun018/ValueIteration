

# Value Iteration

This is a Value Iteration program for a n X m grid world. User have to provide the n (number of rows), m (number of columns), and the discount factor (default value is 1) via command line arguments.
Steps for running the program:

  - Complile using 'javac Driver.java'
  - Run Driver using 'java Driver (number of rows) (number of columns) (discount factor) '
  - Run example: 'java Driver 3 4 1'

If you want to get the output in a file, run as 'java Driver > output.txt', where output.txt is the output filename.

User can change the terminating and walled states here: https://github.com/tarun018/ValueIteration/blob/master/src/MDP.java#L52

User can change the R(s,a) values here:
https://github.com/tarun018/ValueIteration/blob/master/src/MDP.java#L209

Output Details:

State [utility=0.0, bestAction=null, isTerminating=false, isWall=false, possibleActions=[ACTION UP, ACTION DOWN, ACTION LEFT, ACTION RIGHT], row=0, column=0]
Row: 0 Column: 0

For each iteration, state information indicates the utility of that state, bestAction, whether state is Terminating, or a Wall, along with possibleActions, and row and column number of the state in the MDP GridWorld.

Final Output with final utility values, bestActions for all states after convergence. A sample output for the 3 x 4 gridWorld referenced in "Artificial Intelligence : A Modern Approach" with gamma = 1 is shown in bin/output.txt.




