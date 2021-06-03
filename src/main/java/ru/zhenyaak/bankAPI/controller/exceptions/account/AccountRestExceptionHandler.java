package ru.zhenyaak.bankAPI.controller.exceptions.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountRestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AccountTransactionErrorResponse>
    handleException(AccountNotFoundException exc){

        AccountTransactionErrorResponse error = new AccountTransactionErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        // В скобках тело и статус-код
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AccountTransactionErrorResponse>
    handleException(Exception exc){

        AccountTransactionErrorResponse error = new AccountTransactionErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        // В скобках тело и статус-код
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
