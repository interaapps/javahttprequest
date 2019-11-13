# Java Http client - Testing

### Simple post request
```java
HttpRequest.get("http://localhost:8000/example_server.php")
        .addParameter("hello", "world")
        .setOnAsyncLoaded(response->{
            System.out.println(response);
        });
```

### Async
```java
AsyncHttpRequest.get("http://localhost:8000/example_server.php")
        .addParameter("hello", "world")
        .setOnAsyncLoaded(response->{
            System.out.println(response);
        });
```

### Multible ways of doing
```java

new HttpRequest("https://interaapps.de", RequestMethods.GET).send().getResponseText();

// == (USE THIS ONE!)
HttpRequest.get("https://interaapps.de").send().getResponseText(); // RETURNS DIRECTLY STRING

// ==
HttpRequest.get("https://interaapps.de").send()  .toString();

// ==
HttpRequest.get("https://interaapps.de")  .toString();

// == (Not recommended)
import static de.interaapps.httprequest.HttpRequest.*;
get("https://interaapps.de")  .toString();


// NOTE: On SysOut you wont need toString()
````