package com.bank.waterloo.util;

public class EnvironmentConstants {

    public final static String JWT_SECRET_KEY = "waterloo_secret_key";
    public final static String MSG_INVALID_USERNAME_PASSWORD = "User name or password are incorrect";
    public final static String MSG_BLOCK_USER = "Account has been Blocked. Please contact your branch manager";
    public final static Integer INCORRECT_PSWD_ENTRY_COUNT = 3;

    public static final String DB_URL = System.getenv("DB_URL");
    public static final String DB_USERNAME = System.getenv("DB_USERNAME");
    public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

}
