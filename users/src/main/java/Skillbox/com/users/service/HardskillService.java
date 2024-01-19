package Skillbox.com.users.service;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.repository.HardskillRepository;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class HardskillService {
    private final HardskillRepository hardskillRepository;

    public String createHardskill(Hardskill hardskill) {
        Hardskill savedHardskill = hardskillRepository.save(hardskill);
        return String.format("Навык %s добавлен в базу с id %s", savedHardskill.getSkill(), savedHardskill.getId());
    }

    public Hardskill getHardskill(long id) {
        return hardskillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public String updateHardskill(Hardskill hardskill) {
        Hardskill savedHardskill = hardskillRepository.save(hardskill);
        return String.format("Навык %s (id %s) успешно сохранён", savedHardskill.getSkill(), savedHardskill.getId());
    }

    public String deleteHardskill(long id) {
        Utils.verifyIdExists(hardskillRepository, id);
        hardskillRepository.deleteById(id);
        return String.format("Навык (id %s) успешно удалён", id);
    }

    public List<Hardskill> getHardskills() {
        return hardskillRepository.findAll();
    }
}
