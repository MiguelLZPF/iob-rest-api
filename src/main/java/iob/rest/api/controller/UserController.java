package iob.rest.api.controller;

import iob.rest.api.model.User;
import iob.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.exception.CipherException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUser() {
//        return ResponseEntity.ok().body(userService.getAllUser());
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity < User > getUserById(@PathVariable Long id) throws CipherException, IOException {
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @GetMapping("/users/{userName}")
    public ResponseEntity < User > getUserByName(@PathVariable String userName) throws CipherException, IOException {
        return ResponseEntity.ok().body(userService.getUserByName(userName));
    }

    @PostMapping("/users")
    public ResponseEntity < User > createUser(@RequestBody User user) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException {
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity < User > updateUser(@PathVariable long id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }

    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUser(@PathVariable long id) {
        this.userService.deleteUser(id);
        return HttpStatus.OK;
    }
}