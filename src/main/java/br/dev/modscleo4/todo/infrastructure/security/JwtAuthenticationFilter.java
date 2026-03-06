package br.dev.modscleo4.todo.infrastructure.security;

import br.dev.modscleo4.todo.domain.auth.JwtTokenServicePort;
import br.dev.modscleo4.todo.domain.user.UserNotFoundException;
import br.dev.modscleo4.todo.domain.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenServicePort jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var token = recoveryToken(request);
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        var claims = jwtTokenService.getClaimsFromSignedToken(token);
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var subject = claims.getPayload().getSubject();
        var user = userRepository.findByEmail(subject).orElseThrow(UserNotFoundException::new);

        var authentication = new UsernamePasswordAuthenticationToken(
            user,
            null,
            user.getAuthorities()
        );

        try (var _ = MDC.putCloseable("user", user.getEmail())) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            if (!authorizationHeader.startsWith("Bearer ")) {
                return null;
            }

            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
