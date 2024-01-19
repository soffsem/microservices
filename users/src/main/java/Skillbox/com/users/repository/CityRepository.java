package Skillbox.com.users.repository;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CityRepository extends CrudRepository<City, Long> {
    List<City> findAll();
}
