import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class ReqresinTests {

    @Test
    void userCreate() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));
    }

    @Test
    void checkList() {
        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2),
                        "data.email", is("janet.weaver@reqres.in"),
                        "data.first_name", is("Janet"),
                        "data.last_name", is("Weaver"),
                        "data.avatar", is("https://reqres.in/img/faces/2-image.jpg"),
                        "support.url", is("https://reqres.in/#support-heading"),
                        "support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    void checkUser() {
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("page", is(1),
                        "per_page", is(6),
                        "total", is(12),
                        "total_pages", is(2));
    }

    @Test
    void registrationUnsuccesful() {
        given()
                .contentType(ContentType.JSON)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void negativeDeleteUsers() {
        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }
}