package network.request;

public class GetAllPlayersNameWithStatusRequest extends AuthRequest {

    public GetAllPlayersNameWithStatusRequest(){}

    public GetAllPlayersNameWithStatusRequest(String token) {
        super("GetAllPlayersNameWithStatus", token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"," +
                "\"token\" : " + "\"" + getToken()+ "\"" +
                "}";
    }
}
