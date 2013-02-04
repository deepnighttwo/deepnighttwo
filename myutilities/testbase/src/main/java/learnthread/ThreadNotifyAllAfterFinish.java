/**
 * 
 * @author  Moon Zang
 * 
 */

package learnthread;

import java.io.File;

public class ThreadNotifyAllAfterFinish {

    public static void main(String[] args) {

        final Thread th1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(File.separator + "Start1...");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                }
                System.out.println("End1...");
            }

        });
        th1.start();

        Thread th2 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Start2...");

                try {
                    th1.join();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                System.out.println("End2...");
            }

        });
        th2.start();
    }
}
