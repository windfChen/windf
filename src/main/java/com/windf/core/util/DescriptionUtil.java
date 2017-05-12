package com.windf.core.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DescriptionUtil {
    private static final String DES = "DES";
    private static final String UTF8 = "UTF-8";
    public static final String KEY = "WHATYUCENTER11LL00";
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws Exception {
        System.out.println(URLEncoder.encode("E5rCKqQXGkI%3D"));
        //Map<String, String> map = new HashMap<String, String>();
//        String data = "ucenterKey=Ii4Hg5WGyDKfMbnXdFW8MOqxe3WhFDcQdzzBLBsIFtufkS%2Flxx6S6uFa3ZjnlVa4DpHPGLEM8O5E%0AG9nvPsz2GeGoGDmzuWwTTWsZDbdt57ryZgdJabVG7aevGAwJJq3z87rGqPu6CnPK2%2BSN1QYDu8Fx%0A0Ad4F8i3rAh712enaEE8MMveI7AGooNRaQSzIsWawFA3KJQedoKEqpxLjL5Qb%2BBQ%2BGeos4k%2BfXRv%0AZl%2Br274u4e5i46hrqOMOT3q8fEunlOBRAwyoLlG3UEZv5pFaOg%3D%3D";
       // String data = "sHBd7M%2BzV3Pm1BTu5DlKGHeEV5GPxEo7fSWFQqbaLAVUqHZFfcMwEgLHyhZijG1MPuDx4RwxzkkM%0Amvlt3eWpUFYkCA9DgIf5ANDgFGvaW5Ukuaz%2BLO3ePAbKPihaSRH9zSDU6wa0QO1UyeCjlI39VExu%0AKVIBo9x%2FMVwsg8R4tIJaP%2BsLR1I5s1s1QK3aOq4giiThYQzU7xcsF%2BXRx4c6e%2FPwRaHBa9SK";
//        map.put("key", data);
//        System.out.println(map.hashCode());
//        Map<String, String> map1 = new HashMap<String, String>();
//        map1.put("key", data);
//        System.out.println(map1.hashCode());
//        //String key = "wang!@#$%";
//        String key = KEY;
        String str = "root";
        String temp = DescriptionUtil.encrypt(str, KEY);
        System.out.println(temp);
        System.out.println(DescriptionUtil.decrypt("%2FQXATlbGR4HcuHA%2B8zG6Zi5RAm1m3k7q", KEY));
        //System.out.println(encrypt(data, key));
        //System.err.println(decrypt(encrypt(data, key), key));
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = new BASE64Encoder().encode(bt);
        strs = URLEncoder.encode(strs, UTF8);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException, Exception {
        if (data == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        data = URLDecoder.decode(data, UTF8);
        byte[] buf = decoder.decodeBuffer(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

        return cipher.doFinal(data);
    }
    
    /**
     * 对字符串进行MD5加密
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        MessageDigest msgDigest = null;

        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }

        msgDigest.update(text.getBytes());
        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    /**
     * 
     * @param data
     * @return
     */
    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
	}
}
