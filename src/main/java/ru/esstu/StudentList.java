package ru.esstu;

import java.util.List;

public interface StudentList {
    List<Student> getAll();

    void add(Student student);

    Student getById(String id);

    void delete(String id);

    void update(Student student);
}
