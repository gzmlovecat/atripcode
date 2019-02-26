package com.trip.entity;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AesUtil {
	private static final String ENCODING_FORMAT = "UTF-8";
	private static final String KEY_AES = "AES";
	private static final String PASSWORD_KEY = "atrip";
	
	/**
     * 加密
     * 
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
	public static byte[] encrypt(String content) {
        try {
        	SecretKeySpec key = null;
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(PASSWORD_KEY.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, KEY_AES);
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(password.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            byte[] byteContent = content.getBytes(ENCODING_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
     * 解密
     * 
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return
     */
	public static byte[] decrypt(byte[] content) {
        try {
        	SecretKeySpec key = null;
        	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(PASSWORD_KEY.getBytes());
            KeyGenerator kgen = KeyGenerator.getInstance(KEY_AES);
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key = new SecretKeySpec(enCodeFormat, KEY_AES);
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom(password.getBytes()));
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance(KEY_AES);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	/**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    
    /**
     * 加密
     *
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return
     */
//    public static byte[] encrypt2(String content, String password) {
//        try {
//            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
//            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
//            byte[] byteContent = content.getBytes("utf-8");
//            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
//            byte[] result = cipher.doFinal(byteContent);
//            return result; // 加密
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    
    /**
     * 解密用户敏感数据，获取用户信息
     * @param encryptedData	加密数据
     * @param signature	加密密钥
     * @param vi			偏移量
     * @return
     */
	public static String decryptData(String data, String key, String iv) throws Exception {
    	byte[] dataByte = Base64.getDecoder().decode(data);
    	byte[] keyByte = Base64.getDecoder().decode(key);
    	byte[] ivByte = Base64.getDecoder().decode(iv);
    	
    	try {
    		Security.addProvider(new BouncyCastleProvider());
    		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    		SecretKeySpec spec = new SecretKeySpec(keyByte, KEY_AES);
    		AlgorithmParameters params = AlgorithmParameters.getInstance(KEY_AES);
    		params.init(new IvParameterSpec(ivByte));
    		cipher.init(Cipher.DECRYPT_MODE, spec, params);	// 初始化
    		byte[] resultByte = cipher.doFinal(dataByte);
    		if(null != resultByte && resultByte.length > 0) {
    			String result = new String(resultByte, ENCODING_FORMAT);
    			return result;
    		}
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	
		return null;
	}
	
	public static String getSha1(String str) {
		if(str == null || str.length() == 0){ 
			return null; 
		
		} 
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j*2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];      
	      }
	      return new String(buf);
		} catch(Exception e) {
			return null;
		}
	}
    
    public static void main(String[] args) throws UnsupportedEncodingException {
//        String content = "我是shoneworn";
        String content = "{'repairPhone':'18547854787','customPhone':'12365478965','captchav':'58m7'}";  
        // 加密
        System.out.println("加密前：" + content);
        byte[] encode = encrypt(content);
        
        //传输过程,不转成16进制的字符串，就等着程序崩溃掉吧
        String code = parseByte2HexStr(encode);
        System.out.println("密文字符串：" + code);
        byte[] decode = parseHexStr2Byte(code);
        // 解密
        byte[] decryptResult = decrypt(decode);
        System.out.println("解密后：" + new String(decryptResult, ENCODING_FORMAT)); //不转码会乱码
         
    }
}
