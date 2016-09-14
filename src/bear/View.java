/* 

	Graphical component of the Bear program. 
	1/12/15 
	Alex Rupp-Coppi

	Completed at 2:37 AM 1/17/15. (2:37 minutes past deadline. Sorry.)

	There's a lot of commented-out code that I just didn't get around to deleting.
	I ran into a lot of weird issues with images and resizing, so there's a lot of vestigial code lying around from there.

	The maximum values for the different stat bars always cap off correctly. That's a logic problem from October I didn't get around to fixing.

	Because the program loop doesn't terminate after Bear dies, Bear's hunger levels keep increasing even after he's dead.
	I would fix this, but I'm out of time. 

	The 'help' command still works, but it outputs to the GUI without line breaks, so it's not really readable.
	It still shows up in bullet form in the command prompt, though. 
	(The only command line function broken now is 'exit.') 

*/

package bear; 

import java.awt.*; 
import javax.swing.*;
import java.util.ArrayList; 
import java.awt.event.*; 
import bear.logic.State;
import bear.gui.*; 


public class View implements ActionListener, KeyListener { 

	private BearImage bearImage;		    // rendering
	private StatsContainer statsContainer;  // rendering
	//private HungerStats hungerStats;	    // rendering
	private OutputBox outputBox; 			// rendering
	private InputBox inputBox; 				// rendering
	private GridLayout appLayout;			
	private JPanel panelNorth; 				// rendering
	private JPanel panelSouth; 				// rendering
	private String workingDir; 
	private String assetDir; 
	public Main main; 

	// debug
	private JButton button; 

