package com.lind.basic.algorithm;

import com.lind.basic.util.HmacUtils;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * jwt算法.
 * jwt=base64UrlEncode(header)+"."+base64UrlEncode(payload)+"."+signature
 * signature=hmacsha256(base64UrlEncode(header)+"."+base64UrlEncode(payload),secret)
 */
@Slf4j
public class JwtTest {
    static final String secret = "lind";

    @Test
    public void stringToByteArray() {
        String val = "abc";
        byte[] arr = val.getBytes();
        String base64 = Base64UrlCodec.BASE64URL.encode(arr);
        logger.info("val={}", new String(arr));
    }

    @Test
    public void base64Url() {
        String header = Base64UrlCodec.BASE64URL.encode("header string".getBytes());
        String headerSource = new String(Base64UrlCodec.BASE64URL.decode(header));
        logger.info(header);
        logger.info(headerSource);
    }

    @Test
    public void jwt() throws Exception {
        String header = "jwt";
        String payload = "{username:'lind'}";
        String signature = new String(HmacUtils.hmacsha1Encrypt(Base64UrlCodec.BASE64URL.encode(header) + "." + Base64UrlCodec.BASE64URL.encode(payload), secret));
        String token = Base64UrlCodec.BASE64URL.encode(header) +
                "." +
                Base64UrlCodec.BASE64URL.encode(payload) +
                "." +
                signature;
        logger.info("token={}", token);

    }
}
