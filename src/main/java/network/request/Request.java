package network.request;

public abstract class Request {
    private String type;
    public abstract String toString();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
