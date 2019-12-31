package io.honestnode.blockchain.common;

import static org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT;

import java.io.Serializable;

public class ChildNumber implements Serializable {

  private static final long serialVersionUID = 2448142284910607321L;

  private int index;
  private boolean isHardened;
  private int number;

  public ChildNumber(int index, boolean isHardened) {
    this.index = index;
    this.isHardened = isHardened;
    if (isHardened) {
      this.number = index | HARDENED_BIT;
    } else {
      this.number = index;
    }
  }

  public int getIndex() {
    return index;
  }

  public boolean isHardened() {
    return isHardened;
  }

  public int getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return "HDWalletChildNumber{" +
        "index=" + index +
        ", isHardened=" + isHardened +
        ", number=" + number +
        '}';
  }
}
