package com.yc.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 * md5加密出来的长度是32位
 * sha加密出来的长度是40位
 */
@Slf4j
public class Encrypt {
    /**
     * 测试
     */
    public static void main(String[] args) {
//        System.out.println(Encrypt.md5AndSha("ldjy20220701,.!"));
        //System.out.println(Encrypt.md5AndSha("scody520"));
        System.out.println(Encrypt.md5("21020440336"));
    }

    /**
     * 将输入密码转化为数据库中密码对等方式进行比较
     * @param s
     * @return
     */
    public static String fuc(String s){
        return Encrypt.sha(Encrypt.md5(s));
    }

    /**
     * 加密
     *
     * @param inputText
     * @return
     */
    public static String e(String inputText) {
        return md5(inputText);
    }

    /**
     * 二次加密，应该破解不了了吧？
     * @param inputText
     * @return
     */
    public static String md5AndSha(String inputText) {
        return sha(md5(inputText));
    }

    /**
     * md5加密
     * @param inputText
     * @return
     */
    public static String md5(String inputText) {
        return encrypt(inputText, "md5");
    }

    /**
     * sha加密
     * @param inputText
     * @return
     */
    public static String sha(String inputText) {
        return encrypt(inputText, "sha-1");
    }

    /**
     * md5或者sha-1加密
     *
     * @param inputText
     *            要加密的内容
     * @param algorithmName
     *            加密算法名称：md5或者sha-1，不区分大小写
     * @return
     */
    private static String encrypt(String inputText, String algorithmName) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            log.error(" encrypt Exception ",e);
        } catch (UnsupportedEncodingException e) {
            log.error(" encrypt Exception ",e);
        }
        return encryptText;
    }

    /**
     * 返回十六进制字符串
     * @param arr
     * @return
     */
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

}

