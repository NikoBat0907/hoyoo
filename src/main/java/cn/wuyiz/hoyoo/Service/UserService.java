package cn.wuyiz.hoyoo.Service;

import cn.wuyiz.hoyoo.bean.User;

public interface UserService {
    /**
     * 登录业务
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

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

}
