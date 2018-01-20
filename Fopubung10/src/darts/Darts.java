package darts;

public abstract class Darts implements IDarts {
	private String Spielmodus;
	private final int Max_Spielerzahl;
	private int Spielerzahl;
	protected boolean Running;
	private Player[] Spielerliste;
	protected int Aktivspielernummer;
	protected int uebrigedarts;
	protected Player Gewinner;
	
	public Darts(String gamemode, int max_playernumber) {
		Spielmodus = gamemode;
		if (max_playernumber > 0) {Max_Spielerzahl = max_playernumber;}
		else {Max_Spielerzahl = 0;}
		Spielerzahl = 0;
		Running = false;
		Spielerliste = new Player[max_playernumber];
	}
	
	@Override
	public boolean addPlayer(Player player) {
		if (Spielerzahl >= Max_Spielerzahl){
			System.out.println("Error: Zu viele Spieler!");
			return false;
		}
		else if (Running) {
			System.out.println("Error: Das Spiel hat bereit begonnen!");
			return false;
		}
		else {
			for (int i = 0; i < Spielerzahl; i++) {
				if (Spielerliste[i] == player) {
					System.out.println("Error: Der/Die Spieler/in ist bereits ins Spiel!");
					return false;
				}
			}
			Spielerliste[Spielerzahl] = player;
			Spielerzahl += 1;
			//System.out.println("Spieler hinzufuegt! Name: " + player.getName());
			return true;
		}
	}

	@Override
	public int getActivePlayerNumber() {
		// TODO Auto-generated method stub
		return Aktivspielernummer;
	}
	
	public int getMaxplayernumber() {
		return Max_Spielerzahl;
	}
	@Override
	public int getPlayerCount() {
		return Spielerzahl;
	}

	@Override
	public Player[] getPlayers() {
		if (getPlayerCount() < getMaxplayernumber()) {
			int num = getPlayerCount();
			Player[] players = new Player[num];
			for (int i=0; i<getPlayerCount(); i++) {
				players[i] = Spielerliste[i];
			}
			return players;
		}
		return Spielerliste;
	}

	@Override
	public String getGamemode() {
		return Spielmodus;
	}

	@Override
	public int getLeftDarts() {
		return uebrigedarts;
	}

	@Override
	public boolean isRunning() {
		return Running;
	}

	@Override
	public boolean isOver() {
		
		if (Spielmodus == "Shanghai") {
			return false;
		}
		if (Spielmodus == "DoubleOut") {
			return false;
		}
		if (Spielmodus == "Tactics") {
			return false;
		}	
		return false;
	}

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		if (Running) {
			System.out.println("Error: Das Spiel hat schon begonnen!");
			return false;
		}
		else if (Spielerzahl == 0) {
			System.out.println("Error: Zu wenig Spieler!");
			return false;
		}
		else {
			Running = true;
			System.out.println("Jetzt faengt das Spiel an!");
			return true;
		}
	}

	@Override
	public Player getWinner() {
		return Gewinner;
	}

	@Override
	public boolean throwDart(int number, int multiplier) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void endGame() {
		Running = false;
		System.out.println("Jetzt ist das Spiel zu Ende!");
	}

}
