package org.task.itms_db.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.task.itms_db.entity.UserEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.model.UserModel;
import org.task.itms_db.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserModel getUser(Long id, HttpServletRequest request) {
        UserEntity user = repository.findById(id).get();
        return new UserModel(user.getName(), user.getEmail(), request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public List<UserModel> getAllUsers(HttpServletRequest request) {
        return repository.findAll().stream().map(entity -> new UserModel(entity.getName(), entity.getEmail(),
                request.getHeader(HttpHeaders.AUTHORIZATION))).collect(Collectors.toList());
    }

    public UserModel createUsers(UserEntity newUser, HttpServletRequest request) {
        UserEntity user = repository.save(newUser);
        return new UserModel(user.getName(), user.getName(), request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    public UserModel updateUser(Long id, UserEntity newUsers, HttpServletRequest request) throws EmailExistException {
        Optional<UserEntity> oldUser = repository.findByEmail(newUsers.getEmail());
        if (!oldUser.isPresent() || oldUser.get().getId().equals(id)) {
            newUsers.setId(id);
            repository.save(newUsers);
            return new UserModel(newUsers.getName(), newUsers.getEmail(), request.getHeader(HttpHeaders.AUTHORIZATION));
        }
        throw new EmailExistException("Такий email вже існує");
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
