package iob.rest.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Accounts", description = "Accounts management API")
public class AccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/{account}")
    @Operation(
            summary = "Deposit new tokens to account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deposited new tokens to account",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TxExecutionResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<TxExecutionResult> deposit(
            @PathVariable String account,
            @RequestParam("amount") BigInteger amount
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        return ResponseEntity.ok().body(accountService.deposit(user, account, amount));
    }

    @PutMapping("/{account}")
    @Operation(
            summary = "Transfer new tokens to account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully transferred new tokens to account",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TxExecutionResult.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<TxExecutionResult> transfer(
            @PathVariable String account,
            @RequestParam("amount") BigInteger amount
    ) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        return ResponseEntity.ok().body(accountService.transfer(user, account, amount));
    }

    @GetMapping("/{account}")
    @Operation(
            summary = "Get account details like balance and operation history",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved account details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AccountDetailResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Account not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<AccountDetailResponse> getDetails(@PathVariable String account) throws Exception {
        System.out.println("GET to /accounts/" + account + " received. Processing...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByName(auth.getName());
        if (Objects.equals(account, "mine")) {
            account = user.getAccount();
        }
        return ResponseEntity.ok(accountService.getDetails(user, account));
    }
}
