package com.bank.waterloo.security;

import com.bank.waterloo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static String  JWT_SECRET_KEY ="waterloo_secret_key";

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    public static User validate(String token) {

        User jwtUser = null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new User();
            jwtUser.setName(body.getSubject());
            jwtUser.setId(Long.parseLong((String) body.get("id")));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return jwtUser;
    }

    public static String generate(User user) {

        Claims claims = Jwts.claims()
                .setSubject(user.getName());
        claims.put("id", String.valueOf(user.getId()));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY)
                .compact();
    }
}
