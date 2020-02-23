package cn.scau.springcloud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtUtils {
    public static final String TOKEN_HEADER = "Scau";
    public static final String TOKEN_PREFIX = "Bearer ";
    //过期时间，7天
    private static final long JWT_EXPIRATION = 604800L;
    //秘钥
    private static final String JWT_SECRET = "d965d209aee9e605f8b2fc2eba475b9d";

    public static String createJWT(String username) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", username);
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION * 1000))
                .compact();
        return jwt;
    }

    private static Claims parseJWT(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}
