package network.request;

public class LogOutRequest extends AuthRequest {
    public LogOutRequest(){}

    public LogOutRequest(String token) {
        super("LogOut", token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"," +
                "\"token\" : " + "\"" + getToken()+ "\"" +
                "}";
    }
}
