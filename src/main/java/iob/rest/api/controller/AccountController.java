package iob.rest.api.controller;

import iob.rest.api.model.AccountDetailResponse;
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
import java.util.Objects;

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
            @RequestParam("amount") BigInteger amount
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        return ResponseEntity.ok().body(accountService.deposit(user, account, amount));
    }

    @PutMapping("/{account}")
    public ResponseEntity<TxExecutionResult> transfer(
            @PathVariable String account,
            @RequestParam("amount") BigInteger amount
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        return ResponseEntity.ok().body(accountService.transfer(user, account, amount));
    }

    @GetMapping("/{account}")
    public ResponseEntity<AccountDetailResponse> getDetails(@PathVariable String account) throws Exception {
        System.out.println("GET to /accounts/" + account + " received. Processing...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        if (Objects.equals(account, "mine")) {
            account = user.getAccount();
        }
        return ResponseEntity.ok(accountService.getDetails(user, account));
    }

//    @GetMapping("/mine")
//    public ResponseEntity<AccountDetailResponse> getDetails() throws Exception {
//        System.out.println("GET to /accounts/mine received. Processing...");
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.getUserByName(auth.getName());
//        return ResponseEntity.ok(accountService.getDetails(user, user.getAccount()));
//    }
}
