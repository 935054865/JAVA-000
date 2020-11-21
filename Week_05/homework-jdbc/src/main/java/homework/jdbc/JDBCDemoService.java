package homework.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


@Component
public class JDBCDemoService {

    @Autowired
    private DataSource dataSource;

    /**
     * JDBC实践，根据原生JDBC查询数据
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, String> findDemoById(final Integer id) throws SQLException {

        String JDBC_URL = "jdbc:mysql://127.0.0.1:3306/test";
        String JDBC_USER = "root";
        String JDBC_PASSWORD = "123123";

        // JDBC单连接
        //Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        // Hikari连接池
        Connection conn = dataSource.getConnection();
        ResultSet rs = null;
        HashMap<Integer, String> demo = new HashMap();
        try {
            // 关闭自动提交:
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT id,  name FROM test WHERE id = ?");
            ps.setObject(1, id);
            rs = ps.executeQuery();

            // 提交事务:
            conn.commit();
            while (rs.next()) {
                demo.put(rs.getInt("id"), rs.getString("name"));
            }


            rs.close();
        } catch (SQLException e) {
            // 回滚事务:
            conn.rollback();
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return demo;
    }
}
