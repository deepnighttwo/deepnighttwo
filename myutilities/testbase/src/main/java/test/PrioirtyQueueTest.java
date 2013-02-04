package test;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class PrioirtyQueueTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Queue<Integer> q = new PriorityBlockingQueue<Integer>(100);
        q.add(5);
        q.add(9);
        q.add(7);
        q.add(10);
        q.size();
        System.out.println(q.peek());
        while (q.isEmpty() == false) {
            System.out.println(q.peek());
            System.out.println(q.poll());
        }
    }

}
