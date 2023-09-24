package iob.rest.api.controller;
import iob.rest.api.model.AccountDetailResponse;
import iob.rest.api.model.TxExecutionResult;
import iob.rest.api.model.User;
import iob.rest.api.service.AccountService;
import iob.rest.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeposit() throws Exception {
        // Create test data
        String account = "account123";
        BigInteger amount = BigInteger.valueOf(100);
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        user.setUsername("testuser");

        // Mock security context
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        // Mock userService behavior
        when(userService.getUserByName("testuser")).thenReturn(user);

        // Mock accountService behavior
        TxExecutionResult expectedResult = new TxExecutionResult(true);
        when(accountService.deposit(user, account, amount)).thenReturn(expectedResult);

        // Perform the test
        ResponseEntity<TxExecutionResult> responseEntity = accountController.deposit(account, amount);

        // Verify the results
        assertEquals(expectedResult, responseEntity.getBody());
    }

    // Similar tests for other controller methods (transfer, getDetails) can be written here.
}