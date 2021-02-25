package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDateTime;

public class User implements Cloneable {

    private String username;
    private String password;
    private LocalDateTime createdAt;
    private Long employeeId;
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User withoutPassword() {
        User user = null;

        try {
            user = this.clone();
            user.setPassword("");
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    protected User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

    public boolean isSystemAdmin(){
        return role.isSystemAdmin();
    }

    public enum Role {
        ADMIN(1, true),
        EMPLOYEE(2, false);

        private final int code;
        private final boolean systemAdmin;

        Role(int code, boolean systemAdmin) {
            this.code = code;
            this.systemAdmin = systemAdmin;
        }

        public int getCode() {
            return code;
        }

        public boolean isSystemAdmin() {
            return systemAdmin;
        }

        public static Role from(int code) {
            for (var r : values())
                if (r.code == code)
                    return r;

            throw new IllegalArgumentException("No such role");
        }
    }
}
