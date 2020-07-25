package network.request;

public class LoginRequest extends Request{
    private String username;
    private String password;

    public LoginRequest() {}

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
        setType("Login");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"username\" : " + "\"" +  username + "\""  +
                ", \"password\" : " + "\"" + password + "\"" +
                '}';
    }
}
