package io.honestnode.blockchain.util;

import com.google.common.collect.ImmutableMap;
import io.honestnode.blockchain.algorithm.Base58;
import io.honestnode.blockchain.algorithm.Sha256;
import io.honestnode.blockchain.common.Network;
import java.math.BigInteger;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

public class WifKeyUtil {

  protected static final Map<Network, Byte> NETWORK_PREFIX = ImmutableMap.of(Network.MAIN, (byte) 128, Network.TEST, (byte) 239);

  public static String generateWifKey(BigInteger privateKey, Network network) {
    return generateWifKey(privateKey.toByteArray(), network);
  }

  public static String generateWifKey(byte[] privateKey, Network network) {
    byte[] extend = ArrayUtils.add(ArrayUtils.insert(NETWORK_PREFIX.get(network), privateKey), (byte) 1);
    byte[] payload = Sha256.encodeTwice(extend);
    byte[] checksum = ArrayUtils.subarray(payload, 0, 4);
    return Base58.encode(ArrayUtils.addAll(extend, checksum));
  }
}
