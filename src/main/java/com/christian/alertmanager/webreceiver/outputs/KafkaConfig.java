package com.christian.alertmanager.webreceiver.outputs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "outputs.kafka")
@Getter
@Setter
public class KafkaConfig {
    private String bootstrapServers;
    private String topic;
}