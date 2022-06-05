package ThreadTestDemo;

import com.sun.xml.internal.fastinfoset.util.PrefixArray;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author 写你的名字
 * @Date 2022/6/3 22:09 （可以根据需要修改）
 * @Version 1.0 （版本号）
 */
public class ThreadDemo1 {

    static class MyBlockingQueue {
        private Object locker = new Object();
        private int head = 0;
        private int tail = 0;
        private int size = 0;
        private int[] items = new int[10];

        public void put (int elem) throws InterruptedException {
            synchronized (locker) {
                while (size == items.length) {
                    locker.wait();
                }
                items[tail++] = elem;
                if (tail >= items.length) {
                    tail = 0;
                }
                size++;
                locker.notify();
            }
        }

        public int take () throws InterruptedException {
            int result = 0;
            synchronized (locker) {
                while (size == 0) {
                    locker.wait();
                }
                result = items[head];
                head++;
                size--;
                if (head >= items.length) {
                    head = 0;
                }
                locker.notify();
            }
            return result;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue queue = new MyBlockingQueue();
        Thread customer = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int elem = queue.take();
                        Thread.sleep(1000);
                        System.out.println("消费元素" + elem);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        customer.start();

        Thread producer = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i <= 10000; i++) {
                    try {
                        queue.put(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("生产:" + i);
                }
            }
        };
        producer.start();
    }
}
