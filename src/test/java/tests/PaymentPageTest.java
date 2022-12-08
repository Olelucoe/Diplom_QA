package tests;

import Data.DataHelper;
import Data.SQLHelper;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPageTest {
    public static String url = System.getProperty("sut.url");

    @BeforeEach
    public void openPage() {
        open(url);
    }

    @AfterEach
    public void cleanBase() {
        SQLHelper.cleanDatabase();
    }

    //--Happy path--
    @Test
    void shouldBuyAllFieldValidApprovedCard() {
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyAllFieldValidDeclinedCard() {
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getDeclinedCard());
        payment.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getDebitPaymentStatus());
    }

    //--Negative tests--
    // -- поле "Номер карты"--
    @Test
    void shouldBuyWithNonExistDebitCard(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCard());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }
    @Test
    void shouldBuyWithInvalidDebitCard(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidCardNumber());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithEmptyFieldCardNumber(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyFieldCardNumber());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyNonExistAllZeroNumberDebitCard(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCardAllZero());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }
    //--Поле "Месяц"--
    @Test
    void shouldBuyWithFieldMonthOver12(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getMonthOver12());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithFieldMonthIsEmpty(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyMonth());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldMonthLessOne(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistMonth());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithExpiredCardMonth(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidLastMonth());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithFieldMonthOneNumberFormat(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getOneMonth());
        payment.waitForWrongFormatMessage();
    }

    //--Поле "Год"--
    @Test
    void shouldBuyWithFieldYearIsEmpty(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyYear());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldYearIsLastYear(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getLastYear());
        payment.waitForCardExpiredMessage();
    }
    @Test
    void shouldBuyWithFieldInvalidYear(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getFutureYear());
        payment.waitForWrongCardExpirationMessage();
    }

    //--Поле "Владелец"--

    @Test
    void shouldBuyWithEmptyFieldHolder(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyHolderCard());
        payment.waitForValidationFieldMessage();
    }
    @Test
    void shouldBuyWithFieldHolderRusLang(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderRusCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlyName(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderOneNameCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlyNumbers(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderNumbersCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlySymbols(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderSymbolsCard());
        payment.waitForInvalidCharactersMessage();
    }
    //-- поле "CVC/CVV"--
    @Test
    void shouldBuyWithCVCFieldOneNumber(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getOneNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithCVCFieldTwoNumber(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getTwoNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithCVCFieldZeros(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getZeroNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithEmptyCvcField(){
        val startPage = new MainPage();
        val payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyCVC());
        payment.waitForValidationFieldMessage();
    }
}
