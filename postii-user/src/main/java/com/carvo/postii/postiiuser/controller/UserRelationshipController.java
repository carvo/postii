package com.carvo.postii.postiiuser.controller;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.service.UserRelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRelationshipController {

    final UserRelationshipService userService;

    @PatchMapping("/users/{id}/followings/{targetId}")
    public User addFollowing(@PathVariable Long id, @PathVariable Long targetId) {
        return userService.addFollowing(id, targetId);
    }

    @DeleteMapping("/users/{id}/followings/{targetId}")
    public User removeFollowing(@PathVariable Long id, @PathVariable Long targetId) {
        return userService.removeFollowing(id, targetId);
    }

    @GetMapping("/users/{id}/followings")
    public List<User> findFollowings(@PathVariable Long id) {
        return userService.findFollowings(id);
    }

    @GetMapping("/users/{id}/followers")
    public List<User> findFollowers(@PathVariable Long id) {
        return userService.findFollowers(id);
    }

}
