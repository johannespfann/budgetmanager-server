package de.pfann.budgetmanager.server.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

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
