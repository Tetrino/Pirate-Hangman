package uk.ac.aber.dcs.jos56.HangmanGUI;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HangmanButtonListener implements ActionListener {

	private GraphicalHangmanPanel coreGraphicalHangman;
	
	/**
	 * This class is the ActionListener for the buttons and input fields of the GUI.
	 */
	public HangmanButtonListener (GraphicalHangmanPanel hangmanPanel){
		this.coreGraphicalHangman = hangmanPanel;
	}
	
	/**
	 * The action listener either submits a letter guess, a word guess, or restarts the game,
	 * based on the button or input that was triggered.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();
		
		if (command.equals("letterGuess")){
			coreGraphicalHangman.guessTheLetter();
			coreGraphicalHangman.setLetterGuessInputContents("");
		}
		
		else if (command.equals("wordGuess")){
			coreGraphicalHangman.guessTheWord();
		}
		
		else if (command.equals("restartGame")){
		
					coreGraphicalHangman.clearWinPage();
					coreGraphicalHangman.resetGUI();
				
		}
	}
}
