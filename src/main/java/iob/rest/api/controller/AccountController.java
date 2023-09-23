package iob.rest.api.controller;

import iob.rest.api.model.TxExecutionResult;
import iob.rest.api.model.User;
import iob.rest.api.service.AccountService;
import iob.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/{account}")
    public ResponseEntity<TxExecutionResult> deposit(
            @PathVariable String account,
            @RequestParam("amount"
            ) BigInteger amount) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        return ResponseEntity.ok().body(accountService.deposit(user, account, amount));
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
