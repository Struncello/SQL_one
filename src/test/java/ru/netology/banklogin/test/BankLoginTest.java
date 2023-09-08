package ru.netology.banklogin.test;

import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;

import static ru.netology.banklogin.data.SQLHelper.cleanDatabase;

public class BankLoginTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Schould successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(String.valueOf(authInfo));
        verificationPage.validVerify();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify();
    }

    @Test
    @DisplayName("Schould get error notofication if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomLogin();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Schould get error notofication with exist in base and active user, random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomLogin();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.validVerify();
        var verificationCode = DataHelper.generateVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.notifyAll();
    }
}