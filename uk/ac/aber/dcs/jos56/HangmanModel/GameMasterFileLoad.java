package uk.ac.aber.dcs.jos56.HangmanModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GameMasterFileLoad {
	
	private Scanner inputFileRead;
	private String[] pirateWords;
	
	/**
	 * This class loads in the word list to be used by the game.
	 */
	public GameMasterFileLoad(){
		loadWordDataFromFile();
	}

	/**
	* Loads the words to be used for the game from a hard coded file into a String array.
	* If the file is missing it asks the user to make sure it exists in the main folder.
	* If there is any other error loading the file, it tells the user the file could be corrupt.
	* In both error cases it shuts down the program.
	*/
	public void loadWordDataFromFile(){
		try{
		inputFileRead = new Scanner(new InputStreamReader(new FileInputStream("piratewords.txt")));
		}
		catch (IOException e){
			System.out.println("The system couldn't find \"piratewords.txt\". Please make sure it's in the "
					+ "program's root folder!\nGame closed.");
			System.exit(1);
		}
		try{
		int wordCount = inputFileRead.nextInt(); inputFileRead.nextLine();
		pirateWords = new String[wordCount];
		for (int i = 0;i < wordCount;i++)
		{
			String fileWord = inputFileRead.nextLine().toLowerCase();
			pirateWords[i] = fileWord;			
		}
		inputFileRead.close();
		}
		catch (Exception a){ //So many things can go wrong with the file that this is the best option.
			System.out.println("There was an error loading the file! Possibly corrupt?\nGame closed.");
			System.exit(1);
		}
	}

	public String[] getPirateWords() {
		return pirateWords;
	}
}
