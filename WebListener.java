//光创建这个类还不够，还得让tomcat识别这个类，通过@WebListener注解来进行描述
@WebListener
public class MyListener implements ServletContextListener {
    // ServletContext初始化完毕后,会执行这个方法
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext 初始化");
        // 获取一下 ServletContext对象,通过方法的参数获取到的
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("message", "初始化的消息");
    }

    // ServletContext销毁之前，会执行这个方法
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
