package ru.esstu;

public class TestRunner extends UnifiedUnitTest {
    @Override
    protected void initializeStudentList() {
        studentList = new StudentListJSON("dsf");
    }
}
