package rpg.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import rpg.characters.Mage;
import rpg.characters.Warrior;
import rpg.items.Armor;
import rpg.items.Wand;
import rpg.items.Sword;
import rpg.skills.Fire;
import rpg.skills.PowerStrike;

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
	
	@Test
	public void testGetCharacterStats2() {
		Warrior warrior = new Warrior(50,20,15,2);
		warrior.setItem(new Armor());
		assertEquals("Class: Warrior Hp: 50 Mp: 20 At: 15 Def: 5 Item: Armor Skill: _",warrior.getCharacterStats());	
	}
	
	@Test
	public void testAttacks2() {
		Warrior Rick = new Warrior(40,15,10,3);
		Mage Lina = new Mage(40,30,4,8);
		Rick.setItem(new Sword());
		Rick.setSkill(new PowerStrike(Rick));
		Lina.setItem(new Wand());
		Lina.setSkill(new Fire(Lina));
		
		Lina.normalAttack(Rick);
		assertEquals(37,Rick.getCurrentHp());
		assertEquals(30,Lina.getCurrentMp());
		
		Rick.useSkill(Lina);
		assertEquals(6,Lina.getCurrentHp());
		assertEquals(5,Rick.getCurrentMp());
		
		Lina.useSkill(Rick);
		assertEquals(17,Rick.getCurrentHp());
		assertEquals(23,Lina.getCurrentMp());
		
		Lina.useSkill(Rick);
		assertEquals(false,Rick.getAlive());
		
		Lina.receiveNormalDamage(10);
		assertEquals(4,Lina.getCurrentHp());
		Lina.receiveMagicDamage(2);
		assertEquals(2,Lina.getCurrentHp());
	}
	
	@Test
	public void test3() {
		Warrior Sven = new Warrior(50,20,15,2);
		Mage Puck = new Mage(30,40,5,10);
		Sven.setItem(new Armor());
		Sven.setSkill(new PowerStrike(Sven)); //No skill can be set here, coz Warrior Sven has no sword in hand.
		Puck.setItem(new Wand());
		Puck.setSkill(new Fire(Puck));
		assertEquals(15,Sven.getAttack());
		assertEquals(5,Sven.getDefense());
		
		Sven.useSkill(Puck);  //No skill can be used here, coz Warrior Sven has no sword in hand.
		assertEquals(30,Puck.getCurrentHp());
		Puck.normalAttack(Sven);
		assertEquals(48,Sven.getCurrentHp());
		Sven.normalAttack(Puck);
		assertEquals(25,Puck.getCurrentHp());
		Puck.useSkill(Sven);
		assertEquals(28,Sven.getCurrentHp());
		assertEquals(33,Puck.getCurrentMp());
		
		Sven.setItem(new Sword());// Warrior Sven has a Sword now.
		Sven.setSkill(new PowerStrike(Sven));
		Sven.useSkill(Puck); //PowerStrike is used here.
		assertEquals(false,Puck.getAlive());
		assertEquals(10,Sven.getCurrentMp());
	}
	
	@Test
	public void test4() {
		Mage Rose = new Mage(25,16,6,4);
		Warrior Mike = new Warrior(30,18,8,3);
		
		Rose.setItem(new Wand());
		Mike.setItem(new Armor());
		assertEquals(8,Mike.getAttack());
		assertEquals(8,Rose.getAttack());
		assertEquals(18,Mike.getCurrentMp());
		assertEquals(16,Rose.getCurrentMp());
		
		
		Mike.normalAttack(Rose);
		assertEquals(21,Rose.getCurrentHp());
		
		Rose.normalAttack(Mike);
		assertEquals(28,Mike.getCurrentHp());
		Rose.setSkill(new Fire(Rose));
		Rose.useSkill(Mike);
		assertEquals(9,Rose.getCurrentMp());
		assertEquals(8,Mike.getCurrentHp());
		
		Mike.setItem(new Sword());
		Mike.setSkill(new PowerStrike(Mike));
		Mike.useSkill(Rose);
		assertEquals(8,Mike.getCurrentMp());
		assertEquals(false,Rose.getAlive());
		assertEquals(true,Mike.getAlive());
	}
	
	@Test
	public void test5() {
		Mage Zeus = new Mage(30,30,2,5);
		Mage Pugna = new Mage(25,40,4,6);
		Zeus.setItem(new Sword());
		Pugna.setItem(new Wand());
		Pugna.setSkill(new Fire(Pugna));
		assertEquals(2,Zeus.getAttackValue());
		assertEquals(17,Zeus.getAttack());
		Zeus.normalAttack(Pugna);
		assertEquals(14,Pugna.getCurrentHp());
		Pugna.useSkill(Zeus);
		assertEquals(10,Zeus.getCurrentHp());
		Zeus.normalAttack(Pugna);
		assertEquals(3,Pugna.getCurrentHp());
		Pugna.useSkill(Zeus);
		assertEquals(false,Zeus.getAlive());
		assertEquals(true,Pugna.getAlive());
	}
	@Test
	public void test6() {
		Warrior Mars = new Warrior(30,25,5,5);
		Warrior Peter = new Warrior(28,23,10,2);
		assertEquals(30,Mars.getCurrentHp());
		assertEquals(25,Mars.getCurrentMp());
		assertEquals(5,Mars.getAttack());
		assertEquals(5,Mars.getDefense());
		assertEquals(28,Peter.getCurrentHp());
		assertEquals(23,Peter.getCurrentMp());
		assertEquals(10,Peter.getAttack());
		assertEquals(2,Peter.getDefense());
		
		Mars.normalAttack(Peter);
		assertEquals(25,Peter.getCurrentHp());
		
		Mars.setItem(new Armor());
		assertEquals(8,Mars.getDefense());
		Peter.normalAttack(Mars);
		assertEquals(28,Mars.getCurrentHp());
		
		Peter.setItem(new Sword());
		Peter.setSkill(new PowerStrike(Peter));
		assertEquals(25,Peter.getAttack());
		Peter.normalAttack(Mars);
		Peter.useSkill(Mars);
		assertEquals(false,Mars.getAlive());
		assertEquals(true,Peter.getAlive());
	}
	
}
