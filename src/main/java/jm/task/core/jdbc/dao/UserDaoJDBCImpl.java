package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getConnection();
    String query;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        query = "CREATE TABLE if not exists UsersTable (id BIGINT NOT NULL AUTO_INCREMENT, " +
                                                            "name VARCHAR(50), " +
                                                            "lastName VARCHAR(50), " +
                                                            "age TINYINT, " +
                                                            "PRIMARY KEY (id));";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        query = "DROP TABLE if exists UsersTable;";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO UsersTable (name, lastName, age) values (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.out.println("INSERT Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException connExc) {
                    connExc.printStackTrace();
                }
            }
        }
    }

    public void removeUserById(long id) {
        query = "DELETE FROM UsersTable WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.out.println("DELETE Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException connExc) {
                    connExc.printStackTrace();
                }
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        query = "SELECT id, name, lastName, age from UsersTable";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            System.out.println(userList);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    System.out.println("SELECT ALL Transaction is being rolled back");
                    connection.rollback();
                } catch (SQLException connExc) {
                    connExc.printStackTrace();
                }
            }
        }
        return userList;
    }

    public void cleanUsersTable() {
        query = "TRUNCATE UsersTable;";
        try  (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
