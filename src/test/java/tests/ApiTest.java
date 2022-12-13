package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static Data.DataHelper.getApprovedCard;
import static Data.DataHelper.getDeclinedCard;
import static Data.RestApiHelper.creditRequest;
import static Data.RestApiHelper.paymentRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");

    }
    @Test
    void shouldGiveResponseValidApprovedDebitCard() {
        var validApprovedCardForApi = getApprovedCard();
        var response = paymentRequest(validApprovedCardForApi);
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    void shouldGiveResponseValidDeclinedDebitCard() {
        var validDeclinedCardForApi = getDeclinedCard();
        var response = paymentRequest(validDeclinedCardForApi);
        assertTrue(response.contains("DECLINED"));
    }

    @Test
    void shouldGiveResponseValidApprovedCreditCard() {
        var validApprovedCardForApi = getApprovedCard();
        var response = creditRequest(validApprovedCardForApi);
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    void shouldGiveResponseValidDeclinedCreditCard() {
        var validDeclinedCardForApi = getDeclinedCard();
        var response = creditRequest(validDeclinedCardForApi);
        assertTrue(response.contains("DECLINED"));
    }
}
