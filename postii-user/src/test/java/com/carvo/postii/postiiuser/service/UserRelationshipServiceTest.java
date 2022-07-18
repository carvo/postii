package com.carvo.postii.postiiuser.service;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.repository.UserRelationshipRepository;
import com.carvo.postii.postiiuser.service.exception.DataNotFoundException;
import com.carvo.postii.postiiuser.service.exception.DuplicatedDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRelationshipServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRelationshipRepository repository;

    @InjectMocks
    private UserRelationshipService relationshipService;

    @Test
    void shouldAddFollowing() {
        final Long originId = 1L;
        final Long targetId = 2L;
        final User returnedUser = new User();

        doNothing().when(userService).validateUserExists(originId);
        doNothing().when(userService).validateUserExists(targetId);
        when(repository.addFollowing(originId, targetId)).thenReturn(Optional.of(returnedUser));

        final User user = relationshipService.addFollowing(originId, targetId);

        verify(repository, times(1)).addFollowing(originId, targetId);
        Assertions.assertSame(returnedUser, user);
    }

    @Test
    void shouldThrowExceptionForRepeatedFollowing() {
        final Long originId = 1L;
        final Long targetId = 2L;

        doNothing().when(userService).validateUserExists(originId);
        doNothing().when(userService).validateUserExists(targetId);
        when(repository.addFollowing(originId, targetId)).thenReturn(Optional.empty());

        final DuplicatedDataException thrown = Assertions.assertThrows(DuplicatedDataException.class, () -> {
            relationshipService.addFollowing(originId, targetId);
        });

        Assertions.assertEquals("The relationship (1)-[FOLLOWING]->(2) already exists", thrown.getMessage());
    }

    @Test
    void shouldRemoveFollowing() {
        final Long originId = 1L;
        final Long targetId = 2L;
        final User returnedUser = new User();

        doNothing().when(userService).validateUserExists(originId);
        doNothing().when(userService).validateUserExists(targetId);
        when(repository.removeFollowing(originId, targetId)).thenReturn(Optional.of(returnedUser));

        final User user = relationshipService.removeFollowing(originId, targetId);

        verify(repository, times(1)).removeFollowing(originId, targetId);
        Assertions.assertSame(returnedUser, user);
    }

    @Test
    void shouldThrowExceptionForFollowingNotFound() {
        final Long originId = 1L;
        final Long targetId = 2L;

        doNothing().when(userService).validateUserExists(originId);
        doNothing().when(userService).validateUserExists(targetId);
        when(repository.removeFollowing(originId, targetId)).thenReturn(Optional.empty());

        final DataNotFoundException thrown = Assertions.assertThrows(DataNotFoundException.class, () -> {
            relationshipService.removeFollowing(originId, targetId);
        });

        Assertions.assertEquals("No relationship (1)-[FOLLOWING]->(2) found", thrown.getMessage());
    }

}
