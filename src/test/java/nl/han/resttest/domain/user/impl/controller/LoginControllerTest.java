package nl.han.resttest.domain.user.impl.controller;

import nl.han.resttest.api.model.LoginRequest;
import nl.han.resttest.domain.user.impl.model.User;
import nl.han.resttest.domain.user.spec.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest
{
    @Spy
    @InjectMocks
    private LoginController sut;

    @Mock
    private LoginService loginService;

    @Test
    public void doLoginResponseTestSuccess()
    {
        var stubRequest = createRequest();
        var stubUserIn = createUserIn(stubRequest);
        var stubUserOut = createUserOut(stubUserIn);

        when(loginService.mapToUser(stubRequest)).thenReturn(stubUserIn);
        when(loginService.loginUser(stubUserIn)).thenReturn(stubUserOut);

        var response = sut.login(stubRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Wiebe Rinsma", response.getBody().getUser());

        when(loginService.loginUser(stubUserIn)).thenThrow(StubSQLException.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void doLoginResponseTestException()
    {
        var stubRequest = createRequest();
        var stubUserIn = createUserIn(stubRequest);

        when(loginService.loginUser(stubUserIn)).thenThrow(StubSQLException.class);

        var response = sut.login(stubRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    private LoginRequest createRequest()
    {
        var result = new LoginRequest();
        result.setUser("wiebe");
        result.setPassword("123123");
        return result;
    }

    private User createUserIn(LoginRequest stubRequest)
    {
        var result = new User();
        result.setUsername(stubRequest.getUser());
        result.setPassword(stubRequest.getPassword());
        return result;
    }

    private User createUserOut(User stubUserIn)
    {
        var result = new User();
        result.setToken("1234-abcd-4567-efgh-6789");
        result.setFullName("Wiebe", "Rinsma");
        return result;
    }
}
