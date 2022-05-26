//通过递归的方式,来罗列出指定目录中所有的文件路径
public class file {
    public static List<String> result = new ArrayList<>();
    public static void getAllFiles(String basePath) {
        File file = new File(basePath);
        if (file.isFile()) {
            result.add(basePath);
            return;
        } else if (file.isDirectory()) {
            String[] files = file.list();
            for (String f : files) {
                getAllFiles(basePath + '/' + f);
            }
        } else {
            //当前的文件既不是普通文件，也不是目录，这个情况暂时不考虑
            //这样的文件确实存在，只是我们当前课堂不考虑...(socket文件， 管道文件， 设备块文件..)
        }
    }

    public static void main(String[] args) {
        getAllFiles(".");
        for (String s : result) {
            System.out.println(s);
        }
    }
}
