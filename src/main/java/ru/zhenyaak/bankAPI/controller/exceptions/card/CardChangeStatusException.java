package ru.zhenyaak.bankAPI.controller.exceptions.card;

public class CardChangeStatusException extends RuntimeException{

    public CardChangeStatusException(){
        super();
    }

    public CardChangeStatusException(String message, Throwable cause,
                                     boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CardChangeStatusException(String message, Throwable cause){
        super(message, cause);
    }

    public CardChangeStatusException(String message){
        super(message);
    }

    public CardChangeStatusException(Throwable cause){
        super(cause);
    }
}
