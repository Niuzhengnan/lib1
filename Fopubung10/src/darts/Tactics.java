package darts;

public class Tactics extends Darts{
	private int[][] Spielertreffer;
	
	public Tactics (int max_playernumber) {
		super("Tactics", max_playernumber);
		Spielertreffer = new int[max_playernumber][12];
		Running = false;
		uebrigedarts = 3;
		Aktivspielernummer = 0;
	}
	
	public void initialize() {
		for (int i=0; i < getPlayerCount(); i++) {
			for (int j=0; j < 12; j++) {
				Spielertreffer[i][j]=0;
			}
		}
	}
	
	public int[][] getScore() {
		return Spielertreffer;
	}
	
	public boolean isOver() {		
		for (int i=0; i<getPlayerCount(); i++) {
			int counter = 0;
			for (int j=0; j<12; j++) {
				if (Spielertreffer[i][j] >= 3) {
					counter++;
				}
			}
			if (counter==12) {
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
	}
	
	public boolean start() {
		// TODO Auto-generated method stub
		if (getPlayerCount() < getMaxplayernumber()) {
			int num = getPlayerCount();
			int[][] treffer = new int[num][12];
			Spielertreffer = treffer;
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
			if ((1<=number && number<=20 && 1<=multiplier && multiplier<=3) 
					|| (number==25 && 1<=multiplier && multiplier<=2) || (number==0 && multiplier==0)) {
				uebrigedarts -= 1;
				if (10<=number && number<=20 && 1<=multiplier && multiplier<=3) {
					Spielertreffer[Aktivspielernummer][number-10] += multiplier;
					if(isOver()){System.out.println("Das Spiel " + getGamemode() + " ist jetzt zu Ende! Gewinner: " + Gewinner.getName());}
				}
				if (number==25 && 1<=multiplier && multiplier<=2) {
					Spielertreffer[Aktivspielernummer][11] += multiplier;
					if(isOver()){System.out.println("Das Spiel " + getGamemode() + " ist jetzt zu Ende! Gewinner: " + Gewinner.getName());}
				}
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

