package roff.importnew.concurrent.java;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wuyongbo on 16-3-11.
 */
public class BlockingQueueUsage {

    public static void main(String[] args) throws Exception {

        BlockingQueue queue = new ArrayBlockingQueue(20);

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        new Thread(producer).start();
        new Thread(consumer).start();

        Thread.sleep(4000);

        //ExecutorService
        ExecutorService service = Executors.newFixedThreadPool(10);
        service.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous task");
            }
        });
    }
}
