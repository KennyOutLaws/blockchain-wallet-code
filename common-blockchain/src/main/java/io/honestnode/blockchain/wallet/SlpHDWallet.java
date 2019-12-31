package io.honestnode.blockchain.wallet;

import io.honestnode.blockchain.algorithm.Base32;
import io.honestnode.blockchain.algorithm.RipeMD160;
import io.honestnode.blockchain.algorithm.Sha256;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.ChildNumber;
import io.honestnode.blockchain.common.Network;
import io.honestnode.blockchain.util.*;
import org.apache.commons.lang3.ArrayUtils;

public class SlpHDWallet extends BitcoinCashHDWallet {

  public static final String SEPARATOR = ":";
  public static final String MAIN_NET_PREFIX = "simpleledger";
  public static final String TEST_NET_PREFIX = "slptest";

  public static final ChildNumber[] DEFAULT_PATH = new ChildNumber[]{
      new ChildNumber(44, true),
      new ChildNumber(245, true),
      new ChildNumber(0, true),
      new ChildNumber(0, false),
      new ChildNumber(0, false),
  };

  public SlpHDWallet() {
    super(DEFAULT_PATH);
  }

  public SlpHDWallet(ChildNumber[] path) {
    super(path);
  }

  public SlpHDWallet(int mnemonicLength) {
    super(mnemonicLength, DEFAULT_PATH);
  }

  public SlpHDWallet(int mnemonicLength, ChildNumber[] path) {
    super(mnemonicLength, path);
  }

  public SlpHDWallet(int mnemonicLength, String passphrase) {
    super(mnemonicLength, passphrase, DEFAULT_PATH);
  }

  public SlpHDWallet(int mnemonicLength, String passphrase, ChildNumber[] path) {
    super(mnemonicLength, passphrase, path);
  }

  public SlpHDWallet(String mnemonic) {
    super(mnemonic, DEFAULT_PATH);
  }

  public SlpHDWallet(String mnemonic, ChildNumber[] path) {
    super(mnemonic, path);
  }

  public SlpHDWallet(String mnemonic, String passphrase) {
    super(mnemonic, passphrase, DEFAULT_PATH);
  }

  public SlpHDWallet(String mnemonic, String passphrase, ChildNumber[] path) {
    super(mnemonic, passphrase, path);
  }

  @Override
  public String toString() {
    return "SlpHDWallet{" + "mnemonic='" + mnemonic + '\'' + '}';
  }

  public String getP2KHSlpAddress(Network network) {
    Byte networkPrefix = NETWORK_PREFIX.get(network);
    if (networkPrefix == null) {
      throw new IllegalArgumentException();
    }
    String prefixString = getPrefixString(network);
    return generateCashAddress(prefixString,AddressType.P2KH);
  }
  public String getSlpAddress(Network network,AddressType type)
  {
    switch (type) {
      case P2KH:
        return getP2KHSlpAddress(network);
      default:
        throw new UnsupportedOperationException();
    }
  }

  private String getPrefixString(Network network) {
    switch (network) {
      case MAIN:
        return MAIN_NET_PREFIX;
      case TEST:
        return TEST_NET_PREFIX;
      default:
        throw new RuntimeException("MoneyNetwork not handled yet");
    }
  }

}
