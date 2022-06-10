public class ThreadDemo4 {
    static class Singleton {
        private Singleton() { }
        private static volatile Singleton instance = null;
        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        instance = new Singleton();
                    }
                }
            }
            return instance;
        }
    }
    public static void main(String[] args) {

    }
}
