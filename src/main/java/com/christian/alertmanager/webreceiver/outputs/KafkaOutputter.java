package com.christian.alertmanager.webreceiver.outputs;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@Slf4j
public class KafkaOutputter implements EventOutput {

    private KafkaProducer producer;
    private KafkaConfig kafkaConfig;

    @Autowired
    public KafkaOutputter(KafkaConfig kafkaConfig) {

        this.kafkaConfig = kafkaConfig;
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", kafkaConfig.getBootstrapServers());
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");


        producer = new KafkaProducer<String,String>(kafkaProps);
    }

    @Override
    public void process(JsonObject jsonObject) {

        ProducerRecord<String, String> record = new ProducerRecord<>(kafkaConfig.getTopic(), jsonObject.toString());
        try {
            producer.send(record);
        } catch (Exception e) {
            log.error("Failed to send record to kafka");
            log.error("{}", record);
            log.error("", e);
        }
    }

}
