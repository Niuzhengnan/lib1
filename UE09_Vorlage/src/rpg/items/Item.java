package rpg.items;

public abstract class Item {
	private String Name;
	private int Attacke_Grundwert;
	private int Verteidigungs_Grundwert;
	
	public Item(String name, int attackvalue, int defensevalue) {
		Name = name;
		Attacke_Grundwert = attackvalue;
		Verteidigungs_Grundwert = defensevalue;
	}
	
	public String getName() {
		return Name;
	}
	
	public int getAttackValue() {
		return Attacke_Grundwert;
	}
	
	public int getDefenseValue() {
		return Verteidigungs_Grundwert;
	}
	
	public static Item[] getAllItem() {
		Item[] itemarray = {null,new Sword(),new Wand(),new Armor()}; 
		return itemarray;
	}
}
