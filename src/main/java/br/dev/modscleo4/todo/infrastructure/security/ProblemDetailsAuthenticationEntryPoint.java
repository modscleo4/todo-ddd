package br.dev.modscleo4.todo.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.net.URI;

public class ProblemDetailsAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        @NonNull AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/problem+json;charset=UTF-8");

        var pd = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, authException.getMessage());
        pd.setTitle("Unauthorized");
        pd.setInstance(URI.create(request.getRequestURI()));

        objectMapper.writeValue(response.getOutputStream(), pd);
    }
}
