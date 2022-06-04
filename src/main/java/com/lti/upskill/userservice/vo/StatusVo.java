package com.lti.upskill.userservice.vo;

public class StatusVo {

    private String message;
    private statusType status;

    public static enum statusType {
        SUCCESS, FAILED
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public statusType getStatus() {
        return status;
    }

    public void setStatus(statusType status) {
        this.status = status;
    }

}
