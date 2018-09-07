package com.hnqjxj.libd2s.util;

import java.math.BigInteger;

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

	public static String byteToBin(byte src) {
		return Integer.toBinaryString((src & 0xFF) + 0x100).substring(1);
	}

	public static int binToInt(String bin) {
		return new BigInteger(bin, 2).intValue();
	}

	public static String intToBin(int src, int binLen) throws Exception {
		String srcBin = Integer.toBinaryString(src);
		if (binLen > srcBin.length()) {
			char[] buffer = new char[binLen - srcBin.length()];
			for (int i = 0; i < buffer.length; i++) {
				buffer[i] = '0';
			}
			srcBin = new String(buffer).concat(srcBin);
		} else if (binLen < srcBin.length()) {
			throw new Exception("out of length");
		}
		return srcBin;
	}
}
