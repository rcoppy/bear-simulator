/* 

	Enum constant list of bear emotional states.

	Created 10/19/2014.
	Last edited 10/23/2014. 

*/

package bear.logic;

public enum State { 
	HAPPY(0), SAD(1), ANGRY(2), HUNGRY(3), NEUTRAL(4), DEAD(5); // enums are sort of like objects - CAN contain variables, but don't have to

    private int value; //HAPPY(value);

    private State(int value) { // constructor - gives enum its value
            this.value = value;
    }

    public int getValue() { // getter for enum value (tedious to use)
    	return value; 
    }
}

// http://javarevisited.blogspot.com/2011/08/enum-in-java-example-tutorial.html || used to figure out how to assign enums values