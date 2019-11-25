package cn.wuyiz.hoyoo.Service.Impl;

import cn.wuyiz.hoyoo.Dao.CategoryDao;
import cn.wuyiz.hoyoo.Dao.Impl.CategoryDaoImpl;
import cn.wuyiz.hoyoo.Service.CategoryService;
import cn.wuyiz.hoyoo.bean.Category;
import cn.wuyiz.hoyoo.utils.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    //Jedis
    private Jedis jedis = JedisUtils.getJedis();

    @Override
    public List<Category> findAll() {
        //查询sortedSet中的分数和name
        Set<Tuple> tuples = jedis.zrangeWithScores("category", 0, -1);
        List<Category> categoryList = null;
        //判断缓存
        if (tuples == null || tuples.size() == 0) {
            System.out.println("MySQL查询");
            //第一次查询，没有缓存，查询数据库
            categoryList = categoryDao.findAll();
            for (int i = 0; i < categoryList.size(); i++) {
                //将数据库查询到的信息缓存到Redis中
                jedis.zadd("category", categoryList.get(i).getCid(), categoryList.get(i).getCname());
            }
        } else {
            //redis有缓存数据
            System.out.println("Redis查询");
            categoryList = new ArrayList<>();
            for (Tuple tuple : tuples) {
                //封装数据，并存入list集合中
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                categoryList.add(category);
            }
        }
        return categoryList;
    }
}
