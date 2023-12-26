package org.example;
import ru.esstu.*;

import java.util.List;
import java.util.Scanner;

public class          Main {
    public static void main(String[] args) {

        StudentList studentList = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. ArrayList");
        System.out.println("2. DataBase");
        System.out.println("3. File");
        System.out.println("4. JSON");
        System.out.println("5. XML");
        System.out.print("Choice: ");

        int storageChoice = scanner.nextInt();

        if (storageChoice == 1) {
            studentList = new StudentListArrayList();
        } else if (storageChoice == 2) {
            studentList = new StudentListDatabase("jdbc:postgresql://localhost:5432/postgres", "postgres", "1234");
        } else if (storageChoice == 3) {
            studentList = new StudentListFile("student.txt");
        } else if (storageChoice == 4) {
            System.out.print("Enter name JSON: ");
            String jsonFileName = scanner.next();
            studentList = new StudentListJSON(jsonFileName);
        } else if (storageChoice == 5) {
            System.out.print("Enter name XML: ");
            String xmlFileName = scanner.next();
            studentList = new StudentListXML(xmlFileName);
        }

        while (true) {
            System.out.println("1. Show all");
            System.out.println("2. Add");
            System.out.println("3. Edit");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<Student> allStudents = studentList.getAll();
                    if (allStudents.isEmpty()) {
                        System.out.println("Empty");
                    } else {
                        for (Student student : allStudents) {
                            System.out.println("ID: " + student.getId());
                            System.out.println("FirstName: " + student.getFirstName());
                            System.out.println("LastName: " + student.getLastName());
                            System.out.println("Patronymic: " + student.getPartonymicName());
                            System.out.println("Group: " + student.getGroup());
                            System.out.println();
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter ID: ");
                    String id = scanner.next();
                    System.out.print("Enter FirstName: ");
                    String firstName = scanner.next();
                    System.out.print("Enter LastName: ");
                    String lastName = scanner.next();
                    System.out.print("Enter Patronymic: ");
                    String patronymicName = scanner.next();
                    System.out.print("Enter Group: ");
                    String group = scanner.next();

                    Student newStudent = new Student(id, firstName, lastName, patronymicName, group);
                    studentList.add(newStudent);
                    System.out.println("Successfully");
                    break;
                case 3:
                    System.out.print("Choice ID: ");
                    String editId = scanner.next();
                    Student existingStudent = studentList.getById(editId);
                    if (existingStudent != null) {
                        System.out.print("Enter new FirstName: ");
                        existingStudent.setFirstName(scanner.next());
                        System.out.print("Enter new LastName: ");
                        existingStudent.setLastName(scanner.next());
                        System.out.print("Enter new Patronymic: ");
                        existingStudent.setPartonymicName(scanner.next());
                        System.out.print("Enter new Group: ");
                        existingStudent.setGroup(scanner.next());
                        studentList.update(existingStudent);
                        System.out.println("Successfully");
                    } else {
                        System.out.println("not found");
                    }
                    break;
                case 4:
                    System.out.print("Choice ID: ");
                    String deleteId = scanner.next();
                    studentList.delete(deleteId);
                    System.out.println("Successfully");
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }
}