package ru.mail.dimaushenko.utils;

import java.util.Random;


public class RandomUtil {

    private static RandomUtil instance = null;

    private RandomUtil() {
    }

    public static RandomUtil getInstance() {
        if (instance == null) {
            instance = new RandomUtil();
        }
        return instance;
    }

    private final Random rand = new Random();

    public int getInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

}
