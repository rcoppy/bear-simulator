/*
	

	Main class. The actual EVENT LOOP. 
	Pull everything together here.


	Alex Rupp-Coppi
	Created 10/17/2014
	Last edited 10/23/2014.

	
	None of the code in this program has been directly copy-pasted from any website. (Except for a broken snippet in an unused function at the bottom of this class.) 
	I did, however, very often reference the official Java Documentation, Stack Overflow, and various other tutorial websites.
	Everything I've looked up is pretty much common knowledge, and is also applied generally throughout my program, so I haven't put it in specific references except for one or two places.

	URLs that helped me (especially with enums): 
		http://stackoverflow.com/questions/10161408/java-using-switch-statement-with-enum-under-subclass
		http://docs.oracle.com/javase/7/docs/technotes/guides/language/strings-switch.html
		http://javarevisited.blogspot.com/2011/08/enum-in-java-example-tutorial.html
		http://stackoverflow.com/questions/8811815/is-it-possible-to-assign-numeric-value-to-an-enum-in-java

-----------------------------

	Known bugs: 
		Happiness can decrease to -1. (Not sure why.) It also can't increase past 14, even though the level cap is 15.
		Occasionally, prompt output is blank. (Not easily repeatable - not sure why.)
		Anger can move above 15. (Not sure why.)

	Things I would have liked to have time to do: 
		Add a 'kill' interaction.
		Program in a 'depressed' mental state.

		Filter text input for specific groups of words - e.g.: 
			"I love your hair" >> "i love you"
			"Nice armpits!" >> "nice"
			"Give Bear salmon. NOW!!!" >> "give"
		(You can see where I started trying to code this. (Bottom of this class.) It would reduce redundancy in the commands list.)

		Implement emoticons to show Bear's emotions: 
			⊂(￣(工)￣)⊃
		(A level of polish I didn't have time for.)

		Add more dialog possibilities/make bear respond contextually to situations.
		(The program gets boring fast.)

	
*/

package bear;

import java.util.*; 
import bear.logic.*;
import bear.gui.*; 
import java.awt.event.*; 

public class Main {

	private static boolean shouldClose = false; 
	private static String helpString = "Welcome to BEAR SIMULATOR 2014. Interact with Bear the bear!!! \n Here are a list of actions that Bear will respond to: \n-insult\n-complement\n-feed\n-hit\n-pet\n-tell a joke\n-be sarcastic\n-threaten\n-apologize\n-ignore\n\nHere are some program commands:\n-help\n-exit\n";

	View view; // the GUI
	Bear bear; 
	Action action;
	BearFaces faces; 

	public static void main(String[] args) {
		
		// get the working directory of the program (used for asset loading)
		//String workingdir = System.getProperty("user.dir");
		//System.out.println("Current directory: "+workingdir);

		Main main = new Main(); // need to instantiate this to access methods - if no instance exists, can we call its properties?
		
		

		//Scanner sc = new Scanner(System.in); // I have issues referencing this from a static context/within a method - so pass it as a paramater to any function that needs it

		main.init();

		View view = new View(main); // initialize the GUI
		
		/*while(!shouldClose) {
			main.update(sc);
			main.draw(); // if I ever get around to implementing a GUI
		}*/
		// deprecated because of GUI implementation
	}


	public void init() { // should this just be the constructor for Main? 

		bear = new Bear(); 
		action = Action.NULL;
		faces = new BearFaces();  // stores emoticons
		System.out.println("\nWelcome to BEAR SIMULATOR 2014!!! Interact with Bear the bear. \nHe responds well to verbs like 'insult' and 'compliment,' but also statements like 'screw you.' Try experimenting! (If you get really  stuck, you can access a non-exhaustive list of commands by typing 'help.')\n");
		System.out.println("\n"+faces.img[State.NEUTRAL.getValue()]); 
	}

	public String update(String input) { // formerly returned void, took type Scanner

		/* The user acts on Bear by giving text input. Bear shows a change in
		 emotional state, and his reaction is conveyed through ASCII characters and a brief prompt. */

		/* Flow of update: 

			1. Text input 
				GUI version: take in string passed to Main (ideally from text field)
			2. Update bear
			3. Show response (output)
				GUI version: return response as a string

		*/

		// get action from player 
		System.out.println("\n\n>> "); 
		action = getInputAction(input); 
		String output = "..."; 

		System.out.println("\n----------------------\n");

		// certain actions don't warrant a response from bear: EXIT, NULL, HELP
		if (action != Action.EXIT && action != Action.NULL && action != Action.HELP) {
			output = bear.update(action);
		}
		else { // these are actions that deal with the program directly 
			switch (action) {
				case EXIT: // don't need Action. prefix for a reason I don't understand - stackoverflow says only value needed inside of switch statement
					output = "Close the window yourself, jerkface!"; //exit(new Scanner(System.in)); 
					break;
				case NULL: 
					output = ("Bear is confused. What are you telling him?");
					break;
				case HELP: 
					output = helpString;
					break;
			}
		}

		// show response

		int[] states = bear.getStates(); 

		// kill program if bear dies, show the death emoticon
		if (bear.getIsDead()) {
			shouldClose = true; 
			System.out.println(faces.img[State.DEAD.getValue()]);
		}
		// if bear's not dead, display emotion
		else if (states[State.ANGRY.getValue()] >= states[State.SAD.getValue()] && states[State.ANGRY.getValue()] > states[State.HAPPY.getValue()]) { // if not dead, show emotion
			// bear is dominantly angry, show his angry face
			// anger trumps sadness
			System.out.println(faces.img[State.ANGRY.getValue()]); 
		}
		else if (states[State.SAD.getValue()] > states[State.ANGRY.getValue()] && states[State.SAD.getValue()] > states[State.HAPPY.getValue()]) {
			// bear is dominantly sad, show his sad face
			System.out.println(faces.img[State.SAD.getValue()]);
		}
		else if (states[State.HAPPY.getValue()] > states[State.ANGRY.getValue()] && states[State.HAPPY.getValue()] > states[State.SAD.getValue()]) {
			// bear is dominantly happy, show his happy face
			System.out.println(faces.img[State.HAPPY.getValue()]);
		}
		else { 
			// bear has no dominant emotion - show his neutral face
			System.out.println(faces.img[State.NEUTRAL.getValue()]);
		}

		System.out.println(""); // line break

		// bear's text response to user input
		System.out.println(output);

		System.out.println(""); // line break

		// bear stats
		System.out.println("Happiness: "+states[State.HAPPY.getValue()]+" Sadness: "+states[State.SAD.getValue()]+" Anger: "+states[State.ANGRY.getValue()]+" Hunger: "+states[State.HUNGRY.getValue()]); 

		return output; // will be sent to the View class
	}

