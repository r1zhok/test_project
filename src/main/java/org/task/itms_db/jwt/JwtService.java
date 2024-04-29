package org.task.itms_db.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.task.itms_db.entity.EmployeeEntity;
import org.task.itms_db.entity.UserEntity;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "34536f69513772484333516c6d354a51655473774c797868366f742f753139417070673178634753316c66302f5970426d436173554c324e5033576b7273632b";

    public String extractUsername(String token) throws ExpiredJwtException {
        return extractClaim(token, Claims::getSubject);
    }

    public List<Integer> extractEmployeeAndFeedback(String token) throws ExpiredJwtException {
        List<Integer> listClaims = new ArrayList<>();
        listClaims.add(((Integer) extractClaim(token, claims -> claims.get("talent_id"))));
        listClaims.add((Integer) extractClaim(token, claims -> claims.get("feedback_id")));

        return listClaims;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException {
        return claimsResolver.apply(extractAllClaims(token));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }
    public String generateTokenWithUsernameAndPassword(Long employeeId, Long feedbackId) {
        return generateTokenLink(new HashMap<>(), employeeId, feedbackId);
    }

    public String generateTokenLink(
            Map<String, Object> extraClaims,
            Long employeeId,
            Long feedbackId
    ) {
        extraClaims.put("talent_id", employeeId);
        extraClaims.put("feedback_id", feedbackId);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(String.valueOf(employeeId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignedKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(String token, UserDetails userDetails) throws ExpiredJwtException {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) throws ExpiredJwtException {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) throws ExpiredJwtException {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSignedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignedKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}