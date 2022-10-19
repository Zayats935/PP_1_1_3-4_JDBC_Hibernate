package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = Util.getMySQLConnection().createStatement()) {
            boolean exists = connection.getMetaData().getTables(connection.getCatalog(), null, "user", null).next();
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
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            boolean exists = connection.getMetaData().getTables(connection.getCatalog(), null, "user", null).next();
            if (exists) {
                statement.executeUpdate("DROP TABLE user");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления таблицы");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getMySQLConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (`name`, `lastname`, `age`) VALUES (?,?,?)");
            statement.setString(1,name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка сохранения юзера");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM user WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления юзера по ид");
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
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
        try (Connection connection = Util.getMySQLConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки таблицы");
        }
    }
}
