package ru.mail.dimaushenko.utils.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.mail.dimaushenko.utils.Validator;

public class ValidatorImpl implements Validator {

    private static Validator instance;

    private ValidatorImpl() {
    }

    public static Validator getInstaice() {
        if (instance == null) {
            instance = new ValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isInteger(String value) {
        String patternString = "^[0-9]+$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    @Override
    public boolean isStringLengthValid(String value, int length) {
        return value.length() <= length;
    }

    @Override
    public boolean isStringContainsUsernameSymbols(String value) {
        String patternString = "\\w+";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
