package com.tomcat.netChat.exception;

public class ChatException extends Exception {

    // that expect SQL error code is 1146, and have not match group
    public static final Integer NOT_EXIST_GROUP_CODE = 01;

    // that unexpect SQL error code, but this is to notice caller
    // have not any group existed.
    public static final Integer NOT_EXISTED_GROUPS_CODE = 03;

    // that expect SQL error code is 1452.
    public static final Integer CHAT_INSERT_EXCEPTION_CODE = 02;

    private final int EID;

    public ChatException(int eid) {
        EID = eid;
    }

    public int getEID() {
        return EID;
    }
}
