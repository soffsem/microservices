package skillbox.com.users.repository;

import skillbox.com.users.entity.Hardskill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HardskillRepository  extends CrudRepository<Hardskill, Long> {
    List<Hardskill> findAll();
}
