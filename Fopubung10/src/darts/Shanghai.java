package darts;

public class Shanghai extends Darts{
	private int[] Spielernoten;
	private int TrefferMultiplier1;
	private int TrefferMultiplier2;
	private int TrefferMultiplier3;
	private int Runde;
	
	public Shanghai(int max_playernumber) {
		super("Shanghai", max_playernumber);
		Spielernoten = new int[getPlayerCount()];
		for (int i=0; i < getPlayerCount(); i++) {
			Spielernoten[i] = 0;
		}
		Runde = 1;
		uebrigedarts = 3;
		Aktivspielernummer = 0;
		
		TrefferMultiplier1 = 0;
		TrefferMultiplier2 = 0;
		TrefferMultiplier3 = 0;
	}
	
	public void initialize() {
		for (int i=0; i < getPlayerCount(); i++) {
			Spielernoten[i] = 0;
		}
	}
	
	public int[] getScore() {
		return Spielernoten;
	}
	
	public boolean isOver() {
		if (TrefferMultiplier1 == 1 && TrefferMultiplier2 == 2 && TrefferMultiplier3 == 3) {
			Gewinner = getPlayers()[Aktivspielernummer];
			Running = false;
			System.out.println("Shanghai! Sieger ist " + Gewinner.getName());
			return true;
		}
		
		if (Runde > 9) {
			int max_index = 0;
			for (int i=0; i<getPlayerCount()-1; i++) {
				if (Spielernoten[i+1] > Spielernoten[i]) {max_index = i+1;}
			}
			Gewinner = getPlayers()[max_index];
			System.out.println("Die 9te Runde ist zu Ende! Sieger ist " + Gewinner.getName());
			Running = false;
			return true;
		}
		
		return false;
	}
	
	public void nextplayer() {
		Aktivspielernummer += 1;
		if (Aktivspielernummer>=getPlayerCount()) {
			Aktivspielernummer = 0;
			Runde += 1;
			if (Runde > 9) {isOver();}
		}
		uebrigedarts = 3;
		TrefferMultiplier1 = 0;
		TrefferMultiplier2 = 0;
		TrefferMultiplier3 = 0;
	}
	
	public boolean start() {
		// TODO Auto-generated method stub
		if (getPlayerCount() < getMaxplayernumber()) {
			int num = getPlayerCount();
			int[] noten = new int[num];
			Spielernoten = noten;
			initialize();
		}
		if (Running) {
			System.out.println("Error: Das Spiel hat schon begonnen!");
			return false;
		}
		else if (getPlayerCount() == 0) {
			System.out.println("Error: Zu wenig Spieler!");
			return false;
		}
		else {
			Running = true;
			initialize();
			System.out.println("Jetzt faengt das Spiel an!");
			return true;
		}
	}
	
	public boolean throwDart(int number, int multiplier) {
		if (Running && !isOver() && uebrigedarts>0 && Aktivspielernummer < getPlayerCount()) {
			if ((1<=number && number<=20 && 1<=multiplier && multiplier<=3) || (number==25 && 1<=multiplier && multiplier<=2) || (number==0 && multiplier==0)) {
				uebrigedarts -= 1;
				if (number == Runde) {Spielernoten[Aktivspielernummer] += number * multiplier;}
				if (uebrigedarts == 2) {TrefferMultiplier1 = multiplier;}
				if (uebrigedarts == 1) {TrefferMultiplier2 = multiplier;}
				if (uebrigedarts == 0) {
					TrefferMultiplier3 = multiplier;
					isOver();
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
	
	public void endGame() {
		Running = false;
		System.out.println("Jetzt ist das Spiel zu Ende!");
	}

}

