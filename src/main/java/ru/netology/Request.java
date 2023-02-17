package ru.netology;

public class Request {
    //метод запроса, потому что на разные методы можно назначить один и тот же хендлер;
    private final String method;
//заголовки запроса;
    private final String header;

//тело запроса, если есть.
    private String body;

    public Request (String method, String header, String body) {
        this.method = method;
        this.header = header;
        this.body = body;
    }
    public Request (String method, String header) {
        this.method = method;
        this.header = header;

    }

    public String getMethod() {
        return method;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
