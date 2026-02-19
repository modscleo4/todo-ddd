package br.dev.modscleo4.todo.application.user;

import br.dev.modscleo4.todo.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void authenticate_shouldReturnEmptyWhenUserNotFound() {
        when(userRepository.findByEmail("no@one")).thenReturn(Optional.empty());

        var result = userService.authenticate("no@one", "pwd");
        assertTrue(result.isEmpty());
    }

    @Test
    void create_shouldEncodePasswordAndSave() {
        when(passwordEncoder.encode("pwd")).thenReturn("encoded");
        TestUtils.mockSave(userRepository);

        var created = userService.create("a@b.com", "pwd");

        verify(passwordEncoder, times(1)).encode("pwd");
        verify(userRepository, times(1)).save(any());
        assertEquals("a@b.com", created.getEmail());
        assertEquals("encoded", created.getPasswordHash());
    }
}
