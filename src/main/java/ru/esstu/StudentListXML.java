package ru.esstu;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class StudentListXML implements StudentList {
    private List<Student> students; // Список студентов
    private String xmlFileName; // Имя файла XML для сохранения данных

    // Конструктор класса, принимающий имя файла XML и загружающий данные из файла
    public StudentListXML(String xmlFileName) {
        // Инициализация полей класса
        this.xmlFileName = xmlFileName + ".xml";
        this.students = new ArrayList<>();
        // Загрузка данных из файла XML при создании объекта
        loadFromXml();
    }

    // Реализация метода интерфейса, возвращающего список всех студентов
    @Override
    public List<Student> getAll() {
        // Возвращение списка всех студентов
        return students;
    }

    // Реализация метода интерфейса, добавляющего студента и сохраняющего изменения в файле XML
    @Override
    public void add(Student student) {
        // Добавление нового студента в список
        students.add(student);
        // Сохранение изменений в файле XML
        saveToXml();
    }

    // Реализация метода интерфейса, возвращающего студента по его ID
    @Override
    public Student getById(String id) {
        // Поиск студента по ID в списке
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student; // Возвращение найденного студента
            }
        }
        return null; // Если студент с указанным ID не найден
    }

    // Реализация метода интерфейса, удаляющего студента по его ID и сохраняющего изменения в файле XML
    @Override
    public void delete(String id) {
        Student studentToRemove = null;
        // Поиск студента по ID в списке
        for (Student student : students) {
            if (student.getId().equals(id)) {
                studentToRemove = student; // Запоминание студента для удаления
                break;
            }
        }
        // Удаление студента из списка и сохранение изменений в файле XML
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            saveToXml();
        }
    }

    // Реализация метода интерфейса, обновляющего информацию о студенте и сохраняющего изменения в файле XML
    @Override
    public void update(Student student) {
        // Поиск студента по ID в списке
        for (Student existingStudent : students) {
            if (existingStudent.getId().equals(student.getId())) {
                // Обновление информации о студенте и сохранение изменений в файле XML
                existingStudent.setFirstName(student.getFirstName());
                existingStudent.setLastName(student.getLastName());
                existingStudent.setPartonymicName(student.getPartonymicName());
                existingStudent.setGroup(student.getGroup());
                saveToXml();
                return;
            }
        }
    }

    // Загрузка данных из файла XML
// Загрузка данных из файла XML
    private void loadFromXml() {
        try {
            // Создание фабрики для построения документа XML и построитель документа
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            File file = new File(xmlFileName);

            // Проверка наличия файла перед чтением
            if (file.exists()) {
                // Парсинг XML-документа
                Document document = builder.parse(file);
                // Получение списка узлов "student" из корня документа
                NodeList studentNodes = document.getElementsByTagName("student");

                // Чтение данных о студентах из XML и добавление их в список
                for (int i = 0; i < studentNodes.getLength(); i++) {
                    // Получение текущего узла "student"
                    Element studentElement = (Element) studentNodes.item(i);
                    // Извлечение атрибута "id" из элемента "student"
                    String id = studentElement.getAttribute("id");
                    // Извлечение текстовых данных из элементов имени, фамилии, отчества и группы
                    String firstName = studentElement.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = studentElement.getElementsByTagName("lastName").item(0).getTextContent();
                    String partonymicName = studentElement.getElementsByTagName("partonymicName").item(0).getTextContent();
                    String group = studentElement.getElementsByTagName("group").item(0).getTextContent();

                    // Создание объекта Student на основе извлеченных данных и добавление в список
                    Student student = new Student(id, firstName, lastName, partonymicName, group);
                    students.add(student);
                }
            }
        } catch (Exception e) {
            // Обработка возможных исключений, например, ошибок парсинга XML
            e.printStackTrace();
        }
    }

    // Сохранение данных в файл XML
    private void saveToXml() {
        try {
            // Создание фабрики для построения документа XML и построитель документа
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            // Создание корневого элемента "students"
            Element root = document.createElement("students");
            document.appendChild(root);

            // Создание элементов XML для каждого студента и добавление их в корневой элемент
            for (Student student : students) {
                // Создание элемента "student" с атрибутом "id"
                Element studentElement = document.createElement("student");
                studentElement.setAttribute("id", student.getId());

                // Создание элементов для имени, фамилии, отчества и группы студента
                Element firstNameElement = document.createElement("firstName");
                firstNameElement.appendChild(document.createTextNode(student.getFirstName()));
                studentElement.appendChild(firstNameElement);

                Element lastNameElement = document.createElement("lastName");
                lastNameElement.appendChild(document.createTextNode(student.getLastName()));
                studentElement.appendChild(lastNameElement);

                Element partonymicNameElement = document.createElement("partonymicName");
                partonymicNameElement.appendChild(document.createTextNode(student.getPartonymicName()));
                studentElement.appendChild(partonymicNameElement);

                Element groupElement = document.createElement("group");
                groupElement.appendChild(document.createTextNode(student.getGroup()));
                studentElement.appendChild(groupElement);

                // Добавление элемента "student" в корневой элемент
                root.appendChild(studentElement);
            }

            // Создание объекта Transformer для сохранения данных в файл
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(document);
            // Установка файла назначения для сохранения данных
            StreamResult result = new StreamResult(new File(xmlFileName));
            transformer.transform(source, result);
        } catch (Exception e) {
            // Обработка возможных исключений, например, ошибок записи XML
            e.printStackTrace();
        }
    }
}