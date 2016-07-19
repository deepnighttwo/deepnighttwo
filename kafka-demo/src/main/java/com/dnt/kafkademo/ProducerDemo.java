package com.dnt.kafkademo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * User: mzang
 * Date: 2015-04-01
 * Time: 14:22
 */
public class ProducerDemo {
    private Producer<String, String> producer;

    private void init() throws IOException {
        Properties props = new Properties();
        props.load(ProducerDemo.class.getClassLoader().getResourceAsStream("producer.properties"));
        producer = new KafkaProducer<String, String>(props);
    }

    public ProducerDemo() throws IOException {
        init();
    }

    public void sendMessage(String topic, String message) {
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>(topic, message);
        Future<RecordMetadata> future = producer.send(record);
        try {
            RecordMetadata metadata = future.get();
            System.out.println(metadata);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        producer.close();
    }

    public static void main(String[] args) throws IOException {
        ProducerDemo producerDemo = new ProducerDemo();
        for (int i = 0; i < 10; i++) {
            producerDemo.sendMessage("risklogging.idimodellogdatavo", "message " + i);
            System.out.println("sent " + i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producerDemo.close();
    }

}
