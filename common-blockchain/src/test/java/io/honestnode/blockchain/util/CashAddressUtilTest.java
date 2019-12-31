package io.honestnode.blockchain.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Lists;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.Network;
import java.util.List;
import org.junit.jupiter.api.Test;

class CashAddressUtilTest {

  private List<String> legacy = Lists
      .newArrayList("1BpEi6DfDAUFd7GtittLSdBeYJvcoaVggu", "1KXrWXciRDZUpQwQmuM1DbwsKDLYAYsVLR", "16w1D5WRVKJuZUsSRzdLp9w3YGcgoxDXb");
  private List<String> result = Lists
      .newArrayList("bitcoincash:qpm2qsznhks23z7629mms6s4cwef74vcwvy22gdx6a", "bitcoincash:qr95sy3j9xwd2ap32xkykttr4cvcu7as4y0qverfuy",
                    "bitcoincash:qqq3728yw0y47sqn6l2na30mcw6zm78dzqre909m2r");
}
