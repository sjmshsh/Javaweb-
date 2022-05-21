public class demo7 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //1.让用户输入一个路径，待搜索的路径
        System.out.println("请输入要扫描的根目录:");
        String rootDir = scanner.next();
        File rootFile = new File(rootDir);
        if (!rootFile.isDirectory()) {
            System.out.println("该目录不存在或者不是文件，直接退出.");
            return;
        }
        //2.再让用户输入一个查询词，表示要搜索的结果中要包含这个词
        System.out.println("请输入要查询的词");
        String query = scanner.next();
        //3.遍历目录以及文件，进行匹配
        List<File> result = new ArrayList<>();
        scanDirWitContent(rootFile, query, result);
    }

    private static void scanDirWitContent(File rootFile, String query, List<File> result) {
        File[] files = rootFile.listFiles();
        if (files == null || files.length == 0) {
            //空目录直接return
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                scanDirWitContent(f, query, result);
            } else {
                if (f.getName().contains(query)) {
                    // 看看文件名称中是否包含
                    result.add(f);
                } else if (ifContentContains(f, query)) {
                    //看看文件内容中是否包含
                    result.add(f);
                }
            }
        }
    }

    private static boolean ifContentContains(File f, String query) {
        // 打开 f 这个文件，依次取出每一行结果，去和 query 来进行一个indexOf
        StringBuilder stringBuilder = new StringBuilder();
        try (InputStream inputStream = new FileInputStream(f)){
            Scanner scanner = new Scanner(inputStream, "UTF-8");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                stringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //只要结果不等于-1，就说明查到了
        return stringBuilder.indexOf(query) != -1;
    }
}
