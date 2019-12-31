package io.honestnode.blockchain.algorithm;

import com.google.common.base.Strings;
import java.math.BigInteger;
import java.util.Arrays;

public class Base58 {

  private static final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
  private static final BigInteger BASE = BigInteger.valueOf(58);
  private static final int[] INDEXES = new int[128];

  static {
    Arrays.fill(INDEXES, -1);
    for (int i = 0; i < ALPHABET.length; i++) {
      INDEXES[ALPHABET[i]] = i;
    }
  }

  private Base58() {}

  public static String encode(byte[] input) {
    if (input.length == 0) {
      return "";
    }
    StringBuilder result = new StringBuilder();
    BigInteger i = new BigInteger(1, input);
    while (i.compareTo(BigInteger.ZERO) != 0) {
      BigInteger remainder = i.mod(BASE);
      result.insert(0, ALPHABET[remainder.intValue()]);
      i = i.divide(BASE);
    }
    for (byte anInput : input) {
      if (anInput == 0) {
        result.insert(0, ALPHABET[0]);
      } else {
        break;
      }
    }
    return result.toString();
  }

  public static byte[] decode(String input) {
    if (Strings.isNullOrEmpty(input)) {
      return new byte[0];
    }
    BigInteger b = BigInteger.ZERO;
    for (int i = input.length() - 1; i >= 0; --i) {
      int index = INDEXES[input.charAt(i)];
      b = b.add(BigInteger.valueOf(index).multiply(BASE.pow(input.length() - 1 - i)));
    }
    int zeroCount = 0;
    for (char c : input.toCharArray()) {
      if (c == ALPHABET[0]) {
        ++zeroCount;
      } else {
        break;
      }
    }
    byte[] body;
    if (b.compareTo(BigInteger.ZERO) > 0) {
      body = b.toByteArray();
    } else {
      body = new byte[0];
    }
    int sign = (body.length > 1 && body[0] == 0 && body[1] < 0) ? 1 : 0;
    byte[] result = new byte[body.length + zeroCount - sign];
    System.arraycopy(body, sign, result, zeroCount, result.length - zeroCount);
    return result;
  }
}
