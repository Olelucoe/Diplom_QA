package page;

import data.Card;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement cardNumberField =  $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement holderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvcField = $("[placeholder='999']");

    private SelenideElement continueButton = $$("button").find(exactText("Продолжить"));

    private SelenideElement notificationSuccess = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");

    private SelenideElement validationFieldMessage = $(byText("Поле обязательно для заполнения"));

    private SelenideElement wrongFormatMessage = $(byText("Неверный формат"));

    private SelenideElement invalidCharMessage = $(byText("Поле содержит недопустимые символы"));

    private SelenideElement wrongExpirationMessage = $(byText("Неверно указан срок действия карты"));

    private SelenideElement cardExpiredMessage = $(byText("Истёк срок действия карты"));


    public PaymentPage() {
        heading.shouldBe(visible);
    }

    public void fillData(Card card) {
        cardNumberField.setValue(card.getNumber());
        monthField.setValue(card.getMonth());
        yearField.setValue(card.getYear());
        holderField.setValue(card.getHolder());
        cvcField.setValue(card.getCvc());
        continueButton.click();
    }

    public void clearFields() {
        cardNumberField.doubleClick().sendKeys(Keys.BACK_SPACE);
        monthField.doubleClick().sendKeys(Keys.BACK_SPACE);
        yearField.doubleClick().sendKeys(Keys.BACK_SPACE);
        holderField.doubleClick().sendKeys(Keys.BACK_SPACE);
        cvcField.doubleClick().sendKeys(Keys.BACK_SPACE);
    }
    public PaymentPage clear() {
        clearFields();
        return new PaymentPage();
    }

    public void notificationSuccessIsVisible() {
        notificationSuccess.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void notificationErrorIsVisible() {
        notificationError.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForValidationFieldMessage() {
        validationFieldMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForWrongFormatMessage() {
        wrongFormatMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForInvalidCharactersMessage() {
        invalidCharMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForWrongCardExpirationMessage() {
        wrongExpirationMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

    public void waitForCardExpiredMessage() {
        cardExpiredMessage.shouldBe(visible, Duration.ofSeconds(11));
    }

}
