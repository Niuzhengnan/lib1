package rpg.characters;

public interface ICharacter {
	/**
	 * calculates the defense of a character
	 * the defense depends on the defense value of the character and the defense value of his item (if he has an item)
	 * @return the defense of the character
	 */
	public int getDefense();
	
	/**
	 * calculates the attack of a character
	 * the attack depends on the attack value of the character and the attack value of his item (if he has an item)
	 * @return the attack of the character
	 */
	public int getAttack();
	
	/**
	 * calculate the damage a character receives from a normal attack
	 * @param normalDamage the damage of the enemy's normal attack
	 */
	public void receiveNormalDamage(int normalDamage);
	
	/**
	 * calculate the damage a character receives from a magic attack
	 * magic damage ignores the defense of a character
	 * @param magicDamage the magic damage of the enemy's magic attack
	 */
	public void receiveMagicDamage(int magicDamage);
	
	/**
	 * An attack against an enemy
	 * @param enemy the enemy who gets attacked
	 */
	public void normalAttack(RpgCharacter enemy);
	
	/**
	 * Uses a skill against an enemy
	 * @param enemy the enemy who gets attacked
	 */
	public void useSkill(RpgCharacter enemy);
}
