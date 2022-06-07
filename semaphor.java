public class test {
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(4);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //先尝试申请资源
                //申请到之后休眠一秒
                //再释放资源
                try {
                    System.out.println("准备申请资源");
                    semaphore.acquire();
                    System.out.println("申请资源成功");
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println("释放资源");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //创建20个线程
        //让这20个线程来分别去尝试申请资源
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
