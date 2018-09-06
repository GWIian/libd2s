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

	private void parseStat() {
		int pos = binStatData.length() - 9;
		
	}

	public int getStrength() {
		return strength;
	}
}
