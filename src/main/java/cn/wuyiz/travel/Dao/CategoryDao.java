package cn.wuyiz.travel.Dao;

import cn.wuyiz.travel.bean.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有类别
     *
     * @return
     */
    List<Category> findAll();
}
