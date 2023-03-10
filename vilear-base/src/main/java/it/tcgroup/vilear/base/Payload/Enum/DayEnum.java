package it.tcgroup.vilear.base.Payload.Enum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public enum DayEnum {

    LUNEDI("lunedi"),
    MARTEDI("martedì"),
    MERCOLEDI("mercoledì"),
    GIOVEDI("giovedì"),
    VENERDI("venerdì"),
    SABATO("sabato"),
    DOMENICA("domenica");
    @Enumerated(EnumType.STRING)
    private String day;

    DayEnum(String day) {
        this.day = day;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
}