	public void draw() {
		// if this thing ever gets a GUI.
	}

	public void exit(Scanner input) {
		System.out.println("Are you sure you want to quit? BEAR DOESN'T WANT TO BE LONELY!!!");
		String prompt = input.nextLine().toLowerCase();
		
		switch (prompt) {
			case "y":
			case "yes":
			case "true":
			case "t":
			case "quit":
				shouldClose = true; 
				break;
		}
 
	}

	// get the action from user to which bear wil respond
	public Action getInputAction(String input) { // formerly took type Scanner; adjusted for GUI
		String act = input.toLowerCase(); // get player input from scanner, make it all lower case
		Action output = Action.NULL; // default

		// possible actions: INSULT, COMPLIMENT, FEED, HIT, PET, TELL_JOKE, BE_SARCASTIC, THREATEN, APOLOGIZE, EXIT, NULL, IGNORE, HELP
		switch (act) {
			case "insult":
			case "taunt":
			case "mock":
			case "jeer":
			case "you suck":
			case "suck it": 
			case "screw you":
			case "butthead":
			case "idiot":
			case "yo mama":
			case "you're fat":
			case "fatty":
			case "fat":
			case "jerk":
			case "insolent":
			case "worm":
			case "insolent worm":
			case "suck":
			case "die":
			case "turdface":
			case "turd":
			case "kill yourself":
				output = Action.INSULT; // enums are strange - in switch statements, they don't want 'Action.' preceding the value, just the value - but in assignments, they want the full 'Action.VALUE'
				break;
			case "complement":
			case "compliment":
			case "cool":
			case "nice":
			case "nice hair":
			case "nice fur":
			case "i love you":
			case "i love your salmon":
				output = Action.COMPLIMENT; 
				break;
			case "feed":
			case "give food":
			case "salmon":
			case "fish":
			case "food":
				output = Action.FEED;
				break;
			case "hit":
			case "punch":
			case "abuse":
			case "kick":
			case "hurt":
			case "stab":
			case "shank":
			case "choke":
				output = Action.HIT;
				break;
			case "pet":
			case "stroke":
			case "pat":
			case "scratch":
				output = Action.PET;
				break;
			case "tell joke": 
			case "tell_joke":
			case "tell a joke":
			case "be funny":
			case "joke":
			case "funny":
				output = Action.TELL_JOKE; 
				break;
			case "just kidding":
			case "jk":
			case "be sarcastic":
			case "be snide":
			case "be rude":
			case "sarcastic":
			case "rude":
			case "snide":
			case "you're hilarious":
			case "oh really":
			case "hilarious":
			case "ha":
			case "ha ha":
			case "sarcasm":
			case "lol":
				output = Action.BE_SARCASTIC; 
				break;
			case "threaten": 
			case "make a threat":
			case "blackmail":
			case "i'm gonna hurt you":
			case "i will kill you":
			case "kill":
				output = Action.THREATEN; 
				break;
			case "apologize": 
			case "say sorry":
			case "say i'm sorry":
			case "say you're sorry":
			case "sorry":
			case "i'm sorry":
			case "forgive me":
			case "seek forgiveness":
				output = Action.APOLOGIZE; 
				break;
			case "exit": 
			case "quit":
			case "bye":
				output = Action.EXIT; 
				break;
			case "ignore":
			case "not listening":
			case "la la la":
			case "lalala":
			case "what?":
			case "what":
			case "i can't hear you":
			case "i'm not listening":
			case "i don't hear you":
			case "i don't hear anything":
				output = Action.IGNORE; 
				break;
			case "help":
			case "help me":
			case "i'm confused":
			case "info":
			case "documentation":
			case "idk":
				output = Action.HELP; 
				break;
		}

		return output; 
	}

	public String filterInput(String input) { 
		/*// WIP - may or may not use
		// matching & for loop come from stack overflow: http://stackoverflow.com/questions/5091057/how-to-find-a-whole-word-in-a-string-in-java
		String match = "123woods";

		String[] sentence = input.split();

		for(String word: sentence)
		{
		    if(word.equals(match))
		        return word;
		}*/
		return input;
	}

}
