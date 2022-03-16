package nl.han.resttest.core.services.impl;

import nl.han.resttest.core.model.StubTest;
import nl.han.resttest.core.model.User;
import nl.han.resttest.core.repositories.UserRepository;
import nl.han.resttest.database.model.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest
{
    @Spy
    @InjectMocks
    private LoginServiceImpl sut;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testDeepStubCopy() {
        when(userRepository.findAll()).thenReturn(getStubUsers());
        doReturn(new User()).when(sut).mapToUser(any(UserEntity.class));

        User user = new User();
        user.setUsername("WIEBE");
        user.setPassword("123");

        sut.loginUser(user);
    }

    public List<UserEntity> getStubUsers() {
        List<UserEntity> stubUsers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            stubUsers.add(createStubUser());
        }

        return stubUsers;
    }

    private UserEntity createStubUser()
    {
        UserEntity stubUser = new UserEntity();
        List<StubTest> stubTests = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            stubTests.add(createStubTest());
        }

        stubUser.setToken("TEST-TOKEN");
        stubUser.setStubTests(stubTests);
        return stubUser;
    }

    private StubTest createStubTest()
    {
        StubTest stubTest = new StubTest();
        stubTest.setTest("TEST");
        return stubTest;
    }

}
