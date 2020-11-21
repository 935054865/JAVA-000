package springbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "custom.boot.starter.student.enabled", havingValue = "true") //检查配置是否开启
@EnableConfigurationProperties(StudentProperties.class)
public class AutoConfiguration {

    @Autowired
    StudentProperties studentProperties;

    @Bean
    public StudentService studentService(){
        StudentService studentService = new StudentService();
        studentService.setStudentProperties(studentProperties);
        return studentService;
    }
}
