package network.request;

public class NewGameRequest extends AuthRequest {

    public NewGameRequest(){}

    public NewGameRequest(String token){
        super("NewGame", token);
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"token\" : " + "\"" +  token + "\""  +
                '}';
    }
}
