public class TcpEchoClient {
    private Socket socket = null;
    private String serverIP;
    private int serverPort;

    public TcpEchoClient(String serverIP, int serverPort) throws IOException {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        // 让 socket 创建的同时，就和服务器尝试建立了连接
        this.socket = new Socket(serverIP, serverPort);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()){
            while (true) {
                //1. 从键盘上读取用户输入的内容
                System.out.print("->");
                String request = scanner.nextLine();
                if (request.equals("exit")) {
                    break;
                }
                //2. 把这个读取的内容构造成请求，发送给服务器
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.println(request);
                printWriter.flush();
                //3. 从服务器读取响应并且解析
                Scanner responseScanner = new Scanner(inputStream);
                String response = responseScanner.nextLine();
                //4. 把结果显示到界面上
                String log = String.format("req:%s; resp: %s", request, response);
                System.out.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        TcpEchoClient client = new TcpEchoClient("127.0.0.1", 8000);
        client.start();
    }
}
