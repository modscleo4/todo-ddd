package br.dev.modscleo4.todo.application.user;

import br.dev.modscleo4.todo.domain.user.User;
import br.dev.modscleo4.todo.domain.user.UserRepository;
import br.dev.modscleo4.todo.domain.user.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceAdapter {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public Optional<UserDetails> authenticate(String email, String password) {
        try (var _ = MDC.putCloseable("email", email)) {
            var user = repository.findByEmail(email);
            if (user.isEmpty()) {
                return Optional.empty();
            }

            var credentials = new UsernamePasswordAuthenticationToken(email, password);
            var authentication = authenticationManager.authenticate(credentials);
            return Optional.ofNullable((UserDetails) authentication.getPrincipal());
        }
    }

    @Transactional
    public User create(String email, String password) {
        try (var _ = MDC.putCloseable("email", email)) {
            var user = new User();
            user.setEmail(email);
            user.setPasswordHash(passwordEncoder.encode(password));
            user.setRole(UserRole.USER);

            return repository.save(user);
        }
    }
}
