package io.honestnode.blockchain.wallet;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

class SlpHDWalletTest {

  private static final Logger LOGGER = LogManager.getLogger(SlpHDWalletTest.class);

  @Test
  void generateNewWallet() {
    SlpHDWallet wallet = new SlpHDWallet("clarify lizard custom pool light tissue spend robot clever inner lounge ugly");
    LOGGER.info("{}", wallet.key.getPrivateKey());
  }
}