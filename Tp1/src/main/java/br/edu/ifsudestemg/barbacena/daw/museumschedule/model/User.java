package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDateTime;

public class User implements Cloneable {

    private String username;
    private String password;
    private String confirmationPassword;
    private LocalDateTime createdAt;
    private Employee employee;
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

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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

    public boolean confirmationPasswordMatch(){
        return password.equals(confirmationPassword);
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
