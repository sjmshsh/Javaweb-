public class demo5 {
    public static void main(String[] args) throws IOException {
        //1.让用户指定一个待扫描的根目录和要查询的关键词
        System.out.println("请输入要扫描的根目录(绝对路径):");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        String root = scanner.next();
        File rootDir = new File(root);
        if (!rootDir.isDirectory()) {
            System.out.println("您输入的路径错了!程序直接退出");
            return;
        }
        System.out.println("请输入要查找的文件名中要包含的关键词:");
        String token = scanner.next();

        //2.递归的遍历目录
        //result用来表示递归遍历的结果,包含着所有带有token关键词的文件名
        List<File> result = new ArrayList<>();
        scanDir(rootDir, token, result);

        //3.遍历result，问用户是否要删除文件，根据用户的输入决定是否删除
        for (File f : result) {
            System.out.println(f.getCanonicalFile() + "是否要删除?(Y / N)");
            String input = scanner.next();
            if(input.equals("Y")) {
                f.delete();
            }
        }
    }

    //递归的来遍历目录，找出里面所有符合条件的文件
    private static void scanDir(File rootDir, String token, List<File> result) throws IOException {
        //list 返回的时候一个文件名(String),使用listFiles直接得到的是File对象，用起来更方便一些
        File[] files = rootDir.listFiles();
        if (files == null || files.length == 0) {
            //当前的目录是一个空的目录
            return;
        }
        for (File f : files) {
            if(f.isDirectory()) {
                //如果当前的文件是一个目录，就递归的进行查找
                scanDir(f, token, result);
            } else {
                //如果当前不是一个目录，是一个普通的文件，就判定这个文件看看是不是包含了待查找的关键词
                if(f.getName().contains(token)) {
                    result.add(f.getCanonicalFile());
                }
            }
        }
    }
}
