package network.response;

public class Response {
    private String type;

    public Response() {}
    public Response(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + type +
                '}';
    }
}
