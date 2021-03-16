package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class CardNumber {
        private String cardNumber;
    }
    public static CardNumber getFirstCardNumber() {
        return new CardNumber("5559 0000 0000 0001");
    }
    public static CardNumber getSecondCardNumber() {
        return new CardNumber("5559 0000 0000 0002");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }


//    @Value
//    public static class SecondCardInitialBalance {
//        private int initialBalanceFirstCard;
//    }
//    public static SecondCardInitialBalance getSecondCardInitialBalance() {
//        return new SecondCardInitialBalance(10 000);
//    }

//    @Value
//    public static class SecondCardNumber {
//        private String secondCardNumber;
//    }
//    public static SecondCardNumber getSecondCardNumber() {
//        return new SecondCardNumber("5559 0000 0000 0002");
//    }
    public static int generateTransferAmount(int limit) {
        Faker faker = new Faker();
        return faker.random().nextInt(1, limit);
    }
}
