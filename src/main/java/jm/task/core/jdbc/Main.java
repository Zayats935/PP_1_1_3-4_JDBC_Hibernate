package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;


public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("Ivan", "Petrov", (byte) 20);
        userDao.saveUser("Sergey", "Ivanov", (byte) 25);
        userDao.saveUser("Maksim", "Pupkin", (byte) 31);
        userDao.saveUser("Petr", "Shumkin", (byte) 38);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
