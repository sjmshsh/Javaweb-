package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Util;
import dao.Blog;
import dao.BlogDao;
import dao.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 写你的名字
 * @Date 2022/6/27 21:35 （可以根据需要修改）
 * @Version 1.0 （版本号）
 */
//处理博客相关的请求
@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    // Jackson 这个库里面的核心的类
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        // 检测用户登录状态
        User user = Util.checkLoginStatus(req);
        if (user == null) {
            // 未登录的状态
            resp.setStatus(403);
            String html = "<h3>当前用户未登录</h3>";
            resp.getWriter().write(html);
            return;
        }
        String blogId = req.getParameter("blogId");
        BlogDao blogDao = new BlogDao();
        if (blogId == null) {
            List<Blog> blogs = null;
            try {
                blogs = blogDao.selectAll();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            String jsonString = objectMapper.writeValueAsString(blogs);
            resp.getWriter().write(jsonString);
        } else {
            // 参数存在则返回博客详情页
            try {
                Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
                String jsonString = objectMapper.writeValueAsString(blog);
                resp.getWriter().write(jsonString);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
