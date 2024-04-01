package hala.hala.services;

import hala.hala.models.UserEntity;
import hala.hala.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        UserEntity user = new UserEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);
        UserEntity createdUser = userService.createUser(new UserEntity());
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void findUserByName() {
        String userName = "testUser";
        UserEntity user = new UserEntity();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        Optional<UserEntity> foundUser = userService.findUserByName(userName);
        assertEquals(Optional.of(user), foundUser);
        verify(userRepository, times(1)).findByUserName(userName);
    }
}
