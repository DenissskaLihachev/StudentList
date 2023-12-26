package org.example;
import ru.esstu.*;

import java.util.List;
import java.util.Scanner;

public class          Main {
    public static void main(String[] args) {

        StudentList studentList = null; // Интерфейс для работы с хранилищем данных студентов
        Scanner scanner = new Scanner(System.in); // Объект для чтения ввода пользователя

        System.out.println("**************************************");
        System.out.println("*  Система управления списком студентов  *");
        System.out.println("**************************************");

        System.out.println("Выберите тип хранилища данных:");
        System.out.println("1. В памяти (ArrayList)");
        System.out.println("2. В базе данных");
        System.out.println("3. В файле");
        System.out.println("4. JSON");
        System.out.println("5. XML");
        System.out.print("Ваш выбор: ");

        // Чтение выбора пользователя для определения типа хранилища данных
        int storageChoice = scanner.nextInt();

        // Инициализация объекта studentList в зависимости от выбора пользователя
        if (storageChoice == 1) {
            studentList = new StudentListArrayList();
            // Хранилище в памяти (ArrayList)
        } else if (storageChoice == 2) {
            studentList = new StudentListDatabase("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
            // Хранилище в базе данных PostgreSQL
        } else if (storageChoice == 3) {
            studentList = new StudentListFile("student.txt");
            // Хранилище в текстовом файле
        } else if (storageChoice == 4) {
            // Хранилище в формате JSON, с запросом имени файла у пользователя
            System.out.print("Введите название файла JSON: ");
            String jsonFileName = scanner.next();
            studentList = new StudentListJSON(jsonFileName);
        } else if (storageChoice == 5) {
            // Хранилище в формате XML, с запросом имени файла у пользователя
            System.out.print("Введите название файла XML: ");
            String xmlFileName = scanner.next();
            studentList = new StudentListXML(xmlFileName);
        } else {
            // В случае некорректного выбора - завершение программы
            System.out.println("Неверный выбор. Программа завершена.");
            System.exit(0);
        }

        while (true) {
            // Вывод меню действий пользователя
            System.out.println("\nВыберите действие:");
            System.out.println("1. Получить список всех студентов");
            System.out.println("2. Добавить студента");
            System.out.println("3. Редактировать студента");
            System.out.println("4. Удалить студента");
            System.out.println("5. Выйти");
            System.out.print("Ваш выбор: ");

            // Чтение выбора пользователя
            int choice = scanner.nextInt();

            // Обработка выбора пользователя с использованием оператора switch
            switch (choice) {
                case 1:
                    // Вывод списка всех студентов
                    System.out.println("\n*** Список всех студентов ***");
                    List<Student> allStudents = studentList.getAll();
                    if (allStudents.isEmpty()) {
                        System.out.println("Пусто");
                    } else {
                        for (Student student : allStudents) {
                            // Вывод информации о каждом студенте
                            System.out.println("ID: " + student.getId());
                            System.out.println("Имя: " + student.getFirstName());
                            System.out.println("Фамилия: " + student.getLastName());
                            System.out.println("Отчество: " + student.getPartonymicName());
                            System.out.println("Группа: " + student.getGroup());
                            System.out.println("---------------------------");
                        }
                    }
                    break;
                case 2:
                    // Добавление нового студента
                    System.out.println("\n*** Добавление нового студента ***");
                    System.out.print("Введите ID: ");
                    String id = scanner.next();
                    System.out.print("Введите имя: ");
                    String firstName = scanner.next();
                    System.out.print("Введите фамилию: ");
                    String lastName = scanner.next();
                    System.out.print("Введите отчество: ");
                    String patronymicName = scanner.next();
                    System.out.print("Введите группу: ");
                    String group = scanner.next();

                    // Создание нового объекта студента и добавление в хранилище
                    Student newStudent = new Student(id, firstName, lastName, patronymicName, group);
                    studentList.add(newStudent);
                    System.out.println("Студент успешно добавлен.");
                    break;
                case 3:
                    // Редактирование существующего студента
                    System.out.println("\n*** Редактирование студента ***");
                    System.out.print("Введите ID студента для редактирования: ");
                    String editId = scanner.next();
                    Student existingStudent = studentList.getById(editId);
                    if (existingStudent != null) {
                        // Изменение информации о студенте
                        System.out.print("Введите новое имя: ");
                        existingStudent.setFirstName(scanner.next());
                        System.out.print("Введите новую фамилию: ");
                        existingStudent.setLastName(scanner.next());
                        System.out.print("Введите новое отчество: ");
                        existingStudent.setPartonymicName(scanner.next());
                        System.out.print("Введите новую группу: ");
                        existingStudent.setGroup(scanner.next());
                        studentList.update(existingStudent);
                        System.out.println("Студент успешно отредактирован.");
                    } else {
                        System.out.println("Студент с указанным ID не найден.");
                    }
                    break;
                case 4:
                    // Удаление студента
                    System.out.println("\n*** Удаление студента ***");
                    System.out.print("Введите ID студента для удаления: ");
                    String deleteId = scanner.next();
                    studentList.delete(deleteId);
                    System.out.println("Студент успешно удален.");
                    break;
                case 5:
                    // Завершение программы
                    System.out.println("\nПрограмма завершена.");
                    System.exit(0);
                default:
                    // В случае некорректного выбора
                    System.out.println("Неверный выбор. Попробуйте ещё раз.");
            }
        }
    }
}