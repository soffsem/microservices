package Skillbox.com.users.controller;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Добавление пользователя")
    @PostMapping
    String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "Получение пользователя")
    @GetMapping(path = "/{id}")
    User getUser(@PathVariable long id){
        return userService.getUser(id);
    }

    @Operation(summary = "Обновление пользователя")
    @PutMapping(path = "/{id}")
    String updateUser(@RequestBody User user, @PathVariable long id) {
        user.setId(id);
        return userService.updateUser(user);
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping(path = "/{id}")
    String deleteUser(@PathVariable long id){
        return userService.deleteUser(id);
    }

    @Operation(summary = "Получение списка всех пользователей")
    @GetMapping
    List<User> getUsers() {
        return userService.getUsers();
    }

    @Operation(summary = "Получение списка навыков пользователя")
    @GetMapping(path = "/{id}/hardskills")
    Set<Hardskill> getAllHardskillsByUserId(@PathVariable long id ) {
        return userService.getAllHardskillsByUserId(id);
    }

    @Operation(summary = "Добавление навыка пользователю")
    @PostMapping(path = "/{id}/hardskills")
    String addSkillToUser(@RequestBody Hardskill hardskill, @PathVariable Long id) {
        return userService.addSkillToUser(hardskill, id);
    }

    @Operation(summary = "Удаление навыка у пользователя")
    @DeleteMapping(path = "/{id}/hardskills/{hardskillId}")
    String removeSkillFromUser(@PathVariable long id, @PathVariable long hardskillId) {
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
    String subscribeTo(@RequestBody User follower, @PathVariable long followeeId) {
        return userService.subscribeTo(follower, followeeId);
    }

    @Operation(summary = "Отписка от пользователя")
    @DeleteMapping(path = "/{followeeId}/followers/{followerId}")
    String unsubscribeFrom(@PathVariable long followeeId, @PathVariable long followerId) {
        return userService.unsubscribeFrom(followeeId, followerId);
    }
}
