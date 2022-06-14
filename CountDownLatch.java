public class ThreadDemo14 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(8);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("起跑");
                try {
                    Thread.sleep((long) (Math.random() * 10000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("跑步结束");
                latch.countDown();
            }
        };
        for (int i = 0; i < 8; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
        latch.await();
    }
}
