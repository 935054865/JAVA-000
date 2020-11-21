package homework.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class JDBCDemoController {

    @Autowired
    private JDBCDemoService jdbcDemoService;

    @GetMapping("/jdbc/demo/get")
    public Object getDemo(Integer id) throws SQLException {
        return jdbcDemoService.findDemoById(id);
    }
}
