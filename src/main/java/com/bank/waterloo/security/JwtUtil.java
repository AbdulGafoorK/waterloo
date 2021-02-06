package com.bank.waterloo.security;

import com.bank.waterloo.model.User;
import com.bank.waterloo.util.EnvironmentConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public static User validate(String token) {

        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(EnvironmentConstants.JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();
            jwtUser.setName(body.getSubject());
            jwtUser.setId(Long.parseLong((String) body.get("id")));
        } catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }

    public static String generate(User user) {

        Claims claims = Jwts.claims()
                .setSubject(user.getName());
        claims.put("id", String.valueOf(user.getId()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, EnvironmentConstants.JWT_SECRET_KEY)
                .compact();
    }
}
