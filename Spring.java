@RestController
public class UserController {
    /*
    * 添加用户
    * */
    @GetMapping("/add") //路由
    public String addUser(String name) { //参数对应的就是前端传递过来的参数
        // 1. 得到参数[spring boot此步骤可以忽略]

        // 2. 访问数据库，组装数据
        System.out.println("添加用户成功: " + name);
        // 3. 数据返回
        return "添加用户" + name;
    }

    @GetMapping("/get")
    public String getUser(Integer id) {
        String name = "李四";
        // 1.操作数据库，组装数据[伪代码]
        if (id == 1) {
            name = "张三";
        }
        // 2.返回结果给前端
        return "获得用户: " + name;
    }
}
