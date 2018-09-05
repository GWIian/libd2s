package com.hnqjxj.libd2s.util;

public class ConvertUtil {
	public static int bytesToInt(byte[] src) {
		return src[0] & 0xFF | (src[1] & 0xFF) << 8 | (src[2] & 0xFF) << 16 | (src[3] & 0xFF) << 24;
	}

	public static byte[] intToBytes(int src) {
		byte[] value = new byte[] { 0, 0, 0, 0 };
		value[0] = (byte) (src & 0xff);
		value[1] = (byte) ((src >> 8) & 0xff);
		value[2] = (byte) ((src >> 16) & 0xff);
		value[3] = (byte) ((src >> 24) & 0xff);
		return value;
	}
}
