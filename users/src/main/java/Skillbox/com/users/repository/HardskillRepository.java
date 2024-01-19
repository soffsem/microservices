package Skillbox.com.users.repository;

import Skillbox.com.users.entity.Hardskill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HardskillRepository  extends CrudRepository<Hardskill, Long> {
    List<Hardskill> findAll();
}
