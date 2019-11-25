package cn.wuyiz.hoyoo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //1.定义私有静态数据源对象
    private static DataSource ds;

    //2.创建连接池对象
    static {
        //加载配置文件中的数据
        InputStream is = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        //创建配置文件对象
        Properties pro = new Properties();
        try {
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回创建的数据源对象
     *
     * @return
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 返回定义的得到的连接方法
     *
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 关闭连接
     *
     * @param conn
     * @param stmt
     * @param rs
     * @throws SQLException
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * 重载关闭方法
     *
     * @param conn
     * @param stmt
     * @throws SQLException
     */
    public static void close(Connection conn, Statement stmt) throws SQLException {
        close(conn, stmt, null);
    }
}
