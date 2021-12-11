package edu.goncharova.exceptions;

public class RegistrationException extends Exception {
    public RegistrationException(String message){
        super(message);
    }
    public RegistrationException(Exception e){
        super(e);
    }
}
