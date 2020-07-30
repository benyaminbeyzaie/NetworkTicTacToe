package network.request;

public class GetAllPlayerRequest extends AuthRequest {
    public GetAllPlayerRequest(){}

    public GetAllPlayerRequest(String token){
        super("AllPlayer", token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"token\" : " + "\"" +  getToken() + "\""  +
                '}';
    }
}
