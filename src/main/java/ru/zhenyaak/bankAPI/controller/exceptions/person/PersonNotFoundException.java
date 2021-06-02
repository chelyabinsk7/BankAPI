package ru.zhenyaak.bankAPI.controller.exceptions.person;

public class PersonNotFoundException extends RuntimeException{

    public PersonNotFoundException(){
        super();
    }

    public PersonNotFoundException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PersonNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public PersonNotFoundException(String message){
        super(message);
    }

    public PersonNotFoundException(Throwable cause){
        super(cause);
    }
}
