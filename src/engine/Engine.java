package engine;

import java.util.ArrayList;

import codebreaker.CodeBreaker;
import field.Pattern;

/**
 * The engine that does most of the work processing games.
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public class Engine implements GameLogic {

	private boolean gameOver;       // flag to represent if a single game is over. Reset for each game 
	private StringBuilder gameDump; // build up dump of all important game IO
	public Player winner;           // set the winner of a single game. Reset for each game 
	private ArrayList<Player> bots; // bots in the game 

	public Engine(ArrayList<Player> bots ) {
		this.bots = bots;
		this.gameDump = new StringBuilder(); 
	}

	/**
	 * Send an update to both bots and append it to the game dump 
	 * @param update -- the update message to be sent 
	 */
	public void sendUpdate(String update ) {
		for(Player bot : bots){
			bot.botIO.sendUpdate("update " + update);
		}
		gameDump.append("update " + update + "\n");
	}

	
	public boolean gameOver() {
		return this.gameOver;
	}

	/**
	 * Plays one whole game. Start by requesting a secret pattern from each player then start round 1. 
	 * Request a guess from each player then send round updates and repeat. 
	 */
	public void playGame() { 
		
		// Run test request for each bot and reset timebanks/offences
		for(Player p : bots) {
			requestMove(p, "test");
			p.timeBank = CodeBreaker.MAX_TIMEBANK;
			p.offences = 0; 
		}
		
		
		this.gameOver = false; 
		
		// requesting secret patterns 
		for(Player p : bots) { 
			requestMove(p, "secret_pattern");
		}

		// set round then enter game loop 
		int round = 1; 
		while( !gameOver ) { 
			sendUpdate("round " + round);
			// requesting moves  
			for(Player p : bots) { 
				requestMove(p, "guess"); 
			}
			// checking game status and sending bots updates 
			updateGame();
			
			gameDump.append("\n");
			
			if( ++round > CodeBreaker.MAX_ROUNDS_PER_GAME) { gameOver = true; }   // games past 20 rounds will be cut off 

		} // game loop over 

		if(winner != null) {
			winner.addVictory();
			sendUpdate("winner " + winner.getName());
		}else{
			sendUpdate("winner tie");
		}

	}

	/**
	 * Update the game and check if each player has won. Send update on how many red and white pegs each player earned. 
	 * Set the game over condition/winner if neccesary
	 */
	public void updateGame() { 
		for(Player p : bots) {
			Pattern guess = p.getGuess();
			int red = guess.howManyRed(p.getOpponent().getSecretPattern().getPattern());
			int white = guess.howManyWhites(p.getOpponent().getSecretPattern().getPattern()) - red;
			sendUpdate(p.getName() + " red " + red);
			sendUpdate(p.getName() + " white " + white);
			if(red == CodeBreaker.COLS){
				this.gameOver = true; 
				if(winner == null) {
					winner = p;
				}else{
					winner = null;
				}
			}
		}
	}


	/**
	 * Notifies the bot communication thread that a move has been requested and waits until a response is recieved 
	 * or the bot uses up all the time in it's timebank. Set's the bots current pattern then sends updates if the 
	 * move type is a "guess" type 
	 * @param bot -- the bot who is recieving the move request
	 * @param moveType -- the moveType being requested. Either "secret_pattern" , "guess", or "test"
	 */
	public void requestMove(Player bot, String moveType) {

		gameDump.append(bot.getName() + " request_move " + moveType + " " + bot.timeBank +"\n");
		
		if(bot.offences < CodeBreaker.MAX_OFFENCES){
			
			bot.botIO.requestMove(moveType, bot.timeBank);

			synchronized(bot.botIO){
				bot.botIO.setPriority(Thread.MAX_PRIORITY);
				bot.botIO.notify();
			}
				
			long startTime = System.currentTimeMillis(); 
			
			while(bot.botIO.botSays == null && (System.currentTimeMillis() - startTime < bot.timeBank )) {

				try { Thread.sleep(1);} catch (InterruptedException e) { }

			}
			
			bot.updateTimeBank((int)(System.currentTimeMillis() - startTime) - 2);
			if(bot.botIO.botSays == null) {
				bot.offences++;
			}
		}
		
		bot.setPattern(moveType);
		bot.botIO.botSays = null; 
		bot.botIO.setPriority(Thread.MIN_PRIORITY);
		
		if(moveType.equals("guess")){
			gameDump.append(bot.getName() + " " + moveType + " " + bot.getGuess() + "\n");
			sendUpdate(bot.getName() + " " + moveType + " " + bot.getGuess());
		}else{
			gameDump.append(bot.getName() + " " + moveType + " " + bot.getSecretPattern() + "\n");

		}
	}



	@Override
	public void sendSetting(String setting) {
		for(Player bot : bots){
			bot.botIO.sendSetting("settings " + setting);
		}
		gameDump.append("settings " + setting + "\n");
	}
	
	/**
	 * Get the games IO dump 
	 *  
	 * @return all of the Important IO throughout the game 
	 */
	public String getDump() { 
		return this.gameDump.toString(); 
	}

	/**
	 * Finish off the BotChat processes and set the winner to the game dump  
	 */
	public void finish() {
		
		for(Player p : bots) {
			
			if(p.hasWon()){
				gameDump.append("game_winner " + p.getName());
			}
			
			p.botIO.finish();
		}
	}
}
