/* 

	Bear class. Bear's mental states, emotional values, and interaction responses are stored here.

	Created 10/19/2014. 
	Last edited 10/19/2014. 

*/

package bear.logic;

public class Bear { 

	private Action action = Action.NULL; 
	private Action actionPrevious = action; 
	private int[] states = new int[4]; 
	private boolean isDead = false; 

	public Bear() { // constructor 
		states[State.HAPPY.getValue()] = 0; 
		states[State.SAD.getValue()] = 0; 
		states[State.ANGRY.getValue()] = 0; 
		states[State.HUNGRY.getValue()] = 0; 
	}

	public String update(Action action) { // act on action, respond with an output from a behavior
		this.action = action; // update action 
		String output = "";
		// Actions: INSULT, COMPLEMENT, FEED, HIT, PET, TELL_JOKE, BE_SARCASTIC, THREATEN, APOLOGIZE, IGNORE,
		switch (action) {
			case INSULT:
				output = insult(); 
				break;
			case COMPLIMENT:
				output = compliment(); 
				break;
			case FEED:
				output = feed(); 
				break;
			case HIT:
				output = hit(); 
				break; 
			case PET:
				output = pet(); 
				break;
			case TELL_JOKE:
				output = tell_joke(); 
				break;
			case BE_SARCASTIC:
				output = be_sarcastic(); 
				break;
			case THREATEN:
				output = threaten(); 
				break;
			case APOLOGIZE:
				output = apologize(); 
				break;
			case IGNORE:
				output = ignore(); 
				break;
		}

		// cap number values - all emotions have hard cap of 15 (except hunger - hard cap of 20)
		
		if (states[State.HAPPY.getValue()] < 0) {
			states[State.SAD.getValue()] -= states[State.HAPPY.getValue()];
			// negative happiness translates directly into sadness
			states[State.HAPPY.getValue()] = 0; 
		}

		if (states[State.SAD.getValue()] < 0) {
			states[State.HAPPY.getValue()] -= states[State.SAD.getValue()];
			// negative sadness translates directly into happiness
			states[State.SAD.getValue()] = 0; 
		}

		if (states[State.ANGRY.getValue()] < 0) {
			states[State.HAPPY.getValue()] -= states[State.ANGRY.getValue()];
			// negative anger translates directly into happiness (will this off-balance everything?)
			states[State.ANGRY.getValue()] = 0; 
		}		

		for (int i = 0; i < states.length; i++) {
			if (states[i] > 15 && i != State.HUNGRY.getValue()) { // don't update hungry - is handled separately
				states[i] = 15;
			}
		}

		// now that we've capped everything, we can let bear's feelings move towards equilibrium 

		moveTowardsEquilibrium(); // should this be at the beginning of the function? It affects the values that are displayed. 
		// only balances when player enters a valid function - is this what we want, or should bear balance without player input?

		actionPrevious = action; 

		// contextualize hunger 
		if (states[State.HUNGRY.getValue()] >= 20) { 
			output = "Bear starved to death. NOOOO!!!! You hated him anyway.";
			isDead = true; 
		}
		else if (states[State.HUNGRY.getValue()] >= 17) {
			output += "\nBear is... becoming... weak. Bear... can't go on... much longer. UGGH!!!";
			addSad(3);
		}
		else if (states[State.HUNGRY.getValue()] >= 13) {
			output += "\nBear's hunger is becoming UNBEARABLE. RAAAWWWWRRR!!!";
			addAngry(2);
		}
		else if (states[State.HUNGRY.getValue()] >= 7) {
			output += "\nBear's stomach is growling IRRITABLY. BEAR WANTS FOOD!!!";
			addAngry(1);
		}

		// add code for depression/suicide?

		return output; 

	}

	public int[] getStates() { 
		return states; // return all three values at once 
	}

	public boolean getIsDead() { // is bear alive?
		return isDead; 
	}


