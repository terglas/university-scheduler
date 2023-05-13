package com.example.universityscheduler.handler;

import com.example.universityscheduler.controller.EducationalProgramController;
import com.example.universityscheduler.controller.GroupController;
import com.example.universityscheduler.controller.ScheduleController;
import com.example.universityscheduler.controller.SubjectController;
import com.example.universityscheduler.controller.TeacherController;
import com.example.universityscheduler.controller.TestController;
import com.example.universityscheduler.controller.UniversityController;
import com.example.universityscheduler.exception.BadAuthorizeException;
import com.example.universityscheduler.exception.ConflictException;
import com.example.universityscheduler.exception.DataBaseRuntimeException;
import com.example.universityscheduler.exception.ForbiddenException;
import com.example.universityscheduler.exception.NotFoundException;
import com.example.universityscheduler.mapper.rest.RestExceptionMapper;
import com.example.universityscheduler.model.ApiResponseError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = {
        TeacherController.class,
        ScheduleController.class,
        GroupController.class,
        EducationalProgramController.class,
        TestController.class,
        SubjectController.class,
        UniversityController.class

})
@Slf4j
@RequiredArgsConstructor
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private final RestExceptionMapper mapper;

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Order(100)
    public ResponseEntity<ApiResponseError> handleException(NotFoundException e) {
        log.error("Not found exception", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapper.toDto(e, HttpStatus.NOT_FOUND.value()));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @Order(100)
    public ResponseEntity<ApiResponseError> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(mapper.toDto(e, HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @Order(1000)
    public ResponseEntity<ApiResponseError> handleException(ConflictException e) {
        log.error("Conflict exception", e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(mapper.toDto(e, HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(BadAuthorizeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Order(100)
    public ResponseEntity<ApiResponseError> handleException(BadAuthorizeException e) {
        log.error("Bad authorize exception", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapper.toDto(e, HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(DataBaseRuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @Order(100)
    public ResponseEntity<ApiResponseError> handleException(DataBaseRuntimeException e) {
        log.error("Database runtime exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(mapper.toDto(e, HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @Order(100)
    public ResponseEntity<ApiResponseError> handleException(ForbiddenException e) {
        log.error("Forbidden exception", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(mapper.toDto(e, HttpStatus.FORBIDDEN.value()));
    }
}
