package br.dev.modscleo4.todo.application.user;

import br.dev.modscleo4.todo.TestUtils;
import br.dev.modscleo4.todo.domain.user.UserRepository;
import br.dev.modscleo4.todo.infrastructure.persistence.JpaUserRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JpaUserRepository userRepository;
    @InjectMocks
    private UserServiceAdapter userService;

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
        verify((UserRepository)userRepository, times(1)).save(any());
        assertEquals("a@b.com", created.getEmail());
        assertEquals("encoded", created.getPasswordHash());
    }
}
