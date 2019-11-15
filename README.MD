# Java Http client - Testing

### Simple post request
```java
String out = HttpRequest.get("http://localhost:8000/example_server.php")
        .send().getData();

System.out.println(out);
```

### Async
```java
AsyncHttpRequest.get("http://localhost:8000/example_server.php")
        .addParameter("hello", "world")
        .setOnAsyncLoaded(response->{
            System.out.println(response);
        }).send();
```

### Multible ways of doing
```java

new HttpRequest("https://interaapps.de", RequestMethods.GET).send().getData();

// == (USE THIS ONE!)
HttpRequest.get("https://interaapps.de").send().getData(); // RETURNS DIRECTLY STRING

// ==
HttpRequest.get("https://interaapps.de").send()  .toString();

// ==
HttpRequest.get("https://interaapps.de")  .toString();

// == (Not recommended)
import static de.interaapps.httprequest.HttpRequest.*;
get("https://interaapps.de")  .toString();


// NOTE: On SysOut you wont need toString()
````


## Implementing

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.interaapps</groupId>
    <artifactId>javahttprequest</artifactId>
    <version>0.1</version> <!-- NEWEST AT https://github.com/interaapps/javahttprequest/releases -->
</dependency>
```

### Gradle
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
            implementation 'com.github.interaapps:javahttprequest:0.1' <!-- NEWEST AT https://github.com/interaapps/javahttprequest/releases -->
    }

```

