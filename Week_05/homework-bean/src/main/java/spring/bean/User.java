package spring.bean;

public class User {

    private Integer userId;

    private String username;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static User createUser() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("zcs");
        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                '}';
    }
}
