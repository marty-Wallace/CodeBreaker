package codebreaker;

import java.util.ArrayList;

import engine.Engine;
import engine.Player;

/**
 * Contains the main game loop that runs until the maximum amount is met or a player wins the required amount of games.
 * 
 * @author Martin Wallace
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public abstract class Game  {
	
	protected ArrayList<Player> bots;
	protected Engine engine;

	/**
	 * Main game loop 
	 */
	public void run(){
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		
		int gameNum = 0;
		while(!this.gameOver()) {
			engine.sendUpdate("game " + gameNum);
			for(Player p : bots) { 
				engine.sendUpdate(p.status());
			}
			engine.playGame();
			
			if( gameNum++  > CodeBreaker.MAX_GAMES){ 
				break;   
			}
			
		}
		
		saveGame();
		
		engine.finish();
	}
	
	
	/** 
	 * Save the game to a file or database or print out for testing 
	 */
	public void saveGame() {
		
		// write to file if you want to save 
		
		String gameDump = engine.getDump();
		
		System.out.println(gameDump);
		
	}
	
	/**
	 * Check if either player has met the win conditions 
	 * @return -- true if game is over 
	 */
	public boolean gameOver() { 
		for(Player p : bots ) { 
			if(p.hasWon()) { 
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * Set each players opponents 
	 */
	protected void setOpponents() { 
		Player bot1 = bots.get(0);
		Player bot2 = bots.get(1);
		bot1.setOpponent(bot2);
		bot2.setOpponent(bot1);
	}
	
	protected abstract void setupGame();

}
