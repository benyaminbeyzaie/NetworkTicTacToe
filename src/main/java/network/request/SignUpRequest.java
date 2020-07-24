package network.request;

public class SignUpRequest extends Request {
    private String username;
    private String password;

    public SignUpRequest(){
        super();
    }
    public SignUpRequest(String type, String username, String password) {
        super(type);
        this.username = username;
        this.password = password;
        setType("SignUp");
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
