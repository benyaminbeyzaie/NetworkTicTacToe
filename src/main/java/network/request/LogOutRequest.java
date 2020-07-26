package network.request;

public class LogOutRequest extends AuthRequest {
    public LogOutRequest(){}

    public LogOutRequest(String token) {
        super("LogOutRequest", token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"," +
                "\"token\" : " + "\"" + getToken()+ "\"" +
                "}";
    }
}
