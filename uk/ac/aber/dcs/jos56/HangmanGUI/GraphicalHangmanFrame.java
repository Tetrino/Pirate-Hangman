package uk.ac.aber.dcs.jos56.HangmanGUI;
import javax.swing.JFrame;;

public class GraphicalHangmanFrame extends JFrame {
	
	/**
	 * This class sets up the core window for the program.
	 * The window is 300px², spawns in the centre of the screen, and cannot be resized.
	 */
	public GraphicalHangmanFrame(){
		this.setSize(500, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null); //Opens the frame in the middle of the screen.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Displays the frame with the title "Pirate Hangman!"
	 */
	public void displayFrame(){
		this.setTitle("Pirate Hangman!");
		this.setVisible(true);
	}
}
