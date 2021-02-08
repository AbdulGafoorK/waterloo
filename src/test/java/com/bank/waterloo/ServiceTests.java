package com.bank.waterloo;

import com.bank.waterloo.dto.LoginResponseDTO;
import com.bank.waterloo.model.User;
import com.bank.waterloo.model.UserStatus;
import com.bank.waterloo.security.JwtUtil;
import com.bank.waterloo.service.LoginService;
import com.bank.waterloo.service.LoginServiceImp;
import com.bank.waterloo.service.UserService;
import com.bank.waterloo.service.UserServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LoginServiceImp.class)
@PowerMockIgnore({"javax.crypto.*"})
public class ServiceTests {

    @Mock
    private UserService mockUserService;

    @Test
    public void verifyUserNameAndPassword() {
        User user = getUser();
        when(mockUserService.findByUserName(any())).thenReturn(user);
        when(mockUserService.findByUserNameAndPassword(any(), any())).thenReturn(user);
        String token = JwtUtil.generate(user);
        Assert.assertNotNull(token);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(1L, token);
        Assert.assertEquals(user.getId(), loginResponseDTO.getUserId(), 1);
        User currentUser = null;
        Assert.assertNull(currentUser);
        updateUserIncorrectPasswordCount(user);
        currentUser = getUser();
        setToActiveUser(currentUser);
        Assert.assertEquals(0, currentUser.getInCorrectPasswordCount());
        Assert.assertEquals("ACTIVE", currentUser.getStatus());

    }

    @Test
    public void updateUserIncorrectPasswordCount() {
        updateUserIncorrectPasswordCount(getUser());
    }

    @Test
    public void setToActiveUser() {
        setToActiveUser(getUser());
    }

    private void updateUserIncorrectPasswordCount(User user) {
        Assert.assertEquals("ACTIVE", user.getStatus());
        Assert.assertThat("count", 3, greaterThan(user.getInCorrectPasswordCount()));
        user.setStatus("BLOCKED");
        user.setInCorrectPasswordCount(user.getInCorrectPasswordCount() + 1);
        Assert.assertEquals("BLOCKED", user.getStatus());
    }

    private void setToActiveUser(User user) {
        user.setStatus(UserStatus.ACTIVE.toString());
        user.setInCorrectPasswordCount(0);
        Assert.assertEquals("ACTIVE", user.getStatus());
    }

    private User getUser() {
        User user = new User();
        user.setId(1);
        user.setName("userone");
        user.setPassword("a8c1587547303196e23d2d8611d6a084044d089b3de8bd0990d3dec5d3b2c3fc");
        user.setStatus("ACTIVE");
        user.setInCorrectPasswordCount(0);
        return user;
    }

}
