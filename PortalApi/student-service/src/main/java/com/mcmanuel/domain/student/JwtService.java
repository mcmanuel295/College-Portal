package com.mcmanuel.domain.student;

import com.mcmanuel.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt_secret}")
    private String secretKeyString ;

    @Value("${TOKEN_EXPIRATION}")
    private long TOKEN_EXPIRATION;

    public JwtService() throws NoSuchAlgorithmException {
//        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//        SecretKey secretKey =keyGen.generateKey();
//        secretKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());

    }

    public SecretKey key(){
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode( secretKeyString.getBytes()));
    }


    public String generateToken(String username){
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles", Role.STUDENT.name());
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+TOKEN_EXPIRATION))
                .signWith(key())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private  <T> T extractClaim(String token,Function<Claims,T> claimResolver){
       return claimResolver.apply(extractAllClaims(token));
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token){
        List<?> roles = extractClaim(token,claims -> claims.get("roles",List.class));
        if (roles ==null){
            return List.of();
        }
        return roles.stream()
                .map(String::valueOf)
                .toList();
    }

    public boolean verify(UserDetails userDetails, String token) {
        return userDetails.getUsername().equals(extractUsername(token)) && !extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token){
        return extractClaim(token,Claims::getExpiration);
    }
}
