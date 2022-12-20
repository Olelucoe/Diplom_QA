package tests;

import data.DataHelper;
import data.SQLHelper;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentPageTest {
    public static String url = System.getProperty("sut.url");

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");

    }
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
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getApprovedCard());
        payment.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getDebitPaymentStatus());
    }

    @Test
    void shouldBuyAllFieldValidDeclinedCard() {
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getDeclinedCard());
        payment.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getDebitPaymentStatus());
    }

    //--Negative tests--
    // -- поле "Номер карты"--
    @Test
    void shouldBuyWithNonExistDebitCard(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCard());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }
    @Test
    void shouldBuyWithInvalidDebitCard(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidCardNumber());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithEmptyFieldCardNumber(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyFieldCardNumber());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyNonExistAllZeroNumberDebitCard(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNonExistCardAllZero());
        payment.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getDebitPaymentStatus());
    }
    //--Поле "Месяц"--
    @Test
    void shouldBuyWithFieldMonthOver12(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getMonthOver12());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithFieldMonthIsEmpty(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyMonth());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldMonthZeroAndNowYear(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getZeroMonthNowYear());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithFieldMonthZeroAndNextYear(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getZeroMonthNextYear());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithExpiredCardMonth(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidPastMonth());
        payment.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldBuyWithFieldMonthOneNumberFormat(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getOneMonth());
        payment.waitForWrongFormatMessage();
    }

    //--Поле "Год"--
    @Test
    void shouldBuyWithFieldYearIsEmpty(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyYear());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldYearIsLastYear(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getLastYear());
        payment.waitForCardExpiredMessage();
    }
    @Test
    void shouldBuyWithFieldInvalidYear(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getNotComingYear());
        payment.waitForWrongCardExpirationMessage();
    }

    //--Поле "Владелец"--

    @Test
    void shouldBuyWithEmptyFieldHolder(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyHolderCard());
        payment.waitForValidationFieldMessage();
    }
    @Test
    void shouldBuyWithFieldHolderRusLang(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderRusCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlyName(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderOneNameCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlyNumbers(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderNumbersCard());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithFieldHolderOnlySymbols(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getInvalidHolderSymbolsCard());
        payment.waitForInvalidCharactersMessage();
    }
    //-- поле "CVC/CVV"--
    @Test
    void shouldBuyWithCVCFieldOneNumber(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getOneNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithCVCFieldTwoNumber(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getTwoNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithCVCFieldZeros(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getZeroNumberCVC());
        payment.waitForWrongFormatMessage();
    }
    @Test
    void shouldBuyWithEmptyCvcField(){
        var startPage = new MainPage();
        var payment = startPage.goToPaymentPage();
        payment.fillData(DataHelper.getEmptyCVC());
        payment.waitForValidationFieldMessage();
    }
}
