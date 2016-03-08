package codebreaker;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;

import engine.BotChat;
import engine.Engine;
import engine.Player;
import starter.SDBot;

/**
 * CodeBreaker class contains constants used throughout the game engine. Adjust them for testing if needed. 
 * Sets the bots that are playing and sends initial settings then starts up engine 
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org
 *
 */
public class CodeBreaker extends Game {
	
	public static final int COLS = 5;             // the number of marbles in a pattern 
	public static final int MAX_GAMES = 2;        // the maximum number of games that can be played before the engine exits 
	public static final int BEST_OF = 1;          // the number of games a player needs to win to win the entire match 
	public static final int TIME_PER_MOVE = 50;   // maximum number of milliseconds an AI  can take to make a move 
	public static final int MAX_TIMEBANK = 5000;  // maximum amount of milliseconds an AI can have in its time bank 
	public static final int MAX_OFFENCES = 2;     // maximum number of times an AI is allowed to not respond or go over its time limit
	
	public static void main(String[] args) {
		
		CodeBreaker game = new CodeBreaker();
		game.setupGame();
		game.run();
		
		System.exit(0);
	}
	
	private void setupGame(){
		
		////////////////////////////Adjust this to change which bots are playing the game////////////////////////////
		BotLogic bot1 = new RNBot();     
		BotLogic bot2 = new RNBot();     
		///////////////////////////////Or leave as RNBot for a random opponent///////////////////////////////////////
		
		super.bots =  new ArrayList<Player>();
		
		// adding bots to their communication threads 
		BotChat botchat1 = new BotChat(bot1);
		BotChat botchat2 = new BotChat(bot2);
		
		// set thread name to identify bots who throw exceptions 
		botchat1.setName("player_1"); 
		botchat2.setName("player_2");
		 
		// set exception handlers for player 1 communication thread
		botchat1.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.printf("Exception thrown by %s \n",t.getName());
				e.printStackTrace();
			}
		});
		
		// set exception handlers for player 2 communication thread 
		botchat2.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.printf("Exception thrown by %s \n",t.getName());
				e.printStackTrace();
			}
		});
		
		// add BotChats to their players 
		bots.add(new Player(botchat1, "player_1"));
		bots.add(new Player(botchat2, "player_2"));
		setOpponents();
		
		// start threads and send player names 
		for(Player p : bots) { 
			p.botIO.start();
			p.botIO.sendSetting("settings your_bot " + p.getName());
		}
		
		
		// add bots to engine and send settings 
		super.engine = new Engine(this.bots);
		engine.sendSetting("size " + COLS);
		engine.sendSetting("best_of " + BEST_OF);
		engine.sendSetting("time_per_move " + TIME_PER_MOVE);
		engine.sendSetting("max_timebank " + MAX_TIMEBANK);
	}
	
	/**
	 * Set each players opponents 
	 */
	public void setOpponents() { 
		Player bot1 = bots.get(0);
		Player bot2 = bots.get(1);
		bot1.setOpponent(bot2);
		bot2.setOpponent(bot1);
	}
	
}
