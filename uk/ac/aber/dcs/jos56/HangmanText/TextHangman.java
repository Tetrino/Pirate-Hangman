package uk.ac.aber.dcs.jos56.HangmanText;
import java.util.Scanner;

import uk.ac.aber.dcs.jos56.HangmanModel.GameModel;

public class TextHangman {
	
	private char guessedLetter;
	private GameModel hangmanCore;
	private Scanner userInput;
	private String userAnswer;
	private String guessedWord;

	/**
	 * This class is the text portion of the Hangman game, handling the player interaction
	 * on a text level.
	*/
	public TextHangman(){
		System.out.println("Welcome to Pirate Hangman! **Text Edition**");
		hangmanCore = new GameModel();
		userInput = new Scanner(System.in);
		requestUserForGameStart();
		
	}

	/**
	* Asks the user for a simple Y/N input over whether the game is started or not.
	* Whenever a game ends, this method is called. 
	* If the user answers Y, the method calls the reset in the model.
	* If the user answers N, the game is closed.
	* If an incorrect command is given, the method calls itself to ask again.
	*/
	public void requestUserForGameStart(){
		System.out.println("Would you like to start a new game? Y/N");
		userAnswer = userInput.nextLine();
		
		if(userAnswer.toLowerCase().equals("y")){
			hangmanCore.resetGame();
			returnProgressSoFar();
			return;
		}
		
		else if(userAnswer.toLowerCase().equals("n")){
			System.out.print("Thankyou for playing!");
			System.exit(0);
		}
		
		else{
			System.out.print("Sorry, I don't understand that command!");
			requestUserForGameStart();
		}
		
		userInput.close();
	}
	
	/**
	 * Prints the current status of the game and then calls another method for user input.
	 * This is called whenever a game is started, or a guess is made, until the game ends,
	 * at which point the {@link requestUserForGameStart() requestUserForGameStart} method is called.
	 */
	public void returnProgressSoFar(){
		System.out.println("Your word is: "+hangmanCore.getVisible());
		
		if(hangmanCore.checkIfPlayerHasWon()){ //If player has guessed everything correctly.
			System.out.println("Congratulations, you won!");
			requestUserForGameStart();
			return;
		}
		
		if(hangmanCore.guessLeft() == 0){ //If player has run out of guesses.
			System.out.println("Sorry, you lost! The correct answer was \""+hangmanCore.getQuestionWord()+"\"!");
			requestUserForGameStart();
			return;
		}
		
		System.out.println("You have "+hangmanCore.guessLeft()+ " guesses left.");
		
		System.out.println("So far you have guessed: "+hangmanCore.getLetters());
		requestUserForGuessInput();
	}
	
	/**
	 * Asks the user if they wish to guess a letter or a word, based on single letter input.
	 * The answer will dictate whether a method is called to guess a single letter, or a word.
	 * If an incorrect command is given, the method calls itself to ask again.
	 */
	public void requestUserForGuessInput(){
		System.out.println("Would you like to try a (L)etter or the (W)ord?");
		userAnswer = userInput.nextLine();
		
		if(userAnswer.toLowerCase().equals("l")){
			displayLetterGuessQuestion();
			return;
		}
		
		else if(userAnswer.toLowerCase().equals("w")){
			displayWordGuessQuestion();
			return;
		}
		
		else{
			System.out.print("Sorry, I don't understand that command! ");
			requestUserForGuessInput();
		}
	}
	
	/**
	 * Asks the user which letter they'd like to guess, before asking the GameModel hangmanCore if the
	 * letter has already been guessed. If it has, it loops. If not, it calls 
	 * {@link checkIfLetterIsCorrect(char) checkIfLetterIsCorrect}.
	 */
	public void displayLetterGuessQuestion(){
		System.out.println("Guess a letter: ");
		String tempUserLetterInput = userInput.nextLine().toLowerCase();
		
		if (tempUserLetterInput.isEmpty()){ //Checks that the user hasn't simply pressed enter.
			displayLetterGuessQuestion(); //If they have ask again.
			return;
		}
		
		guessedLetter = tempUserLetterInput.charAt(0); //Ensures that only the first letter of the input is given.
		
		if (hangmanCore.checkIfLetterAlreadyGuessed(guessedLetter)){ //If the letter has already been guessed ask again.
			System.out.println("That letter has already been guessed!");
			displayLetterGuessQuestion();
			return;
		}
		
		checkIfLetterIsCorrect(guessedLetter);
	}
	
	/**
	 * Asks the user which letter they'd like to guess, before asking the GameModel hangmanCore if the
	 * letter has already been guessed. If it has, it loops. If not, it calls 
	 * {@link #checkIfWordIsCorrect(String) checkIfWordIsCorrect}.
	 */
	public void displayWordGuessQuestion(){
		System.out.println("Guess the word: ");
		guessedWord = userInput.nextLine().toLowerCase();
		
		if (guessedWord.isEmpty()){ //If user simply presses Enter, ask again.
			displayWordGuessQuestion();
			return;
		}
		
		checkIfWordIsCorrect(guessedWord);
	}

	/**
	* Takes the given letter and calls the tryThis() method in the GameModel hangmanCore.
	* If this returns true, a positive result is given, and negative if false.
	* 
	* @param guessedLetter The letter to try.
	*/
	public void checkIfLetterIsCorrect(char guessedLetter){
		if(hangmanCore.tryThis(guessedLetter)){
			System.out.println("That was correct!");
		}
		
		else{
			System.out.println("Sorry, incorrect! You've used a guess!");
		}
		
		returnProgressSoFar();
	}
	
	/**
	 * Takes the given letter and calls the tryThis() method in the GameModel hangmanCore.
	 * If this returns true, a positive result is given, and negative if false.
	 * 
	 * @param guessedWord The word to try.
	 */
	public void checkIfWordIsCorrect(String guessedWord){
		if(hangmanCore.tryWord(guessedWord)){
			System.out.println("That was correct!");
		}
		
		else{
			System.out.println("Sorry, incorrect! You've used five guesses!");
		}
		
		returnProgressSoFar();
	}
}
