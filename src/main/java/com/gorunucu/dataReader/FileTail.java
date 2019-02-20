package com.gorunucu.dataReader;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * Worker class to print the tail of a file.
 */
public class FileTail implements Runnable {

    /**
     * Full Path of the file to watch
     */
    String fileName;

    /**
     * Monitors the update of the file for every intervalTime seconds.
     */
    private int intervalTime;

    /**
     * Set the flag to true, to stop the execution of thread.
     */
    private volatile boolean stopThread = false;

    /**
     * File To watch
     */
    private File fileToWatch;

    private final Object lockObject = new Object();
    /**
     * Variable used to get the last know position of the file
     */
    private long lastKnownPosition = 0;

    private KafkaUtil ku;

    public FileTail(String fileName) {
        this(fileName, 2000);
    }

    public FileTail(String fileName, int intervalTime) {
        this.fileName = fileName;
        this.intervalTime = intervalTime;
        this.fileToWatch = new File(fileName);
        this.ku = KafkaUtil.getInstance();
    }

    @Override
    public void run() {

        if (!fileToWatch.exists()) {
            throw new IllegalArgumentException(fileName + " not exists");
        }
        try {
            while (!stopThread) {
                //Thread.sleep(intervalTime);
                long fileLength = fileToWatch.length();

                /**
                 * This case occur, when file is taken backup and new file
                 * created with the same name.
                 */
                if (fileLength < lastKnownPosition) {
                    lastKnownPosition = 0;
                }
                if (fileLength > lastKnownPosition) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(fileToWatch, "r");
                    randomAccessFile.seek(lastKnownPosition);
                    String line;
                    while ((line = randomAccessFile.readLine()) != null) {
                        System.out.println(line);
                        ku.send("TEB", line);
                    }
                    lastKnownPosition = randomAccessFile.getFilePointer();
                    randomAccessFile.close();
                }
                synchronized (lockObject){
                    lockObject.wait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopRunning();
        }

    }

    public boolean isStopThread() {
        return stopThread;
    }

    public void setStopThread(boolean stopThread) {
        this.stopThread = stopThread;
    }

    public void stopRunning() {
        stopThread = true;
        synchronized (lockObject){
            lockObject.notify();
        }
        System.out.println("File listener is stopped.");
    }

    public void newDataUploaded(){
        synchronized (lockObject){
            lockObject.notify();
        }
    }

}