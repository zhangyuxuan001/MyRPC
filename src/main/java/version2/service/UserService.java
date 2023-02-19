package version2.service;

import version2.common.User;

public interface UserService {
    //服务端通过该接口获取数据
    User getUserByUserId(Integer id);
    // 给这个服务增加一个功能
    Integer insertUserId(User user);
}
