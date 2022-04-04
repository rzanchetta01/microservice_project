package com.userloginapi.userloginapi.db.services;

import com.userloginapi.userloginapi.db.repositories.UserRepository;
import com.userloginapi.userloginapi.dto.UserDTO;
import com.userloginapi.userloginapi.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userService = new UserService(userRepository);
    }

    @Test
    void canFindUserByLogin() {
        UserDTO userDTOExpect = new UserDTO("login1","P1", "rzanchetta02@gmail.com");
        User userExpected = new User(userDTOExpect);

        given(userRepository.findByLogin(userExpected.getLogin())).willReturn(Optional.of(userExpected));

        userService.findUserByLogin(userDTOExpect.getLogin());

        verify(userRepository, times(1)).findByLogin(anyString());
    }

    @Test
    void canFindUserById() {
        UserDTO userDTOExpect = new UserDTO("login1","P1", "rzanchetta02@gmail.com");
        User userExpected = new User(userDTOExpect);

        given(userRepository.findById(userExpected.getId())).willReturn(Optional.of(userExpected));

        userService.findUserById(userExpected.getId());

        verify(userRepository, times(1)).findById(anyString());
    }

    @Test
    void canFindAllUsers() {
        List<User> userList = List.of(
                new User(new UserDTO("login1", "p1", "rzanchetta02@gmail.com")),
                new User(new UserDTO("login2", "p2", "rzanchetta02@gmail.com")),
                new User(new UserDTO("login3", "p3", "rzanchetta02@gmail.com"))
        );

        given(userRepository.findAll()).willReturn(userList);

        List<User> expected = userService.findAllUsers();

        assertEquals(expected, userList);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void canPostUser() throws NoSuchAlgorithmException, IOException {
        UserDTO userDTO = new UserDTO("teste_login5s", "test_password", "rzanchetta02@gmail.com");

        when(userRepository.save(ArgumentMatchers.isA(User.class))).thenReturn(any(User.class));

        userService.postUser(userDTO);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void canDeleteUserById() {

        User userExpected = new User(new UserDTO("login1", "p1", "rzanchetta02@gmail.com"));

        given(userRepository.findById(userExpected.getId())).willReturn(Optional.of(userExpected));

        userService.deleteUserById(userExpected.getId());
        verify(userRepository, times(1)).findById(userExpected.getId());
        verify(userRepository, times(1)).deleteById(userExpected.getId());

    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    //                         ERRORS TESTS
    //------------------------------------------------------------
    //------------------------------------------------------------

    @Test
    void cannotFindUserById() {

        assertThrows(RuntimeException.class, () -> {
            userService.findUserById("Wrong ID");
        });

        verify(userRepository, times(1)).findById(anyString());

    }

    @Test
    void cannotUserFIndByLogin() {

        assertThrows(RuntimeException.class, () -> {
            userService.findUserByLogin("Wrong LOGIN");
        });

        verify(userRepository, times(1)).findByLogin(anyString());
    }

    @Test
    void cannotPostUser() throws NoSuchAlgorithmException, IOException {

        UserDTO userDTO = new UserDTO("teste_login5s", "test_password", null);

        when(userRepository.save(ArgumentMatchers.isA(User.class))).thenReturn(any(User.class));

        userService.postUser(userDTO);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void cannotDeleteUserById() {

        assertThrows(RuntimeException.class, () -> {
            userService.findUserById("Wrong ID");
        });

        verify(userRepository, times(1)).findById(anyString());
        verify(userRepository, times(0)).deleteById(anyString());
    }

    @Test
    void cannotFindAllUsers() {

        assertThrows(RuntimeException.class, () -> {
            userService.findAllUsers();
        });

        verify(userRepository, times(1)).findAll();
    }

}