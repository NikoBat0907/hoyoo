package cn.wuyiz.travel.Service;

import cn.wuyiz.travel.bean.User;

public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    boolean regist(User user);

    /**
     * 激活新用户账号
     *
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录业务
     *
     * @param user
     * @return
     */
    User login(User user);
}
