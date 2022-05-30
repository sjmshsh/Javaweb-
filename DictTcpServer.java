//通过观察，就发现，其实echo服务器和dict服务器之间，主要的区别就是process方法
//其他的都差不多，因此就可以继承的手段来复用代码
//只需要重写process方法，重新实现根据请求计算响应的逻辑即可
public class TcpDictServer extends TcpThreadEchoServer{
    private HashMap<String, String> dict = new HashMap<>();

    public TcpDictServer(int port) throws IOException {
        super(port);
        dict.put("hello", "你好");
        dict.put("cat", "小猫");
        dict.put("dog", "小狗");
    }

    //只需要修改process方法就可以了
    @Override
    public String process(String request) {
        return dict.getOrDefault(request, "[你要查询的词是不存在的]");
    }

    public static void main(String[] args) throws IOException {
        TcpDictServer server = new TcpDictServer(9090);
        server.start();
    }
}
