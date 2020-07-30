package network.response;

import network.request.GetAllPlayerRequest;

public class GetAllPlayerResponse extends Response {
    private String allPlayersByDash;

    public GetAllPlayerResponse(){}

    public GetAllPlayerResponse(String allPlayersByDash){
        super("AllPlayer");
        this.allPlayersByDash = allPlayersByDash;
    }

    public String getAllPlayersByDash() {
        return allPlayersByDash;
    }

    public void setAllPlayersByDash(String allPlayersByDash) {
        this.allPlayersByDash = allPlayersByDash;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ", \"allPlayersByDash\" : " + "\"" +  getAllPlayersByDash() + "\""  +
                '}';
    }
}
