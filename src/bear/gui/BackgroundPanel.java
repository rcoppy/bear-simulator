// Based on a code example copy-pasted from http://www.coderanch.com/how-to/java/BackgroundImageOnJPanel

// WARNING!!! because this class extends JPanel, adding to another JPanel doesn't seem to work. 
// Take that into account!

package bear.gui;

import javax.imageio.ImageIO.*; 
import javax.swing.*;
import java.awt.*; 
import java.io.*; 

public class BackgroundPanel extends JPanel
{
  Image image;
  public BackgroundPanel(String filepath)
  {
    try
    {
      image = javax.imageio.ImageIO.read(new File(filepath)); // formerly used classpath, but that was broken for some reason
    }
    catch (Exception e) { /*handled in paintComponent()*/ }
  }

  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g); 
    if (image != null)
      g.drawImage(image, 0,0,this.getWidth(),this.getHeight(),this);
  }
}