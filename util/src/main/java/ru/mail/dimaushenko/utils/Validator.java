package ru.mail.dimaushenko.utils;

public interface Validator {
    
    public boolean isInteger(String value);
    
    public boolean isStringLengthValid(String value, int length);
    
    public boolean isStringContainsUsernameSymbols(String value);
    
}
