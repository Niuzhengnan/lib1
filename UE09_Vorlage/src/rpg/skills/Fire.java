package rpg.skills;
import rpg.characters.*;

public class Fire extends Skill{
	public Fire(RpgCharacter rpgChar) {
		super("Fire",rpgChar,7);
	}
	
	public void use(RpgCharacter enemy) {
		enemy.receiveMagicDamage(20);
	}
}
