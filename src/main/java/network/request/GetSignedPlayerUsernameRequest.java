package network.request;

public class GetSignedPlayerUsernameRequest extends AuthRequest {

    public GetSignedPlayerUsernameRequest() {}

    public GetSignedPlayerUsernameRequest(String token){
        super("Username" , token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"token\" : " + "\"" +  getToken() + "\""  +
                '}';
    }
}
