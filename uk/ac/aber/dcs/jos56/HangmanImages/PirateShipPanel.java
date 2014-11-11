package uk.ac.aber.dcs.jos56.HangmanImages;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;


public class PirateShipPanel extends JPanel implements Runnable{
	
	private Thread backgroundAnimation;
	private Image[] backgroundImage;
	private int frameCount;
	private int currentFrame;
	
	/**
	 * This class is used to draw and animate the background image of the graphics.
	 * The constructor sets the image file to be loaded, then starts the thread.
	 * 
	 * @param frameCount The number of frames the animation consists of.
	 */
	public PirateShipPanel(int frameCount){
		this.frameCount = frameCount;
		backgroundImage = new Image[frameCount];
		
		for(int i = 0; i < frameCount; i++){
			backgroundImage[i] = Toolkit.getDefaultToolkit().getImage("images/pirateShip00"+i+".png");
		}
		
		backgroundAnimation = new Thread(this);
		backgroundAnimation.start();
		currentFrame = 0;
	}
	
	/**
	 * The run method first iterates the background image frames up from 0 to 3, then back to 0
	 * in looping cycle.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		while(true){
			for(int i = 0; i < frameCount; i++){
				currentFrame = i;
				try {
					Thread.sleep(250);
				} 
				catch (InterruptedException e) {
				}
				repaint();
			}
		}
	}
	
	/**
	 * Simply paints the background image.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
       super.paintComponent(g);
       g.drawImage(backgroundImage[currentFrame],0,0,this);
	}
}
