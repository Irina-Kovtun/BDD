package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.UploadPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    // Метод для входа в Личный кабинет
    private DashboardPage validAuthorithation() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        return verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondWithinLimit() {
        val dashboardPage = validAuthorithation();
        int numberOfCards = DataHelper.getLengthOfCardsArray();
        int indexesInCardsArray = numberOfCards-1;
        val indexTo = DataHelper.generateIndexForCardToWhichTransfer(indexesInCardsArray);
        val indexFrom = DataHelper.generateIndexForCardFromWhichTransfer(indexesInCardsArray, indexTo);
        val cardNumberOfCardTo = DataHelper.getFullCardNumber(indexTo);
        val cardNumberOfCardFrom = DataHelper.getFullCardNumber(indexFrom);
        val last4digitsCardTo = DataHelper.get4LastDigitsOfCardNumber(cardNumberOfCardTo);
        val last4digitsCardFrom = DataHelper.get4LastDigitsOfCardNumber(cardNumberOfCardFrom);

        val initialBalanceCardTo = dashboardPage.getCardsBalance(DataHelper.get4LastDigitsOfCardNumber(cardNumberOfCardTo));
        val initialBalanceCardFrom = dashboardPage.getCardsBalance(DataHelper.get4LastDigitsOfCardNumber(cardNumberOfCardFrom));
        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCardFrom);
        val uploadPage = dashboardPage.moneyTransferClickButton(last4digitsCardTo);
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, cardNumberOfCardFrom);
        val actualBalanceCardTo = dashboardPage2.getCardsBalance(last4digitsCardTo);
        val actualBalanceCardFrom = dashboardPage2.getCardsBalance(last4digitsCardFrom);
        val expectedBalanceCardTo = initialBalanceCardTo + uploadAmount;
        val expectedBalanceCardFrom = initialBalanceCardFrom - uploadAmount;
        assertEquals(expectedBalanceCardTo, actualBalanceCardTo);
        assertEquals(expectedBalanceCardFrom, actualBalanceCardFrom);
        System.out.println(actualBalanceCardTo);
        System.out.println(expectedBalanceCardTo);
    }
//
//    @Test
//    void shouldTransferMoneyFromSecondCardToFirstWithinLimit() {
//        val dashboardPage = validAuthorithation();
//        val initialBalanceCard1 = dashboardPage.getFirstCardBalance();
//        val initialBalanceCard2 = dashboardPage.getSecondCardBalance();
//        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCard2);
//        val uploadPage = dashboardPage.moneyTransferToFirstCardClick();
//        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, DataHelper.getSecondCardNumber());
//        val actualBalanceCard1 = dashboardPage2.getFirstCardBalance();
//        val actualBalanceCard2 = dashboardPage2.getSecondCardBalance();
//        int expectedBalanceCard1 = initialBalanceCard1 + uploadAmount;
//        int expectedBalanceCard2 = initialBalanceCard2 - uploadAmount;
//        assertEquals(expectedBalanceCard1, actualBalanceCard1);
//        assertEquals(expectedBalanceCard2, actualBalanceCard2);
//    }
//
//    void shouldFailToTransferMoneyFromFirstCardToSecondIfOutOfLimit() {
//        open("http://localhost:9999");
//        val loginPage = new LoginPage();
//        val authInfo = DataHelper.getAuthInfo();
//        val verificationPage = loginPage.validLogin(authInfo);
//        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        val dashboardPage = verificationPage.validVerify(verificationCode);
//        val initialBalanceCard1 = dashboardPage.getFirstCardBalance();
//        val initialBalanceCard2 = dashboardPage.getSecondCardBalance();
//        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCard1);
//        val uploadPage = dashboardPage.moneyTransferToSecondCardClick();
//        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, DataHelper.getFirstCardNumber());
//        val actualBalanceCard1 = dashboardPage2.getFirstCardBalance();
//        val actualBalanceCard2 = dashboardPage2.getSecondCardBalance();
//        int expectedBalanceCard1 = initialBalanceCard1 - uploadAmount;
//        int expectedBalanceCard2 = initialBalanceCard2 + uploadAmount;
//        assertEquals(expectedBalanceCard1, actualBalanceCard1);
//        assertEquals(expectedBalanceCard2, actualBalanceCard2);
//    }
}
