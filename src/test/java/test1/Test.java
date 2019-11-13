package test1;

import de.interaapps.httprequest.AsyncHttpRequest;
import de.interaapps.httprequest.HttpRequest;

public class Test {


    public static void main(String[] args){

        HttpRequest.post("http://localhost:8000/example_server.php")
                .addParameter("asfdasdf", "sdfsadfasfd")
                .setHeader("hilo","wol")
                .printOutResult();
    }

}