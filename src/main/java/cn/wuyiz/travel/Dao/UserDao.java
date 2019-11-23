package cn.wuyiz.travel.Dao;

import cn.wuyiz.travel.bean.User;

public interface UserDao {
    /**
     * 通过用户名查找用户信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 登录业务
     * 通过用户名和密码查询用户是否存在
     *
     * @param user
     * @return
     */
    User findByUsernameAndPassword(String username, String password);


    /**
     * 根据激活码查询用户
     *
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 保存用户信息
     *
     * @param user
     */
    void save(User user);

    /**
     * 更新新注册用户的账号状态为 Y
     *
     * @param user
     * @return
     */
    void updateStatus(User user);

}
