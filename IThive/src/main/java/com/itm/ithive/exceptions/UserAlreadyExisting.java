package com.itm.ithive.exceptions;

import javax.naming.AuthenticationException;

public class UserAlreadyExisting extends AuthenticationException {

    public UserAlreadyExisting(String message){
        super(message);
    }
}
