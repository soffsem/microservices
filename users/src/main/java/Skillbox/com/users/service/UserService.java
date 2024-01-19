package Skillbox.com.users.service;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.repository.HardskillRepository;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String createUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("Пользователь %s добавлен в базу с id %s", savedUser.getSurname(), savedUser.getId());
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateUser(User user) {
        User savedUser = userRepository.save(user);
        return String.format("Пользователь %s (id %s) успешно сохранён", savedUser.getSurname(), savedUser.getId());
    }

    public String deleteUser(long id) {
        Utils.verifyIdExists(userRepository, id);
        userRepository.deleteById(id);
        return String.format("Пользователь (id %s) успешно удалён", id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Set<Hardskill> getAllHardskillsByUserId(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getUserSkills();
    }

    public String addSkillToUser(Hardskill hardskill, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.addSkill(hardskill);
        userRepository.save(user);
        return String.format("Пользователю %s (id %s) успешно добавлен навык %s (id %s)",
                user.getSurname(), user.getId(), hardskill.getSkill(), hardskill.getId());
    }

    public String removeSkillFromUser(Long id, Long hardskillId) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.removeSkill(hardskillId);
        userRepository.save(user);
        return String.format("Пользователю %s (id %s) успешно удалён навык %s",
                user.getSurname(), user.getId(), hardskillId);
    }

    public Set<User> getFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getFollowedBy();
    }

    public Set<User> getFollowees(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return user.getFollowerOf();
    }

    public String subscribeTo(User follower, Long followeeId) {
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        follower.follow(followee);
        userRepository.save(follower);
        return String.format("Пользователь %s (id %s) успешно подписался на пользователя %s (id %s)",
                follower.getSurname(), follower.getId(), followee.getSurname(), followee.getId());
    }

    public String unsubscribeFrom(Long followeeId, Long followerId) {
        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        follower.unfollow(followeeId);
        userRepository.save(follower);
        return String.format("Пользователь %s (id %s) успешно отписался от пользователя id %s",
                follower.getSurname(), follower.getId(), followeeId);
    }
}
