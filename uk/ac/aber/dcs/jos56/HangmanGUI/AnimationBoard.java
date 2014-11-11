package uk.ac.aber.dcs.jos56.HangmanGUI;
import javax.swing.JLayeredPane;

import uk.ac.aber.dcs.jos56.HangmanImages.ImagePanel;
import uk.ac.aber.dcs.jos56.HangmanImages.PirateShipPanel;

import java.awt.Graphics;


public class AnimationBoard extends JLayeredPane implements Runnable{
	
	private int xPos;
	private int yPos;
	private int multiplier;
	private boolean guessError;
	private boolean hasLost;
	private GraphicalHangmanPanel mainMenuPanel;
	private PirateShipPanel pirateShipPanel;
	private ImagePanel pirateManPanel;
	private ImagePanel oceanForegroundPanel;
	private Thread thisThread;

	/**
	 * This class holds the logic for drawing the images of the program, as well
	 * as the logic for moving the pirate character along the plank.
	 * 
	 * @param mainMenuPanel This allows the linking of the menu logic to this class.
	 */
	public AnimationBoard(GraphicalHangmanPanel mainMenuPanel){
		
		this.mainMenuPanel = mainMenuPanel;
		
		guessError = false;
		hasLost = false;
		xPos = 150;
		yPos = 150;
		
		pirateShipPanel = new PirateShipPanel(4);
		pirateShipPanel.setBounds(0, 0, 500, 300);
		
		pirateManPanel = new ImagePanel("images/pirateGuy.png");
		setPiratePosition(xPos, yPos);
		
		oceanForegroundPanel = new ImagePanel("images/oceanForeground.png");
		oceanForegroundPanel.setBounds(0, 0, 500, 300);
		
		this.add(pirateShipPanel, JLayeredPane.DEFAULT_LAYER);
		this.add(pirateManPanel, JLayeredPane.PALETTE_LAYER);
		this.add(oceanForegroundPanel, JLayeredPane.MODAL_LAYER);
		
		thisThread = new Thread(this);
		thisThread.start();
	}
	
	/**
	 * This method resets the position of the pirate on the plank during a game reset.
	 * It also reinitialises the link between this and the graphical menu.
	 * 
	 * @param mainMenuPanel This allows the linking of the menu logic to this class.
	 */
	public void resetGameGraphics(GraphicalHangmanPanel mainMenuPanel){
		this.mainMenuPanel = mainMenuPanel;
		setPiratePosition(150, 150);
	}
	
	/**
	 * The run method of this class first checks if the player has lost. If it has, it calls the end animation
	 * method then stops the thread. If it hasn't lost but has made an error, it calls the error animation
	 * then stops the thread. Else, it does nothing and loops.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run(){
		while(true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}
			if (hasLost == true){
				showEndAnimation();
			}
			else if (guessError == true){
				showErrorAnimation();
			}
			guessError = false;
		}
	}
	
	/**
	 * This animates the pirate image to leap off the plank. First it moves the JPanel containing the
	 * image across the X axis until a certain point, then down the Y axis. It repaints every step,
	 * and sleeps 200ms.
	 * 
	 * The method finishes by setting the boolean that tells the thread if the player has lost the game
	 * or not to "false" as a reset.
	 * 
	 * It then calls the {@link #GraphicalHangmanPanel.showEndPage(boolean) showEndPage} method of the main menu.
	 */
	public void showEndAnimation(){
		for (int i = xPos; i < 354; i+=17){
			xPos = i;
			pirateManPanel.setLocation(xPos, yPos);
			if (xPos >= 337){
				for (; yPos < 300; yPos +=10){
					pirateManPanel.setLocation(xPos, yPos);
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}
			}
			repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		hasLost = false;
		mainMenuPanel.showEndPage(false);
	}
	
	/**
	 * This method iterates the JPanel that includes the pirate image one "step" along the X axis.
	 */
	public void showErrorAnimation(){
		for (int i = 0; i < multiplier; i++){
			xPos += 17;
			pirateManPanel.setLocation(xPos, yPos);
			repaint();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}	
	}
	
	/**
	 * This paints the JPanel.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		repaint();
	}
	
	/**
	 * Used to setup the initial JPanel location and size for the pirateMan ImagePanel.
	 */
	public void setPiratePosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		pirateManPanel.setBounds(xPos, yPos, 64, 64);
	}
	
	/**
	 * Tells the AnimationBoard when the user has made a wrong guess.
	 * 
	 * @param multiplier Used to set how many steps the pirate will take along the plank.
	 */
	public boolean movePirate(int multiplier){
		this.multiplier = multiplier;
		guessError = true;
		return guessError;
	}
	
	/**
	 * Tells the AnimationBoard that the player has lost.
	 */
	public boolean hasLost(){
		hasLost = true;
		return hasLost;
	}
}
