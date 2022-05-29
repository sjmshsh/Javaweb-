public class CaculateClient {
    private DatagramSocket socket = null;
    private String serverIP;
    private int serverPort;

    public CaculateClient(String serverIP, int serverPort) throws SocketException {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.socket = new DatagramSocket();
    }

    public void start() throws IOException {
        // 1. 让用户进行输入
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入操作数 num1: ");
            int num1 = scanner.nextInt();
            System.out.println("输入操作数 num2: ");
            int num2 = scanner.nextInt();
            System.out.println("请输入操作符( + - * /): ");
            String operator = scanner.next();
            // 2. 构造并发送请求
            String request = num1 + ";" + num2 + ";" + operator;
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(), request.getBytes().length,
                    InetAddress.getByName(serverIP), serverPort);
            socket.send(requestPacket);
            DatagramPacket responsePacket = new DatagramPacket(new byte[4096], 4096);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
            System.out.print("计算结果为: ");
            System.out.println(response);
        }
    }

    public static void main(String[] args) throws IOException {
        CaculateClient client = new CaculateClient("127.0.0.1", 9090);
        client.start();
    }
}
