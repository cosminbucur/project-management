package com.sda.project.management.service;

import com.sda.project.management.model.Sprint;
import com.sda.project.management.repository.SprintRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService {

    private static final Logger log = LoggerFactory.getLogger(SprintService.class);
    private final SprintRepository sprintRepository;

    @Autowired
    public SprintService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    public void save(Sprint sprint){
        log.info("save sprint {}", sprint);
        sprintRepository.save(sprint);
    }

    public List<Sprint> findAll(){
        log.info("find sprints");
        return sprintRepository.findAll();
    }

    public Sprint findById(Long id) {
        log.info("find sprint {}", id);
        return sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    public void update(Sprint sprint) {
       sprintRepository.save(sprint);
    }

    public void delete(Long id) {
        log.info("delete sprint {}", id);
        sprintRepository.deleteById(id);
    }
}
