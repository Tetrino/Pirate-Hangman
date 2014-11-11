package uk.ac.aber.dcs.jos56.HangmanTests;
import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.aber.dcs.jos56.HangmanModel.GameModel;


public class GameModelTest {
	
	/**
	 * This tests that a new game is generated correctly.
	 */
	@Test
	public void testNewGameGeneration(){
		GameModel testFish = new GameModel();
		String firstWord = testFish.getQuestionWord();
		
		testFish.resetGame(); //Should generate a new game.
		
		String secondWord = testFish.getQuestionWord();
		
		assertNotEquals("Game didn't reset!", firstWord, secondWord);
	}
	
	/**
	 * This tests to check that the file is loaded into the model correctly.
	 */
	@Test
	public void testFileLoadedCorrectly(){
		GameModel testFish = new GameModel();
		String[] thePirateLibrary = testFish.getPirateWords();
		String shouldBe = "yo ho ho";
		
		String lastWord = thePirateLibrary[37].toString();
		
		assertEquals("Some of the file didn't load!", shouldBe, lastWord);
	}
	
	/**
	 * This checks that the method that hides the words from the user is working correctly.
	 */
	@Test
	public void testConversionToVisibleWord(){
		GameModel testFish = new GameModel();
		String shouldBe = "**** ****";
		
		testFish.setQuestionWord("fish cake");
		testFish.setVisible();
		
		String actuallyIs = testFish.getVisible();
		
		assertEquals("The word wasn't converted!", shouldBe, actuallyIs);
	}
	
	/**
	 * This tests that the check for letters that have already been entered is working correctly.
	 */
	@Test
	public void testIfLetterAlreadyEntered(){
		GameModel testFish = new GameModel();
		
		testFish.tryThis("a".charAt(0));
		
		Boolean isThere = testFish.checkIfLetterAlreadyGuessed("a".charAt(0));
		
		assertTrue(isThere);
	}
	
	/**
	 * This tests that the method that tries letters is working correctly.
	 */
	@Test
	public void testCorrectLetterGuess(){
		GameModel testFish = new GameModel();
		
		String shouldBe = "f*** ***e";
		
		testFish.setQuestionWord("fish cake"); //To give a known word to guess to.
		testFish.setVisible();
		
		testFish.tryThis("f".charAt(0)); //First character in the correct word.
		testFish.tryThis(" ".charAt(0)); //Middle character in the correct word.
		testFish.tryThis("e".charAt(0)); //Final character in the correct word.
		
		String actuallyIs = testFish.getVisible();
		
		assertEquals("The letter wasn't added!", shouldBe, actuallyIs);
	}
	
	/**
	 * This tests that the model handles a correct word guess correctly.
	 */
	@Test
	public void testCorrectWordGuess(){
		
		GameModel testFish = new GameModel();
		
		testFish.setQuestionWord("fish cake");
		testFish.setVisible();
		
		assertTrue(testFish.tryWord("fish cake"));
	}
	
	/**
	 * This tests that an incorrect letter guess is handled correctly.
	 */
	@Test
	public void testIncorrectLetterGuess(){
		GameModel testFish = new GameModel();
		
		testFish.tryThis("1".charAt(0)); //Numbers will always be incorrect!
		testFish.tryThis("2".charAt(0));
		testFish.tryThis("3".charAt(0));
		
		int targetGuessesLeft = 7;
		int actualGuessesLeft = testFish.guessLeft();
		
		assertEquals(targetGuessesLeft, actualGuessesLeft);
	}
	
	/**
	 * This tests that an incorrect word guess is handled correctly.
	 */
	@Test
	public void testIncorrectWordGuess(){
		GameModel testFish = new GameModel();
		
		testFish.tryWord("notThis");
		testFish.tryWord("notThat");
		
		int targetGuessesLeft = 0;
		int actualGuessesLeft = testFish.guessLeft();
		
		assertEquals(targetGuessesLeft, actualGuessesLeft);
	}
}
