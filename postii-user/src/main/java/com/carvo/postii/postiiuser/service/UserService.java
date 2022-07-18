package com.carvo.postii.postiiuser.service;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.repository.UserRepository;
import com.carvo.postii.postiiuser.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository repository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    public Optional<User> findUser(Long id) {
        return repository.findById(id);
    }

    public void validateUserExists(Long id) {
        final Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new DataNotFoundException("User " + id + " not found");
        }
    }
}
