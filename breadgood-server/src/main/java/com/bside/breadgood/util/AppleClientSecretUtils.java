package com.bside.breadgood.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class AppleClientSecretUtils {
    private AppleClientSecretUtils() {
    }

    public static void main(String[] args) throws IOException {
        System.out.println(makeClientSecret());
    }

    public static String makeClientSecret() throws IOException {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(30).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("kid", "GYM8D48Y8V")
                .setHeaderParam("alg", "ES256")
                .setIssuer("42R8G4M88G")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject("com.breadgood.service")
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private static PrivateKey getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource("./config/AuthKey_GYM8D48Y8V.p8");
        String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        return converter.getPrivateKey(object);
    }


}
