package com.bside.breadgood.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static String createToken(String subject, long expiration, String secretKey) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String getSubjectByToken(String token, String secretKey) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


    public static long getExpirationTime(String token, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getExpiration().getTime();
    }

    public static boolean validateAccessToken(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("유효하지 않은 JWT 서명");
            throw ex;
        } catch (MalformedJwtException ex) {
            logger.error("유효하지 않은 JWT 토큰");
            throw ex;
        } catch (ExpiredJwtException ex) {
            logger.error("만료된 JWT 토큰");
            throw ex;
        } catch (UnsupportedJwtException ex) {
            logger.error("지원하지 않는 JWT 토큰");
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("비어있는 JWT");
            throw ex;
        }
    }
}
