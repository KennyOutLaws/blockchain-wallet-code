package io.honestnode.blockchain.util;

public class ByteArrayUtils { // Change to ByteArrayUtils

  public static byte[] convert(byte[] bytes2Bits, int from, int to, boolean strictMode) {
    int length = (int) (strictMode ? Math.floor((double) bytes2Bits.length * from / to) : Math.ceil((double) bytes2Bits.length * from / to));
    int mask = ((1 << to) - 1) & 0xff;
    byte[] result = new byte[length];
    int index = 0;
    int accumulator = 0;
    int bits = 0;
    for (int i = 0; i < bytes2Bits.length; i++) {
      byte value = bytes2Bits[i];
      accumulator = ((accumulator & 0xff) << from) | (value & 0xff);
      bits = bits + from;
      while (bits >= to) {
        bits = bits - to;
        result[index++] = (byte) ((accumulator >> bits) & mask);
      }
    }
    if (!strictMode) {
      if (bits > 0) {
        result[index] = (byte) ((accumulator << (to - bits)) & mask);
        ++index;
      }
    } else {
      if (!(bits < from && ((accumulator << (to - bits)) & mask) == 0)) {
        throw new IllegalArgumentException("Strict mode was used but input couldn't be converted without padding");
      }
    }
    return result;
  }
}
