package cn.wuyiz.hoyoo.Dao;

import cn.wuyiz.hoyoo.bean.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有类别
     *
     * @return
     */
    List<Category> findAll();
}
