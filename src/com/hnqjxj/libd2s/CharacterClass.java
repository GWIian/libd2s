package com.hnqjxj.libd2s;

public class CharacterClass {
	private int classCode;

	public CharacterClass(int classCode) {
		this.classCode = classCode;
	}

	public String getName() {
		switch (classCode) {
		case 0:
			return "Amazon";
		case 1:
			return "Sorceress";
		case 2:
			return "Necromancer";
		case 3:
			return "Paladin";
		case 4:
			return "Barbarian";
		case 5:
			return "Druid";
		case 6:
			return "Assassin";
		default:
			return "unknown character class";
		}
	}

	@Override
	public String toString() {
		return getName();
	}
}
