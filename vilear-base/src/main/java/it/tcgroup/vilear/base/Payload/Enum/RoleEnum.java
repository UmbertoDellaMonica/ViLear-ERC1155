package it.tcgroup.vilear.base.Payload.Enum;

public enum RoleEnum {
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    PARTNER("ROLE_PARTNER"),
    SUPER_PARTNER("ROLE_SUPER_PARTNER"),
    SUPER_ADMIN("ROLE_SUPER_ADMIN");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
