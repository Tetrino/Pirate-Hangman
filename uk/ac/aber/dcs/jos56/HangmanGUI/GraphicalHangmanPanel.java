package uk.ac.aber.dcs.jos56.HangmanGUI;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.DefaultFormatter;

import uk.ac.aber.dcs.jos56.HangmanModel.GameModel;

public class GraphicalHangmanPanel extends JPanel{

	private GameModel hangmanCore;
	private JButton letterGuessButton;
	private JButton wordGuessButton;
	private JButton gameRestartButton;
	private JLabel currentSituation;
	private JLabel endMessage;
	private JTextField letterGuessInput;
	private JFormattedTextField wordGuessInput;
	private AnimationBoard graphicsDisplay;
	private HangmanButtonListener buttonPress;
	private String guessResponse;
	
	/***
	 * This class deals with the drawing of the core user interface including buttons and
	 * user input boxes.
	 */
	public GraphicalHangmanPanel(){
		hangmanCore = new GameModel();
		buttonPress = new HangmanButtonListener(this);
		graphicsDisplay = new AnimationBoard(this);
		guessResponse = "";
		
		initAnimationBoard();
		addInputElements();
		displayLabels();
		addButtonElements();
		validate();
		repaint();	
	}
	
	/**
	 * This class allows a game reset, creating a new word to be guessed and resetting all the panels.
	 */
	public void resetGUI(){
		this.add(currentSituation);
		this.add(letterGuessInput);
		this.add(wordGuessInput);
		this.add(letterGuessButton);
		this.add(wordGuessButton);
		this.add(graphicsDisplay);
		validate();
		repaint();
		hangmanCore.resetGame();
		graphicsDisplay.resetGameGraphics(this);
		printCurrentSituation();
	}
	
	/***
	 * This class sets the size of the animation window, and adds it to the panel.
	 */
	public void initAnimationBoard(){
		graphicsDisplay.setBounds(0, 120, 500, 300);
		this.add(graphicsDisplay);
	}
	
	/***
	 * This class sets the size and position of the label that displays the game's current status,
	 * then adds it to the panel.
	 */
	public void displayLabels(){		
		currentSituation = new JLabel();
		currentSituation.setBounds(0, 0, 500, 100);
		this.add(currentSituation);
		printCurrentSituation();
	}
	
	/**
	 * This method prints the current game situation to the corresponding label and thus the user.
	 * Then it checks if the player has won or lost. If the former it calls {@link #showEndPage(boolean) showEndPage(true)}
	 * If the latter it calls {@link #AnimationBoard.hasLost(boolean) hasLost()}. 
	 */
	public void printCurrentSituation(){
		String whatsVisible = hangmanCore.getVisible();
		String whatsGuessed = hangmanCore.getLetters();
		int guessesLeft = hangmanCore.guessLeft();
		setWordGuessInputContents(hangmanCore.getVisible());
			
		String situationText = "<html><center>Welcome to Pirate Hangman!<br>The visible word is: "+whatsVisible;
		situationText += "<br>You have guessed: "+whatsGuessed+"<br>You have "+guessesLeft+" guesses left!";
		situationText += "<br>"+ guessResponse + "</center></html>";
			
		currentSituation.setText(situationText);
		currentSituation.setHorizontalAlignment(JLabel.CENTER);
		currentSituation.setVerticalAlignment(JLabel.TOP);
		
		if(hangmanCore.checkIfPlayerHasWon()){
			showEndPage(true);
		}
		else if(guessesLeft == 0){
			graphicsDisplay.hasLost();
		}
	}
		
	/**
	 * This method creates, draws and adds the input fields for the user.
	 * It also sets action listeners for each so that the user can press Enter and send the input.
	 */
	public void addInputElements(){
		letterGuessInput = new JTextField();
		letterGuessInput.setBounds(155, 90, 20, 20);
		letterGuessInput.addActionListener(buttonPress); //In case the user presses Enter, it fires the "Guess!" button listener.
		letterGuessInput.setActionCommand("letterGuess");
		
		DefaultFormatter wordInputFormat = new DefaultFormatter(); //Allows text in Word input to be overwritten
		wordGuessInput = new JFormattedTextField(wordInputFormat); //by typing from the user.
		wordGuessInput.setBounds(195, 90, 150, 20);
		wordGuessInput.addActionListener(buttonPress); //In case the user presses Enter, it fires the "Guess!" button listener.
		wordGuessInput.setActionCommand("wordGuess");		
				
		this.add(letterGuessInput);
		letterGuessInput.requestFocus();
		this.add(wordGuessInput);
	}
	
