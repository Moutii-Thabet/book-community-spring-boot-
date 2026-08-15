package com.moutii.book_community.security;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class KeyUtils {
    private KeyUtils(){}
    public static PrivateKey getSecretKey(final String path) throws Exception {
        final String key = getKeyFromFile(path)
                .replace("-----BEGIN PRIVATE KEY-----","")
                .replace("-----END PRIVATE KEY-----","")
                .replace("\\s+","");
        final byte[] keyBytes = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    public static PublicKey getPublicKey(final String path) throws Exception {
        final String key = getKeyFromFile(path)
                .replace("-----BEGIN PUBLIC KEY-----","")
                .replace("-----END PUBLIC KEY-----","")
                .replace("\\s+","");
        final byte[] keyBytes = Base64.getDecoder().decode(key);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }

    private static String getKeyFromFile(String path) throws Exception {
        try(InputStream is = KeyUtils.class.getResourceAsStream(path)) {
            if(is==null) {
                throw new IllegalArgumentException("resources not found with path name "  + path);
            }
            return new String(is.readAllBytes());
        }

    }
}
