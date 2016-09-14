/* 

	Output component. Contains a JLabel that's updated on an event loop. 
	Intended layout is South-West, above Input component. 


	1/13/15
	Alex Rupp-Coppi

*/

package bear.gui;

import javax.swing.*; 

public class OutputBox extends JPanel { 
	
	private String message; 
	private JLabel outputText;

	public OutputBox() {
		message = "";
		outputText = new JLabel();
		this.add(outputText);
	}

	public OutputBox(String message) {
		this.message = message;
		outputText = new JLabel(this.message);
		this.add(outputText);
	}

	public String getMessage() { 
		return message;
	}

	public void setMessage(String message) { 
		this.message = message;
		outputText.setText(this.message);
	}

}