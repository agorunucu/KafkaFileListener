package com.gorunucu.dataGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class FileUtil {

    static {
        checkAndCreateDir();
        setLastFileCount();
    }

    static void writeToFile(String data){
        try {

            // Open given file in append mode.
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(getFile(), true));
            out.write(data + "\n");
            out.close();
        }
        catch (IOException e) {
            System.err.println("exception occoured" + e);
        }
    }

    private static void checkAndCreateDir(){
        File file = new File(DataGeneratorMain.fileCreationDir);
        if(file.exists()) return;
        file.mkdirs();
    }

    private static void setLastFileCount(){
        int lastFileCount = DataGeneratorMain.fileCounter;
        File dir = new File(DataGeneratorMain.fileCreationDir);
        for(File file : dir.listFiles()){
            if(file.getName().startsWith(DataGeneratorMain.defaultFileName)){
                String fileNumber = file.getName()
                        .replace(DataGeneratorMain.defaultFileName, "")
                        .split("\\.")[0];
                lastFileCount = Math.max(lastFileCount, Integer.parseInt(fileNumber));
            }
        }

        DataGeneratorMain.fileCounter = lastFileCount;
    }

    private static File getFile() throws IOException {
        File file = new File(DataGeneratorMain.fileCreationDir + File.separator
        + DataGeneratorMain.defaultFileName + DataGeneratorMain.fileCounter + ".log");


        if(file.exists() && file.length() > DataGeneratorMain.maxFileSize){
            DataGeneratorMain.fileCounter++;
            return getFile();
        }

        if(!file.exists()){
            file.createNewFile();
            System.out.println(file.getName() + " file created.");
        }

        return file;
    }
}
