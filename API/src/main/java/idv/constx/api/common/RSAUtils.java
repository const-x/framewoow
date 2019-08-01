package idv.constx.api.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {
	private final static Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);
	public static final String CHARSET = "UTF-8";
	public static final String RSA_ALGORITHM = "RSA";
	public static final String RSA_ALGORITHM_SIGN = "SHA256WithRSA";

	public static Map<String, String> createKeys(int keySize) {
		// 为RSA算法创建一个KeyPairGenerator对象
		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("createKeys异常",e);
			throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
		}

		// 初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
		kpg.initialize(keySize);
		// 生成密匙对
		KeyPair keyPair = kpg.generateKeyPair();
		// 得到公钥
		Key publicKey = keyPair.getPublic();
		String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
		// 得到私钥
		Key privateKey = keyPair.getPrivate();
		String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
		Map<String, String> keyPairMap = new HashMap<String, String>();
		keyPairMap.put("publicKey", publicKeyStr);
		keyPairMap.put("privateKey", privateKeyStr);

		return keyPairMap;
	}

	public static String publicEncrypt(String data, String publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
			cipher.init(Cipher.ENCRYPT_MODE, publicK);
			return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
					publicK.getModulus().bitLength()));
		} catch (Exception e) {
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
		}
	}

	public static String privateDecrypt(String data, String privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
			cipher.init(Cipher.DECRYPT_MODE, privateK);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
					privateK.getModulus().bitLength()), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	public static String privateEncrypt(String data, String privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
			cipher.init(Cipher.ENCRYPT_MODE, privateK);
			return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET),
					privateK.getModulus().bitLength()));
		} catch (Exception e) {
			throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
		}
	}

	public static String publicDecrypt(String data, String publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
			cipher.init(Cipher.DECRYPT_MODE, publicK);
			return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
					publicK.getModulus().bitLength()), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
		}
	}

	public static String sign(String data, String privateKey) {
		try {
			// sign
			Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes());
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			RSAPrivateKey privateK = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
			signature.initSign(privateK);
			signature.update(data.getBytes(CHARSET));
			return Base64.encodeBase64URLSafeString(signature.sign());
		} catch (Exception e) {
			throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
		}
	}

	public static boolean verify(String data, String sign, String publicKey) {
		try {
			Signature signature = Signature.getInstance(RSA_ALGORITHM_SIGN);
			KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
			byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			RSAPublicKey publicK = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
			signature.initVerify(publicK);
			signature.update(data.getBytes(CHARSET));
			return signature.verify(Base64.decodeBase64(sign));
		} catch (Exception e) {
			throw new RuntimeException("验签字符串[" + data + "]时遇到异常", e);
		}
	}

	private  static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
		int maxBlock = 0;
		if (opmode == Cipher.DECRYPT_MODE) {
			maxBlock = keySize / 8;
		} else {
			maxBlock = keySize / 8 - 11;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] buff;
		int i = 0;
		try {
			while (datas.length > offSet) {
				if (datas.length - offSet > maxBlock) {
					buff = cipher.doFinal(datas, offSet, maxBlock);
				} else {
					buff = cipher.doFinal(datas, offSet, datas.length - offSet);
				}
				out.write(buff, 0, buff.length);
				i++;
				offSet = i * maxBlock;
			}
		} catch (Exception e) {
			throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
		}
		byte[] resultDatas = out.toByteArray();
		IOUtils.closeQuietly(out);
		return resultDatas;
	}
	
	
	public static void main(String[] args){
//		 Map<String, String> keys = RSAUtils.createKeys(1024);
//		 String  publicKey = keys.get("publicKey");
//		 String  privateKey= keys.get("privateKey");
		 
		 String  publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1FDuJX-TGtD8QhAiBVbCdHeCXRPFn1CKCYKzDfS1aVYhCy4DOjnBZF-__K_DTvzF0B-rOv82nNNR9NgV-wIN7uxf1nbaW5lU4gUPoXc41NjvaqpmvK8iFXBFPXSSCHsQBgBEzVpJ5IDnJ4_ZVxobBlpJpJB3HtZh9xVPl_l4DJQIDAQAB";
		 String  privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALUUO4lf5Ma0PxCECIFVsJ0d4JdE8WfUIoJgrMN9LVpViELLgM6OcFkX7_8r8NO_MXQH6s6_zac01H02BX7Ag3u7F_WdtpbmVTiBQ-hdzjU2O9qqma8ryIVcEU9dJIIexAGAETNWknkgOcnj9lXGhsGWkmkkHce1mH3FU-X-XgMlAgMBAAECgYAZGsr-SrsvCUJNCeEb_UNVJRVEI5zMZk66gKizGaG2CnGvwQMEoB7XW0g8ulzTHoi-q3JUY6chbWOgUGMGpa4Ut6vruknZ6OCLN9j3ID_-H0tjKgreIFPC31k35PuEuSWzbd5iYs9xPH7lqVwscUK3ji6ivjcCPi--yinTSI9VqQJBAN2ktnFP4tBslLauArJlFo3RBIwa2NUqHxqs5hczLbRtLrtybscon9nRQ4K0YMaF6etupCxSlj-elTVlXHv-EpMCQQDRJdaUxifPEEBgz4QmKqxJXjNzlW3Uu_MZqaGBWkM1siavm1LBSdciramXeK3J_KZHu3BOPgXBbgTouQUTLY5nAkEA0j4p6XZTuk0lC9woJryJFmEMpTHaOOnJs24KnDspwZpH1_sGZdh9OQqttVAQ9H-WkoLTLE_ywcpV0t6hSeSKDQJAKYP6SUPgSRDvxofKsozL8DBbxDjIOW8pfVGXtoaFStZXKYqKbcXLh52zSVbyIGMfWqPRUiarm87L-KopYLotRwJBAJ7JHfVl3GEHzjH7lE9wp8_kRb6xXdQGZLiWgjHcigHcPk7I5fb2-Sno4Ut825gDFrwOv1fhdtz5sfjAbyQOYl4";

		 
		 System.out.println("公钥：" + publicKey);
		 System.out.println("私钥：" + privateKey);
		 
		 String pain = "categoryId=212&customerName=156&customerMobile=15117585665&cityId=1&address=888&hmsr=bd_xshz_grcpa&znsr=null&customerRequirement=服务类型:接通:是用餐:做饭卫生:老人:孩子数量:孩子年龄:休息:聘用时间:预产期:&longitude=null&latitude=null&company=guorui&ts=1555486869968";
		 System.out.println("明文：" + pain);
		 
		 System.out.println("私钥签名,公钥验证,---------------------------");
		 String sign = RSAUtils.sign(pain, privateKey);
		 System.out.println("签名：" + sign);
		 System.out.println("验签：" +  RSAUtils.verify(pain, sign, publicKey));
		 
		 System.out.println("私钥加密,公钥解密---------------------------");
		 
		 String encrypted = RSAUtils.privateEncrypt(pain, privateKey);
		 System.out.println("加密： " + encrypted);
		 String decrypted = RSAUtils.publicDecrypt(encrypted, publicKey);
		 System.out.println("解密： " + decrypted);
//		 
//         System.out.println("公钥加密,私钥解密---------------------------");
//		 
//		 encrypted = RSAUtils.publicEncrypt(pain, privateKey);
//		 System.out.println("加密： " + encrypted);
//		 decrypted = RSAUtils.privateDecrypt(encrypted, publicKey);
//		 System.out.println("解密： " + decrypted);
//		 

		 
		 
	}
}
