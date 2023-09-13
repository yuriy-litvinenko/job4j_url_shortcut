package ru.job4j.urlshortcut.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

@ControllerAdvice
@Data
@Slf4j
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(IllegalArgumentException.class)
    public void handleException(Exception e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", "Some of fields is incorrect");
            put("details", e.getMessage());
        }}));
        log.error(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void exceptionHandler(MethodArgumentNotValidException e, HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {
            {
                put("message", Objects.requireNonNull(e.getFieldErrors().stream()
                        .findFirst().orElse(null)).getDefaultMessage());
                put("details", e.getMessage());
            }
        }));
        log.error(e.getLocalizedMessage());
    }
}
