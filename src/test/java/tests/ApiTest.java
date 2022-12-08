package tests;

import lombok.val;
import org.junit.jupiter.api.Test;

import static Data.DataHelper.getApprovedCard;
import static Data.DataHelper.getDeclinedCard;
import static Data.RestApiHelper.creditRequest;
import static Data.RestApiHelper.paymentRequest;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest {

    @Test
    void shouldGiveResponseValidApprovedDebitCard() {
        val validApprovedCardForApi = getApprovedCard();
        val response = paymentRequest(validApprovedCardForApi);
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    void shouldGiveResponseValidDeclinedDebitCard() {
        val validDeclinedCardForApi = getDeclinedCard();
        val response = paymentRequest(validDeclinedCardForApi);
        assertTrue(response.contains("DECLINED"));
    }

    @Test
    void shouldGiveResponseValidApprovedCreditCard() {
        val validApprovedCardForApi = getApprovedCard();
        val response = creditRequest(validApprovedCardForApi);
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    void shouldGiveResponseValidDeclinedCreditCard() {
        val validDeclinedCardForApi = getDeclinedCard();
        val response = creditRequest(validDeclinedCardForApi);
        assertTrue(response.contains("DECLINED"));
    }
}
