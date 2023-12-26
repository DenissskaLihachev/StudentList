package ru.esstu;

import java.util.ArrayList;
import java.util.List;

public class StudentListArrayList implements StudentList {
    private List<Student> students = new ArrayList<>();

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public void add(Student student) {
        students.add(student);
    }

    @Override
    public Student getById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public void delete(String id) {
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getId().equals(id)) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            students.remove(studentToRemove);
        }
    }

    @Override
    public void update(Student student) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(student.getId())) {
                students.set(i, student);
                break;
            }
        }
    }
}
