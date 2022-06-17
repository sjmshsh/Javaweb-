package JDBC;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author 写你的名字
 * @Date 2022/4/11 12:01 （可以根据需要修改）
 * @Version 1.0 （版本号）
 */
public class jdbc {

    public static void main(String[] args) throws SQLException {
        // 1. 创建DataSource对象(DataSource 对象生命周期应该是要跟随整个程序)
        DataSource dataSource = new MysqlDataSource();
        // 针对dataSource 进行一些配置，以便于后面能够顺利的访问数据库服务器
        // 主要配置这三方面 ,URL, User, Password。我们要对DataSource进行向下转型成MysqlDataSource
        ((MysqlDataSource) dataSource).setURL("jdbc:mysql://127.0.0.1:3306/sakura1?characterEncoding=utf-8&useSSL=true"); //把父类引用转换为子类实例
        ((MysqlDataSource) dataSource).setUser("root");
        ((MysqlDataSource) dataSource).setPassword("Bao1417165487");
        // 2. 和数据库建立连接,建立连接好了之后就可以进行后续的数据传输了
        // 建立连接(不是链接)的的意义是为了检验当前的网路通信是否正常
        // 如果不正常就会抛出SQLException异常
        // connection 对象生命周期应该是比较短的，每一个请求创建一个新的connection
        Connection connection = dataSource.getConnection();

        // 3. 拼装SQL语句，用到PrepareStatement
        // 先以插入数据为例
        // 当前实例中要插入的数据内容都是写死的，其实也可以让程序在运行的时候获取到
        int id = 1;
        String name = "曹操";
        int classId = 10;
        String sql = "insert into student values(?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        // 1, 2, 3相当于?的下标(从1开始算)
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setInt(3, classId);
        System.out.println("statement: " + statement);

        //4. 拼装完毕之后，可以执行SQL了
        // insert delete update都使用 executeUpdata方法来执行
        // select 就使用 executeQuery来执行
        // 返回值表示此次操作修改了多少行
        int ret = statement.executeUpdate();
        System.out.println("ret: " + ret);

        //5 . 执行完毕之后，关闭释放相关资源
        // 一定是后创建的被先释放
        statement.close();
        connection.close();
    }
}
