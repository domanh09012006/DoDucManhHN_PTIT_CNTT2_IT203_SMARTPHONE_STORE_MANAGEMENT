package util;

public class ValidationUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) {
            return false;
        }
        if (!email.endsWith("@gmail.com")) {
            return false;
        }
        if (email.contains(" ")) {
            return false;
        }
        if (email.length() <= 10) {
            return false;
        }
        return true;
    }
    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) {
            return false;
        }
        if (phone.length() != 10) {
            return false;
        }
        if (!phone.startsWith("0")) {
            return false;
        }
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}