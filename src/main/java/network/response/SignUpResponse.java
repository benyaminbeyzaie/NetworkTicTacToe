package network.response;

public class SignUpResponse extends Response {
    private String status;
    public SignUpResponse(){}

    public SignUpResponse(String status){
        super("SignUp");
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
