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
	private ArrayList<Player> bots;

	public Engine(ArrayList<Player> bots ) {
		this.bots = bots;
		this.gameDump = new StringBuilder(); 
	}

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
		
		this.gameOver = false; 
		
		// requesting secret patterns 
		for(Player p : bots) { 
			requestMove(p, "secret_pattern");
		}

		int round = 1; 
		while( !gameOver ) { 
			sendUpdate("round " + round);
			///// requesting moves ///// 
			for(Player p : bots) { 
				requestMove(p, "guess"); 
			}
			//// checking game status and sending bots updates ////
			updateGame();
			
			if( ++round > 10) { gameOver = true; }   // games past 50 rounds will be cut off 

		} // game loop over 

		if(winner != null) {
			winner.addVictory();
			sendUpdate("winner " + winner.getName());
		}else{
			sendUpdate("winner tie");
		}

	}

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

	public void requestMove(Player bot, String moveType) {

		gameDump.append(bot.getName() + " request_move " + moveType + " " + bot.timeBank +"\n");
		
		if(bot.offences < CodeBreaker.MAX_OFFENCES){
			
			bot.botIO.requestMove(moveType, bot.timeBank);
			
			synchronized(bot.botIO){
				bot.botIO.notify();
			}
			
			long startTime = System.currentTimeMillis(); 
			
			while(bot.botIO.botSays == null && (startTime - System.currentTimeMillis() < bot.timeBank )) {

				try { Thread.sleep(1);} catch (InterruptedException e) { }

			}
			
			bot.updateTimeBank((int)(startTime - System.currentTimeMillis()));
			if(bot.botIO.botSays == null) {
				bot.offences++;
			}
			

		}
		
		bot.setPattern(moveType);
		bot.botIO.botSays = null; 
		
		if(moveType.equals("guess")){
			gameDump.append(bot.getName() + " " + moveType + " " + bot.getGuess() + "\n");

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
