package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.back;
import static java.time.Duration.ofSeconds;

public class UploadPage {
    private SelenideElement addMoneyHeading = $(withText("Пополнение карты"));
    private SelenideElement amountField = $("[data-test-id='amount'] [type='text']");
    private SelenideElement fromField = $("[data-test-id='from'] [type='tel']");
    private SelenideElement uploadButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorBox = $("[data-test-id=error-notification]");

    public UploadPage() {
        addMoneyHeading.shouldBe(Condition.visible, ofSeconds(10));
    }

    public DashboardPage shouldTransferMoneyBetweenCards(int amount, String cardFrom) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        fromField.setValue(cardFrom);
        uploadButton.click();
        return new DashboardPage();
    }

    public void shouldWarnThatTransferAmountIsOutOfLimit(int amount, String cardFrom) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        amountField.setValue(String.valueOf(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.BACK_SPACE);
        fromField.setValue(cardFrom);
        uploadButton.click();
        errorBox.shouldBe(visible, ofSeconds(15));
        $("[data-test-id=error-notification]>.notification__title")
                .shouldHave(text("Ошибка"));
        return;
    }
}
