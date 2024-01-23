package Skillbox.com.users.service;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.repository.CityRepository;
import Skillbox.com.users.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class CityService {
    private final CityRepository cityRepository;

    public City createCity(City city){
        City savedCity = cityRepository.save(city);
        log.info(String.format("Город %s добавлен в базу с id %s", savedCity.getCityName(), savedCity.getId()));
        return savedCity;
    }

    public City getCity(long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public City updateCity(City city) {
        City savedCity = cityRepository.save((city));
        log.info(String.format("Город %s (id %s) успешно сохранён", savedCity.getCityName(), savedCity.getId()));
        return savedCity;
    }

    public ResponseEntity<Void> deleteCity(long id) {
        Utils.verifyIdExists(cityRepository, id);
        cityRepository.deleteById(id);
        log.info(String.format("Город (id %s) успешно удалён", id));
        return ResponseEntity.noContent().build();
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
