package com.gorunucu.dataProcessor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataProcessorMain {

    static KafkaConsumer kafkaConsumer = new KafkaConsumer(getProperties());

    public static void main(String... args){

        List<String> topics = new ArrayList<>();
        topics.add("TEB");
        kafkaConsumer.subscribe(topics);
        try{
            while (true){
                ConsumerRecords records = kafkaConsumer.poll(1000);
                for (ConsumerRecord record : (Iterable<ConsumerRecord>) records) {
                    //if(logData)
                    System.out.println(String.format("Topic - %s, Partition - %d, Value: %s",
                            record.topic(), record.partition(), record.value()));
                    processData(record.value().toString());
                }
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            kafkaConsumer.close();
        }
    }

    private static Properties getProperties(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-group");
        return properties;
    }

    private static void processData(String data) throws IOException, SolrServerException {
        SolrUtil.getInstance().save(data);
    }

}
