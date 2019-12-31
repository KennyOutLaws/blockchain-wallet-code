package io.honestnode.blockchain.wallet;

import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.ChildNumber;
import io.honestnode.blockchain.common.Network;
import org.web3j.crypto.Keys;

public class EthHDWallet extends HDWallet {

  public static final ChildNumber[] DEFAULT_PATH = new ChildNumber[]{
      new ChildNumber(44, true),
      new ChildNumber(60, true),
      new ChildNumber(0, true),
      new ChildNumber(0, false),
      new ChildNumber(0, false),
  };

  public EthHDWallet() {
    super(DEFAULT_PATH);
  }

  public EthHDWallet(ChildNumber[] path) {
    super(path);
  }

  public EthHDWallet(int mnemonicLength) {
    super(mnemonicLength, DEFAULT_PATH);
  }

  public EthHDWallet(int mnemonicLength, ChildNumber[] path) {
    super(mnemonicLength, path);
  }

  public EthHDWallet(int mnemonicLength, String passphrase) {
    super(mnemonicLength, passphrase, DEFAULT_PATH);
  }

  public EthHDWallet(int mnemonicLength, String passphrase, ChildNumber[] path) {
    super(mnemonicLength, passphrase, path);
  }

  public EthHDWallet(String mnemonic) {
    super(mnemonic, DEFAULT_PATH);
  }

  public EthHDWallet(String mnemonic, ChildNumber[] path) {
    super(mnemonic, path);
  }

  public EthHDWallet(String mnemonic, String passphrase) {
    super(mnemonic, passphrase, DEFAULT_PATH);
  }

  public EthHDWallet(String mnemonic, String passphrase, ChildNumber[] path) {
    super(mnemonic, passphrase, path);
  }

  @Override
  public String getAddress(Network network, AddressType type) {
    switch (type) {
      case P2KH:
        return "0x" + Keys.getAddress(key);
      default:
        throw new UnsupportedOperationException();
    }
  }

  public String getPrivateKey() {
    return getHexPrivateKey();
  }

  @Override
  public String toString() {
    return "EthHDWallet{" +
        ", mnemonic='" + mnemonic + '\'' +
        '}';
  }
}
