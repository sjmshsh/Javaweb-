public class CaculateServer {
    private DatagramSocket socket = null;

    public CaculateServer(int port) throws SocketException {
        socket = new DatagramSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务区启动!");
        while (true) {
            //1. 读取请求并解析
            DatagramPacket requestPacket = new DatagramPacket(new byte[4096], 4096);
            socket.receive(requestPacket);
            String request = new String(requestPacket.getData(), 0, requestPacket.getLength());
            //2. 根据请求计算响应
            String response = process(request);
            DatagramPacket responsePacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                    requestPacket.getSocketAddress());
            socket.send(responsePacket);
            //4. 打印日志
            String log = String.format("[%s:%d] rep:%s; resp:%s", requestPacket.getAddress(), requestPacket.getPort(), request, response);
            System.out.println(log);
        }
    }

    // process内部就按照咱们约定好的自定义协议来进行具体的处理
    private String process(String request) {
        //1. 把request 还原成操作数和运算符
        String[] tokens = request.split(";");
        if(tokens.length != 3) {
            return "[请求格式出错]";
        }
        int num1 = Integer.parseInt(tokens[0]);
        int num2 = Integer.parseInt(tokens[1]);
        String operator = tokens[2];
        int result = 0;
        if (operator.equals("+")) {
            result = num1 + num2;
        } else if (operator.equals("-")){
            result = num1 - num2;
        } else if (operator.equals("*")) {
            result = num1 * num2;
        } else if (operator.equals("/")) {
            result = num1 / num2;
        } else {
            return "[请求格式出错，操作符不支持!]";
        }
        return result + "";
    }

    public static void main(String[] args) throws IOException {
        CaculateServer server = new CaculateServer(9090);
        server.start();
    }
}
