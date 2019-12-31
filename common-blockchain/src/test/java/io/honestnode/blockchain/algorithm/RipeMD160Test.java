package io.honestnode.blockchain.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Lists;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

class RipeMD160Test {

  private List<String> sourceList = Lists.newArrayList("", "a", "abc", "message digest",
                                                       "abcdefghijklmnopqrstuvwxyz", "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
                                                       "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
  private List<String> encodedList = Lists.newArrayList("9c1185a5c5e9fc54612808977ee8f548b2258d31", "0bdc9d2d256b3ee9daae347be6f4dc835a467ffe",
                                                        "8eb208f7e05d987a9b044a8e98c6b087f15a0bfc", "5d0689ef49d2fae572b881b123a85ffa21595f36",
                                                        "f71c27109c692c1b56bbdceb5b9d2865b3708dbc", "12a053384a9c0c88e405a06c27dcf49ada62eb2b",
                                                        "b0e20b6e3116640286ed3a87a5713079b21f5189");

  @Test
  void encode() {
    for (int i = 0; i < sourceList.size(); ++i) {
      assertEquals(encodedList.get(i), Hex.toHexString(RipeMD160.encode(sourceList.get(i))));
    }
  }

  @Test
  void encodeNull() {
    String input = null;
    assertThrows(IllegalArgumentException.class, () -> RipeMD160.encode(input));
  }
}