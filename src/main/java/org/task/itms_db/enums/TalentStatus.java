package org.task.itms_db.enums;

public enum TalentStatus {

    SEND("SEND"),
    RESEND("RESEND"),
    ANSWERED("ANSWERED"),
    EXPIRED("EXPIRED");

    private String value;

    TalentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
