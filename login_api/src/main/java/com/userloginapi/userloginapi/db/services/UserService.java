package com.userloginapi.userloginapi.db.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userloginapi.userloginapi.db.repositories.UserRepository;
import com.userloginapi.userloginapi.dto.SendEmailDTO;
import com.userloginapi.userloginapi.dto.UserDTO;
import com.userloginapi.userloginapi.entities.User;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(String id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID :" + id + " NOT FOUND"));
    }

    public User findUserByLogin(String login) {
        return  userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("LOGIN :" + login + " NOT FOUND"));
    }

    public List<User> findAllUsers() throws RuntimeException {
        List<User> querryResult = userRepository.findAll();

        if(querryResult.size() <= 0)
           throw new RuntimeException("NOT FOUND ANY USER");

        return querryResult;
    }

    public void postUser(UserDTO userDTO) throws RuntimeException, NoSuchAlgorithmException, IOException {
        if(userRepository.findByLogin(userDTO.getLogin()).isPresent())
            throw new RuntimeException("THIS LOGIN:" + userDTO.getLogin() + " ALREADY EXISTS");

        userDTO.setPassword(encriptPassword(userDTO.getPassword()));

        userRepository.save(new User(userDTO));

        ObjectMapper sendEmailStream = new ObjectMapper();
        String content = sendEmailStream.writeValueAsString(
                new SendEmailDTO(userDTO.getEmail(), userDTO.getLogin(), "HELLO WORLD")
        );

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        Request request = new Request.Builder()
                .url("http://localhost:8081/api/login")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();

    }

    public void deleteUserById(String id) throws RuntimeException{

        userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("ID: " + id + " NOT FOUND"));

        userRepository.deleteById(id);
    }

    public boolean loginUser(UserDTO userDTO) throws NoSuchAlgorithmException {

        User user = findUserByLogin(userDTO.getLogin());

        userDTO.setPassword(encriptPassword(userDTO.getPassword()));

        if(user.getPassword().equals(userDTO.getPassword()))
            return true;

        return false;

    }

    public String encriptPassword(String password) throws NoSuchAlgorithmException {

        String psw = password.toUpperCase();
        MessageDigest md = MessageDigest.getInstance("MD5");

        md.update(psw.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest);

        return hash;
    }
}
