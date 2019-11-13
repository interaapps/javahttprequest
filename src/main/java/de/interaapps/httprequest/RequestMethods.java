package de.interaapps.httprequest;

public enum RequestMethods {

    GET("GET"),
    POST("POST"),
    OPTION("OPTION"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    TRACE("TRACE");

    private String name;

    RequestMethods(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
