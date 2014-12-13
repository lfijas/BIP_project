package model;

public class Greeting {

    private final String username;
    private final String content;

    public Greeting(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }
}
