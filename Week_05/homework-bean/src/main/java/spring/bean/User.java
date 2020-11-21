package spring.bean;

public class User {

    private Integer userId;

    private String username;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        System.out.println("设置userId："+ userId);
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        System.out.println("设置username："+ username);
        this.username = username;
    }

    public User(){
        System.out.println("User 被实例化");
    }

    public static User createUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("zcs");
        return user;
    }

    public void start(){
        System.out.println("自定义初始化的方法....");
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
