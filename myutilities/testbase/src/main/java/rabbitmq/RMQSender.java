package rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * User: mzang
 * Date: 2014-05-22
 * Time: 16:04
 */
public class RMQSender {
    private final static String QUEUE_NAME = "hello";


    static String buildString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append((char) ('A' + (int) (Math.random() * 27)));
        }
        return sb.toString();
    }

    public static void main(String[] argv) throws java.io.IOException {

        int loop = 100 * 10000;
        write2Q(buildString(100), loop);
//        write2Q(buildString(5 * 1024), loop);
//        write2Q(buildString(1024 * 30), loop);
//        write2Q(buildString(1024 * 50), loop);
//        write2Q(buildString(1024 * 100), loop);


    }

    public static void write2Q(String base, int loop) throws java.io.IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("sec");
        factory.setPassword("sec");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            String message = i + "\t" + base;
            channel.basicPublish("", QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN
                    , message.getBytes());
//            System.out.println(" [x] Sent '" + message + "'");
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("Base len=" + base.length() + ", loop=" + loop + ", takes=" + time + "ms. perf=" + (loop * 1.0 / time) + "msg per ms");
//        channel.queueDelete(QUEUE_NAME, false, false);
        channel.close();
        connection.close();
    }

}
