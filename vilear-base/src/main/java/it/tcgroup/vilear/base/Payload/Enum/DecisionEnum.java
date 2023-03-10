package it.tcgroup.vilear.base.Payload.Enum;

public enum DecisionEnum {
    ACCEPT("ACCEPT"),
    DENY("DENY");

    private String status;

    DecisionEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
