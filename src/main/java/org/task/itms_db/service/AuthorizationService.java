package org.task.itms_db.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.task.itms_db.entity.UserEntity;
import org.task.itms_db.exception.EmailExistException;
import org.task.itms_db.exception.UserNotFoundException;
import org.task.itms_db.jwt.JwtService;
import org.task.itms_db.model.UserModel;
import org.task.itms_db.repository.UserRepository;

@Service
public class AuthorizationService {

    private final UserRepository repository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthorizationService(UserRepository repository, AuthenticationManager authManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserModel loginUser(UserEntity user) throws UserNotFoundException {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(), user.getPassword()
                )
        );

        UserEntity existingUser = repository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Користувач не найдений"));

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new UserNotFoundException("Пароль не вірний");
        }

        return new UserModel(existingUser.getName(), existingUser.getEmail(), jwtService.generateToken(existingUser));
    }

    public UserModel registerUser(UserEntity user) throws EmailExistException {
        if (!repository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(user);
            return new UserModel(user.getName(), user.getEmail(), jwtService.generateToken(repository.save(user)));
        } else {
            throw new EmailExistException("Email already exists");
        }
    }
}