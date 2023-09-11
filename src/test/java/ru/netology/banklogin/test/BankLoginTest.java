package ru.netology.banklogin.test;

import com.mysql.cj.protocol.Warning;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.banklogin.data.DataHelper;
import ru.netology.banklogin.data.SQLHelper;
import ru.netology.banklogin.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.banklogin.data.SQLHelper.cleanDatabase;

public class BankLoginTest {

    @AfterAll
    static void teardown() {
        cleanDatabase();
    }

    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(String.valueOf(authInfo));
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        Warning VerificationCode = null;
        verificationPage.validVerify(String.valueOf(VerificationCode.getCode()));
    }

    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUserWithoutAddingToBase() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(String.valueOf(authInfo));
        loginPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Should get error notification with exist in base and active user, random verification code")
    void shouldGetErrorNotificationIfLoginWithExistUserAndRandomVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(String.valueOf(authInfo));
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateVRandomVerificationCode();
        verificationPage.verify((String) verificationCode);
        verificationPage.verifyErrorNotificationPageVisibility();
    }
}