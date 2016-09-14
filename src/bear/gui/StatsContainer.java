/* 

	Stats container in Bear program. Should be instantiated by view. 
	Intended layout position is North-West. 

	1/12/15
	Alex Rupp-Coppi

*/ 

package bear.gui;

import javax.swing.*; 

public class StatsContainer extends JPanel {
	
	public StatsBar happy; 
	public StatsBar sad;
	public StatsBar angry; 
	public StatsBar hunger; // later addition

	public StatsContainer() {
		// statsbar constructor: int currentVal, int minVal, int maxVal, String imgSrc, String caption
		String dir = System.getProperty("user.dir") + "\\assets\\";
		
		happy = new StatsBar(0, 0, 15, dir+"emoticon_happy.png", "Happiness"); 
		sad = new StatsBar(0, 0, 15, dir+"emoticon_sad.png", "Sadness");
		angry = new StatsBar(0, 0, 15, dir+"emoticon_angry.png", "Anger"); 
		hunger = new StatsBar(0, 0, 20, dir+"icon_hunger.png", "Hunger"); 

		this.add(happy); 
		this.add(sad);
		this.add(angry);
		this.add(hunger);
	}
}