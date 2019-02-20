package com.gorunucu.dataReader;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaUtil {

    private static KafkaUtil kafkaUtil = new KafkaUtil();

    public static KafkaUtil getInstance(){
        return kafkaUtil;
    }

    private KafkaProducer kafkaProducer = new KafkaProducer(getProperties());
    private Properties getProperties(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return properties;
    }

    public void send(String topic, String message){
        try{
            kafkaProducer.send(new ProducerRecord(topic, message));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
