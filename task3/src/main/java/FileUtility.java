import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FileUtility {

    /*
     * Структура файла ввода: в первой строке одно целое число - N
     * далее следует N целых чисел через пробел
     * Метод должен отсортировать элементы с четным значением,
     * а нечетные оставить на своих местах и вывести результат через пробел в файл вывода
     * Пример:
     * in:
     * 5
     * 5 4 2 1 3    // 2 4
     * out:
     * 5 2 4 1 3
     */
    public static void sortEvenElements(File in, File out) throws IOException {
        Scanner file = new Scanner(in);
        String firstLine = file.nextLine();
        String line = null;
        int temp;

        while (file.hasNextLine()) {
            String nextLine = file.nextLine();
            if (!firstLine.equals(nextLine)) {
                line = nextLine;
            }
        }

        int[] numbers = Arrays.stream(line.split(" "))
            .mapToInt(Integer::parseInt)
            .toArray();

        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] % 2 != 0) {
                continue;
            }

            for (int j = (i + 1); j < numbers.length; j++) {
                if (numbers[j] % 2 == 0) {
                    if (numbers[i] > numbers[j]) {
                        temp = numbers[i];
                        numbers[i] = numbers[j];
                        numbers[j] = temp;
                    }
                }
            }
        }

        FileWriter writer = new FileWriter(out);
        for (int number : numbers) {
            writer.write(number + " ");
        }
        writer.close();
    }

    /*
     * Генератор паролей, пароль должен отвечать требованиям:
     * длина не менее 6 и не более 12, включает минимум по одному символу
     * из наборов: a-z, A-Z, 0-9, {*,!,%}
     * все пароли должны быть разными
     * На вход метод получает файл с логинами пользователей
     * Метод должен записать в файл вывода записи с логинами и паролями
     * для каждого пользователя
     */

    public String generatePassayPassword() {
        final int MIN_NUMBER_OF_CHARS = 1;
        final int PASSWORD_LENGTH_MIN = 6;
        final int PASSWORD_LENGTH_MAX = 12;

        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(MIN_NUMBER_OF_CHARS);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(MIN_NUMBER_OF_CHARS);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(MIN_NUMBER_OF_CHARS);

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "";
            }

            public String getCharacters() {
                return "*!%";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(MIN_NUMBER_OF_CHARS);

        int passwordLength = ThreadLocalRandom.current().nextInt(PASSWORD_LENGTH_MIN, PASSWORD_LENGTH_MAX + 1);
        String password = gen.generatePassword(passwordLength, splCharRule, lowerCaseRule,
            upperCaseRule, digitRule);
        return password;
    }

    public void passwordGen(File in, File out) throws IOException {
        Scanner logIn = new Scanner(in);
        FileWriter writer = new FileWriter(out);

        while (logIn.hasNextLine()) {
            String line = logIn.nextLine();
            line += " " + generatePassayPassword();
            writer.write(line + "\n");
        }
        writer.close();
    }

    /*
     *  Метод должен дописать в переданный файл все
     * записи из списка по одной записи в строке
     * */
    public void appender(File file, List<String> records) throws IOException {
        FileWriter writer = new FileWriter(file);
        for (String record : records) {
            writer.write(record + "\n");
        }
        writer.close();

    }

    /*
     * Метод возвращает список из N последних строк файла
     * строки можно дополнять пробелами до длины 80
     * тогда количество символов в файле будет предсказуемо
     * 10 строк это ровно 800 символов
     * Изучите класс RandomAccessFile для эффективного решения данной
     * задачи
     * Альтернативное решение: использование очереди или стека
     * */
    public ArrayList getNString(String pathToFile, int n) throws IOException {
        StringBuilder builder = new StringBuilder();
        RandomAccessFile randomAccessFile = new RandomAccessFile(pathToFile, "r");
        long fileLength = randomAccessFile.length();
        long lastChardAddr = fileLength - 1;

        randomAccessFile.seek(lastChardAddr);
        long offset = (fileLength / 1000) * n;

        for (long pointer = lastChardAddr; pointer >= fileLength - offset; pointer--) {
            randomAccessFile.seek(pointer);
            char c;
            c = (char) randomAccessFile.read();
            builder.append(c);
            if (c == '\r') {
                builder.append(";");
            }
        }
        randomAccessFile.close();

        builder.reverse();
        String builderString = builder.toString().replaceAll(" +", " ");
        String[] arrOfStr = builderString.split(";");

        return new ArrayList(Arrays.asList(arrOfStr));
    }

}