	/*

	There is another category: Hunger. The hungrier bear is, the angrier he gets. (Hunger ranges 0-5.)
	Emotion rules:
	•	If you add to happiness, you need to take away from anger and sadness. 
	•	Adding to anger takes away from happiness, but not sadness. 
	•	Sadness takes away from happiness, but not anger.
	•	If Bear is angry for long enough, he becomes sad. 
	•	Bear has highs and lows.
	•	If Bear is really happy (a high), he will inevitably crash and have a low.  || write now this is implemented as returning to baseine feelings 


	*/

	public void addHappy(int amt) {
		states[State.HAPPY.getValue()] += amt; 
		states[State.SAD.getValue()] -= amt; 
		states[State.ANGRY.getValue()] -= amt; 
	}

	public void addSad(int amt) {
		states[State.HAPPY.getValue()] -= amt; 
		states[State.SAD.getValue()] += amt; 
	}

	public void addAngry(int amt) { 
		states[State.ANGRY.getValue()] += amt;  
		states[State.HAPPY.getValue()] -= amt; 
	}

	public void subtractHungry(int amt) {
		states[State.HUNGRY.getValue()] -= amt; 
		states[State.HAPPY.getValue()] += amt; 
		states[State.SAD.getValue()] -= amt; 
	}

	// over time, Bear's feelings will return to neutral on their own 
	public void moveTowardsEquilibrium() { 
		
		// happy
		if (states[State.HAPPY.getValue()] < 0) {
			states[State.HAPPY.getValue()] = 0; // is this repeating what's already in update?
		}
		else if (states[State.HAPPY.getValue()] > 0) {
			states[State.HAPPY.getValue()] --; 
		}

		// sad
		if (states[State.SAD.getValue()] < 0) {
			states[State.SAD.getValue()] = 0; 
		}
		else if (states[State.SAD.getValue()] > 0) {
			states[State.SAD.getValue()] --; 
		}

		// angry
		if (states[State.ANGRY.getValue()] < 0) {
			states[State.ANGRY.getValue()] = 0; 
		}
		else if (states[State.ANGRY.getValue()] > 0) {
			states[State.ANGRY.getValue()] --; 
		}

		// hunger - will actually increase with time
		states[State.HUNGRY.getValue()] ++; 
		//if (states) -- make hunger eventually increase anger

	}

	// for a full description of the various action methods, see the table in the proposal document.
	public String insult() {
		int amtRage = 0; // amount of rage to add
		int amtSad = 0; 
		String output = ""; 

		if (states[State.ANGRY.getValue()] <= 0) { 
			output = "Bear is insulted. Why do you say these things?"; 
			amtRage = 2;
			amtSad = 1; 
		}
		else if (states[State.ANGRY.getValue()] <= 3) { 
			output = "Why do you keep insulting Bear?!"; 
			amtRage = 3;
			amtSad = 1; 
		}
		else if (states[State.ANGRY.getValue()] >= 5) { 
			output = "YOUR INSOLENCE IS UNFORGIVEABLE!!!"; 
			amtRage = 5;
			amtSad = 2; 
		}

		if (actionPrevious == action) { // if you insult bear consecutively, the effect is doubled 
			amtRage *= 2; 
			amtSad *= 2; 
		}

		addAngry(amtRage); 
		addSad(amtSad);

		return output; 
	} 

	public String compliment() {
		int amtHappy = 0;  
		String output = ""; 

		if (states[State.HAPPY.getValue()] <= 0) { 
			output = "Oh, why, how kind of you.";
			amtHappy = 1; 
		}
		else if (states[State.HAPPY.getValue()] <= 3) { 
			output = "Really, you are too kind. Bear blushes.";

			amtHappy = 2; 
		}
		else if (states[State.HAPPY.getValue()] >= 5) { 
			output = "Oh, you dirty, dirty mammal, you. Bear wonders if you'd like to go back to his place for some salmon?";
			amtHappy = 4; 
		}

		if (actionPrevious == action) { // if you insult bear consecutively, the effect is doubled 
			amtHappy *= 2; 
		}

		addHappy(amtHappy);

		return output; 
	} 

