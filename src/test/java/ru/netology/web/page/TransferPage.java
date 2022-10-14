package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

     public DashboardPage Transfer(int amount, String cardNumber) {
        amountField.setValue(Integer.toString(amount));
        fromField.setValue(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }


}
