package com.example.lab_test1.common.utils;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
    ks.load(keyStore.getInputStream(), keyStorePassword.toCharArray());
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
