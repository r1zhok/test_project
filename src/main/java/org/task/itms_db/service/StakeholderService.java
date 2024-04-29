package org.task.itms_db.service;

import org.springframework.stereotype.Service;
import org.task.itms_db.entity.StakeholderEntity;
import org.task.itms_db.entity.UserEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.repository.StakeholderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StakeholderService {

    private final StakeholderRepository repository;

    public StakeholderService(StakeholderRepository repository) {
        this.repository = repository;
    }

    public StakeholderEntity getStakeholder(Long id) {
        return repository.findById(id).get();
    }

    public List<StakeholderEntity> getAllStakeholders() {
        return repository.findAll();
    }

    public StakeholderEntity createStakeholder(StakeholderEntity newStakeholder) throws EmailExistException {
        if (!repository.existsByEmail(newStakeholder.getEmail())) {
            return repository.save(newStakeholder);
        }
        throw new EmailExistException("Такий email вже існує");
    }

    public StakeholderEntity updateStakeholder(Long id, StakeholderEntity newStakeholder) throws UserNotFoundException, EmailExistException {
        Optional<StakeholderEntity> oldStakeholder = repository.findByEmail(newStakeholder.getEmail());
        if (!oldStakeholder.isPresent() || oldStakeholder.get().getId().equals(id)) {
            newStakeholder.setId(id);
            return repository.save(newStakeholder);
        }
        throw new EmailExistException("Такий email вже існує");
    }

    public void deleteStakeholder(Long id) {
        repository.deleteById(id);
    }
}
