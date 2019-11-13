package de.interaapps.httprequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpURLConnection httpURLConnection;
    private boolean doCache = false;
    private RequestMethods requestMethod;
    private String body = "";
    private Map<String, Object> parameters;
    private boolean doAsynchron = false;
    private OnAsyncLoaded onAsyncLoaded;


    public HttpRequest(String url, RequestMethods requestMethod){
        this.requestMethod = requestMethod;
        parameters = new HashMap<>();
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpRequest addParameter(String key, Object value){
        parameters.put(key, value);
        return this;
    }

    public HttpResponse send(){
        HttpResponse httpResponse = new HttpResponse();
        try {
            httpURLConnection.setRequestMethod(requestMethod.getName());
        } catch (ProtocolException e) { e.printStackTrace(); }

        httpURLConnection.setUseCaches(doCache);

        httpURLConnection.setDoOutput(true);

        httpURLConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        httpURLConnection.setRequestProperty("Content-Language", "en-US");

        DataOutputStream wr = null;
        StringBuilder response = new StringBuilder();

        try {

            if(requestMethod == RequestMethods.GET) {

            } else {

                if (parameters.size() > 0) {
                    StringBuilder parameterString = new StringBuilder();

                    String separator = "";
                    System.out.println("Hi");
                    for (String key : parameters.keySet()) {
                        System.out.println("Hi2");
                        parameterString.append( separator+key+"="+parameters.get(key) );
                        separator = "&";
                    }

                    httpURLConnection.setRequestProperty("Content-Length", Integer.toString(parameterString.toString().getBytes().length));
                    body = parameterString.toString();
                }
            }

            httpURLConnection.getOutputStream().write(body.getBytes("UTF-8"));


            wr = new DataOutputStream(httpURLConnection.getOutputStream());
            wr.close();

            Runnable runnable = () -> {
                    try{
                        InputStream is = httpURLConnection.getInputStream();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

                        String line;
                        while ((line = rd.readLine()) != null)
                            response.append(line + System.getProperty("line.separator"));

                        rd.close();

                        httpResponse.setResponseCode(httpURLConnection.getResponseCode());
                        httpResponse.setResponseText(response.toString());

                        if (doAsynchron)
                            onAsyncLoaded.done(httpResponse);

                    } catch (IOException e) { e.printStackTrace(); }
            };

            if (doAsynchron)
                new Thread(runnable).start();
            else
                runnable.run();


        } catch (IOException e) { e.printStackTrace(); } finally {
            try {
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponse;
    }

    public boolean isDoCache() {
        return doCache;
    }

    public HttpRequest setRequestMethods(RequestMethods requestMethods) {
        this.requestMethod = requestMethods;
        return this;
    }

    public String toString() {
        return send().getResponseText();
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

    public HttpRequest setDoAsynchron(boolean doAsynchron) {
        this.doAsynchron = doAsynchron;
        return this;
    }

    public HttpRequest setOnAsyncLoaded(OnAsyncLoaded onAsyncLoaded) {
        this.onAsyncLoaded = onAsyncLoaded;
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

    public static HttpRequest option(String url){
        return new HttpRequest(url, RequestMethods.OPTION);
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

}
