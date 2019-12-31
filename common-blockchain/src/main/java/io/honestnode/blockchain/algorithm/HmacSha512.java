package io.honestnode.blockchain.algorithm;

import java.nio.charset.StandardCharsets;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class HmacSha512 {

  public static byte[] encode(String input, String key) {
    if (input == null || key == null) {
      throw new IllegalArgumentException("Input should now be null.");
    }
    return encode(input.getBytes(StandardCharsets.UTF_8), key.getBytes(StandardCharsets.UTF_8));
  }

  public static byte[] encode(byte[] data, byte[] key) {
    SHA512Digest digest = new SHA512Digest();
    HMac hMac = new HMac(digest);
    hMac.init(new KeyParameter(key));
    hMac.reset();
    hMac.update(data, 0, data.length);
    byte[] out = new byte[64];
    hMac.doFinal(out, 0);
    return out;
  }
}
