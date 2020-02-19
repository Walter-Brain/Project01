package task;

import util.DBUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/4-16:35
 * Version: 1.0
 **/

public class DBInit {
    public static void init() {
        try {
            InputStream is = DBInit.class.getClassLoader()
                    .getResourceAsStream("init.sql");
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                //忽略--注释代码
                int index = line.indexOf("--");
                if (index != -1) {
                    line.substring(0, index);
                }
                sb.append(line);
            }
            String[] sqls = sb.toString().split(";");
            Connection connection = null;
            Statement statement = null;
            try {
                for (String s : sqls) {
                    connection = DBUtil.getConnection();
                    statement = connection.createStatement();
                    statement.executeUpdate(s);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtil.close(connection, statement);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库初始化任务错误");
        }
    }

    public static void main(String[] args) throws Exception {
        init();
    }
}
