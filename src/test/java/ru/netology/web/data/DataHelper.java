package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.junit.jupiter.api.Test;

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
        private String[] cardsNumbers;
    }

    public static CardNumber getCardsNumbers() {
        return new CardNumber(new String[]{"5559 0000 0000 0001", "5559 0000 0000 0002"});

    }

    public static String getFullCardNumber(int index) {
        return getCardsNumbers().getCardsNumbers()[index];
    }

    public static String get4LastDigitsOfCardNumber(String cardNumber) {
        String lastDigits = cardNumber.substring(cardNumber.length() - 4);
        return lastDigits;
    }

    public static int getLengthOfCardsArray() {
        return getCardsNumbers().cardsNumbers.length;
    }

    public static int generateIndexForCardToWhichTransfer(int limit) {
        Faker faker = new Faker();
        int transferToCard = faker.random().nextInt(0, limit);
        return transferToCard;
    }

    public static int generateIndexForCardFromWhichTransfer(int limit, int exclusion) {
        Faker faker = new Faker();
        int transferFromCard = faker.random().nextInt(0, limit);
        while (transferFromCard == exclusion) {
            transferFromCard = faker.random().nextInt(0, limit);
        }
        return transferFromCard;
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static int generateTransferAmount(int limit) {
        Faker faker = new Faker();
        return faker.random().nextInt(1, limit);
    }
}
