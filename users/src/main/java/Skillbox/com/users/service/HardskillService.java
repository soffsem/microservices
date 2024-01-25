package Skillbox.com.users.service;

import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.repository.HardskillRepository;
import Skillbox.com.users.repository.UserRepository;
import Skillbox.com.users.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class HardskillService {
    private final HardskillRepository hardskillRepository;

    public Hardskill createHardskill(Hardskill hardskill) {
        Hardskill savedHardskill = hardskillRepository.save(hardskill);
        log.info(String.format("Навык %s добавлен в базу с id %s", savedHardskill.getSkill(), savedHardskill.getId()));
        return savedHardskill;
    }

    public Hardskill getHardskill(long id) {
        return hardskillRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Hardskill updateHardskill(Hardskill hardskill) {
        Hardskill savedHardskill = hardskillRepository.save(hardskill);
        log.info(String.format("Навык %s (id %s) успешно сохранён", savedHardskill.getSkill(), savedHardskill.getId()));
        return savedHardskill;
    }

    public ResponseEntity<Void> deleteHardskill(long id) {
        Utils.verifyIdExists(hardskillRepository, id);
        hardskillRepository.deleteById(id);
        log.info(String.format("Навык (id %s) успешно удалён", id));
        return ResponseEntity.noContent().build();
    }

    public List<Hardskill> getHardskills() {
        return hardskillRepository.findAll();
    }

    public void deleteAll() {
        hardskillRepository.deleteAll();
    }
}
