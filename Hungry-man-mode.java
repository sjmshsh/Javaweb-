public class ThreadDemo3 {

    //单例模式
    static class Singleton {
        private Singleton () {

        }

        private static Singleton instance = new Singleton();
        public Singleton getInstance() {
            return instance;
        }
    }

    public static void main(String[] args) {
        Singleton singleton = new Singleton();
        singleton.getInstance();
    }
}
