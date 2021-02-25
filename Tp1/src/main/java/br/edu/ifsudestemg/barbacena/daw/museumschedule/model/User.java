package br.edu.ifsudestemg.barbacena.daw.museumschedule.model;

import java.time.LocalDateTime;

public class User implements Cloneable {

    private String username;
    private String password;
    private LocalDateTime createdAt;
    private Long museum_id;
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

    public Long getMuseum_id() {
        return museum_id;
    }

    public void setMuseum_id(Long museum_id) {
        this.museum_id = museum_id;
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

    public enum Role {
        ADMIN(1, true),
        EMPLOYEE(2, false);

        private final int code;
        private final boolean is_system_admin;

        Role(int code, boolean is_system_admin) {
            this.code = code;
            this.is_system_admin = is_system_admin;
        }

        public int getCode() {
            return code;
        }

        public boolean isIs_system_admin() {
            return is_system_admin;
        }

        public static Role from(int code) {
            for (var r : values())
                if (r.code == code)
                    return r;

            throw new IllegalArgumentException("No such role");
        }
    }
}
