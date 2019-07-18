package com.tomcat.netChat.exception;

public class UserException extends Exception {

    // that expect SQL error code is 1062.
    public static final int JOIN_EXCEPTION_CODE = 01;

    // that unexpect SQL error code, only not match user.
    public static final int NOT_EXIST_EXCEPTION_CODE = 02;

    private final int EID;

    public UserException(int eid) {
        EID = eid;
    }

    public int getEID() {
        return EID;
    }
}
