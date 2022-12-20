package tests;

import data.DataHelper;
import data.SQLHelper;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditPageTest {
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
    void shouldPurchaseCreditAllFieldValidApprovedCard(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getApprovedCard());
        credit.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditAllFieldValidDeclinedCard(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getDeclinedCard());
        credit.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getCreditPaymentStatus());
    }

    //--Negative tests--
    // -- поле "Номер карты"--

    @Test
    void shouldPurchaseCreditWithNonExistCard(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistCard());
        credit.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditWithInvalidCardNumber(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidCardNumber());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithInvalidCardNumberOnlyZero(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistCardAllZero());
        credit.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditWithEmptyFieldCardNumber(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyFieldCardNumber());
        credit.waitForWrongFormatMessage();
    }
    //--Поле "Месяц"--
    @Test
    void shouldPurchaseCreditWithEmptyFieldMonth(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyMonth());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOver12(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getMonthOver12());
        credit.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOnlyZeroAndNowYear(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getZeroMonthNowYear());
        credit.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOnlyZeroAndNextYear(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getZeroMonthNextYear());
        credit.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOneNumberFormat(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getOneMonth());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldLastMonth(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidPastMonth());
        credit.waitForWrongCardExpirationMessage();
    }
    //-- поле "Год"--

    @Test
    void shouldPurchaseCreditWithEmptyFieldYear(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyYear());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldYearIsLastYear(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getLastYear());
        credit.waitForCardExpiredMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldYearToFuture(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNotComingYear());
        credit.waitForWrongCardExpirationMessage();
    }

    //--Поле "Владелец"--

    @Test
    void shouldPurchaseCreditWithFieldHolderIsEmpty(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyHolderCard());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldPurchaseCreditWithFieldHolderIsOnlyName(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderOneNameCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderIsRusLang(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderRusCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderOnlyNumbers(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderNumbersCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderOnlySymbols(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderSymbolsCard());
        credit.waitForWrongFormatMessage();
    }

    //-- поле "CVC/CVV"--

    @Test
    void shouldPurchaseCreditWithCvcFieldOneNumber(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getOneNumberCVC());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldTwoNumbers(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getTwoNumberCVC());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldIsEmpty(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyCVC());
        credit.waitForValidationFieldMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldZeros(){
        var startPage = new MainPage();
        var credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getZeroNumberCVC());
        credit.waitForWrongFormatMessage();
    }
}

