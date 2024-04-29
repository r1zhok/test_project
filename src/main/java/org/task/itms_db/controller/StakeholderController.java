package org.task.itms_db.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.itms_db.entity.StakeholderEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.service.StakeholderService;

import java.util.List;

@RestController
@RequestMapping("/stakeholder")
public class StakeholderController {

    private final StakeholderService service;

    public StakeholderController(StakeholderService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StakeholderEntity> getStakeholder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getStakeholder(id));
    }

    @GetMapping
    public ResponseEntity<List<StakeholderEntity>> getAllStakeholders() {
        return ResponseEntity.ok(service.getAllStakeholders());
    }

    @PostMapping
    public ResponseEntity<StakeholderEntity> createStakeholder(@RequestBody StakeholderEntity newStakeholder) throws EmailExistException {
        return ResponseEntity.ok(service.createStakeholder(newStakeholder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StakeholderEntity> updateStakeholder(@PathVariable Long id, @RequestBody StakeholderEntity newStakeholder) throws UserNotFoundException, EmailExistException {
        return ResponseEntity.ok(service.updateStakeholder(id, newStakeholder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStakeholder(@PathVariable Long id) {
        service.deleteStakeholder(id);
        return ResponseEntity.ok("Stakeholder deleted");
    }
}
