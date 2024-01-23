package Skillbox.com.users.service;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Sex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

class UserServiceTest {

    UserRepository userRepository = Mockito.mock(UserRepository.class);

    User initDefaultUserFull(){
        return User.builder().
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
    }

    User initDefaultUserShort(){
        return User.builder().
                firstName("Анна").
                middleName("Ивановна").
                surname("Иванова").
                birthday(LocalDate.of(2024, 1, 19)).
                sex(Sex.F).
                city_id(1).
                nickname("anyusha").
                passwordHash("123321").
                build();
    }

    @Test
    void createUser() {
        User user = initDefaultUserFull();
        User savedUser = initDefaultUserFull();
        savedUser.setId(1L);

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);

        User result = userService.createUser(user);

        Assertions.assertEquals(savedUser, result);
    }

    @Test
    void createUserOnlyNecessaryFields() {
        User user = initDefaultUserShort();
        User savedUser = initDefaultUserShort();
        savedUser.setId(1L);

        Mockito.when(userRepository.save(user)).thenReturn(savedUser);
        UserService userService = new UserService(userRepository);

        User result = userService.createUser(user);

        Assertions.assertEquals(savedUser, result);
    }

    @Test
    void getUser() {
        long userId = 1L;
        User user = initDefaultUserShort();
        user.setId(userId);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(user, userService.getUser(userId));
    }

    @Test
    void getUserWhenNotExists() {
        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.getUser(1L);
        });
    }

    @Test
    void updateUser() {
        User updatedUser = initDefaultUserFull();
        updatedUser.setId(1L);
        updatedUser.setSurname("Петров");

        Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        UserService userService = new UserService(userRepository);
        User result = userService.updateUser(updatedUser);

        Assertions.assertEquals(updatedUser, result);
    }

    @Test
    void deleteUser() {
        long userId = 1L;
        UserService userService = new UserService(userRepository);
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        ResponseEntity<Void> result = userService.deleteUser(userId);

        Assertions.assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void deleteUserWhenNotExists() {
        UserService userService = new UserService(userRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.deleteUser(1L);
        });
    }

    @Test
    void getUsers() {
        List<User> users = new ArrayList<>();
        users.add(initDefaultUserFull());
        users.get(0).setId(1L);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(users, userService.getUsers());
    }

    @Test
    void getAllHardskillsByUserId() {
        long userId = 1L;
        Set<Hardskill> hardskills = new HashSet<>();
        Collections.addAll(hardskills,
                Hardskill.builder().id(1L).skill("Java").build(),
                Hardskill.builder().id(2L).skill("Spring").build(),
                Hardskill.builder().id(3L).skill("JUnit").build());

        User user = initDefaultUserShort();
        user.setId(userId);

        user.setUserSkills(hardskills);
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(hardskills, userService.getAllHardskillsByUserId(userId));
    }

    @Test
    void addSkillToUser() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);
        user.setUserSkills(new HashSet<>());

        Hardskill hardskill = Hardskill.builder().id(10L).skill("Java").build();
        hardskill.setUsers(new HashSet<>());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        User result = userService.addSkillToUser(hardskill, userId);

        Assertions.assertEquals(user, result);
    }

    @Test
    void removeSkillFromUser() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);
        user.setUserSkills(new HashSet<>());

        Hardskill hardskill = Hardskill.builder().id(10L).skill("Java").build();
        hardskill.setUsers(new HashSet<>());

        user.getUserSkills().add(hardskill);
        hardskill.getUsers().add(user);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        ResponseEntity<Void> result = userService.removeSkillFromUser(userId, 10L);

        Assertions.assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void getFollowers() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);

        Set<User> followers = new HashSet<>();
        User follower = initDefaultUserShort();
        follower.setId(2L);
        followers.add(follower);
        user.setFollowedBy(followers);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(followers, userService.getFollowers(userId));
    }

    @Test
    void getFollowees() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);
        Set<User> followees = new HashSet<>();
        User followee = initDefaultUserShort();
        followee.setId(2L);
        followees.add(followee);
        user.setFollowerOf(followees);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);

        Assertions.assertEquals(followees, userService.getFollowees(userId));
    }

    @Test
    void subscribeTo() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);

        long followerId = 2L;
        User follower = initDefaultUserShort();
        follower.setId(followerId);

        user.setFollowedBy(new HashSet<>());
        follower.setFollowerOf(new HashSet<>());

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserService userService = new UserService(userRepository);
        User result = userService.subscribeTo(follower, userId);

        Assertions.assertEquals(user, result);
    }

    @Test
    void subscribeToNonExistingUser() {
        User follower = initDefaultUserFull();
        follower.setId(2L);

        follower.setFollowerOf(new HashSet<>());

        UserService userService = new UserService(userRepository);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.subscribeTo(follower, 1L));
    }

    @Test
    void unsubscribeFrom() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);

        long followerId = 2L;
        User follower = initDefaultUserShort();
        follower.setId(followerId);

        user.setFollowedBy(new HashSet<>());
        user.getFollowedBy().add(follower);
        follower.setFollowerOf(new HashSet<>());
        follower.getFollowerOf().add(user);

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(follower));
        UserService userService = new UserService(userRepository);
        User result = userService.unsubscribeFrom(userId, followerId);

        Assertions.assertEquals(follower, result);
    }

    @Test
    void unsubscribeNonExistingFollower() {
        long userId = 1L;
        User user = initDefaultUserFull();
        user.setId(userId);

        user.setFollowedBy(new HashSet<>());
        UserService userService = new UserService(userRepository);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> userService.unsubscribeFrom(userId, 2L));
    }
}
