package com.example.cih.common.message;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum MessageCode {

    ALARM_REQUEST_CONSULT(1, "alarm.request.consult", "consultRequest"),
    AUCTION_REQUEST_AUCTION(2, "alarm.request.auction", "auctionRequest"),
    WELCOME_GREETING(3, "welcome.greeting", "none")
    ;
    private final int msgType;
    private final String msgCode;
    private final String actionType;

    private final static Map<String, MessageCode> valueMap = Arrays.stream(MessageCode.values())
            .collect(Collectors.toMap(MessageCode::getActionType, Function.identity()));

    MessageCode(int msgType, String msgCode, String actionType) {
        this.msgType = msgType;
        this.msgCode = msgCode;
        this.actionType = actionType;
    }

    public int getMsgType(){
        return msgType;
    }
    public String getMsgCode(){
        return msgCode;
    }
    public String getActionType(){
        return actionType;
    }

    public static MessageCode fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        return valueMap.get(value);
    }


}
