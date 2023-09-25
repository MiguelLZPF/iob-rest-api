package iob.rest.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@Tag(name = "Users", description = "Users management API")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/users")
//    public ResponseEntity<List<User>> getAllUser() {
//        return ResponseEntity.ok().body(userService.getAllUser());
//    }

//    @GetMapping("/users/{id}")
//    public ResponseEntity < User > getUserById(@PathVariable String id) throws CipherException, IOException {
//        return ResponseEntity.ok().body(userService.getUserById(id));
//    }

    @GetMapping("/users/{userName}")
    @Operation(
            summary = "Get user by Username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved user details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<User> getUserByName(@PathVariable String userName) throws CipherException, IOException {
        return ResponseEntity.ok().body(userService.getUserByName(userName));
    }

    @PostMapping("/users")
    @Operation(
            summary = "Add new user",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully created new user",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            }
    )
    public ResponseEntity<User> createUser(@RequestBody User user) throws InvalidAlgorithmParameterException,
            CipherException, NoSuchAlgorithmException, IOException, NoSuchProviderException, URISyntaxException {
        return ResponseEntity.ok().body(this.userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    @Operation(
            summary = "Edit user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully edited user details",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok().body(this.userService.updateUser(user));
    }
    @Operation(
            summary = "Delete user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully removed user"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User not found",
                            content = @Content
                    )
            },
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @DeleteMapping("/users/{id}")
    public HttpStatus deleteUser(@PathVariable String id) {
        this.userService.deleteUser(id);
        return HttpStatus.OK;
    }
}