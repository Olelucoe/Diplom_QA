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

public class CreditPageTest {
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
    void shouldPurchaseCreditAllFieldValidApprovedCard(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getApprovedCard());
        credit.notificationSuccessIsVisible();
        assertEquals("APPROVED", SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditAllFieldValidDeclinedCard(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getDeclinedCard());
        credit.notificationErrorIsVisible();
        assertEquals("DECLINED", SQLHelper.getCreditPaymentStatus());
    }

    //--Negative tests--
    // -- поле "Номер карты"--

    @Test
    void shouldPurchaseCreditWithNonExistCard(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistCard());
        credit.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditWithInvalidCardNumber(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidCardNumber());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithInvalidCardNumberOnlyZero(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistCardAllZero());
        credit.notificationErrorIsVisible();
        assertEquals(null, SQLHelper.getCreditPaymentStatus());
    }
    @Test
    void shouldPurchaseCreditWithEmptyFieldCardNumber(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyFieldCardNumber());
        credit.waitForWrongFormatMessage();
    }
    //--Поле "Месяц"--
    @Test
    void shouldPurchaseCreditWithEmptyFieldMonth(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyMonth());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOver12(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getMonthOver12());
        credit.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOnlyZero(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getNonExistMonth());
        credit.waitForWrongCardExpirationMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldMonthOneNumberFormat(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getOneMonth());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldLastMonth(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidLastMonth());
        credit.waitForWrongCardExpirationMessage();
    }
    //-- поле "Год"--

    @Test
    void shouldPurchaseCreditWithEmptyFieldYear(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyYear());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldYearIsLastYear(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getLastYear());
        credit.waitForCardExpiredMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldYearToFuture(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getFutureYear());
        credit.waitForWrongCardExpirationMessage();
    }

    //--Поле "Владелец"--

    @Test
    void shouldPurchaseCreditWithFieldHolderIsEmpty(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyHolderCard());
        credit.waitForValidationFieldMessage();
    }

    @Test
    void shouldPurchaseCreditWithFieldHolderIsOnlyName(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderOneNameCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderIsRusLang(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderRusCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderOnlyNumbers(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderNumbersCard());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithFieldHolderOnlySymbols(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getInvalidHolderSymbolsCard());
        credit.waitForWrongFormatMessage();
    }

    //-- поле "CVC/CVV"--

    @Test
    void shouldPurchaseCreditWithCvcFieldOneNumber(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getOneNumberCVC());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldTwoNumbers(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getTwoNumberCVC());
        credit.waitForWrongFormatMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldIsEmpty(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getEmptyCVC());
        credit.waitForValidationFieldMessage();
    }
    @Test
    void shouldPurchaseCreditWithCvcFieldZeros(){
        val startPage = new MainPage();
        val credit = startPage.goToCreditPage();
        credit.fillData(DataHelper.getZeroNumberCVC());
        credit.waitForWrongFormatMessage();
    }
}

