package ru.zhenyaak.bankAPI.controller.exceptions.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PersonRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse>
    handleException(PersonNotFoundException exc){

        PersonErrorResponse error = new PersonErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        // В скобках тело и статус-код
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse>
    handleException(Exception exc){

        PersonErrorResponse error = new PersonErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        // В скобках тело и статус-код
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
