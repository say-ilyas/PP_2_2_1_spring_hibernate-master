package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User rim = new User("Rim", "Vasechkin", "rim@mail.io");
      User rimma = new User("Rimma", "Sidorov", "rimma@mail.io");
      User olya = new User("Olya", "Petrova", "olya@mail.io");
      User sveta = new User("Svetlana", "Ivanova", "ivanova@mail.io");

      Car volvo = new Car("Volvo", 9);
      Car bmw = new Car("BMW", 325);
      Car suzuki = new Car("Sisuki", 4);
      Car lada = new Car("Ladaa", 21014);

      userService.add(rim.setCar(volvo).setUser(rim));
      userService.add(rimma.setCar(bmw).setUser(rimma));
      userService.add(olya.setCar(suzuki).setUser(olya));
      userService.add(sveta.setCar(lada).setUser(sveta));

      // пользователи с машинами
      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }

      // достать юзера, владеющего машиной по ее модели и серии
      System.out.println(userService.getUserByCar("BMW", 325));

      // нет такого юзера с такой машиной
      try {
         User notFoundUser = userService.getUserByCar("GAZ", 4211);
      } catch (NoResultException e) {
         System.out.println("User not found");
      }

      context.close();
   }
}