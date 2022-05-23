public class thread {
    static class WaitTask implements Runnable {
        private Object locker = null; //这里加一个锁对象

        public WaitTask(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            //进行wait的线程
            synchronized (locker) {
                System.out.println("wait开始");
                try {
                    //直接调用wait，相当于this.wait()，也就是针对WaitTask对象来进行等待
                    //但是我们一会儿在NotifyTask中要求得针对同一个对象来通知，然而，
                    //并没有那么容易拿到WaitTask实例
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("wait结束");
            }
        }
    }

    static class Notify implements Runnable {
        private Object locker = null;

        public Notify(Object locker) {
            this.locker = locker;
        }

        @Override
        public void run() {
            //进行notify的线程
            synchronized (locker) {
                System.out.println("notify 开始");
                locker.notify();
                System.out.println("notify 结束");
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //为了解决刚才的问题，专门创建一个对象，去负责加锁/通知操作
        Object locker = new Object();
        Thread t1 = new Thread(new WaitTask(locker));
        Thread t2 = new Thread(new Notify(locker));
        t1.start();
        Thread.sleep(3000);
        t2.start();
    }
}
