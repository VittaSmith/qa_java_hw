package RestAssured.Postman_Echo;

import io.restassured.*;//baseURI
import static io.restassured.RestAssured.*;//methods of restAssured
import static org.hamcrest.Matchers.*;//equalTo,

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RequestMethodsTests {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetRequest(){
        given()
                .queryParam("foo1","bar1").queryParam("foo2","bar2")// because after "?", pathParam - before "?"
        .when()
                .get("/get")
        .then()
                .body("args.foo1", equalTo("bar1")).body("args.foo2", equalTo("bar2"))
                .statusCode(200);//.statusCade(HttpStatus.OK.value()); <-the same

    }

    @Test
    public void testPostRowText(){
        String body = "This is expected to be sent back as part of response body.";
        given()
                .log().body()//.all() .headers() .params() .uri()
                .contentType(ContentType.JSON) //"application/json; charset=UTF-8"
                .body(body)

        .when()
                .post("/post")
        .then()
                .log().body()//.all()
                .body("data", equalTo(body))
                .statusCode(200);
    }

    @Test
    public void testPostFormData(){
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")//ContentType.URLENC-"text/javascript"
                .accept(ContentType.JSON)
                .formParam("foo1","bar1").formParam("foo2","bar2")
        .when()
                .post("https://postman-echo.com/post")
        .then()
                .body("form.foo1", equalTo("bar1"))
                .body("form.foo2", equalTo("bar2"))
                .statusCode(200);
    }

    @Test
    public void testPutRequest(){
        String body = "This is expected to be sent back as part of response body.";
        given()
                .log().body()
                .contentType(ContentType.TEXT)
                .accept(ContentType.JSON)
                .body(body)
        .when()
                .put("/put")
        .then()
                .log().body()
                .body("data",equalTo(body))
                .statusCode(200);
    }

    @Test
    public void testPatchRequest(){
        String body = "This is expected to be sent back as part of response body.";
        given()
                .log().body()
                .contentType(ContentType.TEXT)
                .accept(ContentType.JSON)
                .body(body)
        .when()
                .patch("/patch")
        .then()
                .log().body()
                .body("data", equalTo(body))
                .statusCode(200);
    }

    @Test
    public void testDeleteRequest(){
        String body = "This is expected to be sent back as part of response body.";
        given()
                .log().method()
                .contentType(ContentType.TEXT)
                .accept(ContentType.JSON)
                .body(body)
        .when()
                .delete("/delete")
        .then()
                .body("data", equalTo(body))
                .statusCode(200);
    }
}
