package darts;
/**
 * Models a darts-like game
 * 
 */
public interface IDarts {

	/**
	 * Adds a player to the game, 
	 * prints an error in the console if the player limit is exceeded
	 * 
	 * @param player the player that is added to the game
	 * @return true if the player was added, false if an error occurred
	 */
	boolean addPlayer(Player player);
	
	
	/**
	 * returns the number of the active player
	 * the players are numbered in the order they were added. The first player has number 0, the second 1, ...
	 * @return the number of the active player
	 */
	int getActivePlayerNumber();
	
	/** 
	 * returns the number of players playing the game 
	 * @return the number of players
	 */
	int getPlayerCount();
	
	/**
	 * Returns an array filled with all players
	 * @return an array with all players
	 */
	Player[] getPlayers();
	
	/**
	 * Returns the name of the gamemode
	 * @return the name of the gamemode
	 */
	String getGamemode();
	
	/**
	 * returns the number of darts a player has left
	 * @return the number of darts a player has left
	 */
	int getLeftDarts();
	
	/**
	 * Returns if the game is currently running
	 * @return if the game is running
	 */
	boolean isRunning();
	
	/**
	 * returns if the game is over
	 * @return true if the game is over, else false
	 */
	boolean isOver();
	
	/**
	 * starts the game
	 * @return true, if the game was started, false if an error occurred (to less players, game already started,...)
	 */
	boolean start();
	
	/**
	 * returns the winner
	 * @return the player who has won or null if the game isn't over yet or there is no winner
	 */
	Player getWinner();
	
	/**
	 * a player throws a dart
	 * 
	 * @param number the number of the hit field, 0 when the player missed
	 * @param multiplicator the multiplier of the hit field, 0 when the player missed
	 * @return if the throw was valid true, else false (invalid field/multiplier, game wasn't running,...)
	 */
	boolean throwDart(int number, int multiplier);
	
	/**
	 * This method ends the game
	 */
	void endGame();
}
