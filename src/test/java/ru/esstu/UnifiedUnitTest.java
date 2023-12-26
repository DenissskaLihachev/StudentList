package ru.esstu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class UnifiedUnitTest {
    protected StudentList studentList;

    @BeforeEach
    void setUp() {
        initializeStudentList();
    }

    @Test
    void testAddAndGetAll() {
        Student student1 = new Student("1", "ТЕСТ", "ТЕСТ", "ТЕСТ", "ТЕСТ");
        Student student2 = new Student("2", "Имя", "Фамилия", "Отчество", "Группа");
        Student student3 = new Student("3", "Новое", "Студент", "Еще", "Группа");
        Student student4 = new Student("4", "Еще", "Один", "Студент", "Группа");
        Student student5 = new Student("5", "Третий", "Тест", "Студент", "Группа");

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);

        List<Student> allStudents = studentList.getAll();

        assertNotNull(allStudents);
        assertEquals(5, allStudents.size());
        assertEquals(student1, studentList.getById("1"));
        assertEquals(student2, studentList.getById("2"));
        assertEquals(student3, studentList.getById("3"));
        assertEquals(student4, studentList.getById("4"));
        assertEquals(student5, studentList.getById("5"));
    }

    @Test
    void testDelete() {
        Student student = new Student("1", "ТЕСТ", "ТЕСТ", "ТЕСТ", "ТЕСТ");
        studentList.add(student);

        studentList.delete("1");

        assertNull(studentList.getById("1"));
    }

    @Test
    void testUpdate() {
        Student originalStudent = new Student("1", "ТЕСТ", "ТЕСТ", "ТЕСТ", "ТЕСТ");
        studentList.add(originalStudent);

        Student updatedStudent = new Student("1", "ТЕСТ", "ИЗМЕНЕНО", "ТЕСТ", "ТЕСТ");
        studentList.update(updatedStudent);

        Student retrievedStudent = studentList.getById("1");

        assertNotNull(retrievedStudent);
        assertEquals(updatedStudent, retrievedStudent);
    }

    @Test
    void testAddAndDelete() {
        Student student = new Student("6", "Новый", "Студент", "Еще", "Группа");

        studentList.add(student);
        studentList.delete("6");

        assertNull(studentList.getById("6"));
    }

    @Test
    void testAddAndUpdate() {
        Student student = new Student("7", "Тестовый", "Студент", "Еще", "Группа");
        studentList.add(student);

        Student updatedStudent = new Student("7", "Тестовый", "Обновленный", "Студент", "Группа");
        studentList.update(updatedStudent);

        Student retrievedStudent = studentList.getById("7");

        assertNotNull(retrievedStudent);
        assertEquals(updatedStudent, retrievedStudent);
    }

    @Test
    void testDeleteNonexistentStudent() {
        studentList.delete("8"); // Assuming "8" doesn't exist

        assertNull(studentList.getById("8"));
    }

    protected abstract void initializeStudentList();
}
