package Skillbox.com.users.service;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Sex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

class UserServiceTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    long userId = 1L;

    @Test
    void createUser() {
        User user = User.builder().
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                phoneNumber("88005553535").
                email("test@test.ru").
                nickname("vanyusha").
                passwordHash("123").
                avatarLink("link").
                aboutUser("I am Vanya").
                build();

        User savedUser = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                phoneNumber("88005553535").
                email("test@test.ru").
                nickname("vanyusha").
                passwordHash("123").
                avatarLink("link").
                aboutUser("I am Vanya").
                build();

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);

        String result = userService.createUser(user);

        Assertions.assertEquals("Пользователь Иванов добавлен в базу с id 1", result);
    }

    @Test
    void createUserOnlyNecessaryFields() {
        User user = User.builder().
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        User savedUser = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);

        String result = userService.createUser(user);

        Assertions.assertEquals("Пользователь Иванов добавлен в базу с id 1", result);
    }

    @Test
    void createUserWithoutNecessaryFields() {
        User user = User.builder().
                firstName("Иван").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.createUser(user);
        });
    }

    @Test
    void getUser() {
    }

    @Test
    void getUserWhenNotExists() {
        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.getUser(userId);
        });
    }

    @Test
    void updateUser() {
        User updatedUser = User.builder().
                id(userId).
                firstName("Иван").
                middleName("Петрович").
                surname("Петров").
                birthday(LocalDate.of(1980, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();
        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        UserService userService = new UserService(userRepository);
        String result = userService.updateUser(updatedUser);

        Assertions.assertEquals("Пользователь Петров (id 1) успешно сохранён", result);
    }

    @Test
    void deleteUser() {
        UserService userService = new UserService(userRepository);
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        String result = userService.deleteUser(userId);

        Assertions.assertEquals("Пользователь (id 1) успешно удалён", result);
    }

    @Test
    void deleteUserWhenNotExists() {
        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUser(userId);
        });
    }

    @Test
    void getUsers() {
        List<User> users = new ArrayList<>();
        Collections.addAll(users,
                User.builder().
                        firstName("Иван").
                        middleName("Иванович").
                        surname("Иванов").
                        birthday(LocalDate.of(2024, 1, 18)).
                        sex(Sex.M).
                        city_id(1).
                        nickname("vanyusha").
                        passwordHash("123").
                        build(),
                User.builder().
                        firstName("Анна").
                        middleName("Ивановна").
                        surname("Иванова").
                        birthday(LocalDate.of(2024, 1, 19)).
                        sex(Sex.F).
                        city_id(1).
                        nickname("anyusha").
                        passwordHash("123321").
                        build(),
                User.builder().
                        firstName("Пётр").
                        middleName("Петрович").
                        surname("Петров").
                        birthday(LocalDate.of(2024, 1, 18)).
                        sex(Sex.M).
                        city_id(2).
                        nickname("petrusha").
                        passwordHash("1").
                        build());
        Mockito.when(userRepository.findAll()).thenReturn(users);
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(users, userService.getUsers());
    }

    @Test
    void getAllHardskillsByUserId() {
        Set<Hardskill> hardskills = new HashSet<>();
        Collections.addAll(hardskills,
                Hardskill.builder().id(1L).skill("Java").build(),
                Hardskill.builder().id(2L).skill("Spring").build(),
                Hardskill.builder().id(3L).skill("JUnit").build());

        User user = User.builder().
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();;
        user.setUserSkills(hardskills);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(hardskills, userService.getAllHardskillsByUserId(userId));
    }

    @Test
    void addSkillToUser() {
        User user = User.builder().
                id(userId).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();
        user.setUserSkills(new HashSet<>());

        Hardskill hardskill = Hardskill.builder().id(10L).skill("Java").build();
        hardskill.setUsers(new HashSet<>());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        String result = userService.addSkillToUser(hardskill, userId);

        Assertions.assertEquals("Пользователю Иванов (id 1) успешно добавлен навык Java (id 10)", result);
    }

    @Test
    void removeSkillFromUser() {
        User user = User.builder().
                id(userId).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();
        user.setUserSkills(new HashSet<>());

        Hardskill hardskill = Hardskill.builder().id(10L).skill("Java").build();
        hardskill.setUsers(new HashSet<>());

        user.getUserSkills().add(hardskill);
        hardskill.getUsers().add(user);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        String result = userService.removeSkillFromUser(userId, 10L);

        Assertions.assertEquals("Пользователю Иванов (id 1) успешно удалён навык 10", result);
    }

    @Test
    void getFollowers() {
        User user = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        Set<User> followers = new HashSet<>();
        Collections.addAll(followers,
                User.builder().
                        id(2L).
                        firstName("Анна").
                        middleName("Ивановна").
                        surname("Иванова").
                        birthday(LocalDate.of(2024, 1, 19)).
                        sex(Sex.F).
                        city_id(1).
                        nickname("anyusha").
                        passwordHash("123321").
                        build());
        user.setFollowedBy(followers);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(followers, userService.getFollowers(userId));
    }

    @Test
    void getFollowees() {
        User user = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        Set<User> followees = new HashSet<>();
        Collections.addAll(followees,
                User.builder().
                        id(2L).
                        firstName("Анна").
                        middleName("Ивановна").
                        surname("Иванова").
                        birthday(LocalDate.of(2024, 1, 19)).
                        sex(Sex.F).
                        city_id(1).
                        nickname("anyusha").
                        passwordHash("123321").
                        build());
        user.setFollowerOf(followees);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(followees, userService.getFollowees(userId));
    }

    @Test
    void subscribeTo() {
        User user = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();


        User follower = User.builder().
                        id(2L).
                        firstName("Анна").
                        middleName("Ивановна").
                        surname("Иванова").
                        birthday(LocalDate.of(2024, 1, 19)).
                        sex(Sex.F).
                        city_id(1).
                        nickname("anyusha").
                        passwordHash("123321").
                        build();

        user.setFollowedBy(new HashSet<>());
        follower.setFollowerOf(new HashSet<>());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        String result = userService.subscribeTo(follower, userId);

        Assertions.assertEquals("Пользователь Иванова (id 2) успешно подписался на пользователя Иванов (id 1)",
                result);
    }

    @Test
    void subscribeToNonExistingUser() {
        User follower = User.builder().
                id(2L).
                firstName("Анна").
                middleName("Ивановна").
                surname("Иванова").
                birthday(LocalDate.of(2024, 1, 19)).
                sex(Sex.F).
                city_id(1).
                nickname("anyusha").
                passwordHash("123321").
                build();

        follower.setFollowerOf(new HashSet<>());

        UserService userService = new UserService(userRepository);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.subscribeTo(follower, 1L));
    }

    @Test
    void unsubscribeFrom() {
        User user = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        User follower = User.builder().
                id(2L).
                firstName("Анна").
                middleName("Ивановна").
                surname("Иванова").
                birthday(LocalDate.of(2024, 1, 19)).
                sex(Sex.F).
                city_id(1).
                nickname("anyusha").
                passwordHash("123321").
                build();

        user.setFollowedBy(new HashSet<>());
        user.getFollowedBy().add(follower);
        follower.setFollowerOf(new HashSet<>());
        follower.getFollowerOf().add(user);

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(follower));
        UserService userService = new UserService(userRepository);
        String result = userService.unsubscribeFrom(1L, 2L);

        Assertions.assertEquals("Пользователь Иванова (id 2) успешно отписался от пользователя id 1",
                result);
    }

    @Test
    void unsubscribeNonExistingFollower() {
        User user = User.builder().
                id(1L).
                firstName("Иван").
                middleName("Иванович").
                surname("Иванов").
                birthday(LocalDate.of(2024, 1, 18)).
                sex(Sex.M).
                city_id(1).
                nickname("vanyusha").
                passwordHash("123").
                build();

        user.setFollowedBy(new HashSet<>());
        UserService userService = new UserService(userRepository);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.unsubscribeFrom(1L, 2L));
    }
}