package br.dev.modscleo4.todo.domains.auth;

import br.dev.modscleo4.todo.domains.user.UserNotFoundException;
import br.dev.modscleo4.todo.domains.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        var token = recoveryToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var claims = jwtTokenService.getClaimsFromToken(token);
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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }
}
