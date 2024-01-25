package Skillbox.com.users.service;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.repository.HardskillRepository;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Utils;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;

    public User createUser(User user) {
        User savedUser = userRepository.save(user);
        log.info(String.format("Пользователь %s добавлен в базу с id %s", savedUser.getSurname(), savedUser.getId()));
        return savedUser;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public User updateUser(User user) {
        User savedUser = userRepository.save(user);
        log.info(String.format("Пользователь %s (id %s) успешно сохранён", savedUser.getSurname(), savedUser.getId()));
        return savedUser;
    }

    public ResponseEntity<Void> deleteUser(long id) {
        Utils.verifyIdExists(userRepository, id);
        userRepository.deleteById(id);
        log.info(String.format("Пользователь (id %s) успешно удалён", id));
        return ResponseEntity.noContent().build();
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Set<Hardskill> getAllHardskillsByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getUserSkills();
    }

    public User addSkillToUser(Hardskill hardskill, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.addSkill(hardskill);
        userRepository.save(user);
        log.info(String.format("Пользователю %s (id %s) успешно добавлен навык %s (id %s)",
                user.getSurname(), user.getId(), hardskill.getSkill(), hardskill.getId()));
        return user;
    }

    public ResponseEntity<Void> removeSkillFromUser(Long id, Long hardskillId) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.removeSkill(hardskillId);
        userRepository.save(user);
        log.info(String.format("Пользователю %s (id %s) успешно удалён навык %s",
                user.getSurname(), user.getId(), hardskillId));
        return ResponseEntity.noContent().build();
    }

    public Set<User> getFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getFollowedBy();
    }

    public Set<User> getFollowees(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getFollowerOf();
    }

    public User subscribeTo(User follower, Long followeeId) {
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        follower.follow(followee);
        userRepository.save(follower);
        log.info(String.format("Пользователь %s (id %s) успешно подписался на пользователя %s (id %s)",
                follower.getSurname(), follower.getId(), followee.getSurname(), followee.getId()));
        return followee;
    }

    public User unsubscribeFrom(Long followeeId, Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        follower.unfollow(followeeId);
        userRepository.save(follower);
        log.info(String.format("Пользователь %s (id %s) успешно отписался от пользователя id %s",
                follower.getSurname(), follower.getId(), followeeId));
        return follower;
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }
}
