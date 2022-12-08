package kz.recar.kafka;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaClassListener {
    @KafkaListener(topics = "auto", groupId = "myGroup")
    void listener(String data){
        System.out.println("received "+ data);
    }
}