public class demo5 {
    public static void main(String[] args) {
        try (OutputStream outputStream = new FileOutputStream("./test.txt")) {
            //使用PrintWriter类来包装一下,这个跟Scanner差不多
            try (PrintWriter writer = new PrintWriter(outputStream)) {
                writer.println("我喜欢你珂朵莉");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
