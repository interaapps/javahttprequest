package test1;

import de.interaapps.httprequest.HttpRequest;
import de.interaapps.httprequest.HttpResponse;
import de.interaapps.httprequest.RequestMethods;

public class Test {


    public static void main(String[] args){

        System.out.println("HI1");

        HttpRequest httpRequest = new HttpRequest("http://localhost:8000/example_server.php", RequestMethods.POST);
        httpRequest.setDoAsynchron(true);
        httpRequest.addParameter("hello", "world");

        httpRequest.setOnAsyncLoaded((response)->{
            System.out.println(response);
        });

        HttpResponse response = httpRequest.send();

        System.out.println(response);

        System.out.println("HI2");

    }

}