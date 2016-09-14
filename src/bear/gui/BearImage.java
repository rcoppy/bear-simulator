/* 

	Bear image component. Contains BackgroundPanel (the actual image), handles which image to render. 

	Intended layout is center.

	1/13/15.
	Alex Rupp-Coppi

*/ 

package bear.gui;

import javax.swing.*; 
import java.awt.*;
import bear.logic.State;
import javax.imageio.ImageIO.*; 
import java.io.*;

public class BearImage extends JPanel { 
	
	//private String filepath; 
	private BackgroundPanel img; 
	private JLabel imgLabel;
	private ImageIcon icon; 
	private String[] imgFiles = new String[6]; // 6 instead of 5 b/c working around the fact that original enum had 6 cases, dead being the last case - so State.DEAD is 5, which is out of bounds of array[5]
	private String dir; // directory of image files
	private int state; 

	public BearImage(int index) { // index should be int enum - State.HAPPY, State.SAD, etc.

		state = index; 

		dir = System.getProperty("user.dir") + "\\assets\\bear_states\\";

		imgFiles[State.ANGRY.getValue()]    = dir + "ANGRY.png";
		imgFiles[State.SAD.getValue()]      = dir + "SAD.png";	
		imgFiles[State.NEUTRAL.getValue()] = dir + "NEUTRAL.png";	
		imgFiles[State.HAPPY.getValue()]    = dir + "HAPPY.png"; 	
		imgFiles[State.DEAD.getValue()]     = dir + "DEAD.png"; 

		// was having issues with panels within panels
		/*try { // try to use user setting, otherwise default to neutral expression
			img = new BackgroundPanel(imgFiles[state]);	
		}
		catch (Exception e) { 
			img = new BackgroundPanel(imgFiles[State.NEUTRAL.getValue()]);
		}*/
		
		//img.setMinimumSize(new Dimension(512,512));

		//this.add(img);
		icon = new ImageIcon(dir+"NEUTRAL.png");
		imgLabel = new JLabel(icon);

		this.add(imgLabel);

		//this.setMinimumSize(new Dimension(512,512));

		this.setPreferredSize(this.getPreferredSize());
		this.validate(); 

	}

	public int getState() {
		return state; 
	}

	public void setState(int state) {
		
		try { // try to use user setting, otherwise don't do anything
			imgLabel.setIcon(new ImageIcon(imgFiles[state]));	
			
			this.state = state; 
			System.out.println("image switch worked");
		}
		catch (Exception e) { 
			System.out.println("image switch didn't work");
		}
	}

}