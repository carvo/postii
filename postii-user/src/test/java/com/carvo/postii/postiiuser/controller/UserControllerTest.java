package com.carvo.postii.postiiuser.controller;

import com.carvo.postii.postiiuser.entity.User;
import com.carvo.postii.postiiuser.service.exception.DataNotFoundException;
import com.carvo.postii.postiiuser.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldFindUser() throws Exception {
        final User user = new User();
        user.setId(1L);
        user.setUsername("John");

        Mockito.when(userService.findUser(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("John"))
        ;
    }

    @Test
    void shouldNotFindUser() throws Exception {
        Mockito.when(userService.findUser(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/users/{id}", "1"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    void shouldUserExist() throws Exception {
        Mockito.doNothing().when(userService).validateUserExists(1L);

        mockMvc.perform(get("/users/{id}/exists", "1"))
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    void shouldUserNotExist() throws Exception {
        Mockito.doThrow(DataNotFoundException.class).when(userService).validateUserExists(1L);

        mockMvc.perform(get("/users/{id}/exists", "1"))
                .andExpect(status().isNotFound())
        ;
    }

}
