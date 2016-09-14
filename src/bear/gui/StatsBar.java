/* 

	Statbar class. Actual visual representation of a stats bar. Should be contained in StatsContainer.

	1/12/15 

	Alex Rupp-Coppi 

*/  

package bear.gui;

import javax.swing.*; 
import java.awt.*; 

public class StatsBar extends JPanel { 
	private int minVal; 
	private int maxVal; 
	private int currentVal; 
	private JLabel label;
	private BackgroundPanel img;
	private String imgSrc; // filepath
	private String caption;
	private JProgressBar bar;  

	// no-args
	public StatsBar() {
		this.currentVal = 0; 
		this.minVal = 0; 
		this.maxVal = 0; 
		this.imgSrc = ""; // no image will render b/c of try/catch in backpanel
		this.caption = "stat";
		
		init();
	}

	public StatsBar(int currentVal, int minVal, int maxVal, String imgSrc, String caption) {
		this.currentVal = currentVal; 
		this.minVal = minVal; 
		this.maxVal = maxVal; 
		this.imgSrc = imgSrc;
		this.caption = caption; 

		init();
	}

	public void init() {

		label = new JLabel(caption+": "+this.currentVal+"/"+this.maxVal);

		img = new BackgroundPanel(imgSrc); 

		bar = new JProgressBar(this.minVal, this.maxVal);
		bar.setValue(this.currentVal);

		this.setLayout(new GridLayout(2,1));

		// wrapper for img and label, put them above bar
		JPanel temp = new JPanel(); 
		temp.add(img);
		temp.add(label);

		this.add(temp);
		this.add(bar);

	}

	// getters
	public int getMinVal() { 
		return minVal;
	}

	public int getMaxVal() { 
		return maxVal;
	}

	public int getCurrentVal() { 
		return currentVal;
	}

	public String getImgSrc() { 
		return imgSrc; 
	}

	public String getCaption() { 
		return caption; 
	}

	// setters
	public void setMinVal(int e) { 
		minVal = e;
	}

	public void setMaxVal(int e) { 
		maxVal = e;
	}

	public void setCurrentVal(int e) { 
		currentVal = e;
		bar.setValue(currentVal);
		updateLabel(); 
	}

	public void setImgSrc(String e) { 
		imgSrc = e; 
		img = new BackgroundPanel(imgSrc);
	}

	public void setCaption(String e) { 
		caption = e;
		updateLabel();
	}

	public void updateLabel() {
		label.setText(caption+": "+this.currentVal+"/"+this.maxVal); 
	}

}