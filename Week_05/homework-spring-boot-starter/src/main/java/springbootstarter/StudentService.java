package springbootstarter;

public class StudentService {

    StudentProperties studentProperties;

    public void setStudentProperties(StudentProperties studentProperties) {
        this.studentProperties = studentProperties;
    }

    public String sayName(){
        return studentProperties.getName();
    }

}
