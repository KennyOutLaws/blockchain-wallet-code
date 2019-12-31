package io.honestnode.blockchain.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.Lists;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.Network;
import java.util.List;
import org.junit.jupiter.api.Test;

class HDWalletTest {

  private List<String> mnemonicList = Lists.newArrayList("split flower fiscal zoo machine orbit budget liar syrup assault mixture ripple",
                                                         "kiwi better tissue sure circle advance steel faint swarm burst manual swarm",
                                                         "when siren danger frown reform bracket gentle egg tide corn marriage pattern",
                                                         "office expand assume insane net enable talk despair dial increase ecology morning effort rebel brick wear point wave",
                                                         "glance gauge hedgehog ready inmate shuffle suggest inject lawsuit rug fork burger bubble cook dolphin panther vote stomach visit balcony trade neutral cliff taxi");

  private List<String> bitcoinAddressList = Lists.newArrayList("1MFXp1Rd3KYhTEjqKhBJpYnNQ9R63BUjeH",
                                                               "19haozg2HcBHjuYNEJhZRF8mEUTKm45ZRo",
                                                               "1FDWSsYTRaDN2qbUwweC1GU7wEhwokavpX",
                                                               "12pH3nyuK4b4e4jzQkoD1CPqge9bgmScdJ",
                                                               "12FUbVRvMGFEgx68jtDFSk2jdwmfxnXNq7");

  private List<String> bitcoinCashAddressList = Lists.newArrayList("bitcoincash:qr327mnjuxu8p8t9z67q25xcgzz7g7lcdcm3p3szwa",
                                                                   "bitcoincash:qrh8x34ej07za7gwryrk2fxrkn6gk2dy3qe3uuvyx5",
                                                                   "bitcoincash:qzfm26nknw7z6gd84dv2cne580qvuwjkhqktwtvn52",
                                                                   "bitcoincash:qp9lasef2wurvt3t078zlj7ahmm0eqkpwsqfh9cjy4",
                                                                   "bitcoincash:qrpgzjvnwwlcajjmu0makgshjmtuhem4ryz4age9gw");

  private List<String> slpAddressList = Lists.newArrayList("simpleledger:qz93gh7z7tau7yx5hk808689gyycqd0gmcyf5d3vf5",
                                                           "simpleledger:qph5uv5rl09fm7gdplqr0hl4x77p7hvzmucsml5twt",
                                                           "simpleledger:qrsw3et9vefcvdqeagshgxtjj3zkdqsg2cyk4h3n8x",
                                                           "simpleledger:qqs85fw3p3ymylpzfpey7dznvk5zrynyjyx2p0czua",
                                                           "simpleledger:qqzcqkpga32fjuwy40m6tpl72z24tx0nmgadkxwuat");

  private List<String> ethAddressList = Lists.newArrayList("0x16a1b7369da9dd178623a426398494429ceaa0ef",
                                                           "0xfe0edf491e57740c689fd61c8cf9328ef7b5ea5d",
                                                           "0x030ca7f3aed85414b4a399d8dcda23458e05e39d",
                                                           "0x3d3caa1261a50fd1716142b2b9639442f8d2715a",
                                                           "0x920c8bd5722071eb206151b8bf7d4ec23c63864c");

  @Test
  void testBitcoinWallet() {
    for (int i = 0; i < mnemonicList.size(); ++i) {
      HDWallet wallet = new BitcoinHDWallet(mnemonicList.get(i));
      assertEquals(bitcoinAddressList.get(i), wallet.getAddress(Network.MAIN));
    }
  }

  @Test
  void testBitcoinCashWallet() {
    for (int i = 0; i < mnemonicList.size(); ++i) {
      BitcoinCashHDWallet wallet = new BitcoinCashHDWallet(mnemonicList.get(i));
      assertEquals(bitcoinCashAddressList.get(i), wallet.getCashAddress(Network.MAIN,AddressType.P2KH));
    }
  }

  @Test
  void testSlpWallet() {
    for (int i = 0; i < mnemonicList.size(); ++i) {
      SlpHDWallet wallet = new SlpHDWallet(mnemonicList.get(i));
      assertEquals(slpAddressList.get(i), wallet.getSlpAddress(Network.MAIN,AddressType.P2KH));
    }
  }

  @Test
  void testEthWallet() {
    for (int i = 0; i < mnemonicList.size(); ++i) {
      HDWallet wallet = new EthHDWallet(mnemonicList.get(i));
      assertEquals(ethAddressList.get(i), wallet.getAddress(Network.MAIN));
    }
  }

}