package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection mySQLConnection = Util.getMySQLConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Statement statement = mySQLConnection.createStatement()) {
            boolean exists = mySQLConnection.getMetaData().getTables(mySQLConnection.getCatalog(), null,
                    "user", null).next();
            if (!exists) {
                statement.executeUpdate("""
                        CREATE TABLE user (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `name` VARCHAR(45) NULL,
                          `lastname` VARCHAR(45) NULL,
                          `age` INT(3) NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);""");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания таблицы");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = mySQLConnection.createStatement()) {
            boolean exists = mySQLConnection.getMetaData().getTables(mySQLConnection.getCatalog(), null,
                    "user", null).next();
            if (exists) {
                statement.executeUpdate("DROP TABLE user");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = mySQLConnection.prepareStatement("INSERT INTO user (`name`, `lastname`," +
                    " `age`) VALUES (?,?,?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения юзера");
        }
    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = mySQLConnection.prepareStatement("DELETE FROM user WHERE id=?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления юзера по ид");
        }
    }

    public List<User> getAllUsers() {
        try (Statement statement = mySQLConnection.createStatement()) {
            List<User> users = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения всех юзеров");
        }
    }

    public void cleanUsersTable() {
        try (Statement statement = mySQLConnection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки таблицы");
        }
    }
}
