package com.hnqjxj.libd2s;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hnqjxj.libd2s.util.ConvertUtil;

public class D2sCharStat {
	private RandomAccessFile origFile;
	private List<Byte> statData;
	private String binStatData;

	private int strength;
	private int energy;
	private int dexterity;
	private int vitality;
	private int unusedStats;
	private int unusedSkills;
	private int currentHP;
	private int maxHP;
	private int currentMana;
	private int maxMana;
	private int currentStamina;
	private int maxStamina;
	private int level;
	private int experience;
	private int gold;
	private int stashedGold;

	public D2sCharStat(RandomAccessFile origFile) throws IOException {
		this.origFile = origFile;
		fetchStatDataFromFile();
	}

	private void fetchStatDataFromFile() throws IOException {
		origFile.seek(767);
		statData = new ArrayList<Byte>();
		while (true) {
			if (origFile.readByte() == 0x69) {
				if (origFile.readByte() == 0x66) {
					break;
				} else {
					origFile.seek(origFile.getFilePointer() - 2);
					statData.add(origFile.readByte());
					statData.add(origFile.readByte());
				}
			} else {
				origFile.seek(origFile.getFilePointer() - 1);
				statData.add(origFile.readByte());
			}
		}
		Collections.reverse(statData);
		StringBuffer binBuffer = new StringBuffer();
		for (byte x : statData) {
			binBuffer.append(ConvertUtil.byteToBin(x));
		}
		binStatData = binBuffer.toString();
		parseStat();
	}

	private int getAttribute(int pos, int len) {
		return ConvertUtil.binToInt(binStatData.substring(pos, pos + len));
	}

