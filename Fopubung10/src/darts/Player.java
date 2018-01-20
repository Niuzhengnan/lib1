package darts;
/** 
 * Represents a player in a dart game
 *
 */
public class Player {

	/**
	 * name of the player
	 */
	private String name;
	
	/**
	 * Initializes the player
	 * 
	 * @param playerName the name of the player
	 */
	public Player(String playerName){
		name=playerName;
	}
	
	/**
	 * returns the name of the player
	 * @return the name of the player
	 */
	public String getName(){
		return name;
	}
	
}
