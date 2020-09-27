package de.interaapps.httprequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpURLConnection httpURLConnection;
    private boolean doCache = false;
    private String url;
    private RequestMethods requestMethod;
    private String body = "";
    private Map<String, Object> parameters;
    private Map<String, String> headers = new HashMap<>();
    private boolean doAsynchron = false;
    private OnAsyncLoaded onAsyncLoaded;

    public HttpRequest(String url, RequestMethods requestMethod){
        this.url = url;
        this.requestMethod = requestMethod;
        parameters = new HashMap<>();
    }

    public HttpRequest addParameter(String key, Object value){
        parameters.put(key, value);
        return this;
    }

    public HttpResponse send(){
        HttpResponse httpResponse = new HttpResponse();
        StringBuilder parameterString = null;
        if (parameters.size() > 0) {
            parameterString = new StringBuilder();

            String separator = "";
            for (String key : parameters.keySet()) {
                try {
                    parameterString.append(separator).append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode((String) parameters.get(key), "UTF-8"));
                    separator = "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            body = parameterString.toString();

        }

        if (parameterString != null && requestMethod == RequestMethods.GET) {
            url += "?"+parameterString;
        }

        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            httpURLConnection.setRequestMethod(requestMethod.name());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        httpURLConnection.setDoInput(true);

        httpURLConnection.setUseCaches(doCache);

        httpURLConnection.setRequestProperty("Content-Language", "en-US");

        if (parameterString != null)
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        headers.forEach((key, val)->{
            httpURLConnection.setRequestProperty(key, val);
        });

        try {
            httpURLConnection.setRequestProperty("Content-Length", Integer.toString(body.getBytes().length));
            httpURLConnection.setDoOutput(true);
            httpURLConnection.getOutputStream().write(body.getBytes("UTF-8"));
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder response = new StringBuilder();

        Runnable runnable = () -> {
                try{

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = bufferedReader.readLine()) != null)
                        response.append(line).append(System.getProperty("line.separator"));

                    bufferedReader.close();

                    httpResponse.setCode(httpURLConnection.getResponseCode());
                    httpResponse.setData(response.toString());

                    if (doAsynchron)
                        onAsyncLoaded.done(httpResponse);

                } catch (IOException e) { e.printStackTrace(); }
        };

        if (doAsynchron)
            new Thread(runnable).start();
        else
            runnable.run();


        return httpResponse;
    }

    public boolean isDoCache() {
        return doCache;
    }

    public void printOutResult(){
        System.out.println(send().getData());
    }

    public HttpRequest setRequestMethods(RequestMethods requestMethods) {
        this.requestMethod = requestMethods;
        return this;
    }

    public String toString() {
        return send().getData();
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public HttpRequest setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getBody() {
        return body;
    }

    public HttpRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    public HttpURLConnection getConnection() {
        return httpURLConnection;
    }



    public HttpRequest setDoAsynchron(boolean doAsynchron) {
        this.doAsynchron = doAsynchron;
        return this;
    }

    public HttpRequest setOnAsyncLoaded(OnAsyncLoaded onAsyncLoaded) {
        this.onAsyncLoaded = onAsyncLoaded;
        return this;
    }

    public HttpRequest setHeader(String key, String value){
        headers.put(key, value);
        return this;
    }


    public interface OnAsyncLoaded {
        void done(HttpResponse response);
    }



    public static HttpRequest get(String url){
        return new HttpRequest(url, RequestMethods.GET);
    }

    public static HttpRequest post(String url){
        return new HttpRequest(url, RequestMethods.POST);
    }

    public static HttpRequest put(String url){
        return new HttpRequest(url, RequestMethods.PUT);
    }

    public static HttpRequest options(String url){
        return new HttpRequest(url, RequestMethods.OPTIONS);
    }

    public static HttpRequest trace(String url){
        return new HttpRequest(url, RequestMethods.TRACE);
    }

    public static HttpRequest connect(String url){
        return new HttpRequest(url, RequestMethods.CONNECT);
    }

    public static HttpRequest delete(String url){
        return new HttpRequest(url, RequestMethods.DELETE);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
