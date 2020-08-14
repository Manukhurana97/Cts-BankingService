package com.example.demo.Util;

import java.util.Date;

import java.util.function.Function;



import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;



@Component
public class Util {

	private static final String Key = "411657C24D2986D9EE41AFCF2722F394";
	
	
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

       
}


