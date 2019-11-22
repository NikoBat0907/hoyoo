package cn.wuyiz.travel.Service.Impl;

import cn.wuyiz.travel.Dao.Impl.UserDaoImpl;
import cn.wuyiz.travel.Dao.UserDao;
import cn.wuyiz.travel.Service.UserService;
import cn.wuyiz.travel.bean.User;
import cn.wuyiz.travel.utils.MailUtils;
import cn.wuyiz.travel.utils.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        if (u != null) {
            return false;
        }
        //2.保存用户信息
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);
        //3.发送激活邮件，邮件正文
        String content = "欢迎注册旅游网账号<br><a href='http://localhost/travel/activeUserServlet?code=" + user.getCode() + "'>点击激活【旅游网激活】</a>";
        MailUtils.sendMail(user.getEmail(), content, "旅游网激活");
        return true;
    }

    @Override
    public boolean active(User user) {
        return userDao.updateStatus(user);
    }
}
