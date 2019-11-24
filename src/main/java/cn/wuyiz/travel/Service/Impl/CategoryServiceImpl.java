package cn.wuyiz.travel.Service.Impl;

import cn.wuyiz.travel.Dao.CategoryDao;
import cn.wuyiz.travel.Dao.Impl.CategoryDaoImpl;
import cn.wuyiz.travel.Service.CategoryService;
import cn.wuyiz.travel.bean.Category;
import cn.wuyiz.travel.utils.JedisUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    //Jedis
    private Jedis jedis = JedisUtils.getJedis();

    @Override
    public List<Category> findAll() {
        Set<String> categorySet = jedis.zrange("category", 0, -1);
        List<Category> categoryList = null;
        //判断缓存
        if (categorySet == null || categorySet.isEmpty()) {
            System.out.println("MySQL查询");
            //第一次查询，没有缓存，查询数据库
            categoryList = categoryDao.findAll();
            for (int i = 0; i < categoryList.size(); i++) {
                //将数据库查询到的信息缓存到Redis中
                jedis.zadd("category", categoryList.get(i).getCid(), categoryList.get(i).getCname());
            }
        } else {
            //redis有缓存数据
            categoryList = new ArrayList<>();
            for (String name : categorySet) {
                //封装数据，并存入list集合中
                Category category = new Category();
                category.setCname(name);
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
