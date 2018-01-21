package darts;

/**
 *  @author Siqian WU 2243179
 *  @author Zhengnan NIU 2493806
 *  @author Luyao LI 2393951
 *  20. Januar 2018
 *
 * this is a subclass of Darts for modeling the game mode "Tactics"
 * 
 */
public class Tactics extends Darts{
	//private attribute for "Treffer" array
	// The first dimension is Aktivspielernummer, the active player that is playing darts
	private int[][] Spielertreffer;
	
	/**
	 * Constructor of the class DoubleOut
	 * @param max_playernumber: the maximal number of the players
	 */
	public Tactics (int max_playernumber) {
		super("Tactics", max_playernumber);
		
		//initialize the attributes
		Spielertreffer = new int[max_playernumber][12];
		Running = false;
		uebrigedarts = 3;
		Aktivspielernummer = 0;
	}
	
	/**
	 * this method initializes the "Treffer" array that records all the "Treffer" of each player
	 */
	public void initialize() {
		for (int i=0; i < getPlayerCount(); i++) {
			for (int j=0; j < 12; j++) {
				Spielertreffer[i][j]=0;
			}
		}
	}
	
	/**
	 * Getter - Method for the "Treffer" array that records all the "Treffer"(including 10-20 and bull) of each player
	 * @return the 2-dimension array 
	 */
	public int[][] getScore() {
		return Spielertreffer;
	}
	
	/**
	 * return true if the game is already over
	 * @return true if the game is over, else false
	 */

	public boolean isOver() {		
		for (int i=0; i<getPlayerCount(); i++) {
			int counter = 0;
			for (int j=0; j<12; j++) {
				if (Spielertreffer[i][j] >= 3) {
					//If 1 field is >= 3 then counter + 1, so if counter = 12 then every
					// field is >= 3
					counter++;
				}
			}
			//To check whether every field is >= 3
			if (counter==12) {
				//then this player will be the winner, and the game is over
				Gewinner = getPlayers()[i];
				Running = false;
				return true;
			}
		}
		//or else return false
		return false;
	}
	
	/**
	 * this method turns to the next player
	 */
	public void nextplayer() {
		Aktivspielernummer += 1;
		if (Aktivspielernummer>=getPlayerCount()) {
			Aktivspielernummer = 0;
		}
		uebrigedarts = 3;
	}
	
	/**
	 * starts the game
	 * @return true, if the game was started
	 */
	public boolean start() {
		//if the current number of players is less than the maximum
		if (getPlayerCount() < getMaxplayernumber()) {
			// Then adjust the first dimension to fit the player number
			int num = getPlayerCount();
			int[][] treffer = new int[num][12];
			Spielertreffer = treffer;
			initialize();
		}
		//if the game was already started
		if (Running) {
			System.out.println("Error: Das Spiel hat schon begonnen!");
			return false;
		}
		//if there are too less players
		else if (getPlayerCount() == 0) {
			System.out.println("Error: Zu wenig Spieler!");
			return false;
		}
		else {
			//start the game successfully
			Running = true;
			initialize();
			System.out.println("Jetzt faengt das Spiel an!");
			return true;
		}
	}
	
	/**
	 * a player throws a dart
	 * 
	 * @param number: the number of the hit field, 0 when the player missed
	 * @param multiplicator: the multiplier of the hit field, 0 when the player missed
	 * @return true if the throw was valid true, 
	 *         else false (invalid field/multiplier, game wasn't running)
	 */
	public boolean throwDart(int number, int multiplier) {
		//if the game is running
		if (Running && !isOver() && uebrigedarts>0 && Aktivspielernummer < getPlayerCount()) {
			//if the throw is valid (throw on the field or missing)
			if ((1<=number && number<=20 && 1<=multiplier && multiplier<=3) 
					|| (number==25 && 1<=multiplier && multiplier<=2) || (number==0 && multiplier==0)) {
				uebrigedarts -= 1;
				//Change the array according to the number and multiplier
				if (10<=number && number<=20 && 1<=multiplier && multiplier<=3) {
					Spielertreffer[Aktivspielernummer][number-10] += multiplier;
					//to judge whether the winning condition is fulfilled
					if(isOver()){System.out.println("Das Spiel " + getGamemode() + " ist jetzt zu Ende! Gewinner: " + Gewinner.getName());}
				}
				if (number==25 && 1<=multiplier && multiplier<=2) {
					Spielertreffer[Aktivspielernummer][11] += multiplier;
					//to judge whether the winning condition is fulfilled
					if(isOver()){System.out.println("Das Spiel " + getGamemode() + " ist jetzt zu Ende! Gewinner: " + Gewinner.getName());}
				}
				//if no darts left, go to the next player
				if (uebrigedarts == 0) {				
					nextplayer();
				}
				return true;
			}
			else {			
				System.out.println("Ungueltige Wurf!");
				return false;
			}
		}
		else {
			System.out.println("Das Spiel ist jetzt zu Ende!");
			return false;
		}
	}
	
}

