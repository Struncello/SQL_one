package ru.netology.banklogin.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.banklogin.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id=code] input");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        verify(verificationCode.getCode());
        return new DashboardPage();
    }

    public void verifyErrorNotificationVisibility() {
        errorNotification.shouldBe(visible);
    }

    public void verify(String verificationCode) {
        codeField.setValue(String.valueOf(verificationCode));
        verifyButton.click();
    }

}