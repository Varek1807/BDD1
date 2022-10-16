package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");
     public DashboardPage transfer(int amount, String cardNumber) {
        amountField.setValue(Integer.toString(amount));
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }


    public DashboardPage getNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(20));
       errorNotification.shouldHave(Condition.exactText("Ошибка"));
        return new DashboardPage();
    }

}
