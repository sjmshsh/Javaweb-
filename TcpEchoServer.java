public class TcpEchoServer {
    private ServerSocket listenSocket = null;

    public TcpEchoServer(int port) throws IOException {
        listenSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        System.out.println("服务器启动!");
        while (true) {
            //UDP 的服务器进入主循环，就直接尝试 receive 读取请求
            //但是tcp是有连接的，先需要做的是，建立好连接
            // 当服务器运行的时候，当前是否有客户端建立连接，不确定~
            //如果客户端没有建立连接，accept就会阻塞等待
            //如果有客户端建立连接了，此时的accept就会返回一个socket对象
            //进一步的服务器和客户端之间的交互，就交给clientSocket来完成了~
            Socket clientSocket = listenSocket.accept();
            processConnection(clientSocket);
        }
    }

    private void processConnection(Socket clientSocket) throws IOException {
        // 处理一个连接，在这个连接中可能会涉及客户端和服务器之间的多次交互
        String log = String.format("[%s:%d] 客户端上线!", clientSocket.getInetAddress(), clientSocket.getPort());
        System.out.println(log);
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()){
            while (true) {
                //1. 去读请求并解析
                // 可以直接通过InputStream.read()把数据读到一个byte[]，然后再转换成一个String，但是比较麻烦
                // 我们还可以借助 Scanner 来完成这个工作
                // InputStream就是读取请求
                // OutputStream就是发送请求，写请求
                Scanner scanner = new Scanner(inputStream);
                if (!scanner.hasNextLine()) {
                    log = String.format("[%s:%d] 客户端下线!", clientSocket.getInetAddress().toString(), clientSocket.getPort());
                    System.out.println(log);
                    break; // 如果没有数据了话就结束循环
                }
                String request = scanner.nextLine();
                //2. 根据请求计算响应
                String response = process(request);
                //3. 把响应写回给客户端
                PrintWriter writer = new PrintWriter(outputStream);
                writer.println(response);
                writer.flush();
                log = String.format("[%s:%d] req: %s; resp: %s", clientSocket.getInetAddress().toString(), clientSocket.getPort(), request, response);
                System.out.println(log);
                // 记得要进行关闭
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 当前的clientSocket 生命周期部署跟随整个程序，而是和连接有关
            // 因此就需要每个连接结束之后都需要进行关闭，否则随着连接的增多，这个socket文件就可能出现资源泄漏的情况
            clientSocket.close();
        }
    }

    private String process(String request) {
        return request;
    }

    public static void main(String[] args) throws IOException {
        int port = 8000;
        TcpEchoServer server = new TcpEchoServer(port);
        server.start();
    }
}
