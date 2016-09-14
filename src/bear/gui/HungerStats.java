/*

	GUI hunger component. Intended layout  position is North-East. 

	1/13/15
	Alex Rupp-Coppi

	12:02 AM 1/17/15 - this class got too complicated, replaced it with a standard stats bar.

*/

package bear.gui;

import javax.swing.*; 
import java.awt.*; 

public class HungerStats extends StatsBar { // overrides only the init methods, set current value methods 
	
	private int minVal; 
	private int maxVal; 
	private int currentVal; 
	private JLabel label;
	private JPanel food; // food container
	private BackgroundPanel img;
	private String imgSrc; // filepath
	private String caption;
	private JProgressBar bar;  

	public HungerStats() {

		this.currentVal = 20; 
		this.minVal = 0; 
		this.maxVal = 20; 
		this.imgSrc = "";
		this.caption = "Hunger"; 

		init();

	}

	public HungerStats(int currentVal, int minVal, int maxVal, String imgSrc, String caption) {

		this.currentVal = currentVal; 
		this.minVal = minVal; 
		this.maxVal = maxVal; 
		this.imgSrc = imgSrc;
		this.caption = caption; 

		init(); // would calling super use this class's init() method or the parent's?
	}

	@Override 
	public void init() {

		label = new JLabel(caption+": "+this.currentVal+"/"+this.maxVal);

		this.setLayout(new GridLayout(2,1));

		// wrapper for food icons
		food = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		for (int i = minVal; i < currentVal; i ++) { 
			food.add(new BackgroundPanel(imgSrc));
		}

		this.add(label);
		this.add(food);
	}

	@Override
	public void setCurrentVal(int e) { 
		currentVal = e;
		init();
		System.out.println("New current val: "+currentVal);
	}
}