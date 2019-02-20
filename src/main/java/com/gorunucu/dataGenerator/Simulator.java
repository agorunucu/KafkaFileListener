package com.gorunucu.dataGenerator;

import java.util.Random;

class Simulator implements Runnable{

    private int minWaitTime;
    private int maxWaitTime;
    private int dataCount;

    Simulator(int minWaitTime, int maxWaitTime, int dataCount){
        this.maxWaitTime = maxWaitTime;
        this.minWaitTime = minWaitTime;
        this.dataCount = dataCount;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < dataCount; i++){
                processData(Generator.generate());
                simulate();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processData(String data){
        System.out.println(data);
        FileUtil.writeToFile(data);
    }

    private void simulate() throws InterruptedException {
        Thread.sleep(new Random().nextInt(maxWaitTime - minWaitTime) + minWaitTime);
    }
}
