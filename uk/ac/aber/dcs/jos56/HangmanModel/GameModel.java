package uk.ac.aber.dcs.jos56.HangmanModel;
import java.util.Random;

public class GameModel implements GameModelInterface{
	private String[] pirateWords;
	private char[] currentGuessedLetters;
	private boolean correctWordGuess;
	private int numberOfGuesses;
	private Random randomPirateWord;
	private String visibleWord;
	private String questionWord;
	
	/**
	 * This class handles the "heavy lifting" of word logic for the game.
	 * The constructor creates a char[] for guessed letters, then loads the needed
	 * words to a String[] via the {@link <GameMasterFileLoad>#<GameMasterFileLoad()>} class.
	 */
	public GameModel(){
		GameMasterFileLoad loadFile = new GameMasterFileLoad();
		setPirateWords(loadFile.getPirateWords());
		resetGame();
	}
	
	/**
	 * This class resets the game for a new word.
	 */
	public void resetGame(){
		getHidden();
		setVisible();
		setNumberOfGuesses(10);
		currentGuessedLetters = new char[26]; //More values than needed to help avoid overflow at minimal memory cost.
	}

	/**
	 * Adds the given char to a char[], designed to keep track of what letters have been guessed.
	 * 
	 * @param guessedLetter The letter to guess (char).
	 */
	public void addNewGuessedLetterToArray(char guessedLetter){
		for(int i = 0; i < currentGuessedLetters.length; i++){
			if (currentGuessedLetters[i] == 0){
				currentGuessedLetters[i] = guessedLetter;
				return;
			}
		}
	}
	
	/**
	 * Takes the given char and checks to see if it is contained within the char[]
	 * of already guessed letters. 
	 * 
	 * @param guessedLetter The letter to check (char)
	 * @return isLetterGuessedAlready Where the letter is guessed or not.
	 */
	public boolean checkIfLetterAlreadyGuessed(char guessedLetter){
		boolean isLetterGuessedAlready = false;
		
		for (int i: currentGuessedLetters){
			if (guessedLetter == i){
				isLetterGuessedAlready = true;
				return isLetterGuessedAlready;
			}
		}
		
		return isLetterGuessedAlready;
	}
	
	/** 
	* This method returns what the user is allowed to see. 
	*/ 
	public String getVisible(){
		return visibleWord;
	}
	
	/**
	 * This method is used to replace the visible characters of the question word with asterisks
	 * for display to the user via getVisible().
	 */
	public void setVisible(){
		visibleWord = questionWord;
		
		for (int i = 0; i < questionWord.length(); i++){
			char tempCharForReplacing = 42;
			if (questionWord.charAt(i) != 32){
				visibleWord = visibleWord.replace(visibleWord.charAt(i), tempCharForReplacing);
			}
		}
	}
	
	/**
	 * This method gets the word to guess from the String array of pirate words.
	 * 
	 * @return The word to guess.
	 */
	public String getHidden(){
		randomPirateWord = new Random();
		int pirateWordIndex = randomPirateWord.nextInt(pirateWords.length);
		questionWord = pirateWords[pirateWordIndex];
		return questionWord;
	} 
	 
	/** 
	* This method tells the user how many guesses are left.
	* If the number of guesses is less than zero, it sets it to zero.
	* 
	* @return Number of guesses left.
	*/ 
	public int guessLeft(){
		if (numberOfGuesses < 0){
			numberOfGuesses = 0;
		}
		return numberOfGuesses;
	}
	 
	/** 
	* This method returns the letters that have already been guessed. 
	*/ 
	public String getLetters(){
		String compileCurrentGuessedLetters = "";
		
		for(int i = 0; i < currentGuessedLetters.length; i++){
			if (currentGuessedLetters[i] != 0){
				compileCurrentGuessedLetters += currentGuessedLetters[i] + ", ";
			}
		}
		
		return compileCurrentGuessedLetters;
	}
	 
	/** 
	* This method sees if the letter is in the words.
	* If it is it updates what the user can see (visible). 
	* If not it removes a guess. 
	* 
	* @param letter the letter to try 
	* @return correctLetterGuess whether there is winner 
	*/ 
	public boolean tryThis(char letter){
		boolean correctLetterGuess = false;
		
		if (questionWord.contains(String.valueOf(letter))){ //If it's not in there, don't do the statement.
			for (int i = 0; i < questionWord.length(); i++){
				if (questionWord.charAt(i) == letter){
					StringBuilder helpSetTheVisible = new StringBuilder(visibleWord);
					helpSetTheVisible.setCharAt(i, letter);
					visibleWord = helpSetTheVisible.toString();
				}
			}
			correctLetterGuess = true;
			addNewGuessedLetterToArray(letter);
			return correctLetterGuess;
		}
		
		addNewGuessedLetterToArray(letter);
		numberOfGuesses--;
		return correctLetterGuess;
	}
	 
	/** 
	* This method sees if the word guess is correct.
	* If it is it updates what the user can see (visible). 
	* If not it removes 5 guesses. 
	* 
	* @param guess the word to guess 
	* @return correctWordGuess whether there is winner 
	*/ 
	public boolean tryWord(String guess){
		correctWordGuess = false;
		
		if (questionWord.equals(guess.toLowerCase())){
			visibleWord = questionWord;
			correctWordGuess = true;
		}
		
		else{
			numberOfGuesses -= 5;
		}
		
		return correctWordGuess;
	}
	
	/**
	 * This method checks to see if the currently visible letters match the question string,
	 * which means the player has won the game.
	 * 
	 * @return hasPlayerWonTheGame Returns true if the visibleWord and questionWord match.
	 */
	public boolean checkIfPlayerHasWon(){
		boolean hasPlayerWonTheGame = false;
		
		if (visibleWord.equals(questionWord)){
			hasPlayerWonTheGame = true;
		}
		
		return hasPlayerWonTheGame;
	}
	
	/**
	 * Used to display the correct word if the player loses the game.
	 * 
	 * @return questionWord The correct word.
	 */
	public String getQuestionWord(){
		return questionWord;
	}
	
	/**
	 * Sets the question word.
	 * 
	 * @param questionWord
	 */
	public void setQuestionWord(String questionWord){
		this.questionWord = questionWord;
	}
	
	/**
	 * Returns the already guessed letters.
	 * 
	 * @return currentGuessedLetters
	 */
	public char[] getCurrentGuessedLetters() {
		return currentGuessedLetters;
	}
		
	/**
	 * Sets the number of guesses available to the user.
	 */
	public void setNumberOfGuesses(int numberOfGuesses) {
		this.numberOfGuesses = numberOfGuesses;
	}
	
	/**
	 * Sets the contents of the String array of pirate words.
	 */
	public void setPirateWords(String[] pirateWords) {
		this.pirateWords = pirateWords;
	}
	
	/**
	 * Returns the stored words for testing.
	 * 
	 * @return pirateWords The array of pirate words.
	 */
	public String[] getPirateWords(){
		return pirateWords;
	}

}
