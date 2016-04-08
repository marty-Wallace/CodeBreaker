package engine;

import codebreaker.CodeBreaker;
import field.Pattern;

/**
 * Class to hold all the game related information about a bot 
 * 
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class Player {
	
	public int timeBank;      // the amount of time this player has to respond to move requests 
	
	private Pattern guess;    // this players last guessed pattern
	private Pattern secret;   // this players secret pattern
	
	public int offences;      // number of times the AI has failed to Respond 
	public BotChat botIO;     // the AI's communicator 
	
	private String name;      // bot name 
	private int games_won;    // number of game victories so far 
	private Player opponent;  // the other bot 

	
	/**
	 * Constructor for Player
	 * @param botIO -- The communication channel for the engine with the bot 
	 * @param name  -- The name of the bot 
	 */
	public Player(BotChat botIO, String name ) {
		 this.botIO = botIO; 
		 this.name = name;
		 this.timeBank = CodeBreaker.MAX_TIMEBANK;
		 this.offences = 0;
	}
	

	
	/**
	 * Add one to the count of games won
	 */
	public void addVictory() { 
		this.games_won ++ ;
	}
	
	/**
	 * Checks to see if this player has won as many games as the Constant BEST_OF in the CodeBreaker class 
	 * @return
	 */
	public boolean hasWon() {
		if( this.games_won == CodeBreaker.BEST_OF){
			return true; 
		}
		return false;
	}
	
	/**
	 * Get the name of this bot 
	 * @return -- name 
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set this players opponent for easy switching in the engine
	 * @param opponent -- other bot 
	 */
	public void setOpponent(Player opponent) { 
		this.opponent = opponent;
	}
	
	/**
	 * Get the opponent of this bot 
	 * @return opponent -- other bot 
	 */
	public Player getOpponent() {
		return this.opponent;
	}
	
	
	/**
	 * Subtracts the time taken from the last move from the timebank and adds the time for the next move to the timebank
	 * @param timeTaken - time taken from last move 
	 */
	public void updateTimeBank(int timeTaken) {
		this.timeBank = Math.max(0, this.timeBank - timeTaken);
		this.timeBank = Math.min(this.timeBank + CodeBreaker.TIME_PER_MOVE, CodeBreaker.MAX_TIMEBANK);
		
	}
	
	/**
	 * Takes the most recent value returned by the bot to the BotChat and sets that to be the pattern 
	 * for the appropriate movetype 
	 * 
	 * @param moveType -- The type of move, either "secret_pattern" or "guess"
	 */
	public void setPattern(String moveType) { 
		if(moveType.equals("secret_pattern")){
			if(botIO.botSays != null){
				this.secret = new Pattern(botIO.botSays); 
			}else {
				this.secret = new Pattern();
			} 
		}else if(moveType.equals("guess")){
			if(botIO.botSays != null){
				this.guess = new Pattern(botIO.botSays);
			}else {
				this.guess = new Pattern();
			}
			
		}else{
			System.err.println("Improper move type " + moveType);
		}
	}
	
	/**
	 * Get the name of the bot and the number of games it has won 
	 * @return
	 */
	public String status() {
		return this.name + " games_won " + this.games_won;
	}
	
	/**
	 * Get this players current guess pattern 
	 * @return -- the most recent guess pattern of this player 
	 */
	public Pattern getGuess() { 
		return this.guess;
	}
	
	/**
	 * Get this players current secret pattern 
	 * @return -- the most recent secret pattern of this player 
	 */
	public Pattern getSecretPattern() { 
		return this.secret;
	}
	
}
