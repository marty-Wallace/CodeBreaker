package codebreaker;

/**
 * All AI's must implement these methods to be able to run on the engine
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public interface BotLogic {

	public void updateSettings(String setting); // send initial game settings to AI's
	
	public void updateGame(String update);  // update AI's on state of game throughout
	
	public String requestMove(String moveType, int timeLimit); // request action from an AI with a set time limit 
	
}
