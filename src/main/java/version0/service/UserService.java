package version0.service;

import version0.common.User;

public interface UserService {
    //服务端通过该接口获取数据
    User getUserByUserId(Integer id);

//    Integer insertUserId(User user);
}
