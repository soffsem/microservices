package skillbox.com.users.controller;

import skillbox.com.users.entity.Hardskill;
import skillbox.com.users.entity.User;
import skillbox.com.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Добавление пользователя")
    @PostMapping
    User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping(path = "/{id}")
    User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @Operation(summary = "Обновление пользователя")
    @PutMapping(path = "/{id}")
    User updateUser(@RequestBody User user, @PathVariable long id) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable long id) {
        return userService.deleteUser(id);
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Получение списка навыков пользователя")
    @GetMapping(path = "/{id}/hardskills")
    Set<Hardskill> getAllHardskillsByUserId(@PathVariable long id) {
        return userService.getAllHardskillsByUserId(id);
    }

    @Operation(summary = "Добавление навыка пользователю")
    @PostMapping(path = "/{id}/hardskills")
    User addSkillToUser(@RequestBody Hardskill hardskill, @PathVariable Long id) {
        return userService.addSkillToUser(hardskill, id);
    }

    @Operation(summary = "Удаление навыка у пользователя")
    @DeleteMapping(path = "/{id}/hardskills/{hardskillId}")
    ResponseEntity<Void> removeSkillFromUser(@PathVariable long id, @PathVariable long hardskillId) {
        return userService.removeSkillFromUser(id, hardskillId);
    }

    @Operation(summary = "Получение списка подписчиков пользователя")
    @GetMapping(path = "/{id}/followers")
    Set<User> getFollowers(@PathVariable long id) {
        return userService.getFollowers(id);
    }

    @Operation(summary = "Получение списка подписок пользователя")
    @GetMapping(path = "/{id}/followees")
    Set<User> getFollowees(@PathVariable long id) {
        return userService.getFollowees(id);
    }

    @Operation(summary = "Подписка на нового пользователя")
    @PostMapping(path = "/{followeeId}/followers")
    User subscribeTo(@RequestBody User follower, @PathVariable long followeeId) {
        return userService.subscribeTo(follower, followeeId);
    }

    @Operation(summary = "Отписка от пользователя")
    @DeleteMapping(path = "/{followeeId}/followers/{followerId}")
    User unsubscribeFrom(@PathVariable long followeeId, @PathVariable long followerId) {
        return userService.unsubscribeFrom(followeeId, followerId);
    }
}