	private void setAttribute(int pos, int len, int val) throws Exception {
		binStatData = new StringBuilder(binStatData).replace(pos, pos + len, ConvertUtil.intToBin(val, len)).toString();
		statData = new ArrayList<Byte>();
		for (int i = 0; i < binStatData.length() - 1; i += 8) {
			statData.add((byte) ConvertUtil.binToInt(binStatData.substring(i, i + 8)));
		}
		Collections.reverse(statData);
		byte[] buffer = new byte[statData.size()];
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = statData.get(i);
		}
		saveCharStatToFile(buffer);
	}

	private void saveCharStatToFile(byte[] statData) throws IOException {
		// 取头
		origFile.seek(0);
		byte[] header = new byte[767];
		origFile.read(header);
		while (true) {
			if (origFile.readByte() == 0x69) {
				if (origFile.readByte() == 0x66) {
					break;
				}
			}
		}
		// 取尾
		origFile.seek(origFile.getFilePointer() - 2);

		byte[] footer = new byte[(int) (origFile.length() - origFile.getFilePointer())];
		origFile.read(footer);
		origFile.seek(0);
		origFile.write(header);
		origFile.write(statData);
		origFile.write(footer);
	}

	private void parseStat() {
		int pos = binStatData.length() - 9;
		int id = ConvertUtil.binToInt(binStatData.substring(pos, pos + 9));
		while (id != 0x1ff) {
			switch (id) {
			case 0:
				pos -= 10;
				strength = getAttribute(pos, 10);
				break;
			case 1:
				pos -= 10;
				energy = getAttribute(pos, 10);
				break;
			case 2:
				pos -= 10;
				dexterity = getAttribute(pos, 10);
				break;
			case 3:
				pos -= 10;
				vitality = getAttribute(pos, 10);
				break;
			case 4:
				pos -= 10;
				unusedStats = getAttribute(pos, 10);
				break;
			case 5:
				pos -= 8;
				unusedSkills = getAttribute(pos, 8);
				break;

			case 6:
				pos -= 21;
				maxHP = getAttribute(pos, 21) / 256;
				break;
			case 7:
				pos -= 21;
				currentHP = getAttribute(pos, 21) / 256;
				break;
			case 8:
				pos -= 21;
				maxMana = getAttribute(pos, 21) / 256;
				break;
			case 9:
				pos -= 21;
				currentMana = getAttribute(pos, 21) / 256;
				break;
			case 10:
				pos -= 21;
				maxStamina = getAttribute(pos, 21) / 256;
				break;
			case 11:
				pos -= 21;
				currentStamina = getAttribute(pos, 21) / 256;
				break;
			case 12:
				pos -= 7;
				level = getAttribute(pos, 7);
				break;
			case 13:
				pos -= 32;
				experience = getAttribute(pos, 32);
				break;
			case 14:
				pos -= 25;
				gold = getAttribute(pos, 25);
				break;
			case 15:
				pos -= 25;
				stashedGold = getAttribute(pos, 25);
				break;
			}
			pos -= 9;
			id = getAttribute(pos, 9);
		}

	}

	/**
	 * 0
	 * 
	 * @return
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * 1
	 * 
	 * @return
	 */
	public int getEnergy() {
		return energy;
	}

	/**
	 * 2
	 * 
	 * @return
	 */
	public int getDexterity() {
		return dexterity;
	}

	/**
	 * 3
	 * 
	 * @return
	 */
	public int getVitality() {
		return vitality;
	}

	/**
	 * 4
	 * 
	 * @return
	 */
	public int getUnusedStats() {
		return unusedStats;
	}

	/**
	 * 5
	 * 
	 * @return
	 */
	public int getUnusedSkills() {
		return unusedSkills;
	}

	/**
	 * 6
	 * 
	 * @return
	 */
	public int getCurrentHP() {
		return currentHP;
	}

	/**
	 * 7
	 * 
	 * @return
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * 8
	 * 
	 * @return
	 */
	public int getCurrentMana() {
		return currentMana;
	}

	/**
	 * 9
	 * 
	 * @return
	 */
	public int getMaxMana() {
		return maxMana;
	}

	/**
	 * 10
	 * 
	 * @return
	 */
	public int getCurrentStamina() {
		return currentStamina;
	}

	/**
	 * 11
	 * 
	 * @return
	 */
	public int getMaxStamina() {
		return maxStamina;
	}

	/**
	 * 12
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 13
	 * 
	 * @return
	 */
	public int getExperience() {
		return experience;
	}

	/**
	 * 14
	 * 
	 * @return
	 */
	public int getGold() {
		return gold;
	}

	/**
	 * 15
	 * 
	 * @return
	 */
	public int getStashedGold() {
		return stashedGold;
	}

	public void setExperience(int exp) throws Exception {
		setBinStatData(13, exp);
	}

	public void setStrength(int str) throws Exception {
		setBinStatData(0, str);
	}

	public void setDexterity(int dex) throws Exception {
		setBinStatData(2, dex);
	}

	private void setBinStatData(int statId, int val) throws Exception {
		int pos = binStatData.length() - 9;
		int id = ConvertUtil.binToInt(binStatData.substring(pos, pos + 9));
		while (id != 0x1ff) {
			switch (id) {
			case 0:
				pos -= 10;
				if (id == statId) {
					setAttribute(pos, 10, val);
				}
				strength = getAttribute(pos, 10);
				break;
			case 1:
				pos -= 10;
				if (id == statId) {
					setAttribute(pos, 10, val);
				}
				energy = getAttribute(pos, 10);
				break;
			case 2:
				pos -= 10;
				if (id == statId) {
					setAttribute(pos, 10, val);
				}
				dexterity = getAttribute(pos, 10);
				break;
			case 3:
				pos -= 10;
				if (id == statId) {
					setAttribute(pos, 10, val);
				}
				vitality = getAttribute(pos, 10);
				break;
			case 4:
				pos -= 10;
				if (id == statId) {
					setAttribute(pos, 10, val);
				}
				unusedStats = getAttribute(pos, 10);
				break;
			case 5:
				pos -= 8;
				if (id == statId) {
					setAttribute(pos, 8, val);
				}
				unusedSkills = getAttribute(pos, 8);
				break;

			case 6:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				maxHP = getAttribute(pos, 21) / 256;
				break;
			case 7:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				currentHP = getAttribute(pos, 21) / 256;
				break;
			case 8:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				maxMana = getAttribute(pos, 21) / 256;
				break;
			case 9:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				currentMana = getAttribute(pos, 21) / 256;
				break;
			case 10:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				maxStamina = getAttribute(pos, 21) / 256;
				break;
			case 11:
				pos -= 21;
				if (id == statId) {
					setAttribute(pos, 21, val);
				}
				currentStamina = getAttribute(pos, 21) / 256;
				break;
			case 12:
				pos -= 7;
				if (id == statId) {
					setAttribute(pos, 7, val);
				}
				level = getAttribute(pos, 7);
				break;
			case 13:
				pos -= 32;
				if (id == statId) {
					setAttribute(pos, 32, val);
				}
				experience = getAttribute(pos, 32);
				break;
			case 14:
				pos -= 25;
				if (id == statId) {
					setAttribute(pos, 25, val);
				}
				gold = getAttribute(pos, 25);
				break;
			case 15:
				pos -= 25;
				if (id == statId) {
					setAttribute(pos, 25, val);
				}
				stashedGold = getAttribute(pos, 25);
				break;
			}
			pos -= 9;
			id = getAttribute(pos, 9);
		}
	}
}
