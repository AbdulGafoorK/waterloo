package com.bank.waterloo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtil {

    private static final String ALGORITHM_NAME = "SHA-256";

    public static String sha256Password(String password)
            throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(ALGORITHM_NAME);

        md.update(password.getBytes());

        byte byteData[] = md.digest();

        // convert the byte to hex format method 1

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < byteData.length; i++) {

            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
                    .substring(1));

        }

        // convert the byte to hex format method 2

        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < byteData.length; i++) {

            String hex = Integer.toHexString(0xff & byteData[i]);

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);

        }

        return hexString.toString();

    }
}
