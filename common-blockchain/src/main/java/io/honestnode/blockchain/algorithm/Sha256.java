package io.honestnode.blockchain.algorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {

  public static byte[] encode(String input) {
    if (input == null) {
      throw new IllegalArgumentException("Input should now be null.");
    }
    return encode(input.getBytes(StandardCharsets.UTF_8));
  }

  public static byte[] encode(byte[] input) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(input);
      return md.digest();
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalArgumentException(ex);
    }
  }

  public static byte[] encodeTwice(byte[] input) {
    return encode(encode(input));
  }
}
