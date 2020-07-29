package network.response;

public class LoginResponse extends Response {
    private String status;

    public LoginResponse(){}

    public LoginResponse(String status){
        super("Login");
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ",\"status\" : " + "\"" + getStatus()+ "\"" +
                '}';
    }
}
