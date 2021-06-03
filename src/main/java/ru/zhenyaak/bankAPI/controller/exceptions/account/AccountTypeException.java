package ru.zhenyaak.bankAPI.controller.exceptions.account;

public class AccountTypeException extends RuntimeException{

    public AccountTypeException(){
        super();
    }

    public AccountTypeException(String message, Throwable cause,
                                boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AccountTypeException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountTypeException(String message){
        super(message);
    }

    public AccountTypeException(Throwable cause){
        super(cause);
    }
}
