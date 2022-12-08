package Data;

public class DataHelper {

    private DataHelper() {
    }

    //positive
    public static Card getApprovedCard() {
        return new Card("4444 4444 4444 4441","11","23","Tinkov Oleg","123");
    }

    public static Card getDeclinedCard() {
        return new Card("4444 4444 4444 4442","11","23","Tinkov Oleg","123");
    }

    // поле "Номер карты"
    public static Card getNonExistCard() {
        return new Card("4444 4444 4444 4443","11","23","Tinkov Oleg","123");
    }

    public static Card getInvalidCardNumber() {
        return new Card("4444 4444 4444 44","11","23","Tinkov Oleg","123");
    }
    public static Card getNonExistCardAllZero() {
        return new Card("0000 0000 0000 0000","11","23","Tinkov Oleg","123");
    }

    public static Card getEmptyFieldCardNumber() {
        return new Card("","11","23","Tinkov Oleg","123");
    }

    //поле "Владелец"
    public static Card getInvalidHolderRusCard() {
        return new Card("4444 4444 4444 4441", "11", "23", "Иванов Иван", "123");
    }
    public static Card getEmptyHolderCard() {
        return new Card("4444 4444 4444 4441", "11", "23", "", "123");
    }
    public static Card getInvalidHolderOneNameCard() {
        return new Card("4444 4444 4444 4441", "11", "23", "Oleg", "123");
    }
    public static Card getInvalidHolderNumbersCard() {
        return new Card("4444 4444 4444 4441", "11", "23", "111111", "123");
    }
    public static Card getInvalidHolderSymbolsCard() {
        return new Card("4444 4444 4444 4441", "11", "23", "@@@@@@", "123");
    }

    //поле "Год"
    public static Card getEmptyYear() {
        return new Card("4444 4444 4444 4441", "11", "", "Tinkov Oleg", "123");
    }
    public static Card getLastYear() {
        return new Card("4444 4444 4444 4441", "11", "21", "Tinkov Oleg", "123");
    }
    public static Card getFutureYear() {
        return new Card("4444 4444 4444 4441", "11", "28", "Tinkov Oleg", "123");
    }


    // поле "Месяц"
    public static Card getInvalidLastMonth() {
        return new Card("4444 4444 4444 4441", "11", "22", "Tinkov Oleg", "123");
    }
    public static Card getEmptyMonth() {
        return new Card("4444 4444 4444 4441", "", "22", "Tinkov Oleg", "123");
    }
    public static Card getNonExistMonth() {
        return new Card("4444 4444 4444 4441", "00", "23", "Tinkov Oleg", "123");
    }
    public static Card getMonthOver12() {
        return new Card("4444 4444 4444 4441", "13", "23", "Tinkov Oleg", "123");
    }
    public static Card getOneMonth() {
        return new Card("4444 4444 4444 4441", "7", "23", "Tinkov Oleg", "123");
    }

    //поле "CVC/CVV"
    public static Card getEmptyCVC() {
        return new Card("4444 4444 4444 4441", "11", "23", "Tinkov Oleg", "");
    }
    public static Card getOneNumberCVC() {
        return new Card("4444 4444 4444 4441", "11", "23", "Tinkov Oleg", "1");
    }
    public static Card getTwoNumberCVC() {
        return new Card("4444 4444 4444 4441", "11", "23", "Tinkov Oleg", "12");
    }
    public static Card getZeroNumberCVC() {
        return new Card("4444 4444 4444 4441", "11", "23", "Tinkov Oleg", "000");
    }
}
