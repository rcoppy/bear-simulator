/* 

	Input component. "Command line." Contains textfield, on detected keypress of 'enter' will fire an event parent instance can detect.

	1/13/15
	Alex Rupp-Coppi

*/

package bear.gui;

import java.awt.*; 
import javax.swing.*; 
import java.awt.event.*; 

public class InputBox extends JPanel { 
	
	private String defMessage;
	public JTextField input; 
	private String inputString;

	public InputBox() {
		this.defMessage = "";
		input = new JTextField("", 20); // 20 spaces by default
		inputString = "";

		this.add(input);

		//this.setMinimumSize(new Dimension(400,20)); 

	}

	public InputBox(String defMessage) {
		this.defMessage = defMessage; // default message to show 
		this.input = new JTextField(this.defMessage, 20);
		this.add(input); 
	}

	public String getDefMessage() {
		return defMessage;
	}

	public void setDefMessage(String defMessage) {
		this.defMessage = defMessage;
	}

	public String getInputString() { 
		return inputString; // return the input string
	}

	//public void resetField() {   redundant function 
	//  input.setText(defMessage);	 
	//}

	public void keyPressEnter() { 
		inputString = input.getText();
		input.setText(defMessage); // formerly resetField() function; clear input
	}

}