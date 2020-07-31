package network.response;

public class NewGameResponse extends Response {
    private int result;
    public NewGameResponse(){}

    public NewGameResponse(int result){
        super("NewGame");
        this.result = result;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\" : " + "\"" + getType()+ "\"" +
                ",\"result\" : "  + getResult() +
                '}';
    }
}
