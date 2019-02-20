package com.gorunucu.dataGenerator;

import java.sql.Timestamp;
import java.util.Random;

import static com.gorunucu.dataGenerator.DataGeneratorMain.*;

class Generator {

    static String generate(){
        String timestamp = generateTimestamp();
        String logLevel = generateLogLevel();
        // Min
        String randomString = generateRandomString(new Random().nextInt(maxRandomDataLength - minRandomDataLength) + minRandomDataLength);
        String IP = generateIP();
        String host = generateHost();
        String location = generateLocation();
        String delimiter = DataGeneratorMain.delimiter;

        return
                timestamp + delimiter
                + logLevel + delimiter
                + randomString + delimiter
                + IP + delimiter
                + host + delimiter
                + location;
    }

    private static String generateTimestamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

    private static String generateLogLevel(){
        return logLevels[
                        new Random().nextInt(logLevels.length)];
    }

    private static String generateRandomString(int targetStringLength) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    private static String generateIP(){
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    private static String generateHost(){
        return generateRandomString(6) + ".com";
    }

    private static String generateLocation(){
        return cities[
                new Random().nextInt(cities.length)];
    }
}
