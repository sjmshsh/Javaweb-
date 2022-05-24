public class UdpEchoClient {
    private DatagramSocket socket = null;
    private String serverIp;
    private int serverPort;

    //要连接的那个服务器的ip和port.服务器的IP和端口
    public UdpEchoClient(String serverIp, int serverPort) throws SocketException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.socket = new DatagramSocket();
    }

    public void start() throws IOException {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            //1.从标准输入读入一个数据
            System.out.println("-> ");
            String request = scanner.nextLine();
            if (request.equals("goodbye")) {
                System.out.println("goodbye");
                return;
            }
            //2.把字符串构造称一个UDP请求,发送数据
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(), 0, request.getBytes().length, InetAddress.getByName(serverIp), serverPort);
            socket.send(requestPacket);
            //3.尝试从服务器之类读取响应
            DatagramPacket responsePacket = new DatagramPacket(new byte[4096], 4069);
            socket.receive(requestPacket);
            //4.显示结果
            String response = new String(requestPacket.getData(), 0, requestPacket.getLength());
            String log = String.format("req: %s, resp: %s", request, response);
            System.out.println(log);
        }
    }

    public static void main(String[] args) throws IOException {
        UdpEchoClient client = new UdpEchoClient("127.0.0.1", 9090);
        client.start();
    }
}
