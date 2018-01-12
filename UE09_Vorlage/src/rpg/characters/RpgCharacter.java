package rpg.characters;
import rpg.items.*;
import rpg.skills.*;

public abstract class RpgCharacter implements ICharacter {

	private String RpgKlasse;
	private int Lebenspunkte;
	private int Magiepunkte;
	private int Attacke_Grundwert;
	private int Verteidigungs_Grundwert;
	private boolean alive;
	private Item Gegenstand;
	private Skill Faehigkeit;
	
	public RpgCharacter(String Rpgclass, int healthpoint, int magicpoint, 
			int attackvalue, int defensevalue) {
		RpgKlasse = Rpgclass;
		Lebenspunkte = healthpoint;
		Magiepunkte = magicpoint;
		Attacke_Grundwert = attackvalue;
		Verteidigungs_Grundwert = defensevalue;
		alive = true;
	}
	
	public String getRpgClass() {
		return RpgKlasse;
	}
	
	public int getMaxHp() {
		return Lebenspunkte;
	}
	
	public int getCurrentHp() {
		return Lebenspunkte;
	}
	
	public int getMaxMp() {
		return Magiepunkte;
	}
	
	public int getCurrentMp() {
		return Magiepunkte;
	}
	
	public int getAttackValue() {
		return Attacke_Grundwert;
	}
	
	public int getDefenseValue() {
		return Verteidigungs_Grundwert;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public Item getItem() {
		return Gegenstand;
	}
	
	public Skill getSkill() {
		return Faehigkeit;
	}
	
	public void setItem(Item item) {
		Gegenstand = item;
	}
	
	public void setSkill(Skill skill) {
		Faehigkeit = skill;
	}
	@Override
	public int getDefense() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void receiveNormalDamage(int normalDamage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveMagicDamage(int magicDamage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void normalAttack(RpgCharacter enemy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void useSkill(RpgCharacter enemy) {
		// TODO Auto-generated method stub

	}

}
