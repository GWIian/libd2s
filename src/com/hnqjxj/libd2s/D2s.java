package com.hnqjxj.libd2s;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.hnqjxj.libd2s.util.ConvertUtil;

public class D2s {
	private String d2sFileName;
	private RandomAccessFile origFile;
	private D2sHeader header;

	public D2s(String d2sFilename) throws FileNotFoundException {
		this.d2sFileName = d2sFilename;
		origFile = new RandomAccessFile(d2sFileName, "rw");
		this.header = new D2sHeader(origFile);
	}

	public void close() throws IOException {
		origFile.close();
	}

	public D2sHeader getHeader() {
		return header;
	}

	public void getStrength() throws IOException {
		origFile.seek(765);
		byte[] buffer = new byte[] { 0, 0 };
		origFile.read(buffer);
		System.out.println(String.format("%x%x", buffer[0], buffer[1]));
	}

	public void checkSum() throws IOException {
		// 清空原值
		origFile.seek(12);
		origFile.write(new byte[] { 0, 0, 0, 0 });
		// 计算新值
		origFile.seek(0);
		int sum = 0;
		for (int i = 0; i < origFile.length(); i++) {
			sum = (sum << 1) + origFile.readUnsignedByte() + ((sum & 0x80000000) < 0 ? 1 : 0);
		}
		// 写入
		origFile.seek(12);
		origFile.write(ConvertUtil.intToBytes(sum));
	}
}
