package com.babak.iojavaintake.excption;

public class UsernameExistsException extends Exception {

    public UsernameExistsException() {
        super("Username exists!");
    }
}
