package com.dnt.kafkademo;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: mzang
 * Date: 2015-04-02
 * Time: 09:51
 */
public class ConsumerDemo2 {

    private ExecutorService threadPool;

    private ConsumerConnector connector;

    private Charset charset = Charset.forName("utf8");


    private void init() throws IOException {
        Properties props = new Properties();
        props.load(ConsumerDemo.class.getClassLoader().getResourceAsStream("consumer.properties"));

        ConsumerConfig consumerConfig = new ConsumerConfig(props);

        final kafka.javaapi.consumer.ConsumerConnector connector = Consumer.createJavaConsumerConnector(consumerConfig);

        Map<String, Integer> topics = new HashMap<String, Integer>();
        topics.put("topic1", 3);

        Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topics);

        List<KafkaStream<byte[], byte[]>> partitions = streams.get("topic1");

        threadPool = Executors.newFixedThreadPool(2);

        for (KafkaStream<byte[], byte[]> stream : partitions) {
            final KafkaStream inner = stream;
            threadPool.execute(new Runnable() {
                public void run() {
                    ConsumerIterator<byte[], byte[]> it = inner.iterator();

                    while (it.hasNext()) {

                        MessageAndMetadata<byte[], byte[]> item = it.next();
                        String content = new String(item.message(), charset);
                        System.out.println(content);

                    }
                }
            });
        }

    }

    public ConsumerDemo2() throws IOException {
        init();
    }

    public static void main(String[] args) throws IOException {
        ConsumerDemo2 demo2 = new ConsumerDemo2();
    }
}
