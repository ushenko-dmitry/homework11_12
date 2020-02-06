package ru.mail.dimaushenko.utils;

public class PrepareDataBase {

    private static PrepareDataBase instance = null;

    private PrepareDataBase() {
    }

    public static PrepareDataBase getInstance() {
        if (instance == null) {
            instance = new PrepareDataBase();
        }
        return instance;
    }

    public void prepareDB() {
        
    }

}
