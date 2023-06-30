package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String url = "jdbc:postgresql://localhost:5432/jdbc";
        String user = "myself";
        String password = "123";
        PersonJDBC pjdbc = new PersonJDBC(url, user, password);

        Person person = new Person();
        person.setName("Chloe");
        person.setBirthday("16/10/2000");
    }
}