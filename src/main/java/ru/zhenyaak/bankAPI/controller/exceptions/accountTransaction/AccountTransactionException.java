package ru.zhenyaak.bankAPI.controller.exceptions.accountTransaction;

public class AccountTransactionException extends RuntimeException{

    public AccountTransactionException(){
        super();
    }

    public AccountTransactionException(String message, Throwable cause,
                                       boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AccountTransactionException(String message, Throwable cause){
        super(message, cause);
    }

    public AccountTransactionException(String message){
        super(message);
    }

    public AccountTransactionException(Throwable cause){
        super(cause);
    }
}
