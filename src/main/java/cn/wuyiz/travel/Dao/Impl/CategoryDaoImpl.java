package cn.wuyiz.travel.Dao.Impl;

import cn.wuyiz.travel.Dao.CategoryDao;
import cn.wuyiz.travel.bean.Category;
import cn.wuyiz.travel.utils.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    //定义sql语句对象
    String sql = null;
    //定义连接池模板
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        sql = "select * from tab_category ";
        return template.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
