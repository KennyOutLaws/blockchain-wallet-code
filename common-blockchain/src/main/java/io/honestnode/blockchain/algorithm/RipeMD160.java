package io.honestnode.blockchain.algorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RipeMD160 {

  public static byte[] encode(String input) {
    if (input == null) {
      throw new IllegalArgumentException("Input should now be null.");
    }
    return encode(input.getBytes(StandardCharsets.UTF_8));
  }

  public static byte[] encode(byte[] input) {
    try {
      MessageDigest md = MessageDigest.getInstance("RipeMD160", new BouncyCastleProvider());
      md.update(input);
      return md.digest();
    } catch (NoSuchAlgorithmException ex) {
      throw new IllegalArgumentException(ex);
    }
  }
}
