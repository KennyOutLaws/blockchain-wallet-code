package io.honestnode.blockchain.algorithm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.collect.Lists;
import java.util.List;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

class Sha256Test {

  private List<String> sourceList = Lists.newArrayList("", "abc", "abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq",
                                                       "abcdefghbcdefghicdefghijdefghijkefghijklfghijklmghijklmnhijklmnoijklmnopjklmnopqklmnopqrlmnopqrsmnopqrstnopqrstu");
  private List<String> encodedList = Lists.newArrayList("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                                                        "ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad",
                                                        "248d6a61d20638b8e5c026930c3e6039a33ce45964ff2167f6ecedd419db06c1",
                                                        "cf5b16a778af8380036ce59e7b0492370b249b11e8f07a51afac45037afee9d1");

  @Test
  void encode() {
    for (int i = 0; i < sourceList.size(); ++i) {
      assertEquals(encodedList.get(i), Hex.toHexString(Sha256.encode(sourceList.get(i))));
    }
  }

  @Test
  void encodeNull() {
    String input = null;
    assertThrows(IllegalArgumentException.class, () -> Sha256.encode(input));
  }
}