package rpg.skills;
import rpg.characters.*;

public abstract class Skill {
	private String Name;
	private RpgCharacter Besitzer;
	private int MagiepunkteKosten;
	
	public Skill(String name, RpgCharacter owner, int magicpointcost) {
		Name = name;
		Besitzer = owner;
		MagiepunkteKosten = magicpointcost;
	}
	
	public String getName() {
		return Name;
	}
	
	public RpgCharacter getRpgCharacter() {
		return Besitzer;
	}
	
	public int getMpCosts() {
		return MagiepunkteKosten;
	}
	
	public static Skill[] getAllSkill(RpgCharacter rpgChar) {
		if (rpgChar.getRpgClass() == "Mage") {
				if (rpgChar.getItem().getName() == "Wand") { 
					Skill[] skillarray = {null,new Fire(rpgChar)};
					return skillarray; 
				}
				else {
					Skill[] skillarray = {null};
					return skillarray;
				}					
		}
		else if (rpgChar.getRpgClass() == "Warrior") {
			if (rpgChar.getItem().getName() == "Sword") {
				Skill[] skillarray = {null,new PowerStrike(rpgChar)};
				return skillarray;
			}
			else {
				Skill[] skillarray = {null};
				return skillarray;
			}
		}
		else {
			Skill[] skillarray = {null};
			return skillarray;
		}
	}
}
