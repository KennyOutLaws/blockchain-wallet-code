package io.honestnode.blockchain.wallet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.ChildNumber;
import io.honestnode.blockchain.common.Network;
import java.security.SecureRandom;
import java.util.List;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.utils.Numeric;

public abstract class HDWallet {

  public static final int DEFAULT_MNEMONIC_LENGTH = 12;
  public static final String DEFAULT_PASSPHRASE = "";

  protected String mnemonic;
  protected List<String> mnemonicWords;
  protected Bip32ECKeyPair rootKey;
  protected ChildNumber[] path;
  protected Bip32ECKeyPair key;

  public HDWallet(ChildNumber[] path) {
    this(DEFAULT_MNEMONIC_LENGTH, DEFAULT_PASSPHRASE, path);
  }

  public HDWallet(int mnemonicLength, ChildNumber[] path) {
    this(mnemonicLength, DEFAULT_PASSPHRASE, path);
  }

  public HDWallet(int mnemonicLength, String passphrase, ChildNumber[] path) {
    Preconditions.checkArgument(mnemonicLength % 3 == 0, "Mnemonic length should be multiple of 3");
    Preconditions.checkArgument(mnemonicLength >= 12 && mnemonicLength <= 24, "Mnemonic length should be between 12 to 24");
    byte[] initialEntropy = new byte[mnemonicLength / 3 * 4];
    new SecureRandom().nextBytes(initialEntropy);
    mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
    initializeKey(mnemonic, passphrase);
    initializeDeriveKey(path);
  }

  public HDWallet(String mnemonic, ChildNumber[] path) {
    this(mnemonic, DEFAULT_PASSPHRASE, path);
  }

  public HDWallet(String mnemonic, String passphrase, ChildNumber[] path) {
    this.mnemonic = mnemonic;
    initializeKey(mnemonic, passphrase);
    initializeDeriveKey(path);
  }

  private void initializeKey(String mnemonic, String passphrase) {
    mnemonicWords = Lists.newArrayList(mnemonic.split(" "));
    byte[] seed = MnemonicUtils.generateSeed(mnemonic, passphrase);
    rootKey = Bip32ECKeyPair.generateKeyPair(seed);
  }

  private void initializeDeriveKey(ChildNumber[] path) {
    this.path = path;
    int[] finalPath = new int[path.length];
    for (int i = 0; i < path.length; ++i) {
      finalPath[i] = path[i].getNumber();
    }
    key = Bip32ECKeyPair.deriveKeyPair(rootKey, finalPath);
  }

  public String getMnemonic() {
    return mnemonic;
  }

  public List<String> getMnemonicWords() {
    return mnemonicWords;
  }

  public String getHexPrivateKey() {
    return Numeric.toHexStringNoPrefix(key.getPrivateKey());
  }

  public String getAddress(Network network) {
    return getAddress(network, AddressType.P2KH);
  }

  public abstract String getAddress(Network network, AddressType type);
}
