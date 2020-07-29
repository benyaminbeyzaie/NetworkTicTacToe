package network.request;


public class AuthRequest extends Request {
    String token;

    public AuthRequest(String type, String token){
        super(type);
        this.token = token;
    }

    public AuthRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
