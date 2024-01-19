package Skillbox.com.users.controller;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.entity.User;
import Skillbox.com.users.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/cities")
@AllArgsConstructor
public class CityController {

    private final CityService cityService;

    @Operation(summary = "Добавление города")
    @PostMapping
    String createCity(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @Operation(summary = "Получение города")
    @GetMapping(path = "/{id}")
    City getCity(@PathVariable long id) {
        return cityService.getCity(id);
    }

    @Operation(summary = "Обновление города")
    @PutMapping(path = "/{id}")
    String updateCity(@RequestBody City city, @PathVariable long id) {
        city.setId(id);
        return cityService.updateCity(city);
    }

    @Operation(summary = "Удаление города")
    @DeleteMapping(path = "/{id}")
    String deleteCity(@PathVariable long id){
        return cityService.deleteCity(id);
    }

    @Operation(summary = "Получение списка городов")
    @GetMapping
    List<City> getCities() {
        return cityService.getCities();
    }
}
