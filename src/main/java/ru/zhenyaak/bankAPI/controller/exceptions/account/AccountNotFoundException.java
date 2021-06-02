package ru.zhenyaak.bankAPI.controller.exceptions.account;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(){
        super();
    }

    public AccountNotFoundException(String message, Throwable cause,
                                    boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AccountNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountNotFoundException(String message){
        super(message);
    }

    public AccountNotFoundException(Throwable cause){
        super(cause);
    }
}
