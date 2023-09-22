package iob.rest.api.controller;

import iob.rest.api.model.User;
import iob.rest.api.service.AccountService;
import iob.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.exception.CipherException;

import java.io.IOException;
import java.math.BigInteger;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private UserService userService;
    private AccountService accountService;

    @PostMapping("/{account}")
    public Boolean deposit(@PathVariable String account, BigInteger amount) throws CipherException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        accountService.deposit(user.getAccount(), account, amount);
        return true;
    }
    @PutMapping("/{account}")
    public Boolean transfer(@PathVariable String account, BigInteger amount) {
        return true;
    }
    @GetMapping("/{account}")
    public Boolean getDetails(@PathVariable String account) {
        return true;
    }
    @GetMapping("/mine")
    public Boolean getDetails() {
        return true;
    }
}
