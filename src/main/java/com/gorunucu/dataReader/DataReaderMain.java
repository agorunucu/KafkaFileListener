package com.gorunucu.dataReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DataReaderMain {

    static final String fileCreationDir = "logData";
    private static final List<FileTail> fileTailList = new ArrayList<>();

    public static void main(String... args) throws InterruptedException, IOException {
        //readOldData();
        fileWatcher();
    }

    private static void readOldData() {
        File dir = new File(fileCreationDir);
        for(File file : dir.listFiles()){
            fileTailList.add(FileTailUtil.getTail(file.getAbsolutePath()));
        }
    }

    private static void fileWatcher() throws IOException, InterruptedException {
            WatchService watchService
                    = FileSystems.getDefault().newWatchService();

            Path path = Paths.get(fileCreationDir);

            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                        onFileUpdate(fileCreationDir + File.separator + event.context().toString());
                    }else if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                        onFileCreate(fileCreationDir + File.separator + event.context().toString());
                    }else if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                        onFileDelete(fileCreationDir + File.separator + event.context().toString());
                    }
                }
                key.reset();
            }
    }

    private static void onFileCreate(String fileName){
        fileTailList.add(FileTailUtil.getTail(fileName));
    }

    private static void onFileUpdate(String fileName){
        FileTail fileTail = getFileTail(fileName);
        if (fileTail != null) {
            fileTail.newDataUploaded();
        } else{
            onFileCreate(fileName);
        }
    }

    private static void onFileDelete(String fileName){
        FileTail fileTail = getFileTail(fileName);
        if (fileTail != null) {
            fileTail.stopRunning();
        }else{
            onFileCreate(fileName);
        }
    }

    private static FileTail getFileTail(String fileName){
        for(FileTail fileTail : fileTailList)
            if(fileTail.fileName.equals(fileName))
                return fileTail;

        return null;
    }

}
