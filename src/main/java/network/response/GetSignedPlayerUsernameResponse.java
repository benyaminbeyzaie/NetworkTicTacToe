package network.response;

public class GetSignedPlayerUsernameResponse extends Response {
    private String username;

    public GetSignedPlayerUsernameResponse(){}

    public GetSignedPlayerUsernameResponse(String username){
        super("Username");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"username\" : " + "\"" +  getUsername() + "\""  +
                '}';
    }
}
