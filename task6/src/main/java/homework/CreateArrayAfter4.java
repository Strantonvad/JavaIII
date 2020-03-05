package homework;

public class CreateArrayAfter4 {

    public Integer[] getArrayAfter4(Integer[] arr) {
        int index = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                index = i + 1;
            }
        }

        if (index == 0) {
            throw new RuntimeException();
        }

        Integer[] newArray = new Integer[arr.length - index];

        for (int j = index, x = 0; j < arr.length; j++, x++) {
            newArray[x] = arr[j];
        }

        return newArray;
    }
}
