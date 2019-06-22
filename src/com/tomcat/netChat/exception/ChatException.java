package com.tomcat.netChat.exception;

public class ChatException extends Exception {

    public static final Integer NOT_EXIST_GROUP_CODE = 01;
    public static final Integer NOT_EXISTED_GROUPS_CODE = 03;
    public static final Integer CHAT_INSERT_EXCEPTION_CODE = 02;

    private final int EID;

    public ChatException(int eid) {
        EID = eid;
    }

    public int getEID() {
        return EID;
    }
}
