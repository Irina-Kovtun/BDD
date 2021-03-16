package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private ElementsCollection uploadButton = $$("[data-test-id=action-deposit]");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public DashboardPage() {
        heading.shouldBe(Condition.visible);
    }

    public int getFirstCardBalance() {
        val text = cards.first().text();
        return exactBalance(text);
    }

    public int getSecondCardBalance() {
        val text = cards.last().text();
        return exactBalance(text);
    }

    private int exactBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish).trim();
        return Integer.parseInt(value);
    }

    public UploadPage moneyTransferToFirstCardClick() {
        uploadButton.first().click();
        return new UploadPage();
    }

    public UploadPage moneyTransferToSecondCardClick() {
        uploadButton.last().click();
        return new UploadPage();
    }
}
