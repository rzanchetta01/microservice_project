package com.userloginapi.userloginapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userloginapi.userloginapi.db.services.FactoryService;
import com.userloginapi.userloginapi.dto.UserDTO;
import com.userloginapi.userloginapi.db.services.UserService;
import com.userloginapi.userloginapi.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Autowired
    private FactoryService factoryService;

    @MockBean
    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void loginUser() throws Exception {
        UserDTO userDTO = new UserDTO("login1", "p1", "rzanchetta02@gmail.com");
        User user = new User(userDTO);

        user.setPassword(userService.encriptPassword(user.getPassword()));

        ObjectMapper userMapper = new ObjectMapper();


        given(factoryService.getUserService()).willReturn(userService);

        mockMvc.perform(get("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userMapper.writeValueAsString(userDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    void wrongPasswordLoginUser() throws Exception {
        UserDTO userDTO = new UserDTO("login1", "p1", "rzanchetta02@gmail.com");
        User user = new User(userDTO);

        ObjectMapper userMapper = new ObjectMapper();

        mockMvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userMapper.writeValueAsString(userDTO)))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void postUser() throws Exception {
        UserDTO userDTO = new UserDTO("login1", "p1", "rzanchetta02@gmail.com");
        ObjectMapper userMapper = new ObjectMapper();

        given(factoryService.getUserService()).willReturn(userService);

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteUserById() throws Exception {
        User user = new User(new UserDTO("login1", "p1", "rzanchetta02@gmail.com"));

        given(factoryService.getUserService()).willReturn(userService);
        given(factoryService.getUserService().findUserById(user.getId())).willReturn(user);

        mockMvc.perform(delete("/api/user/"+user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    //                         ERRORS TESTS
    //------------------------------------------------------------
    //------------------------------------------------------------

}