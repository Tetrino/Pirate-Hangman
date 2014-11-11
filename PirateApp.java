import uk.ac.aber.dcs.jos56.HangmanGUI.GraphicalHangmanLaunch;
import uk.ac.aber.dcs.jos56.HangmanText.TextHangman;

public class PirateApp {

	/**
	 * 
	 * @param args Argument -t to open text version of program.
	 * @throws IOException See {@link GameMasterFileLoad.loadWordDataFromFile() GameMasterFileLoad.loadWordDataFromFile}.
	 */
	public static void main(String[] args){
		if (args.length != 0){
			if (args[0].equals("-t")){
			TextHangman mainApplication = new TextHangman();
			}
			else
			{
			GraphicalHangmanLaunch mainApplication = new GraphicalHangmanLaunch();
			}
		}
		else{
			GraphicalHangmanLaunch mainApplication = new GraphicalHangmanLaunch();
		}
	}
}