	private int maxHunger; 

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			//statsContainer.hunger.setCurrentVal(statsContainer.hunger.getCurrentVal()-1);
			//System.out.println("Button clicked");
		}
	}

	// KeyListener example from http://java.about.com/od/Handling-Events/ss/A-Keylistener-Example-Program.htm

	//The KeyListener interface is implemented as an anonymous 
	//inner class using the addKeyListener method. 
	 
	//When any key is pressed and released then the  
	//keyPressed and keyReleased methods are called respectively. 
	//The keyTyped method is called when a valid character is typed. 
	//The getKeyChar returns the character for the key used. If the key 
	//is a modifier key (e.g., SHIFT, CTRL) or action key (e.g., DELETE, ENTER) 
	//then the character will be a undefined symbol. 
	@Override  
	public void keyPressed(KeyEvent e) { 
		// inputString += e.getKeyChar();
		if (e.getSource() == inputBox.input && e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			// refresh the inputBox
			inputBox.keyPressEnter();

			// debug
			System.out.println(inputBox.getInputString());
			
			// update MAIN - pass user command to the actual program
			outputBox.setMessage(main.update(inputBox.getInputString()));
				// main's update returns an output string - display it

			// now update all the GUI components to reflect updated main
			updateGUI(); 
		}
	} 

	@Override 
	public void keyReleased(KeyEvent e) { 
		//inputString += "Key Released: " + e.getKeyChar() + "\n"; 
	}  

	@Override 
	public void keyTyped(KeyEvent e) { 
		//The getKeyModifiers method is a handy 
		//way to get a String representing the 
		//modifier key. 
		//inputString += "Key Typed: " + e.getKeyChar() + " " + KeyEvent.getKeyModifiersText(e.getModifiers()) + "\n"; 
	} 

	public View(Main main) { 
		this.main = main; // let us access directly the main class (this is a really weird way of doing this)
		maxHunger = 20; // default vars
		init();
		updateGUI(); 
	}

	public View(Main main, int maxHunger) {
		this.main = main; 
		this.maxHunger = maxHunger; 
		init();
	}

	public void init() {

		// get the working directory so we can find assets
		workingDir = System.getProperty("user.dir");
		assetDir = workingDir + "\\assets";

		bearImage = new BearImage(State.HAPPY.getValue()); // set to happy image
		//System.out.println(bearImage.getFilepath()); 

		statsContainer = new StatsContainer(); // contains happiness, sadness, anger, hunger
		//hungerStats = new HungerStats(0, 0, 20, assetDir+"\\icon_hunger.png", "Hunger"); // int currentVal, int minVal, int maxVal, String imgSrc, String caption 
		outputBox = new OutputBox(); 
		inputBox = new InputBox(); 
		inputBox.input.addKeyListener(this);

		// debug
		//button = new JButton("OK"); 
		//button.addActionListener(this);

		// grouping panels
		panelNorth = new JPanel(); 
		panelSouth = new JPanel(new GridLayout(2,1)); // 2 rows, 1 column 

		panelNorth.add(statsContainer);
		//panelNorth.add(hungerStats);
		//panelNorth.add(button);

		outputBox.setMessage("Welcome to BEAR SIMULATOR. Type a command, then press enter.");

		//panelSouth.add(new BackgroundPanel(assetDir+"\\emoticon_happy.png")); // classpath vs. filepath - one's automatically in the root directory (BackgroundPanel takes classpath)
		panelSouth.add(outputBox);
		panelSouth.add(inputBox);

		//appLayout = new GridLayout(2,3); 

		JFrame frame = new JFrame("Bear Simulator GUI"); // each JFrame is its own window 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contents = frame.getContentPane(); // add all the buttons to the pane
		
		contents.setLayout(new GridLayout(3,1)); // 3 rows, 1 column; apply the grid layout

		// row 1 - north
		contents.add(panelNorth);

		//row 2 - center
		contents.add(bearImage);

		// row 3 - south
		//ImageIcon p = new ImageIcon()
		//contents.add(new JLabel(new ImageIcon(assetDir+"\\emoticon_angry.png")));
		//contents.add(new BackgroundPanel(assetDir+"\\bear_states\\ANGRY.png"));
		contents.add(panelSouth);

		frame.pack(); // automatically resize all elements to look good
		frame.setMinimumSize(new Dimension(100,200));
		frame.setVisible(true);// by default, frame isn't visible
	}

	public void updateGUI() { 
		// input/output boxes already taken care of

		// get bear states (not super efficient or neat, but whatevs)
		int[] states = main.bear.getStates(); // read off bear state statistics

		// panel north
		statsContainer.happy.setCurrentVal(states[State.HAPPY.getValue()]);  
		statsContainer.angry.setCurrentVal(states[State.ANGRY.getValue()]);
		statsContainer.sad.setCurrentVal(states[State.SAD.getValue()]);
		statsContainer.hunger.setCurrentVal(states[State.HUNGRY.getValue()]);
		
		// bear image
		// kill program if bear dies, show the death emoticon
		if (main.bear.getIsDead()) {
			
			// kill program? How?

			// update image to death face
			bearImage.setState(State.DEAD.getValue());

			//shouldClose = true; 
			//System.out.println(faces.img[State.DEAD.getValue()]);
		}

		// if bear's not dead, display emotion
		else if (states[State.ANGRY.getValue()] >= states[State.SAD.getValue()] && states[State.ANGRY.getValue()] > states[State.HAPPY.getValue()]) { // if not dead, show emotion
			// bear is dominantly angry, show his angry face
			// anger trumps sadness
			
			bearImage.setState(State.ANGRY.getValue());
			
			//System.out.println(faces.img[State.ANGRY.getValue()]); 
		}
		else if (states[State.SAD.getValue()] > states[State.ANGRY.getValue()] && states[State.SAD.getValue()] > states[State.HAPPY.getValue()]) {
			// bear is dominantly sad, show his sad face
			
			bearImage.setState(State.SAD.getValue());

			//System.out.println(faces.img[State.SAD.getValue()]);
			

		}
		else if (states[State.HAPPY.getValue()] > states[State.ANGRY.getValue()] && states[State.HAPPY.getValue()] > states[State.SAD.getValue()]) {
			// bear is dominantly happy, show his happy face
			
			bearImage.setState(State.HAPPY.getValue());

			//System.out.println(faces.img[State.HAPPY.getValue()]);
		}
		else { 
			// bear has no dominant emotion - show his neutral face
			
			bearImage.setState(State.NEUTRAL.getValue());

			//System.out.println(faces.img[State.NEUTRAL.getValue()]);
		}

		// panel south
			// nothing to do here
	}
}