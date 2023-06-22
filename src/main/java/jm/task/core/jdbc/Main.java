package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        userServiceImpl.createUsersTable();
        userServiceImpl.saveUser("Ivan", "Ivanov", (byte) 20);
        userServiceImpl.saveUser("Igor", "Igorev", (byte) 33);
        userServiceImpl.saveUser("Elena", "Elenova", (byte) 19);
        userServiceImpl.saveUser("Zhanna", "Zhannova", (byte) 56);
        userServiceImpl.getAllUsers();
        userServiceImpl.cleanUsersTable();
        userServiceImpl.dropUsersTable();


    }
}
