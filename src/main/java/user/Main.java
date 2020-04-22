package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            dao.createTable();

            User user1 = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("password007")
                    .email("bond007@email.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user2 = User.builder()
                    .name("Anakin Skywalker")
                    .username("Sky")
                    .password("123456")
                    .email("skyawalker@gmail.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1977-08-16"))
                    .enabled(true)
                    .build();

            User user3 = User.builder()
                    .name("Leia Organa")
                    .username("Leia")
                    .password("pswrd")
                    .email("organa@gmail.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1977-08-16"))
                    .enabled(true)
                    .build();

            dao.insert(user1);
            dao.insert(user2);
            dao.insert(user3);
            dao.list().stream().forEach(System.out::println);
            System.out.println("\nSearching by id 1: " + dao.findById(1));
            System.out.println("Searching by username 'Sky': " + dao.findByUsername("Sky"));
            System.out.println("\nDeleting Luke Skywalker's profile...");
            dao.delete(user2);
            System.out.println("Updated list: ");
            List<User> users = dao.list();
            users.forEach((temp) -> {
                System.out.println(temp);
            });
        }
    }
}
