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
    @Test
    void shouldTransferMoneyFromFirstCardToSecondWithinLimit() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val initialBalanceCard1 = dashboardPage.getFirstCardBalance();
        val initialBalanceCard2 = dashboardPage.getSecondCardBalance();
        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCard1);
        val uploadPage = dashboardPage.moneyTransferToSecondCardClick();
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, DataHelper.getFirstCardNumber());
        val actualBalanceCard1 = dashboardPage2.getFirstCardBalance();
        val actualBalanceCard2 = dashboardPage2.getSecondCardBalance();
        int expectedBalanceCard1 = initialBalanceCard1 - uploadAmount;
        int expectedBalanceCard2 = initialBalanceCard2 + uploadAmount;
        assertEquals(expectedBalanceCard1, actualBalanceCard1);
        assertEquals(expectedBalanceCard2, actualBalanceCard2);
    }
    @Test
    void shouldTransferMoneyFromSecondCardToFirstWithinLimit() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);

        val initialBalanceCard1 = dashboardPage.getFirstCardBalance();
        val initialBalanceCard2 = dashboardPage.getSecondCardBalance();
        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCard2);
        val uploadPage = dashboardPage.moneyTransferToFirstCardClick();
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, DataHelper.getSecondCardNumber());
        val actualBalanceCard1 = dashboardPage2.getFirstCardBalance();
        val actualBalanceCard2 = dashboardPage2.getSecondCardBalance();
        int expectedBalanceCard1 = initialBalanceCard1 + uploadAmount;
        int expectedBalanceCard2 = initialBalanceCard2 - uploadAmount;
        assertEquals(expectedBalanceCard1, actualBalanceCard1);
        assertEquals(expectedBalanceCard2, actualBalanceCard2);
    }
    void shouldFailToTransferMoneyFromFirstCardToSecondIfOutOfLimit() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        val initialBalanceCard1 = dashboardPage.getFirstCardBalance();
        val initialBalanceCard2 = dashboardPage.getSecondCardBalance();
        val uploadAmount = DataHelper.generateTransferAmount(initialBalanceCard1);
        val uploadPage = dashboardPage.moneyTransferToSecondCardClick();
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, DataHelper.getFirstCardNumber());
        val actualBalanceCard1 = dashboardPage2.getFirstCardBalance();
        val actualBalanceCard2 = dashboardPage2.getSecondCardBalance();
        int expectedBalanceCard1 = initialBalanceCard1 - uploadAmount;
        int expectedBalanceCard2 = initialBalanceCard2 + uploadAmount;
        assertEquals(expectedBalanceCard1, actualBalanceCard1);
        assertEquals(expectedBalanceCard2, actualBalanceCard2);
    }
}
