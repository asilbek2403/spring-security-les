package programmer.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import programmer.dto.JwtDTO;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    //Access token = Jwt
    //RefreshToken new Access token



    public static String encode(String phone , String role){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("phone", phone);
        extraClaims.put("role", role);//unique boo'lgan qiymatlar bo'lsinda
//kalit str qiymat return obj

        return Jwts.builder()
                .claims(extraClaims)
                .subject(phone)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();

    }
    public static String generateRefreshT(String phone , String role){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("phone", phone);
        extraClaims.put("role", role);//unique boo'lgan qiymatlar bo'lsinda
//kalit str qiymat return obj

        return Jwts.builder()
                .claims(extraClaims)
                .subject(phone)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenLiveTime))
                .signWith(getSignInKey())
                .compact();

    }



    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String phone = (String) claims.getSubject();
        String role = (String) claims.get("role");
        return new JwtDTO(phone, role);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //refreshToken +++

    public static boolean isValid(String refreshToken) {

            Claims claims = Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(refreshToken)
                    .getPayload();

            return claims.getExpiration().after(new Date());

    }
//HAr xil serviceda kerakli metodlar uchun Jwt Basic
}
