class Message {
    public String from;
    public String to;
    public String message;
}

@WebServlet("/message")
public class messageLoveWall extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    // 这个可以暂时理解为数据库，暂时当作数据库来看待
    //private List<Message> list = new ArrayList<>();
    // 这个GET就是刷新页面的时候，或者打开页面的时候，客户端向服务器请求数据，也就是body里面的内容，这个是json的形式

    //这个是保存文件的路径
    private String filePath = "d:/message.txt";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        List<Message> messageList = null;
        try {
            messageList = load();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        objectMapper.writeValue(resp.getWriter(), messageList);
    }

    private List<Message> load() throws SQLException {
        System.out.println("从数据库读取数据");
        List<Message> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from message";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Message message = new Message();
                message.from = resultSet.getString("from");
                message.to = resultSet.getString("to");
                message.message = resultSet.getString("message");
                list.add(message);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, resultSet);
        }
        return list;
    }

    // 这个就是提交的时候，把数据从json转换成对象，然后存储在list里面
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Message message = objectMapper.readValue(req.getInputStream(), Message.class);
        // 在这里要进行一个 "写文件" 操作
        try {
            save(message);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        resp.setContentType("application/json; charset=utf-8");
        resp.getWriter().write("\"OK\": 1");
    }

    private void save(Message message) throws SQLException {
        System.out.println("向数据库中写入数据");
        // 1. 先和数据库建立连接
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into message values(?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, message.from);
            statement.setString(2, message.to);
            statement.setString(3, message.message);
            int ret = statement.executeUpdate();
            if (ret == 1) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失败");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBUtil.close(connection, statement, null);
        }
    }
}
