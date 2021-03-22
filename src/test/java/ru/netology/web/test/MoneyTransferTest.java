package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.UploadPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
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
    void shouldTransferMoneyBetweenRandomCardsWithinLimit() {
        //загружаем личный кабинет используя валидные данные и получаем рандомные карты для транзакций
        val dashboardPage = validAuthorithation();
        val cardPlusFull = DataHelper.findCardPlus();
        val cardMinusFull = DataHelper.findCardMinus(cardPlusFull);

        //производим между выбранными картами перевод рандомной суммы в пределах суммы, имеющейся на карте
        val initialBalanceCardPlus = dashboardPage.getCardsBalance(DataHelper.getLastDigits(cardPlusFull));
        val initialBalanceCardMinus = dashboardPage.getCardsBalance(DataHelper.getLastDigits(cardMinusFull));
        val uploadAmount = DataHelper.generateTransferAmountWithinLimit(initialBalanceCardMinus);
        val uploadPage = dashboardPage.moneyTransferClickButton(DataHelper.getLastDigits(cardPlusFull));
        val dashboardPage2 = uploadPage.shouldTransferMoneyBetweenCards(uploadAmount, cardMinusFull);
        val actualBalanceCardPlus = dashboardPage2.getCardsBalance(DataHelper.getLastDigits(cardPlusFull));
        val actualBalanceCardMinus = dashboardPage2.getCardsBalance(DataHelper.getLastDigits(cardMinusFull));
        assertEquals(initialBalanceCardPlus + uploadAmount, actualBalanceCardPlus);
        assertEquals(initialBalanceCardMinus - uploadAmount, actualBalanceCardMinus);

        //Теперь возвращаем баланс карт к исходному
        val uploadPage2 = dashboardPage2.moneyTransferClickButton(DataHelper.getLastDigits(cardMinusFull));
        val dashboardPage3 = uploadPage2.shouldTransferMoneyBetweenCards(uploadAmount, cardPlusFull);
        assertEquals(initialBalanceCardPlus, dashboardPage3.getCardsBalance(DataHelper.getLastDigits(cardPlusFull)));
        assertEquals(initialBalanceCardMinus, dashboardPage3.getCardsBalance(DataHelper.getLastDigits(cardMinusFull)));
    }

    @Test
    void shouldFailToAuthorizeWithInvalidAuthData() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getOtherAuthInfo(DataHelper.getAuthInfo());
        loginPage.invalidLogin(authInfo);
        $("[data-test-id=error-notification]").shouldBe(visible);
        $("[data-test-id=error-notification]>.notification__title")
                .shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]>.notification__content")
                .shouldHave(text("Неверно указан логин или пароль"));
    }
    @Test
    void shouldFailToAuthorizeWithInvalidVerificationCode() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getOtherVerificationCodeFor(authInfo);
        verificationPage.invalidVerify(verificationCode);
        $("[data-test-id=error-notification]").shouldBe(visible);
        $("[data-test-id=error-notification]>.notification__title")
                .shouldHave(text("Ошибка"));
        $("[data-test-id=error-notification]>.notification__content")
                .shouldHave(text("Ошибка! \nНеверно указан код! Попробуйте ещё раз."));
    }

    //следующий тест требует предварительного устранения бага в приложении - при переводе сверх лимита карта уходит в отрицательный баланс
//    void shouldFailToTransferMoneyFromFirstCardToSecondIfOutOfLimit() {
    //загружаем личный кабинет используя валидные данные и получаем рандомные карты для транзакций
//    val dashboardPage = validAuthorithation();
//    val cardPlusFull = DataHelper.findCardPlus();
//    val cardMinusFull = DataHelper.findCardMinus(cardPlusFull);
//    //производим между выбранными картами перевод рандомной суммы в пределах суммы, имеющейся на карте
//    val initialBalanceCardPlus = dashboardPage.getCardsBalance(DataHelper.getLastDigits(cardPlusFull));
//    val initialBalanceCardMinus = dashboardPage.getCardsBalance(DataHelper.getLastDigits(cardMinusFull));
//    val uploadAmount = DataHelper.generateTransferAmountOutLimit(initialBalanceCardMinus);

}
