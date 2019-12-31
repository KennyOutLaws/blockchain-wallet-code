package io.honestnode.blockchain.wallet;

import com.google.common.collect.ImmutableMap;
import io.honestnode.blockchain.algorithm.Base58;
import io.honestnode.blockchain.algorithm.RipeMD160;
import io.honestnode.blockchain.algorithm.Sha256;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.ChildNumber;
import io.honestnode.blockchain.common.Network;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;

public class BitcoinHDWallet extends HDWallet {

  public static final ChildNumber[] DEFAULT_PATH = new ChildNumber[]{
      new ChildNumber(44, true),
      new ChildNumber(0, true),
      new ChildNumber(0, true),
      new ChildNumber(0, false),
      new ChildNumber(0, false),
  };
  public static final Map<Network, Byte> NETWORK_PREFIX = ImmutableMap.of(Network.MAIN, (byte) 0x00, Network.TEST, (byte) 0x6f);

  public BitcoinHDWallet() {
    super(DEFAULT_PATH);
  }

  public BitcoinHDWallet(ChildNumber[] path) {
    super(path);
  }

  public BitcoinHDWallet(int mnemonicLength) {
    super(mnemonicLength, DEFAULT_PATH);
  }

  public BitcoinHDWallet(int mnemonicLength, ChildNumber[] path) {
    super(mnemonicLength, path);
  }

  public BitcoinHDWallet(int mnemonicLength, String passphrase) {
    super(mnemonicLength, passphrase, DEFAULT_PATH);
  }

  public BitcoinHDWallet(int mnemonicLength, String passphrase, ChildNumber[] path) {
    super(mnemonicLength, passphrase, path);
  }

  public BitcoinHDWallet(String mnemonic) {
    super(mnemonic, DEFAULT_PATH);
  }

  public BitcoinHDWallet(String mnemonic, ChildNumber[] path) {
    super(mnemonic, path);
  }

  public BitcoinHDWallet(String mnemonic, String passphrase) {
    super(mnemonic, passphrase, DEFAULT_PATH);
  }

  public BitcoinHDWallet(String mnemonic, String passphrase, ChildNumber[] path) {
    super(mnemonic, passphrase, path);
  }

  @Override
  public String getAddress(Network network, AddressType type) {
    switch (type) {
      case P2KH:
        return getP2KHAddress(network);
      default:
        throw new UnsupportedOperationException();
    }
  }

  private String getP2KHAddress(Network network) {
    Byte networkPrefix = NETWORK_PREFIX.get(network);
    if (networkPrefix == null) {
      throw new IllegalArgumentException();
    }
    byte[] md160 = getMd160PublicKey();
    byte[] extendedMd160 = ArrayUtils.insert(0, md160, networkPrefix);
    byte[] payload = Sha256.encodeTwice(extendedMd160);
    byte[] checksum = ArrayUtils.subarray(payload, 0, 4);
    return Base58.encode(ArrayUtils.addAll(extendedMd160, checksum));
  }

  protected byte[] getMd160PublicKey() {
    byte[] publicKey33 = key.getPublicKeyPoint().getEncoded(true);
    return RipeMD160.encode(Sha256.encode(publicKey33));
  }
}
