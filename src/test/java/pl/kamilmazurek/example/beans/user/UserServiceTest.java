package pl.kamilmazurek.example.beans.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldLogExistingUsers(CapturedOutput output) {
        //given
        var user1 = new UserEntity();
        user1.setLogin("jdoe");

        var user2 = new UserEntity();
        user2.setLogin("asmith");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        //when
        userService.logExistingUsers();

        //then
        verify(userRepository).findAll();
        assertTrue(output.getOut().contains("Existing users: jdoe, asmith"));
    }

    @Test
    void shouldLogNoUsers(CapturedOutput output) {
        //given
        when(userRepository.findAll()).thenReturn(List.of());

        //when
        userService.logExistingUsers();

        //then
        verify(userRepository).findAll();
        assertTrue(output.getOut().contains("No users found."));
    }

}