package br.dev.modscleo4.todo.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
@Slf4j
public class ExceptionHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ProblemDetail> handleException(Exception e, HttpServletRequest request) {
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

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.parseMediaType("application/problem+json;charset=UTF-8"))
                .body(pd);
        }
    }
}
