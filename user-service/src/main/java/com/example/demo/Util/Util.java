package com.example.demo.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.example.demo.Dao.Userdao;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class Util {

	private static final String Key = "411657C24D2986D9EE41AFCF2722F394";
	private static final String Issuer = "ADMIN";
	private static final String Subject =" AccessKey";
	private static final String sess_username = "SESS_USERNAME";
	
	@Autowired
	private Userdao userdao;
	
	public String createToken(String username) {
		
		
		Map<String, Object> map = new HashMap<>();
		map.put(sess_username, username);
		
		SignatureAlgorithm algo = SignatureAlgorithm.HS256;
		

		return Jwts.builder().setIssuer(Issuer).setClaims(map).setSubject(Subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis()+ (1000*60*60)))
	                .signWith(algo, Key).compact();
		 
	}
	
	public String createTokenAdmin(String username) {
		
		
		Map<String, Object> map = new HashMap<>();
		map.put(sess_username, username);
		
		SignatureAlgorithm algo = SignatureAlgorithm.HS256;
		

		return Jwts.builder().setIssuer(Issuer).setClaims(map).setSubject(Subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis()+ (1000*60*60*24)))
	                .signWith(algo, Key).compact();
		 
	}
	
	public Claims CheckToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(Key).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            System.out.println("no user found");
        }
        return claims;
    }

    //    Check Expiration date
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(Key).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Key).parseClaimsJws(token).getBody();
    }

   
}


