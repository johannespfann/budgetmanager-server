package de.pfann.budgetmanager.server.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptUtil {

    private static Key key = MacProvider.generateKey();

    public static String encrypt(String aValue, String aKey){
        return  Jwts.builder()
                .setSubject(aValue)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static String decrypt(String aValue, String aKey){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(aValue).getBody().getSubject();
    }



}
