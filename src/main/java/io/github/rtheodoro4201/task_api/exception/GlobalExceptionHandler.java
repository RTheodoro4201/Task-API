package io.github.rtheodoro4201.task_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.HtmlUtils;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final HttpServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler (TaskNotFoundException.class)
    public ResponseEntity<APIError> handleTaskNotFoundException(TaskNotFoundException ex) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        logger.warn("Tarefa não encontrada: {}", ex.getMessage());
        APIError error = new APIError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                path,
                null);


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);    }

    @ExceptionHandler (TaskAlreadyExistsException.class)
    public ResponseEntity<APIError> handleTaskAlreadyExistsException(TaskAlreadyExistsException ex) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        logger.warn("Tarefa duplicada: {}", ex.getMessage());
        APIError error = new APIError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                path,
                null);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @Override
    protected final @NonNull ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest webRequest) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        if (logger.isDebugEnabled()) {
            logger.debug("Erro de validação na requisição {}: {}", path, ex.getMessage());
        }

        List<APIError.FieldErrorDetail> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new APIError.FieldErrorDetail(fe.getField(), fe.getDefaultMessage()))
                .toList();

        APIError error = new APIError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Erro de validação nos campos da requisição",
                path,
                fieldErrors
        );
        return new ResponseEntity<>(error, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleGeneralException(Exception ex) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        logger.error("Erro interno inesperado no endpoint {}: {}", path, ex.getMessage(), ex);
        APIError error = new APIError(
                Instant.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Erro interno do servidor",
                path,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @Override
    protected final ResponseEntity<@NonNull Object> handleHttpMessageNotReadable (
            @NonNull HttpMessageNotReadableException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest webRequest) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        String userMessage = "Corpo da requisição inválido ou ausente";
        if (logger.isWarnEnabled()) {
            String detail = ex.getMostSpecificCause().getMessage();
            logger.warn("JSON inválido no endpoint [{}]: {}", path, detail);
        }

        APIError error = new APIError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                userMessage,
                path,
                null
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected final @NonNull ResponseEntity<Object> handleMethodArgumentTypeMismatchException(@NonNull MethodArgumentTypeMismatchException ex) {
        String path = HtmlUtils.htmlEscape(request.getRequestURI());

        logger.warn("Valor inválido '{}' para o parâmetro {} no endpoint {} ", ex.getValue(), ex.getName(), path, ex);

        APIError error = new APIError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Valor inválido '" + HtmlUtils.htmlEscape(String.valueOf(ex.getValue())) +
                        "' para o campo '" + HtmlUtils.htmlEscape(ex.getName()) + "'",
                path,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}