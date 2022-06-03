package MultiThread;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Date;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Author 写你的名字
 * @Date 2022/5/11 16:53 （可以根据需要修改）
 * @Version 1.0 （版本号）
 */


public class thread {
    //使用这个类来描述这个任务
    static class Task implements Comparable<Task>{
        // command表示这个任务是啥
        // time表示这个任务什么时候到时间
        // 这里的time我们使用一个毫秒级别的时间戳
        private Runnable command;
        private long time;

        // 约定这里的参数 time 是一个时间差(类似于3000)
        // 希望this.time 来保存一个绝对的时间(毫秒级时间戳)
        public Task(Runnable command, long time) {
            this.command = command;
            this.time = time + System.currentTimeMillis();
        }

        @Override
        public int compareTo(Task o) {
            return (int) (this.time - o.time);
        }

        public void run() {
            command.run();
        }
    }

    static class Timer {
        //使用这个带优先级版本的阻塞队列来组织这些任务
        private PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        private Object locker = new Object(); //解决忙等问题

        public void schedule (Runnable command, long delay){
            Task task = new Task(command, delay);
            queue.put(task);
            //每次插入新的任务都要唤醒扫描线程，让扫描线程能够重新计算wait的时间，保证新的任务也不会错过
            synchronized (locker) {
                locker.notify();
            }
        }

        public Timer() {
            //创建一个扫描线程，这个线程就来判定当前的任务，看看是不是已经到时间能执行了
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        //取出队列的首元素，判定时间是不是到了
                        try {
                            Task task = queue.take();
                            long curTime = System.currentTimeMillis();
                            if (task.time > curTime) {
                                //还没到，暂时不执行
                                //前面的take操作会把队首元素给删除掉
                                //但是此时队首元素的任务还没用执行，不能删除，于是需要重新插入回队列
                                queue.put(task);
                                //根据时间差来进行一个等待
                                synchronized (locker) {
                                    locker.wait(task.time - curTime);
                                }
                            } else {
                                //时间到了
                                task.run();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            //如果出现了 interrupt 方法，我们就能够退出线程
                            break;
                        }
                    }
                }
            };
            t.start();
        }
    }
    public static void main(String[] args){
        System.out.println("程序开始");
        Timer timer = new Timer();
        timer.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("I love you zyq");
            }
        }, 3000);
        System.out.println("程序结束");
    }
}
