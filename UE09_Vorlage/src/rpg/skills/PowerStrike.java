package rpg.skills;
import rpg.characters.*;

public class PowerStrike extends Skill{
	public PowerStrike(RpgCharacter rpgChar) {
		super("PowerStrike",rpgChar,10);
	}
	
	public void use(RpgCharacter enemy) {
		enemy.receiveNormalDamage(getRpgCharacter().getAttack());
		enemy.receiveNormalDamage(getRpgCharacter().getAttack());
	}
}
