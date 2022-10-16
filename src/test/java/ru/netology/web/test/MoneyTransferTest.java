package ru.netology.web.test;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferPage;


import java.time.Duration;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {

    @BeforeEach
    void login() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferMoneyFromFirstToSecond() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 1;
        int amount = 1;
        dashboardPage.transferTo(destinationCardIndex)
                .transfer(amount, DataHelper.getFirstCardNumber().getCardNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(initialBalanceFirst - amount, currentBalanceFirst);
        Assertions.assertEquals(initialBalanceSecond + amount, currentBalanceSecond);
    }

    @Test
    public void transferFromSecondCardToFirst() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 0;
        int amount = 2;
        dashboardPage.transferTo(destinationCardIndex)
                .transfer(amount, DataHelper.getSecondCardNumber().getCardNumber());
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(initialBalanceFirst + amount, currentBalanceFirst);
        Assertions.assertEquals(initialBalanceSecond - amount, currentBalanceSecond);

    }

    @Test
    public void transferFromSecondCardToFirstIfInsufficientFunds() {
        var dashboardPage = new DashboardPage();
        var initialBalanceFirst = dashboardPage.getFirstCardBalance();
        var initialBalanceSecond = dashboardPage.getSecondCardBalance();
        int destinationCardIndex = 0;
        int amount = 18000;
        var currentBalanceFirst = dashboardPage.getFirstCardBalance();
        var currentBalanceSecond = dashboardPage.getSecondCardBalance();
        dashboardPage.transferTo(destinationCardIndex)
                .transfer(amount, DataHelper.getSecondCardNumber().getCardNumber());
        var transferPage = new TransferPage();
        transferPage.getNotification();

        Assertions.assertEquals(initialBalanceFirst, currentBalanceFirst);
        Assertions.assertEquals(initialBalanceSecond, currentBalanceSecond);

    }
}
