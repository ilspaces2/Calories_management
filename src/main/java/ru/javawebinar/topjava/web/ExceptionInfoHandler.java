package ru.javawebinar.topjava.web;

import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {

    public final static String EXCEPTION_EMAIL_DUPLICATE = "error.email.duplicate";
    public final static String EXCEPTION_DATETIME_DUPLICATE = "error.datetime.duplicate";

    private final static Map<String, String> CONSTRAINT_MAP_I18N = Map.of(
            "users_unique_email_idx", EXCEPTION_EMAIL_DUPLICATE,
            "meals_unique_user_datetime_idx", EXCEPTION_DATETIME_DUPLICATE
    );

    //https://www.logicbig.com/tutorials/spring-framework/spring-core/message-sources.html
    private final MessageSource messageSource;

    public ExceptionInfoHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, DATA_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String rootCause = ValidationUtil.getRootCause(e).getMessage();
        if (rootCause != null) {
            String lowerCaseMsg = rootCause.toLowerCase();
            for (Map.Entry<String, String> entry : CONSTRAINT_MAP_I18N.entrySet()) {
                if (lowerCaseMsg.contains(entry.getKey())) {
                    return logAndGetErrorInfo(req, e, DATA_ERROR, messageSource.getMessage(entry.getValue(), null, Locale.getDefault()));
                }
            }
        }
        return logAndGetErrorInfo(req, e, DATA_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({
            IllegalRequestDataException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(BindException.class)
    public ErrorInfo bindValidationError(HttpServletRequest req, BindException e) {
        return logAndGetErrorInfo(req, e, VALIDATION_ERROR, ValidationUtil.getErrorResponse(e.getBindingResult()));
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType, String... messages) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (messages != null) {
            return new ErrorInfo(req.getRequestURL(), errorType, Arrays.toString(messages));
        }
        return new ErrorInfo(req.getRequestURL(), errorType, rootCause.toString());
    }
}