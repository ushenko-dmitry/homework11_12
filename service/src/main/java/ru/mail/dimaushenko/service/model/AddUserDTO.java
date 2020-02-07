package ru.mail.dimaushenko.service.model;

public class AddUserDTO {

    private String username;
    private String password;
    private Integer roleId;

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

    public Integer getRole() {
        return roleId;
    }

    public void setRole(Integer role) {
        this.roleId = role;
    }
}
