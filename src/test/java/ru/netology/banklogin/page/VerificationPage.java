package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.banklogin.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public DashboardPage validVerify() {
        DataHelper.VerificationCode verificationCode = null;
        verify(null);
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        codeField.setValue(String.valueOf(verificationCode));
        verifyButton.click();
    }

}