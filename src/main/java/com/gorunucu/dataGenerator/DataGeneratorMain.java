package com.gorunucu.dataGenerator;


public class DataGeneratorMain{

    static final String fileCreationDir = "logData";
    static final long maxFileSize = 4096; // 4KB
    static final String defaultFileName = "log_";
    static int fileCounter = 0;
    static final String delimiter = ",";
    static final short minRandomDataLength = 20;
    static final short maxRandomDataLength = 200;

    private static final short simulatorMinWaitTime= 10;
    private static final short simulatorMaxWaitTime= 900;

    static final String[] cities = {
            "Istanbul", "Ankara", "Corum", "Izmir", "Antalya", "Mugla", "Balikesir",
            "Ardahan", "Hatay", "Edirne", "Trakya", "Bolu"
    };

    static final String[] logLevels = {
            "DEBUG", "INFO", "WARN", "ERROR", "FATAL", "TRACE"
    };

    public static void main(String... args) throws InterruptedException {

        Thread thread = new Thread(new Simulator(simulatorMinWaitTime, simulatorMaxWaitTime, Integer.MAX_VALUE));
        thread.start();
        thread.join();
    }

}
