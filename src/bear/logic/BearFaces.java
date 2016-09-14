/* 

	Images for bear's emotions, stored as strings in an array.
	Created 10/23/14. 
	Last edited 10/23/14.

*/

package bear.logic;

public class BearFaces { 
	public String[] img = new String[6]; // array is as long as enum list State 

	public BearFaces() { // the original emoticons had unmappable characters - oh well
		img[State.ANGRY.getValue()] = "(>(*)<)";
		img[State.SAD.getValue()] = "(/(*)\\)";	
		img[State.NEUTRAL.getValue()] = "(-(*)-)";	
		img[State.HAPPY.getValue()] = "(^(*)^)"; 	
		img[State.DEAD.getValue()] = "(x(*)x)";
	} 

}