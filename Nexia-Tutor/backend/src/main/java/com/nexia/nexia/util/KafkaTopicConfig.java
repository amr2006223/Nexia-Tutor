package com.nexia.nexia.util;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${spring.kafka.template.default-topic}")
    private String topicName;
    
    @Bean   
    public NewTopic topic(){
        return TopicBuilder.name("new-topic")
        //.partitions(3) //dont know what it do yet but for now use default partitions
        .build();
    }
}
