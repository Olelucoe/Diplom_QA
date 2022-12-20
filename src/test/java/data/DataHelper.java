package data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private DataHelper() {
    }
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    //positive
    public static Card getApprovedCard() {
        return new Card("4444 4444 4444 4441",getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442",getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }

    // поле "Номер карты"
    public static Card getNonExistCard() {
        return new Card(getRandomCardNumber(),getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }

    public static Card getInvalidCardNumber() {
        return new Card("4444 4444 4444 44",getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }
    public static Card getNonExistCardAllZero() {
        return new Card("0000 0000 0000 0000",getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }

    public static Card getEmptyFieldCardNumber() {
        return new Card("",getValidMonth(),getValidYear(),getValidHolder(),getValidCvc());
    }

    //поле "Владелец"
    public static Card getInvalidHolderRusCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getRusHolder(), getValidCvc());
    }
    public static Card getEmptyHolderCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "", getValidCvc());
    }
    public static Card getInvalidHolderOneNameCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "Oleg", getValidCvc());
    }
    public static Card getInvalidHolderNumbersCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "111111", getValidCvc());
    }
    public static Card getInvalidHolderSymbolsCard() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), "@@@@@@", getValidCvc());
    }

    //поле "Год"
    public static Card getEmptyYear() {
        return new Card("4444 4444 4444 4441", getValidMonth(), "", getValidHolder(), getValidCvc());
    }
    public static Card getLastYear() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getPastYear(), getValidHolder(), getValidCvc());
    }
    public static Card getNotComingYear() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getFutureYear(), getValidHolder(), getValidCvc());
    }

    // поле "Месяц"
    public static Card getInvalidPastMonth() {
        return new Card("4444 4444 4444 4441", "11", getValidYear(), getValidHolder(), getValidCvc());
    }
    public static Card getEmptyMonth() {
        return new Card("4444 4444 4444 4441", "", getValidYear(), getValidHolder(), getValidCvc());
    }
    public static Card getZeroMonthNowYear() {
        return new Card("4444 4444 4444 4441", "00", getValidYear(), getValidHolder(), getValidCvc());
    }
    public static Card getZeroMonthNextYear() {
        return new Card("4444 4444 4444 4441", "00", getNextYear(), getValidHolder(), getValidCvc());
    }
    public static Card getMonthOver12() {
        return new Card("4444 4444 4444 4441", "13", getValidYear(), getValidHolder(), getValidCvc());
    }
    public static Card getOneMonth() {
        return new Card("4444 4444 4444 4441", "7", getValidYear(), getValidHolder(), getValidCvc());
    }

    //поле "CVC/CVV"
    public static Card getEmptyCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "");
    }
    public static Card getOneNumberCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "1");
    }
    public static Card getTwoNumberCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "12");
    }
    public static Card getZeroNumberCVC() {
        return new Card("4444 4444 4444 4441", getValidMonth(), getValidYear(), getValidHolder(), "000");
    }

    //генераторы данных

    public static String getRandomCardNumber() {
        return fakerEn.business().creditCardNumber();
    }
    public static String getValidMonth() {
        String validMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        return validMonth;
    }
    public static String getValidYear() {
        String validYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        return validYear;
    }
    public static String getNextYear() {
        String nextYear = LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return nextYear;
    }
    public static String getPastYear() {
        String pastYear = LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
        return pastYear;
    }
    public static String getFutureYear() {
        String futureYear = LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
        return futureYear;
    }
    public static String getValidHolder() {
        return fakerEn.name().firstName() + " " + fakerEn.name().lastName();
    }

    public static String getRusHolder(){
        return fakerRu.name().fullName();
    }
    public static String getValidCvc() {
        return fakerEn.number().digits(3);
    }
}
