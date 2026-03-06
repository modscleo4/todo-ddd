package br.dev.modscleo4.todo.infrastructure.configuration;

import br.dev.modscleo4.todo.domain.auth.InvalidCredentialsException;
import br.dev.modscleo4.todo.domain.note.NoteNotFoundException;
import br.dev.modscleo4.todo.domain.profile.ProfileAlreadyExistsException;
import br.dev.modscleo4.todo.domain.profile.ProfileNotFoundException;
import br.dev.modscleo4.todo.domain.user.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.nio.file.AccessDeniedException;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthorizationDeniedException.class, AccessDeniedException.class})
    public ProblemDetail handleAccessDeniedException(Exception e) {
        var pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Access Denied");
        pd.setDetail(e.getMessage());

        return pd;
    }

    @ExceptionHandler({InvalidCredentialsException.class})
    public ProblemDetail handleInvalidCredentialsException(Exception e) {
        var pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Invalid Credentials");
        pd.setDetail(e.getMessage());

        return pd;
    }

    @ExceptionHandler({NoteNotFoundException.class, UserNotFoundException.class, ProfileNotFoundException.class})
    public ProblemDetail handleNotFoundException(Exception e) {
        var pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Not Found");
        pd.setDetail(e.getMessage());

        return pd;
    }

    @ExceptionHandler({ProfileAlreadyExistsException.class})
    public ProblemDetail handleProfileAlreadyExistsException(Exception e) {
        var pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Profile Already Exists");
        pd.setDetail(e.getMessage());

        return pd;
    }

    @ExceptionHandler({Exception.class})
    public ProblemDetail handleException(Exception e, HttpServletRequest request) {
        try (
            var _ = MDC.putCloseable("method", request.getMethod());
            var _ = MDC.putCloseable("path", request.getPathInfo())
        ) {
            log.error("Unhandled {}.", e.getClass().getName(), e);

            var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            pd.setTitle("Internal Server Error");
            pd.setDetail(e.getMessage());
            pd.setInstance(URI.create(request.getRequestURI()));
            pd.setProperty("timestamp", Instant.now().toString());
            pd.setProperty("path", request.getRequestURI());

            return pd;
        }
    }
}
