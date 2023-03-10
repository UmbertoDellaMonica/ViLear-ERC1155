package it.tcgroup.vilear.base.Payload.Enum;

public enum StatusEnum {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    DELETED("DELETED"),
    APPROVED("APPROVED");

    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
