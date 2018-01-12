package rpg.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import rpg.characters.Mage;
import rpg.characters.Warrior;
import rpg.items.Armor;
import rpg.items.Wand;
import rpg.skills.Fire;

/**
 * tests for the exercise
 * @author Frederik Bark
 *
 */
public class StudentTests {

	
	@Test
	public void testCharacters(){
		Mage mage = new Mage(30,20,3,10);
		mage.setItem(new Wand());
		mage.setSkill(new Fire(mage));
		
		assertEquals(30,mage.getMaxHp());
		assertEquals(30,mage.getCurrentHp());
		assertEquals(20,mage.getMaxMp());
		assertEquals(20,mage.getCurrentMp());
		assertEquals(3,mage.getAttackValue());
		assertEquals(10,mage.getDefenseValue());
		
		assertEquals("Mage",mage.getRpgClass());
		assertEquals(20,mage.getCurrentMp());
		
		assertEquals(5,mage.getAttack());
		assertEquals(10,mage.getDefense());
		
		Warrior warrior = new Warrior(50,10,5,10);
		warrior.setItem(new Armor());
		assertEquals(5,warrior.getAttack());
		assertEquals(13,warrior.getDefense());
	}
	
	@Test
	public void testAttacks(){
		Mage mage =new Mage(20,20,5,2);
		Warrior warrior =new Warrior(30,10,5,4);
		
		mage.normalAttack(warrior);
		assertEquals(29,warrior.getCurrentHp());
		mage.setItem(new Wand());
		mage.setSkill(new Fire(mage));
		mage.useSkill(warrior);
		assertEquals(9,warrior.getCurrentHp());
	}
	
	@Test
	public void testGetCharacterStats(){
		Mage mage = new Mage(50,40,30,20);
		assertEquals("Class: Mage Hp: 50 Mp: 40 At: 30 Def: 20 Item: _ Skill: _",mage.getCharacterStats());
	
	}
	
}
