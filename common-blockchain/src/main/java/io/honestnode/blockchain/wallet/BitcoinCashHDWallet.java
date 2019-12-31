package io.honestnode.blockchain.wallet;

import io.honestnode.blockchain.algorithm.Base32;
import io.honestnode.blockchain.algorithm.RipeMD160;
import io.honestnode.blockchain.algorithm.Sha256;
import io.honestnode.blockchain.common.AddressType;
import io.honestnode.blockchain.common.ChildNumber;
import io.honestnode.blockchain.common.Network;
import io.honestnode.blockchain.util.*;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;

public class BitcoinCashHDWallet extends BitcoinHDWallet {

  public static final String SEPARATOR = ":";
  public static final String MAIN_NET_PREFIX = "bitcoincash";
  public static final String TEST_NET_PREFIX = "bchtest";

  public static final ChildNumber[] DEFAULT_PATH = new ChildNumber[] {
      new ChildNumber(44, true),
      new ChildNumber(145, true),
      new ChildNumber(0, true),
      new ChildNumber(0, false),
      new ChildNumber(0, false),
  };

  public BitcoinCashHDWallet() {
    super(DEFAULT_PATH);
  }

  public BitcoinCashHDWallet(ChildNumber[] path) {
    super(path);
  }

  public BitcoinCashHDWallet(int mnemonicLength) {
    super(mnemonicLength, DEFAULT_PATH);
  }

  public BitcoinCashHDWallet(int mnemonicLength, ChildNumber[] path) {
    super(mnemonicLength, path);
  }

  public BitcoinCashHDWallet(int mnemonicLength, String passphrase) {
    super(mnemonicLength, passphrase, DEFAULT_PATH);
  }

  public BitcoinCashHDWallet(int mnemonicLength, String passphrase, ChildNumber[] path) {
    super(mnemonicLength, passphrase, path);
  }

  public BitcoinCashHDWallet(String mnemonic) {
    super(mnemonic, DEFAULT_PATH);
  }

  public BitcoinCashHDWallet(String mnemonic, ChildNumber[] path) {
    super(mnemonic, path);
  }

  public BitcoinCashHDWallet(String mnemonic, String passphrase) {
    super(mnemonic, passphrase, DEFAULT_PATH);
  }

  public BitcoinCashHDWallet(String mnemonic, String passphrase, ChildNumber[] path) {
    super(mnemonic, passphrase, path);
  }

  @Override
  public String toString() {
    return "BitcoinCashHDWallet{" +
        "mnemonic='" + mnemonic + '\'' +
        '}';
  }


  public String getCashAddress(Network network, AddressType type) {
    switch (type) {
      case P2KH:
        return getP2KHCashAddress(network);
      default:
        throw new UnsupportedOperationException();
    }

  }

  public String getP2KHCashAddress(Network network) {
    Byte networkPrefix = NETWORK_PREFIX.get(network);
    if (networkPrefix == null) {
      throw new IllegalArgumentException();
    }
    String prefixString = getPrefixString(network);
    return generateCashAddress(prefixString, AddressType.P2KH);
  }


    public String generateCashAddress(String prefixString, AddressType addressType) {
    byte[] prefix = getPrefixByte(prefixString);
    byte[] hash = getMd160PublicKey();
    byte[] payload = getPayload(hash, addressType);
    byte[] checksumInput = ArrayUtils.addAll(ArrayUtils.addAll(ArrayUtils.add(prefix, (byte) 0), payload), new byte[] {0, 0, 0, 0, 0, 0, 0, 0});
    byte[] checksumOutput = PolyMod(checksumInput);
    checksumOutput = ByteArrayUtils.convert(checksumOutput, 8, 5, true);
    return prefixString + SEPARATOR + Base32.encodeStandard(ArrayUtils.addAll(payload, checksumOutput));
  }

  private byte[] getPrefixByte(String prefixString) {
    byte[] prefixByte = new byte[prefixString.length()];
    for (int i = 0; i <= prefixString.length() - 1; i++) {
      prefixByte[i] = (byte) (prefixString.charAt(i) & 0x1f);//checksum requires lower 5 bits of the prefix
    }
    return prefixByte;
  }

  private  String getPrefixString(Network network) {
    switch (network) {
      case MAIN:
        return MAIN_NET_PREFIX;
      case TEST:
        return TEST_NET_PREFIX;
      default:
        throw new IllegalArgumentException("Illegal MoneyNetwork");
    }
  }

  private  byte getVersionByte(AddressType addressType) {
      if (addressType == null) {
          throw new NullPointerException("The addressType can't be null");
      }
      if (addressType == AddressType.P2KH) {
          return (byte) 0;
      } else {
          return (byte) 8;
      }
  }

  private  byte[] getPayload(byte[] hash, AddressType addressType) {
    byte[] payloadBytes = ArrayUtils.insert(0, hash, getVersionByte(addressType));
    payloadBytes = ByteArrayUtils.convert(payloadBytes, 8, 5, false);
    return payloadBytes;
  }

  private  byte[] PolyMod(byte[] checksumInput) {
    BigInteger c = BigInteger.ONE;
    for (byte d : checksumInput) {
      byte c0 = c.shiftRight(35).byteValue();
      c = c.and(new BigInteger("07ffffffff", 16)).shiftLeft(5).xor(new BigInteger(String.format("%02x", d), 16));
      if ((c0 & 0x01) != 0) {
        c = c.xor(new BigInteger("98f2bc8e61", 16));
      }
      if ((c0 & 0x02) != 0) {
        c = c.xor(new BigInteger("79b76d99e2", 16));
      }
      if ((c0 & 0x04) != 0) {
        c = c.xor(new BigInteger("f33e5fb3c4", 16));
      }
      if ((c0 & 0x08) != 0) {
        c = c.xor(new BigInteger("ae2eabe2a8", 16));
      }
      if ((c0 & 0x10) != 0) {
        c = c.xor(new BigInteger("1e4f43e470", 16));
      }
    }

    byte[] checksum = c.xor(BigInteger.ONE).toByteArray();
    //The return array might not be exactly 40 bits
    if (checksum.length == 5) {
      return checksum;
    } else {
      byte[] checksumNew = new byte[5];
      System.arraycopy(checksum, Math.max(0, checksum.length - 5), checksumNew, 0, Math.min(5, checksum.length));
      return checksumNew;
    }
  }
}
