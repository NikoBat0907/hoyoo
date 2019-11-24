package cn.wuyiz.travel.Dao.Impl;

import cn.wuyiz.travel.Dao.UserDao;
import cn.wuyiz.travel.bean.User;
import cn.wuyiz.travel.utils.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    //定义sql语句对象
    private String sql = null;
    //定义连接池模板
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        //定义sql查询语句
        sql = "select * from tab_user where username = ?";
        //查询结果并返回user对象
        //使用Spring的JdbcTemplate查询数据库，获取List结果列表，数据库表字段和实体类自动对应，可以使用BeanPropertyRowMapper
        //自动绑定，需要列名称和Java实体类名字一致，如：属性名 “userName” 可以匹配数据库中的列字段 "USERNAME" 或 “user_name”。这样，我们就不需要一个个手动绑定了，大大提高了开发效率
        User user = null;
        try {
            //查询失败时候会报异常，而不是返回null
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (DataAccessException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return user;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        //定义sql查询语句
        sql = "select * from tab_user where username = ? and password = ?";
        //查询结果并返回user对象
        //使用Spring的JdbcTemplate查询数据库，获取List结果列表，数据库表字段和实体类自动对应，可以使用BeanPropertyRowMapper
        //自动绑定，需要列名称和Java实体类名字一致，如：属性名 “userName” 可以匹配数据库中的列字段 "USERNAME" 或 “user_name”。这样，我们就不需要一个个手动绑定了，大大提高了开发效率
        User user = null;
        try {
            //查询失败时候会报异常，而不是返回null
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        } catch (DataAccessException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
        }
        return user;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        //插入数据
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public void updateStatus(User user) {
        sql = "update tab_user set status = 'Y' where code = ?";
        template.update(sql, user.getCode());
    }
}
