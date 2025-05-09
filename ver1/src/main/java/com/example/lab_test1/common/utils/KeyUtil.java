package com.example.lab_test1.common.utils;

import org.springframework.stereotype.Component;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

@Component
public class KeyUtil {
  @Value("${jwt.keystore.path}")
  private Resource keyStore;

  @Value("${jwt.keystore.type}")
  private String keyStoreType;

  @Value("${jwt.keystore.password}")
  private String keyStorePassword;

  @Value("${jwt.key.alias}")
  private String keyAlias;

  @Value("${jwt.key.password}")
  private String keyPassword;

  private KeyStore loadKeyStore() throws Exception {
    KeyStore ks = KeyStore.getInstance(keyStoreType);
    try {
      ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());
    } catch (Exception e) {
      throw new RuntimeException("Failed to load keystore", e);
    }
    return ks;
  }

  public PrivateKey getPrivateKey() throws Exception {
    return (PrivateKey) loadKeyStore().getKey(keyAlias, keyPassword.toCharArray());
  }

  public PublicKey getPublicKey() throws Exception {
    return loadKeyStore().getCertificate(keyAlias).getPublicKey();
  }

  public SecretKey getKeyV2(String alias, String keyPassword) throws Exception {
    return (SecretKey) loadKeyStore().getKey(alias, keyPassword.toCharArray());
  }
}
