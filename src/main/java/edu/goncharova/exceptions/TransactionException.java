package edu.goncharova.exceptions;

public class TransactionException extends Exception {
    public TransactionException(String message){
        super(message);
    }
    public TransactionException(){}
}
