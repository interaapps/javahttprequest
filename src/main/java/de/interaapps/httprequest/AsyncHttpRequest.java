package de.interaapps.httprequest;

public class AsyncHttpRequest {

    public static HttpRequest get(String url){
        return new HttpRequest(url, RequestMethods.GET).setDoAsynchron(true);
    }

    public static HttpRequest post(String url){
        return new HttpRequest(url, RequestMethods.POST).setDoAsynchron(true);
    }

    public static HttpRequest put(String url){
        return new HttpRequest(url, RequestMethods.PUT).setDoAsynchron(true);
    }

    public static HttpRequest delete(String url){
        return new HttpRequest(url, RequestMethods.DELETE).setDoAsynchron(true);
    }

    public static HttpRequest options(String url){
        return new HttpRequest(url, RequestMethods.OPTIONS).setDoAsynchron(true);
    }

    public static HttpRequest trace(String url){
        return new HttpRequest(url, RequestMethods.TRACE).setDoAsynchron(true);
    }

    public static HttpRequest connect(String url){
        return new HttpRequest(url, RequestMethods.CONNECT).setDoAsynchron(true);
    }

}