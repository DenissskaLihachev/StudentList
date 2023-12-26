package ru.esstu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentListJSON implements StudentList {
    // Имя файла JSON
    private final String jsonFileName;

    // Конструктор класса, принимающий имя файла JSON и добавляющий расширение .json
    public StudentListJSON(String jsonFileName) {
        this.jsonFileName = jsonFileName + ".json";
    }

    // Получение списка всех студентов из файла JSON
    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        try {
            // Чтение массива JSON из файла
            JSONArray jsonArray = readJSONArrayFromJSONFile();

            // Проход по каждому элементу массива JSON и преобразование в объекты Student
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                Student student = jsonToStudent(jsonStudent);
                students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Возвращение списка студентов
        return students;
    }

    // Добавление нового студента в файл JSON
    @Override
    public void add(Student student) {
        try {
            // Чтение массива JSON из файла
            JSONArray jsonArray = readJSONArrayFromJSONFile();

            // Преобразование объекта Student в JSON и добавление в массив JSON
            JSONObject jsonStudent = studentToJSON(student);
            jsonArray.put(jsonStudent);

            // Запись массива JSON в файл
            writeJSONArrayToJSONFile(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Получение студента по ID из файла JSON
    @Override
    public Student getById(String id) {
        try {
            // Чтение массива JSON из файла
            JSONArray jsonArray = readJSONArrayFromJSONFile();

            // Поиск студента с заданным ID и преобразование в объект Student
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (jsonStudent.getString("id").equals(id)) {
                    return jsonToStudent(jsonStudent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Возвращение null, если студент с заданным ID не найден
        return null;
    }

    // Удаление студента по ID из файла JSON
    @Override
    public void delete(String id) {
        try {
            // Чтение массива JSON из файла
            JSONArray jsonArray = readJSONArrayFromJSONFile();

            // Создание нового массива JSON без студента с заданным ID
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (!jsonStudent.getString("id").equals(id)) {
                    newJsonArray.put(jsonStudent);
                }
            }

            // Запись нового массива JSON в файл
            writeJSONArrayToJSONFile(newJsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Обновление данных о студенте в файле JSON
    @Override
    public void update(Student student) {
        try {
            // Чтение массива JSON из файла
            JSONArray jsonArray = readJSONArrayFromJSONFile();

            // Создание нового массива JSON с обновленными данными о студенте
            JSONArray newJsonArray = new JSONArray();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonStudent = jsonArray.getJSONObject(i);
                if (jsonStudent.getString("id").equals(student.getId())) {
                    // Замена старого JSON студента на новый, соответствующий обновленным данным
                    JSONObject updatedJsonStudent = studentToJSON(student);
                    newJsonArray.put(updatedJsonStudent);
                } else {
                    newJsonArray.put(jsonStudent);
                }
            }

            // Запись нового массива JSON в файл
            writeJSONArrayToJSONFile(newJsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Чтение массива JSON из файла
    private JSONArray readJSONArrayFromJSONFile() throws IOException {
        File file = new File(jsonFileName);

        // Проверка наличия файла перед чтением
        if (!file.exists()) {
            // Возвращение пустого массива, если файл не существует
            return new JSONArray();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;

            // Чтение содержимого файла построчно и добавление в StringBuilder
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }

            // Преобразование строки JSON в объект JSONArray и возвращение
            return new JSONArray(json.toString());
        }
    }

    // Запись массива JSON в файл
    private void writeJSONArrayToJSONFile(JSONArray jsonArray) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFileName))) {
            // Запись массива JSON в файл с отступами (форматирование)
            writer.write(jsonArray.toString(2));
        }
    }

    // Преобразование объекта Student в объект JSON
    private JSONObject studentToJSON(Student student) {
        JSONObject jsonStudent = new JSONObject();

        // Заполнение JSON объекта данными из объекта Student
        jsonStudent.put("id", student.getId());
        jsonStudent.put("firstName", student.getFirstName());
        jsonStudent.put("lastName", student.getLastName());
        jsonStudent.put("partonymicName", student.getPartonymicName());
        jsonStudent.put("group", student.getGroup());

        // Возвращение JSON объекта
        return jsonStudent;
    }

    // Преобразование объекта JSON в объект Student
    private Student jsonToStudent(JSONObject jsonStudent) {
        // Извлечение данных из JSON объекта
        String id = jsonStudent.getString("id");
        String firstName = jsonStudent.getString("firstName");
        String lastName = jsonStudent.getString("lastName");
        String partonymicName = jsonStudent.getString("partonymicName");
        String group = jsonStudent.getString("group");

        // Создание и возвращение нового объекта Student
        return new Student(id, firstName, lastName, partonymicName, group);
    }
}