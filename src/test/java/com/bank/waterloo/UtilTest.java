package com.bank.waterloo;

import com.bank.waterloo.util.CommonUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class UtilTest {

    @Test
    void sha256Password() throws NoSuchAlgorithmException {
        String password = CommonUtil.sha256Password("pswd@123");
        Assert.assertEquals("a8c1587547303196e23d2d8611d6a084044d089b3de8bd0990d3dec5d3b2c3fc", password);
    }
}
