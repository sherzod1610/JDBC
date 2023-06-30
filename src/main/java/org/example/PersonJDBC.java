package org.example;

import java.sql.*;
import java.util.ArrayList;

public class PersonJDBC implements PersonDAO{

    private Connection connection;

    public PersonJDBC(String url, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        this.connection = DriverManager.getConnection(url, user, password);
    }
    @Override
    public void addPerson(Person person) throws SQLException {
        String sql = "insert into person(name, birthday)" + "values (?, ?)";

        PreparedStatement ps =  this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, person.getName());
        ps.setString(2, person.getBirthday());

        ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()){
            person.setId((generatedKeys.getInt(1)));
        }
    }

    @Override
    public void removePerson(Person person) throws SQLException {
        String sql = "delete from person where id = ?";
        PreparedStatement ps = this.connection.prepareStatement(sql);
        ps.setInt(1, person.getId());
        ps.executeUpdate();
    }

    @Override
    public Person getPersonById(int id) throws SQLException {
        String sql = "select * from person where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();
        Person person = new Person();
        if (resultSet.next()){
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setBirthday(resultSet.getString("birthday"));
        }else {
            System.out.println("No row found with id " + id);
        }
        return person;
    }

    @Override
    public ArrayList<Person> getAllPersons() throws SQLException {
        ArrayList<Person> arrayList = new ArrayList<>();
        ResultSet resultSet = this.connection.prepareStatement("select * from person").executeQuery();
        while (resultSet.next()){
            Person person = new Person();

            person.setName(resultSet.getString("name"));
            person.setBirthday(resultSet.getString("birthdate"));
            person.setId(resultSet.getInt("id"));
            arrayList.add(person);
        }
        resultSet.close();
        return arrayList;
    }
}
