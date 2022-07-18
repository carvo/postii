package com.carvo.postii.postiiuser.service;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.service.exception.DuplicatedDataException;
import com.carvo.postii.postiiuser.repository.UserRelationshipRepository;
import com.carvo.postii.postiiuser.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRelationshipService {

    final UserService userService;
    final UserRelationshipRepository repository;

    public User addFollowing(final Long originId, final Long targetId) {
        validateUsersExists(originId, targetId);
        final Optional<User> user = repository.addFollowing(originId, targetId);
        if (user.isEmpty()) {
            throw new DuplicatedDataException("The relationship (" + originId + ")-[FOLLOWING]->(" + targetId + ") already exists");
        }
        return user.get();
    }

    public User removeFollowing(final Long originId, final Long targetId) {
        validateUsersExists(originId, targetId);
        final Optional<User> user = repository.removeFollowing(originId, targetId);
        if (user.isEmpty()) {
            throw new DataNotFoundException("No relationship (" + originId + ")-[FOLLOWING]->(" + targetId + ") found");
        }
        return user.get();
    }

    private void validateUsersExists(Long originId, Long targetId) {
        userService.validateUserExists(originId);
        userService.validateUserExists(targetId);
    }

    @Transactional(readOnly = true)
    public List<User> findFollowings(final Long id) {
        userService.validateUserExists(id);
        return repository.findFollowingsById(id);
    }

    @Transactional(readOnly = true)
    public List<User> findFollowers(final Long id) {
        userService.validateUserExists(id);
        return repository.findFollowersById(id);
    }

}
