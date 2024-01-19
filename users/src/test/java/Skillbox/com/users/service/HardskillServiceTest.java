package Skillbox.com.users.service;

import Skillbox.com.users.entity.City;
import Skillbox.com.users.entity.Hardskill;
import Skillbox.com.users.repository.CityRepository;
import Skillbox.com.users.repository.HardskillRepository;
import Skillbox.com.users.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HardskillServiceTest {

    HardskillRepository hardskillRepository = Mockito.mock(HardskillRepository.class);
    long skillId = 1L;

    @Test
    void createHardskill() {
        Hardskill hardskill = Hardskill.builder().skill("Java").build();
        Hardskill savedHardskill = Hardskill.builder().id(skillId).skill("Java").build();
        Mockito.when(hardskillRepository.save(hardskill)).thenReturn(savedHardskill);
        HardskillService hardskillService = new HardskillService(hardskillRepository);

        String result = hardskillService.createHardskill(hardskill);

        Assertions.assertEquals("Навык Java добавлен в базу с id 1", result);
    }

    @Test
    void getHardskill() {
        Hardskill hardskill = Hardskill.builder().skill("Java").build();
        Mockito.when(hardskillRepository.findById(skillId)).thenReturn(Optional.ofNullable(hardskill));
        HardskillService hardskillService = new HardskillService(hardskillRepository);

        Assertions.assertEquals(hardskill, hardskillService.getHardskill(skillId));
    }

    @Test
    void getHardskillWhenNotExists() {
        HardskillService hardskillService = new HardskillService(hardskillRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            hardskillService.getHardskill(skillId);
        });
    }

    @Test
    void updateHardskill() {
        Hardskill updatedHardskill = Hardskill.builder().id(skillId).skill("C++").build();
        Mockito.when(hardskillRepository.save(updatedHardskill)).thenReturn(updatedHardskill);

        HardskillService hardskillService = new HardskillService(hardskillRepository);
        String result = hardskillService.updateHardskill(updatedHardskill);

        Assertions.assertEquals("Навык C++ (id 1) успешно сохранён", result);
    }

    @Test
    void deleteHardskill() {
        HardskillService hardskillService = new HardskillService(hardskillRepository);
        Mockito.when(hardskillRepository.existsById(skillId)).thenReturn(true);
        String result = hardskillService.deleteHardskill(skillId);

        Assertions.assertEquals("Навык (id 1) успешно удалён", result);
    }

    @Test
    void deleteHardskillWhenNotExists() {
        HardskillService hardskillService = new HardskillService(hardskillRepository);
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            hardskillService.deleteHardskill(skillId);
        });
    }

    @Test
    void getHardskills() {
        List<Hardskill> hardskills = new ArrayList<>();
        Collections.addAll(hardskills,
                Hardskill.builder().id(1L).skill("Java").build(),
                Hardskill.builder().id(2L).skill("Spring").build(),
                Hardskill.builder().id(3L).skill("JUnit").build());
        Mockito.when(hardskillRepository.findAll()).thenReturn(hardskills);
        HardskillService hardskillService = new HardskillService(hardskillRepository);

        Assertions.assertEquals(hardskills, hardskillService.getHardskills());
    }
}