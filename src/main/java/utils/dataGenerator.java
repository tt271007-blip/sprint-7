package utils;

import java.util.UUID;

public class dataGenerator {

    public static String generateRandomLogin() {
        return "login_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateRandomPassword() {
        return "pass_" + UUID.randomUUID().toString().substring(0, 8);
    }

    public static String generateRandomFirstName() {
        return "Name_" + UUID.randomUUID().toString().substring(0, 6);
    }
}