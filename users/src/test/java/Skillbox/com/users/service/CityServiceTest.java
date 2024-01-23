package Skillbox.com.users.service;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CityServiceTest {
    CityRepository cityRepository = Mockito.mock(CityRepository.class);
    long cityId = 1L;

    @Test
    void createCity() {
        City city = City.builder().cityName("Москва").countryName("Россия").build();
        City savedCity = City.builder().id(cityId).cityName("Москва").countryName("Россия").build();
        Mockito.when(cityRepository.save(city)).thenReturn(savedCity);
        CityService cityService = new CityService(cityRepository);

        City result = cityService.createCity(city);

        Assertions.assertEquals(savedCity, result);
    }

    @Test
    void getCity() {
        City city = City.builder().id(cityId).cityName("Москва").countryName("Россия").build();
        Mockito.when(cityRepository.findById(cityId)).thenReturn(Optional.ofNullable(city));
        CityService cityService = new CityService(cityRepository);

        Assertions.assertEquals(city, cityService.getCity(cityId));
    }

    @Test
    void getCityWhenNotExists() {
        CityService cityService = new CityService(cityRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            cityService.getCity(cityId);
        });
    }

    @Test
    void updateCity() {
        City updatedCity = City.builder().id(cityId).cityName("Санкт-Петербург").countryName("Россия").build();
        Mockito.when(cityRepository.save(updatedCity)).thenReturn(updatedCity);

        CityService cityService = new CityService(cityRepository);
        City result = cityService.updateCity(updatedCity);

        Assertions.assertEquals(updatedCity, result);
    }

    @Test
    void deleteCity() {
        CityService cityService = new CityService(cityRepository);
        Mockito.when(cityRepository.existsById(cityId)).thenReturn(true);
        ResponseEntity<Void> result = cityService.deleteCity(cityId);

        Assertions.assertEquals(ResponseEntity.noContent().build(), result);
    }

    @Test
    void deleteCityWhenNotExists() {
        CityService cityService = new CityService(cityRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            cityService.deleteCity(cityId);
        });
    }

    @Test
    void getCities() {
        List<City> cities = new ArrayList<>();
        Collections.addAll(cities,
                City.builder().id(1L).cityName("Москва").countryName("Россия").build(),
                City.builder().id(2L).cityName("Санкт-Петербург").countryName("Россия").build(),
                City.builder().id(3L).cityName("Минск").countryName("Беларусь").build());
        Mockito.when(cityRepository.findAll()).thenReturn(cities);
        CityService cityService = new CityService(cityRepository);

        Assertions.assertEquals(cities, cityService.getCities());
    }
}