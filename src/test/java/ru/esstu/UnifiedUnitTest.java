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
        Student student1 = new Student("1", "demo", "demo", "demo", "demo");
        Student student2 = new Student("2", "etc", "etc", "etc", "etc");
        Student student3 = new Student("3", "null", "null", "null", "null");

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        List<Student> allStudents = studentList.getAll();

        assertNotNull(allStudents);
        assertEquals(3, allStudents.size());
        assertEquals(student1, studentList.getById("1"));
        assertEquals(student2, studentList.getById("2"));
        assertEquals(student3, studentList.getById("3"));
    }

    @Test
    void testDelete() {
        Student student = new Student("1", "demo", "demo", "demo", "demo");
        studentList.add(student);

        studentList.delete("1");

        assertNull(studentList.getById("1"));
    }

    @Test
    void testUpdate() {
        Student originalStudent = new Student("1", "demo", "demo", "demo", "demo");
        studentList.add(originalStudent);

        Student updatedStudent = new Student("1", "DEMO", "DEMO", "DEMO", "DEMO");
        studentList.update(updatedStudent);

        Student retrievedStudent = studentList.getById("1");

        assertNotNull(retrievedStudent);
        assertEquals(updatedStudent, retrievedStudent);
    }

    @Test
    void testAddAndDelete() {
        Student student = new Student("6", "new", "new", "new", "new");

        studentList.add(student);
        studentList.delete("6");

        assertNull(studentList.getById("6"));
    }

    @Test
    void testAddAndUpdate() {
        Student student = new Student("7", "Hello", "Hello", "Hello", "Hello");
        studentList.add(student);

        Student updatedStudent = new Student("7", "World", "World", "World", "World");
        studentList.update(updatedStudent);

        Student retrievedStudent = studentList.getById("7");

        assertNotNull(retrievedStudent);
        assertEquals(updatedStudent, retrievedStudent);
    }

    @Test
    void testDeleteNonexistentStudent() {
        studentList.delete("8");

        assertNull(studentList.getById("8"));
    }

    protected abstract void initializeStudentList();
}