	public String feed() {
		int amtHungry = 0;  
		int amtHappy = 0;
		String output = ""; 

		if (states[State.HUNGRY.getValue()] <= 0) { 
			output = "Bear is not actually hungry, but Bear appreciates your effort.";
			amtHungry = 0;
			amtHappy = 1; 
		}
		else if (states[State.HUNGRY.getValue()] <= 3) { 
			output = "Bear was beginning to have the belly-growels. But Bear is satisfied now.";
			amtHungry = 2;
			//amtHappy = 2; - redundant; happiness factor built into amtHungry
		}
		else if (states[State.HUNGRY.getValue()] >= 5) { 
			output = "BEAR HAS BEEN STARVING!!!! Why did you not bring Bear food SOONER?!!";
			amtHungry = 4;
			//amtHappy = 4; 
		}

		// don't double amount of food decrease

		subtractHungry(amtHungry);
		addHappy(amtHappy);

		return output; 
	} 

	public String hit() {
		int amtRage = 0;  
		String output = ""; 

		output = "WHAT?!?! You DARE hit BEAR!?!! RAAAWWWWRRR!!!!";
		amtRage = 5; 

		if (actionPrevious == action) { 
			output = "STOP IT!!! STOP HITTING BEAR!!! STOP IT NOOOOWWWWW!!!!";
			amtRage *= 2; 
		}

		addAngry(amtRage); 

		return output; 
	} 

	public String pet() { // this function's kind of clunky - it can be condensed 
		int amt = 0; 
		String output = ""; 

		if (states[State.ANGRY.getValue()] >= 5) { // petting will actually enrage bear more if he's already angry
			amt = 2;
			output = "You think PETTING will console Bear?! INSOLENT HUMAN!!! THE RAGE OF BEAR IS UNSTOPPABLE!!!";
			addAngry(amt);
		}
		else { 
			amt = 1; 
			output = "Bear enjoys your gentle strokes. Do you like salmon?";
			addHappy(amt); 
		}

		return output; 
	} 

	public String tell_joke() {
		String output; 

		if (actionPrevious != action) {
			output = "HA HA HA!!! Bear is pleased by your Knock-Knock jokes.";
			addHappy(3);
		} 
		else { 
			output = "Bear is bored by your jokes. There are only so many Shelbys who can come around a mountain!";
			addAngry(1); 
		}

		return output; 
	} 

	public String be_sarcastic() {
		int amt = 0; 
		String output; 

		if (states[State.ANGRY.getValue()] <= 5) {
			output = "Bear is confused. You are not the Pope.";
			amt = 1; 
		}
		else { 
			output = "What do you mean this is 'hilarious?!!' BEAR FINDS NONE OF THIS HILARIOUS!!!"; 
			amt = 3; 
		}

		addAngry(amt); 

		return output; 
	} 

	public String threaten() {
		addAngry(6); 
		return "You DARE threaten BEAR?!?! INSOLENT WORM!!!"; 
	} 

	public String apologize() {
		if (states[State.ANGRY.getValue()] <= 0 && states[State.SAD.getValue()] <= 0) {
			return "Why are you apologizing? Bear's feelings are not hurt."; 
		}
		else if (states[State.ANGRY.getValue()] <= 4) { 
			addHappy(2); 
			return "Bear begrudginly accepts your apology. Hrumf.";
		}

		// if the previous two return false,
		addAngry(2); 
		return "Filthy human!!! Bear KNOWS you are not TRULY sorry!! Do you take Bear for a FOOL?!?!?!";
	} 

	public String ignore() {
		if (states[State.ANGRY.getValue()] <= 0) {
			addSad(3); 
			return "Why do you ignore Bear? BEAR NEEDS PEOPLE!!!";
		}
		else if (states[State.ANGRY.getValue()] <= 3) { 
			addAngry(2); 
			addSad(1);
			return "Bear is not amused. Have you gone DEAF?!";
		} 

		// if very angry, provokes bear further 
		addAngry(4);
		return "SPEAK, HUMAN!!! YOU CANNOT HIDE FROM BEAR BEHIND SILENCE!!!"; 	
	}


}