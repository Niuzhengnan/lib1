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
			int attackbasicvalue, int defensebasicvalue) {
		RpgKlasse = Rpgclass;
		Lebenspunkte = healthpoint;
		Magiepunkte = magicpoint;
		Attacke_Grundwert = attackbasicvalue;
		Verteidigungs_Grundwert = defensebasicvalue;
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
		if (getRpgClass() == "Mage" && getItem().getName() == "Wand" && skill.getName() == "Fire")
		{Faehigkeit = skill;}
		else if (getRpgClass() == "Warrior" && getItem().getName() == "Sword" && skill.getName() == "PowerStrike")
		{Faehigkeit = skill;}
		else {Faehigkeit = null;}
	}
	@Override
	public int getDefense() {
		int defensevalue = getDefenseValue();
		if (getItem() == null) {
			return defensevalue;
		}
		else {
			int itemdefensevalue = getItem().getDefenseValue();
			return defensevalue + itemdefensevalue;
		}
	}

	@Override
	public int getAttack() {
		int attackvalue = getAttackValue();
		if (getItem() == null) {
			return attackvalue;
		}
		else {
			int itemattackvalue = getItem().getAttackValue();
			return attackvalue + itemattackvalue;
		}		
	}

	@Override
	public void receiveNormalDamage(int normalDamage) {
		int realdamage = normalDamage - getDefense();
		if (realdamage<0) { realdamage = 0;}
		int life_now = getCurrentHp() - realdamage; 
		if (life_now <= 0) {
			Lebenspunkte = life_now;
			alive = false;
		}
		else {
			Lebenspunkte = life_now;
		}
	}

	@Override
	public void receiveMagicDamage(int magicDamage) {
		int life_now = getCurrentHp() - magicDamage;
		if (life_now <= 0) {
			Lebenspunkte = life_now;
			alive = false;
		}
		else {
			Lebenspunkte = life_now;
		}
	}

	@Override
	public void normalAttack(RpgCharacter enemy) {
		if (enemy.getAlive()) { enemy.receiveNormalDamage(getAttack());}
	}

	@Override
	public void useSkill(RpgCharacter enemy) {
		if (enemy.getAlive() && getSkill() != null 
				&& getCurrentMp() >= getSkill().getMpCosts()) 
		{
			Magiepunkte -= getSkill().getMpCosts();
			if (getSkill().getName() == "Fire") {
				Fire fire = (Fire)getSkill();
				fire.use(enemy);
			}
			else if (getSkill().getName() == "PowerStrike") {
				PowerStrike powerstrike = (PowerStrike)getSkill();
				powerstrike.use(enemy);
			}
		}
	}
	
	public String getCharacterStats() {
		String itemname;
		String skillname;
		if (getItem() == null) {itemname = "_";}
		else {itemname = getItem().getName();}
		if (getSkill() == null) {skillname = "_";}
		else {skillname = getSkill().getName();}
		//System.out.println("Class: " + getRpgClass() + " Hp: " + getMaxHp() + " Mp: " + getMaxMp() + 
		//		" At: " + getAttack() + " Def: " + getDefense() + " Item: " + itemname + 
		//		" Skill: " + skillname);
		return "Class: " + getRpgClass() + " Hp: " + getMaxHp() + " Mp: " + getMaxMp() + 
				" At: " + getAttack() + " Def: " + getDefense() + " Item: " + itemname + 
				" Skill: " + skillname;
	}
}
