package homework;

public class CheckIntArray {
    public boolean check(int[] array) {
        boolean isOnePresent = false;
        boolean isFourPresent = false;

        for (int integer : array) {
            if (integer != 1 && integer != 4) {
                return false;
            }

            if (integer == 1) {
                isOnePresent = true;
            }

            if (integer == 4) {
                isFourPresent = true;
            }
        }

        return isOnePresent && isFourPresent;
    }
}