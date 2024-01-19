package Skillbox.com.users.service;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.repository.CityRepository;
import Skillbox.com.users.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public String createCity(City city){
        City savedCity = cityRepository.save(city);
        return String.format("Город %s добавлен в базу с id %s", savedCity.getCityName(), savedCity.getId());
    }

    public City getCity(long id) {
        return cityRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateCity(City city) {
        City savedCity = cityRepository.save((city));
        return String.format("Город %s (id %s) успешно сохранён", savedCity.getCityName(), savedCity.getId());
    }

    public String deleteCity(long id) {
        Utils.verifyIdExists(cityRepository, id);
        cityRepository.deleteById(id);
        return String.format("Город (id %s) успешно удалён", id);
    }

    public List<City> getCities() {
        return cityRepository.findAll();
    }
}
