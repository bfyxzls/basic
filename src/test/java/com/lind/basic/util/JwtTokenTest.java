package com.lind.basic.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 测试jwt token生成方式.
 */
@Slf4j
public class JwtTokenTest {


    /**
     * token是否过期
     *
     * @return true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    @Test
    public void generateTokenJwt() {

        String jwtToken = "Bearer " + Jwts.builder()
                //主题 放入用户名
                .setSubject("lind")
                //自定义属性 放入用户拥有请求权限
                .claim("permissions", "write")
                //失效时间:秒
                .setExpiration(new Date(System.currentTimeMillis() + 5 * 1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
        logger.info("jwt token:{}", jwtToken);
        logger.info("current:{}", LocalDateTime.now());
        Claims claims = getClaimByToken(jwtToken);
        logger.info("jwt isSigned:{}", claims.getExpiration());

    }

    public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] header = token.split("Bearer");
        token = header[1];
        try {
            return Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.debug("validate is token error ", e);
            return null;
        }
    }

}
