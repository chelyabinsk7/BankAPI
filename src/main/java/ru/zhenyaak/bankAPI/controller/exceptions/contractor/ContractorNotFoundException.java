package ru.zhenyaak.bankAPI.controller.exceptions.contractor;

public class ContractorNotFoundException extends RuntimeException{

    public ContractorNotFoundException(){
        super();
    }

    public ContractorNotFoundException(String message, Throwable cause,
                                       boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ContractorNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public ContractorNotFoundException(String message){
        super(message);
    }

    public ContractorNotFoundException(Throwable cause){
        super(cause);
    }
}
