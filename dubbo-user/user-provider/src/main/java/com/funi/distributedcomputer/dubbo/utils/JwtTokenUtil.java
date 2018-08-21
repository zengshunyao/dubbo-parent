package com.funi.distributedcomputer.dubbo.utils;

import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * 生成token工具类
 */
public class JwtTokenUtil {
    /**
     * @return
     */
    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] dc = DatatypeConverter.parseBase64Binary("funi-user");
        return new SecretKeySpec(dc, signatureAlgorithm.getJcaName());
    }

    /**
     * 生成token的方法
     *
     * @param jwtInfo
     * @param expire
     * @return
     */
    public static String generateToken(JwtInfo jwtInfo, int expire) {
        return Jwts.builder().claim(JwtConstants.JWT_KEY_USER_ID, jwtInfo.getUid())
                .setExpiration(DateTime.now().plus(expire).toDate())
                .signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    /**
     * 根据token获得token中的信息
     *
     * @param token
     * @return
     */
    public static JwtInfo getTokenInfo(String token) {
        Jws<Claims> claimsJwts = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
        Claims claims = claimsJwts.getBody();
        return new JwtInfo(claims.get(JwtConstants.JWT_KEY_USER_ID).toString());
    }
}
