package com.hnqjxj.libd2s;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.hnqjxj.libd2s.util.ConvertUtil;

public class D2sHeader {
	private RandomAccessFile origFile;

	public D2sHeader(RandomAccessFile origFile) {
		this.origFile = origFile;
	}

	public String getVersion() throws IOException {
		origFile.seek(4);
		int versionID = origFile.readUnsignedByte();

		switch (versionID) {
		case 71:
			return "1.00 through v1.06";
		case 87:
			return "1.07 or Expansion Set v1.08";
		case 89:
			return "standard game v1.08";
		case 92:
			return "v1.09 ";
		case 96:
			return "v1.10+";
		default:
			return "unkown version";
		}
	}

	public int getFileSize() throws IOException {
		origFile.seek(8);
		byte[] buffer = new byte[4];
		origFile.read(buffer);
		return ConvertUtil.bytesToInt(buffer);
	}

	public String getCharacterName() throws IOException {
		origFile.seek(20);
		byte[] buffer = new byte[16];
		origFile.read(buffer);
		return new String(buffer, "US-ASCII").trim();
	}

	public CharacterClass getCharacterClass() throws IOException {
		origFile.seek(40);
		int classCode = origFile.readUnsignedByte();
		return new CharacterClass(classCode);
	}

	public int getLevel() throws IOException {
		origFile.seek(43);
		return origFile.readUnsignedByte();
	}
}
