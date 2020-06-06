package test1;

import de.interaapps.httprequest.HttpRequest;

public class Test {


    public static void main(String[] args){
        System.out.println(HttpRequest.post("https://postman-echo.com/post").addParameter("afsdfdsa", "").send().getData());
    }

}