package com.hnqjxj.libd2s;

import java.io.IOException;

public class Start {

	public static void main(String[] args) throws IOException {
		D2s d2s = new D2s("asd.d2s");
		d2s.checkSum();
		d2s.close();
	}

	public static void printD2sInfo(D2s d2s) throws IOException {
		System.out.println("版本：" + d2s.getHeader().getVersion());
		System.out.println("文件大小：" + d2s.getHeader().getFileSize());
		System.out.println("角色名：" + d2s.getHeader().getCharacterName());
		System.out.println("角色职业：" + d2s.getHeader().getCharacterClass());
		System.out.println("角色等级：" + d2s.getHeader().getLevel());
	}
}
