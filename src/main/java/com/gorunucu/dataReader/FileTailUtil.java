package com.gorunucu.dataReader;

import java.io.File;

public class FileTailUtil {

    public static FileTail getTail(String fileToWatch) {
        if (fileToWatch == null || fileToWatch.isEmpty()) {
            throw new IllegalArgumentException("fileName shouldn't be empty");
        }

        /* Check for existence of file */
        File file = new File(fileToWatch);
        if (!file.exists()) {

            throw new IllegalArgumentException(fileToWatch + " doesn't exists");
        }

        FileTail fileTail = new FileTail(fileToWatch);
        Thread watcherThread = new Thread(fileTail);
        watcherThread.start();
        System.out.println("New file listener is created: " + fileToWatch);
        return fileTail;
    }
}