package util;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/4-15:06
 * Version: 1.0
 **/

public class DBUtil {
    private static volatile DataSource DATA_SOURSE;

    private DBUtil() {
    }

    private static String getUrl() throws URISyntaxException {
        String dbName = "xiangmu1.db";

        //getClassLoader  获取类加载器
        URL url = DBUtil.class.getClassLoader().getResource(".");
        return "jdbc:sqlite://" + new File(url.toURI()).getParent() + File.separator + dbName;

    }

    //获取数据源：
    public static DataSource getDataSourse() throws URISyntaxException {
        if (DATA_SOURSE == null) {
            synchronized (DBUtil.class) {
                if (DATA_SOURSE == null) {

                    //mySql日期格式：yyyy-MM-dd HH:mm:ss
                    //Sqlite日期格式：yyyy-MM-dd HH:mm:ss:ms
                    SQLiteConfig config = new SQLiteConfig();
                    config.setDateStringFormat(Util.DATA_PATTERN);
                    DATA_SOURSE = new SQLiteDataSource(config);
                    ((SQLiteDataSource) DATA_SOURSE).setUrl(getUrl());
                }
            }
        }
        return DATA_SOURSE;
    }

    /**
     * 1.class.forname("驱动类全名")加载驱动，DriverManger.getConnecttion()
     * 2.DataSource
     */
    //获取数据库连接:
    public static Connection getConnection() {
        try {
            return getDataSourse().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据库连接获取失败");
        }
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {

                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("释放数据库资源错误");
        }
    }

    public static void close(Connection connection, Statement statement) {
        close(connection, statement, null);
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
    }
}
