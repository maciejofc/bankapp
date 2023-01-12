package pl.maciejowsky.bankapp.utils;

public class AccountNumberGenerator {
    public static String generateNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {

            int digit = (int) (Math.random() * 10);
            cardNumber.append(digit);

        }
        return cardNumber.toString();
    }
}
