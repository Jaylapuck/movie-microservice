package com.forest.utils.http;

import com.forest.utils.exceptions.InvalidInputException;
import com.forest.utils.exceptions.NotFoundException;
import com.forest.utils.exceptions.NumberCannotExceed100Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public HttpErrorInfo handleNotFoundException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(NOT_FOUND, request, exception);
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, exception);
    }

    @ExceptionHandler(NumberCannotExceed100Exception.class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    public HttpErrorInfo handleNullPointerException(ServerHttpRequest request, Exception exception){
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, exception);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception exception) {

        final String path = request.getPath().pathWithinApplication().value();
        final String message = exception.getMessage();

        LOGGER.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path,message);

        return  new HttpErrorInfo(httpStatus, path, message);
    }
}
