package com.hnqjxj.libd2s;

import java.io.IOException;

public class Start {

	public static void main(String[] args) throws Exception {
		D2s d2s = new D2s("choumao");
		d2s.getCharStat().setStrength(500);
		d2s.getCharStat().setDexterity(800);
		d2s.getCharStat().setExperience(2099999999);
		printD2sInfo(d2s);
		d2s.checkSum();
		d2s.close();
		// System.out.println(ConvertUtil.intToBin(10, 3));
		// String a="00000000";
		// System.out.println(new StringBuilder(a).replace(0, 9, "aa"));
	}

	public static void printD2sInfo(D2s d2s) throws IOException {
		System.out.println("版本：" + d2s.getHeader().getVersion());
		System.out.println("文件大小：" + d2s.getHeader().getFileSize());
		System.out.println("角色名：" + d2s.getHeader().getCharacterName());
		System.out.println("角色职业：" + d2s.getHeader().getCharacterClass());
		System.out.println("角色等级：" + d2s.getHeader().getLevel());
		System.out.println("力量：" + d2s.getCharStat().getStrength());
		System.out.println("敏捷：" + d2s.getCharStat().getDexterity());
		System.out.println("体力：" + d2s.getCharStat().getVitality());
		System.out.println("精力：" + d2s.getCharStat().getEnergy());
		System.out.println("未使用属性点：" + d2s.getCharStat().getUnusedStats());
		System.out.println("未使用技能点：" + d2s.getCharStat().getUnusedSkills());
		System.out.println("生命值：" + d2s.getCharStat().getCurrentHP() + "/" + d2s.getCharStat().getMaxHP());
		System.out.println("法力值：" + d2s.getCharStat().getCurrentMana() + "/" + d2s.getCharStat().getMaxMana());
		System.out.println("耐力值：" + d2s.getCharStat().getCurrentStamina() + "/" + d2s.getCharStat().getMaxStamina());
		System.out.println("经验值：" + d2s.getCharStat().getExperience());
		System.out.println("金币：" + d2s.getCharStat().getGold());
		System.out.println("储藏箱金币：" + d2s.getCharStat().getStashedGold());
	}
}
