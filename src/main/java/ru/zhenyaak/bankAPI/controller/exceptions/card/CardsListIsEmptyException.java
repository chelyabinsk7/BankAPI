package ru.zhenyaak.bankAPI.controller.exceptions.card;

public class CardsListIsEmptyException extends RuntimeException{

    public CardsListIsEmptyException(){
        super();
    }

    public CardsListIsEmptyException(String message, Throwable cause,
                                 boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CardsListIsEmptyException(String message, Throwable cause){
        super(message, cause);
    }

    public CardsListIsEmptyException(String message){
        super(message);
    }

    public CardsListIsEmptyException(Throwable cause){
        super(cause);
    }
}
