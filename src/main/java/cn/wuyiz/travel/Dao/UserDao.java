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
    boolean updateStatus(User user);
}
