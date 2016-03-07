package starter;


/**
 * Basic class to store the GameState information 
 * 
 * @author Martin Wallace 
 * <p> Martin.V.Wallace@ieee.org 
 *
 */
class GameState {


	int size;            // the number of marbles in pattern 
	int max_timebank;    // the maximum number of ms available in your timebank 
	int time_per_move;   // the time added to your timebank for each move 
	int best_of;         // how many games you need to win to win the match 
	int round_number;    // the current round number
	int game_number;     // the current game number 
	int games_won;       // how many games you have currently won
	int games_lost;      // how many games your opponent has won 
	int my_red_pegs;     // how many red pegs did your last guess get
	int my_white_pegs;   // how many white pegs did your last guess get 
	int opp_red_pegs;    // how many red pegs did your opponents last guess get
	int opp_white_pegs;  // how many white pegs did your opponents last guess get

	String my_bot;
	
	GameState() {


	}

	void updateSettings(String setting) {
		String[] parts = setting.split(" ");

		switch(parts[1]) {

		case "size": 
			this.size = Integer.parseInt(parts[2]);
			break;
		case "best_of": 
			this.best_of = Integer.parseInt(parts[2]);
			break;
		case "time_per_move":
			this.time_per_move = Integer.parseInt(parts[2]);
			break;
		case "max_timebank":
			this.max_timebank = Integer.parseInt(parts[2]);
			break;
		case "your_bot": 
			this.my_bot = parts[2];
		}

	}

	void updateGame(String update) {

		String [] parts = update.split(" ");

		switch(parts[1]) {

		case "round": 
			this.round_number = Integer.parseInt(parts[2]);
			break;
		case "player_1": 
		case "player_2":
			if(parts[2].equals("red") && parts[1].equals(my_bot)){
				my_red_pegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("red")){
				opp_red_pegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("white") && parts[1].equals(my_bot)){
				my_white_pegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("white")){
				opp_white_pegs = Integer.parseInt(parts[3]);
			}else if(parts[2].equals("games_won") && parts[1].equals(my_bot)){
				games_won = Integer.parseInt(parts[3]);
			}else if( parts[2].equals("games_won")) {
				games_lost = Integer.parseInt(parts[3]);
			}
		}
	}


	int getSize() {
		return this.size;
	}


}
