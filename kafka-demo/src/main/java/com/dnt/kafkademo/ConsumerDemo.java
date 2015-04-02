package com.dnt.kafkademo;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * User: mzang
 * Date: 2015-04-01
 * Time: 14:22
 */
public class ConsumerDemo {

    private Consumer<String, String> consumer;

    private void init() throws IOException {
        Properties props = new Properties();
        props.load(ConsumerDemo.class.getClassLoader().getResourceAsStream("consumer.properties"));
        consumer = new KafkaConsumer<String, String>(props);
        TopicPartition partition0 = new TopicPartition("topic1", 0);
        TopicPartition partition1 = new TopicPartition("topic1", 1);
        TopicPartition partition2 = new TopicPartition("topic1", 2);
        consumer.subscribe(partition0, partition1, partition2);
    }

    public ConsumerDemo() throws IOException {
        init();
    }

    public Map<String, ConsumerRecords<String, String>> getMessages() {
        Map<String, ConsumerRecords<String, String>> ret = null;
        while (ret == null) {
            ret = consumer.poll(1000);
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {
        ConsumerDemo consumerDemo = new ConsumerDemo();

        for (Map.Entry<String, ConsumerRecords<String, String>> entry : consumerDemo.getMessages().entrySet()) {
            System.out.println(entry.getKey());
            for (ConsumerRecord<String, String> record : entry.getValue().records()) {
                System.out.println(record.key());
                System.out.println(record.value());
            }
        }
    }

}
