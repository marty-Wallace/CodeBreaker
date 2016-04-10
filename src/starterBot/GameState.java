package starterBot;

/**
 * Basic class to store the GameState information and parse the updates and settings sent by the engine
 * 
 * Right now this class only stores the current information of the game state
 * You may want to find a way to store all of information of past rounds to influence your decisions
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
public class GameState {

	private int maxTimebank;      // the maximum number of ms available in your timebank 
	private int timePerMove;      // the time added to your timebank for each move 
	private int bestOf;           // how many games you need to win to win the match 
	private int roundNumber;      // the current round number
	private int gameNumber;       // the current game number 
	private int gamesWon;         // how many games you have currently won
	private int gamesLost;        // how many games your opponent has won 
	private int myRedPegs;        // how many red pegs did your last guess get
	private int myWhitePegs;      // how many white pegs did your last guess get 
	private int oppRedPegs;       // how many red pegs did your opponents last guess get
	private int oppWhitePegs;     // how many white pegs did your opponents last guess get
	private int size;             // the number of marbles in pattern 
	private String my_bot;        // the name of my bot in the game engine, either player_1 or player_2
	private Pattern myLastGuess;  // the pattern I guess last turn
	private Pattern oppLastGuess; // the pattern my opp guesses last turn 

	void updateSettings(String setting) {
		String[] parts = setting.split(" ");

		switch(parts[1]) {

		case "size": 
			this.size = Integer.parseInt(parts[2]);
			break;
		case "best_of": 
			this.bestOf = Integer.parseInt(parts[2]);
			break;
		case "time_per_move":
			this.timePerMove = Integer.parseInt(parts[2]);
			break;
		case "max_timebank":
			this.maxTimebank = Integer.parseInt(parts[2]);
			break;
		case "your_bot": 
			this.my_bot = parts[2];
		}

	}

	void updateGame(String update) {

		String [] parts = update.split(" ");

		switch(parts[1]) {

		case "round": 
			this.roundNumber = Integer.parseInt(parts[2]);
			break;
		case "player_1": 
		case "player_2":
			if(parts[2].equals("red") && parts[1].equals(my_bot)){
				myRedPegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("red")){
				oppRedPegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("white") && parts[1].equals(my_bot)){
				myWhitePegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("white")){
				oppWhitePegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("games_won") && parts[1].equals(my_bot)){
				gamesWon =  Integer.parseInt(parts[3]);
			}else if( parts[2].equals("games_won")) {
				gamesLost = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("guess") && parts[1].equals(my_bot)) {
				myLastGuess = new Pattern(parts[3]);
			}else if(parts[2].equals("guess")) {
				oppLastGuess = new Pattern(parts[3]);
			}
		}
	}

	public int getMaxTimebank() {
		return maxTimebank;
	}

	public int getTimePerMove() {
		return timePerMove;
	}

	public int getBestOf() {
		return bestOf;
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	public int getGameNumber() {
		return gameNumber;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getGamesLost() {
		return gamesLost;
	}

	public int getMyRedPegs() {
		return myRedPegs;
	}

	public int getMyWhitePegs() {
		return myWhitePegs;
	}

	public int getOppRedPegs() {
		return oppRedPegs;
	}

	public int getOppWhitePegs() {
		return oppWhitePegs;
	}

	public int getSize() {
		return size;
	}

	public String getMy_bot() {
		return my_bot;
	}

	public Pattern getMyLastGuess() {
		return myLastGuess;
	}

	public Pattern getOppLastGuess() {
		return oppLastGuess;
	}


}
