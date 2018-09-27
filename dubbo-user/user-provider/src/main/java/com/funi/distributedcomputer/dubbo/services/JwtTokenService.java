package com.funi.distributedcomputer.dubbo.services;

import com.funi.distributedcomputer.dubbo.utils.JwtInfo;
import com.funi.distributedcomputer.dubbo.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

    /**
     * 过期时长(单位：)
     */
    @Value("${jwt.expire}")
    private int expire;

    /**
     * 根据JwtInfo生成token
     *
     * @param jwtInfo
     * @return
     */
    public String generateToken(JwtInfo jwtInfo) {
        return JwtTokenUtil.generateToken(jwtInfo, expire);
    }

    /**
     * 从token获得JwtInfo
     *
     * @param token
     * @return
     */
    public JwtInfo getInfoFromToken(String token) {
        return JwtTokenUtil.getTokenInfo(token);
    }
}
