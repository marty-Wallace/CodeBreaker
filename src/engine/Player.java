package engine;

import codebreaker.CodeBreaker;
import field.Pattern;

public class Player {
	
	private Pattern guess; 
	private Pattern secret; 
	
	public int offences;  // number of times the AI has failed to Respond 
	public BotChat botIO; // the AI's communicator 
	
	private String name;  
	private int games_won;
	private Player opponent; 
	
	
	public Player(BotChat botIO, String name ) {
		 this.botIO = botIO; 
		 this.name = name;
		 this.timeBank = CodeBreaker.MAX_TIMEBANK;
		 this.offences = 0;
	}
	
	public int timeBank; 
	
	/**
	 * Add one to the count of games won
	 */
	public void addVictory() { 
		this.games_won ++ ;
	}
	
	
	public boolean hasWon() {
		if( this.games_won == CodeBreaker.BEST_OF){
			return true; 
		}
		return false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setOpponent(Player p) { 
		this.opponent = p;
	}
	
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
	
	public void setPattern(String moveType) { 
		if(moveType.equals("secret_pattern")){
			this.secret = new Pattern(botIO.botSays); 
		}else if(moveType.equals("guess")){
			this.guess = new Pattern(botIO.botSays);
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
	
	public Pattern getGuess() { 
		return this.guess;
	}
	
	public Pattern getSecretPattern() { 
		return this.secret;
	}
	
}
