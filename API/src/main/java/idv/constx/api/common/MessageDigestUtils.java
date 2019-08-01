package idv.constx.api.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.bubugao.framework.util.UtilSys;

public class MessageDigestUtils {

	/** 将输入字符串经过MD5处理后返回 */
	public static String md5(String values) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(values.getBytes(UtilSys.UTF_8));
		return toHexString(messageDigest.digest());
	}
	
	/** 将输入字符串经过SHA1处理后返回 */
	public static String SHA1(String values) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		messageDigest.update(values.getBytes(UtilSys.UTF_8));
		return toHexString(messageDigest.digest());
	}

	public static String toHexString(byte[] bytes) {
		StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (int i = 0, len = bytes.length, value; i < len; i++) {
			value = bytes[i] & 0xff;
			if (value < 0x10)
				buf.append('0');
			buf.append(Integer.toHexString(value));
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		String s = "明文";
		System.out.println(MessageDigestUtils.md5(s));
		System.out.println(MessageDigestUtils.SHA1(s));
	}

}
