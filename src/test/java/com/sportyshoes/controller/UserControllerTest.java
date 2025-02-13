package com.sportyshoes.controller;

import com.sportyshoes.model.User;
import com.sportyshoes.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetUsers() throws Exception {
        List<User> users = new ArrayList<>();
        User user = new User("john_doe", "john@example.com", "test123");
        user.setId(1L);
        users.add(user);

        when(userService.searchUsers(anyString())).thenReturn(users);

        mockMvc.perform(get("/admin/users")
                        .param("keyword", "john"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username", is("john_doe")));
    }
}
