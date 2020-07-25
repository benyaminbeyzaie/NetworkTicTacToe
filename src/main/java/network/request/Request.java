package network.request;

public abstract class Request {
    private String type;

    public Request() {}
    public Request(String type) {
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
