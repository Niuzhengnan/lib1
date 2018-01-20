package darts;

public class DoubleOut extends Darts{
	private final int Startpunkt;
	int[] Spielernoten;
	private int Note;
	
	public DoubleOut (int max_playernumber, int Startpoint) {
		super("DoubleOut", max_playernumber);
		Startpunkt = Startpoint;
		Spielernoten = new int[max_playernumber];
		Running = false;
		uebrigedarts = 3;
		Aktivspielernummer = 0;
		Note=0;
	}
	
	public void initialize() {
		for (int i=0; i < getPlayerCount(); i++) {
			Spielernoten[i] = Startpunkt;
		}
	}
	
	public int[] getScore() {
		return Spielernoten;
	}
	
	public boolean isOver() {
		for (int i=0; i<getPlayerCount(); i++) {
			if (Spielernoten[i] == 0) {
				Gewinner = getPlayers()[i];
				Running = false;
				return true;
			}
		}
		return false;
	}
	
	public void nextplayer() {
		Aktivspielernummer += 1;
		if (Aktivspielernummer>=getPlayerCount()) {
			Aktivspielernummer = 0;
		}
		uebrigedarts = 3;
		Note = 0;
		//if (Running)
		//{System.out.println("Naechster Spieler!");}
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
				Note += number * multiplier;
				uebrigedarts -= 1;
				if (Note == Spielernoten[Aktivspielernummer] && multiplier == 2) {
						Spielernoten[Aktivspielernummer] -= Note;
						if (isOver()) {System.out.println("Das Spiel " + getGamemode() + " ist jetzt zu Ende! Gewinner: " + Gewinner.getName());}
					}
				if (Note == Spielernoten[Aktivspielernummer]-1 || Note>Spielernoten[Aktivspielernummer])
					{nextplayer();}
				
				if (uebrigedarts == 0) {
					
					if (Note < Spielernoten[Aktivspielernummer] - 1) {
						Spielernoten[Aktivspielernummer] -= Note;						
					}
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
