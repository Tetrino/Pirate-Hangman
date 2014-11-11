package uk.ac.aber.dcs.jos56.HangmanGUI;
import java.awt.BorderLayout;


public class GraphicalHangmanLaunch extends GraphicalHangmanFrame {

	private GraphicalHangmanPanel graphicalHangman;
	
	/**
	 * This class creates the JPanel that the entire UI is drawn within.
	 */
	public GraphicalHangmanLaunch(){
		graphicalHangman = new GraphicalHangmanPanel();
		graphicalHangman.setLayout(null);
		add(graphicalHangman,BorderLayout.CENTER);
		displayFrame();
	}
}
