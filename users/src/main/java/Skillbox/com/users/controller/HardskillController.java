package Skillbox.com.users.controller;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.service.HardskillService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/hardskills")
@AllArgsConstructor
public class HardskillController {

    private final HardskillService hardskillService;

    @Operation(summary = "Добавление навыка")
    @PostMapping
    String createHardskill(@RequestBody Hardskill hardskill) {
        return hardskillService.createHardskill(hardskill);
    }

    @Operation(summary = "Получение навыка")
    @GetMapping(path = "/{id}")
    Hardskill getHardskill(@PathVariable long id){
        return hardskillService.getHardskill(id);
    }

    @Operation(summary = "Обновление навыка")
    @PutMapping("/{id}")
    String updateHardskill(@RequestBody Hardskill hardskill, @PathVariable long id) {
        hardskill.setId(id);
        return hardskillService.updateHardskill(hardskill);
    }

    @Operation(summary = "Удаление навыка")
    @DeleteMapping(path = "/{id}")
    String deleteHardskill(@PathVariable long id){
        return hardskillService.deleteHardskill(id);
    }

    @Operation(summary = "Получение списка навыков")
    @GetMapping
    List<Hardskill> getHardskills() {
        return hardskillService.getHardskills();
    }
}
