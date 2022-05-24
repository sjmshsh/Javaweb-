public class UdpEchoServer {
    private DatagramSocket socket = null;

    //port表示端口号
    //服务器在启动的时候，需要关联(绑定)上一个端口号
    //收到数据的时候，就会根据这个端口号来决定把数据交给哪个进程
    //虽然此处port写的类型是int，但实际上端口号是一个两个字节的无符号整数
    //范围是0-65535
    public UdpEchoServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    //此处的process方法负责的功能就是根据请求来计算响应
    //由于当前是一个回显服务器，就把客户端发来的请求直接返回回去即可
    private String process(String request) {
        return request;
    }

    //通过这个方法来启动服务器
    public void start() throws IOException {
        System.out.println("服务器启动!");
        //持续运行(7*24)
        while (true) {
            //1.读取请求,当前服务器不知道客户端什么时候发来请求，receive方法也会阻塞
            //如果真的有请求过来了，此时receive就会返回
            //DatagramPacket 在构造的时候，需要指定一个缓冲区(就是一块儿内存空间，通常使用byte[])
            //这里是一个输出型参数
            DatagramPacket requestPacket = new DatagramPacket(new byte[4069], 4069);
            socket.receive(requestPacket);
            // 把 requestPacket 对象里面的内容取出来，作为一个字符串
            String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
            //2. 根据请求来计算响应，
            String response = process(request);
            //3. 把相应写回到客户端，这时候也需要构造一个DatagramPacket
            //此处我们给DatagramPacket中设置的长度，必须是"字节的个数"
            //如果直接去response.length(),此处得到的是字符串的长度，也就是"字符发个数"
            //当前的 responsePacket在构造的时候，还需要指定这个包要发给谁
            //其实发送给的目标就是发请求的那一方
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.getBytes().length, requestPacket.getSocketAddress());
            socket.send(responsePacket);
            //4.加上日志打印
            String log = String.format("[%s:%d] req: %s; resp: %s", requestPacket.getAddress().toString(), requestPacket.getPort(), request, response);//这个是格式化打印，就根C语言的sprintf差不多
            System.out.println(log);
        }
    }

    public static void main(String[] args) throws IOException {
        int port = 3306;
        UdpEchoServer udpEchoServer = new UdpEchoServer(port);
        udpEchoServer.start();
    }
}
