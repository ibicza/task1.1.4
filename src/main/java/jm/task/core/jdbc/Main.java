package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        func(userService);


        userService.setUserDao(new UserDaoHibernateImpl());


        func(userService);
    }

    private static void func(UserService userService) {
        userService.createUsersTable();


        userService.saveUser("Хотелось", "бы", (byte) 25);
        System.out.println("(User с именем — Хотелось бы добавлен в базу данных");
        userService.saveUser("мне", "наВаганьково", (byte) 30);
        System.out.println("(User с именем — мне наВаганьково добавлен в базу данных");
        userService.saveUser("там", "юность", (byte) 35);
        System.out.println("(User с именем — там юность добавлен в базу данных");
        userService.saveUser("шумела", "моя", (byte) 40);
        System.out.println("(User с именем — шумела моя добавлен в базу данных");


        System.out.println("Список пользователей:");
        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }


        userService.cleanUsersTable();


        userService.dropUsersTable();
    }
}
