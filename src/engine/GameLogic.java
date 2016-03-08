package engine;

/**
 * Logic for the Engine 
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public interface GameLogic {
	
	public void sendSetting(String setting);              // send initial game settings to both AI's 
	public void sendUpdate(String update);    	          // send game updates to both AI's
	public void playGame();                               // plays a single game 
	public void requestMove(Player bot, String moveType); // request a move from a player 
	public boolean gameOver();                            // checks to see if either player has attained the required number of games

}
