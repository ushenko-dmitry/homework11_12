package ru.mail.dimaushenko.constants;

public enum DefaultRoles {

    ADMIN("admin"),
    USER("user");

    private String description;
    
    private DefaultRoles(String description) {
        this.description = description;
    }
    
    public String getDescripton(){
        return description;
    }

}
