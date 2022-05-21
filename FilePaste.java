public class demo6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要复制的文件：(绝对路径)");
        String srcPath = scanner.next();
        File srcFile = new File(srcPath);
        if (!srcFile.isFile()) {
            System.out.println("文件路径错误，程序直接退出!");
            return;
        }
        System.out.println("请输入要复制到的目标路径：(绝对路径)");
        String destPath = scanner.next();
        //要求这个destFile必须不能存在,如果已经存在，你就复制不过去了
        File destFile = new File(destPath);
        if (destFile.exists()) {
            System.out.println("目标文件的路径已经存在,程序直接退出");
            return;
        }
        if (!destFile.getParentFile().exists()) {
            //父级目录不存在，也提示一个报错
            System.out.println("目标文件的父目录不存在，程序直接退出");
            return;
        }
        //具体进行复制操作,读取出每一个字节，然后再把这些字节写入到目标文件中
        try (InputStream inputStream = new FileInputStream(srcFile);
        OutputStream outputStream = new FileOutputStream(destFile)) {
            //从inputStream中按照字节来读，然后把结果写入到outputStream中
            while (true) {
                byte[] buffer = new byte[1024];
                int len = inputStream.read(buffer);
                if (len == -1) {
                    break;
                }
                outputStream.write(buffer, 0, len);
            }
            //如果这里不加flush,触发close操作也会自动刷新缓冲区
            outputStream.flush();
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("复制完成！");
    }
}
