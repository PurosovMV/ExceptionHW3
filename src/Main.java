import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {


            try {
                while (true) {
                    makeRecord();
                    System.out.println("Успешно!");
                }
            } catch (FileSystemException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }


    }


    public static void makeRecord() throws Exception {
        System.out.println("Введите через пробел фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (без разделителей) и пол(символ латиницей f или m)");

        String inputText;
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            inputText = bf.readLine();
        } catch (IOException e) {
            throw new Exception("Произошла ошибка при работе с консолью");
        }

        String[] array = inputText.split(" ");
        if (array.length != 6) {
            throw new Exception("Введено неверное количество параметров");
        }

        String lastName = array[0];
        String firstName = array[1];
        String patronymic = array[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yyyy");
        Date dateOfBirth;
        try {
            dateOfBirth = format.parse(array[3]);
        } catch (ParseException e) {
            throw new ParseException("Дата рождения введена не верно", e.getErrorOffset());
        }

        int phone;
        try {
            phone = Integer.parseInt(array[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Номер телефона введён неверно");
        }

        String sex = array[5];
        if (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")) {
            throw new RuntimeException("Пол введён неверно");
        }


        File file = new File(lastName.toLowerCase() + ".txt");
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (file.length() > 0) {
                fileWriter.write('\n');
            }
            fileWriter.write(String.format("<%s> <%s> <%s> <%s> <%s> <%s>", lastName, firstName, patronymic, format.format(dateOfBirth), phone, sex));

        } catch (IOException e) {
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }


    }
}