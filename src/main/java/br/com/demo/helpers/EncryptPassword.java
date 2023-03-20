package br.com.demo.helpers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptPassword {
  private static final String ALGORITHM = "SHA-256";
  private static final String CHARSET_NAME = "UTF-8";

  public static String encrypt(String value) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    MessageDigest algorithm = MessageDigest.getInstance(ALGORITHM);
    byte messageDigest[] = algorithm.digest(value.getBytes(CHARSET_NAME));
    StringBuilder hexString = new StringBuilder();
    for (byte b : messageDigest) {
      hexString.append(String.format("%02X", 0xFF & b));
    }
    return hexString.toString();
  }

}