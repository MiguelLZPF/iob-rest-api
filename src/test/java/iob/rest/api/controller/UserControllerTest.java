package iob.rest.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import iob.rest.api.model.User;
import iob.rest.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @Autowired
    private UserService userService;

    @Test
    public void createUserTest() throws Exception {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");

        User expectedResult = new User();
        expectedResult.setId("650f0a62950cfc0a97efed7c");
        expectedResult.setUsername("testUser");
        expectedResult.setPassword("$2a$10$DQxne4pNKCpKNjhgsCTd0.M.IW9avTqDtbP5KWP.yBjCsXJPZiFfu");
        expectedResult.setCredentials("UTC--2023-09-23T15-55-13.846417000Z--4638592ff88717eadba66cfc37e46c33f7b407fe.json");
        expectedResult.setAccount("0x4638592ff88717eadba66cfc37e46c33f7b407fe");

        when(userService.createUser(user)).thenReturn(expectedResult);

        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.username", is(user.getUsername())))
                .andExpect((ResultMatcher) jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.credentials").exists())
                .andExpect(jsonPath("$.account").exists());
    }
}
