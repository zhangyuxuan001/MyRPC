package version2.client;

import version2.common.User;
import version2.service.UserService;
import version2.common.Blog;
import version2.service.BlogService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService userService = clientProxy.getProxy(UserService.class);
        BlogService blogService = clientProxy.getProxy(BlogService.class);

        User userById = userService.getUserByUserId(10);
        System.out.println("服务器端查询到了user:" +  userById);

        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.insertUserId(user);
        System.out.println("成功插入数据：" + integer);

        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}