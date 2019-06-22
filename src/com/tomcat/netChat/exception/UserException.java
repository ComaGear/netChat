package com.tomcat.netChat.exception;

public class UserException extends Exception {

    public static final int JOIN_EXCEPTION_CODE = 01;
    public static final int LOGIN_EXCEPTION_CODE = 02;
    public static final int NOT_EXIST_EXCEPTION_CODE = 03;

    private final int EID;

    public UserException(int eid) {
        EID = eid;
    }

    public int getEID() {
        return EID;
    }
}