	/**
	 * This method creates, draws and adds the buttons to display on the UI.
	 * It also adds the respective action listeners.
	 */
	public void addButtonElements(){		
		letterGuessButton = new JButton("Guess a letter?");
		letterGuessButton.setBounds(10, 90, 140, 18);
		letterGuessButton.addActionListener(buttonPress);
		letterGuessButton.setActionCommand("letterGuess");
		
		wordGuessButton = new JButton("Guess the word?");
		wordGuessButton.setBounds(345, 90, 140, 18);
		wordGuessButton.addActionListener(buttonPress);
		wordGuessButton.setActionCommand("wordGuess");
		
		this.add(letterGuessButton);
		this.add(wordGuessButton);
	}
	
	/**
	 * This method sets the default contents of the Letter input field.
	 * It is called to reset the value to blank after each user submission.
	 */
	public void setLetterGuessInputContents(String letterGuessContents){
		letterGuessInput.setText(letterGuessContents);
		letterGuessInput.requestFocus();
	}
	
	/**
	 * This method sets the contents of the Word input field. It is called to match the contents
	 * with the currently known parts of the hidden word.
	 */
	public void setWordGuessInputContents(String wordGuessContents){
		wordGuessInput.setText(wordGuessContents);
	}
	
	/**
	 * This method deals with the creation and adding of components that display either a win, or loss,
	 * end game page, while asking the user if they'd like to restart the game.
	 */
	public void showEndPage(boolean hasWon){
		String endMessageText;
		if(hasWon){
			endMessageText = "<html><center>Congratulations, you won!<br>Your answer was \""+hangmanCore.getVisible()+"\"!<center></html>";
		}
		else{
			endMessageText = "<html><center>Sorry, you lost!<br>Your answer was \""+hangmanCore.getVisible()+"\".";
			endMessageText += "<br>The correct answer was \""+hangmanCore.getQuestionWord()+"\"!";
		}
		
		endMessage = new JLabel(endMessageText);
		endMessage.setBounds(0, 0, 500, 200);
		endMessage.setHorizontalAlignment(JLabel.CENTER);
		
		gameRestartButton = new JButton("New Game!");
		gameRestartButton.addActionListener(buttonPress);
		this.getRootPane().setDefaultButton(gameRestartButton);
		gameRestartButton.requestFocus();
		gameRestartButton.setBounds(175, 200, 150, 30);
		gameRestartButton.setActionCommand("restartGame");
		
		clearInputComponents();		
		this.add(endMessage);
		this.add(gameRestartButton);
	}
	
	/**
	 * This method clears all the gameplay related components to make way for the win/lose screen.
	 */
	public void clearInputComponents(){
		this.remove(currentSituation);
		this.remove(letterGuessInput);
		this.remove(wordGuessInput);
		this.remove(letterGuessButton);
		this.remove(wordGuessButton);
		this.remove(graphicsDisplay);
		validate();
		repaint();
	}
	
	/**
	 * This method clears all the components of the win/lose screen.
	 */
	public void clearWinPage(){
		this.remove(endMessage);
		this.remove(gameRestartButton);
		validate();
		repaint();
	}
	
	/**
	 * This method submits the user input for a guessed letter to the game model, before calling the
	 * {@link #printCurrentSituation() printCurrentSituation} method to report to the user
	 * whether they were correct or not.
	 * 
	 * If the user was incorrect, it also calls the {@link #AnimationBoard.movePirate(int) movePirate()}
	 * method with the int value of 1.
	 */
	public void guessTheLetter(){
		if (letterGuessInput.getText().equals(null)){
			return;
		}
		else{
			
			char guessedLetter = letterGuessInput.getText().toLowerCase().charAt(0);
			if(hangmanCore.checkIfLetterAlreadyGuessed(guessedLetter)){
				guessResponse = "That letter was already guessed!";
			}
			else{
				if (hangmanCore.tryThis(guessedLetter)){
					guessResponse = "Correct guess!";
				}
				else{
					guessResponse = "Incorrect! You walk the plank!";
					graphicsDisplay.movePirate(1);
				}
			}
			
		}
		printCurrentSituation();
	}
	
	/**
	 * This method submits the user input for a guessed letter to the game model, before calling the
	 * {@link #printCurrentSituation() printCurrentSituation} method to report to the user
	 * whether they were correct or not.
	 * 
	 * If the user was incorrect, it also calls the {@link #AnimationBoard.movePirate(int) movePirate()}
	 * method with the int value of 5.
	 */
	public void guessTheWord(){
			String wordGuess = wordGuessInput.getText().toLowerCase();
			if (hangmanCore.tryWord(wordGuess) == false);{
				graphicsDisplay.movePirate(5);
				guessResponse = "Incorrect word!";
			}
			printCurrentSituation();		
	}
}

