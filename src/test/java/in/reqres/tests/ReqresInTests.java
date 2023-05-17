package in.reqres.tests;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    @Test
    void successfulRegistrationTest() {
        String body = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"))
                .body("id", is(4));
    }

    @Test
    void unSuccessfulRegistrationWithMissingPasswordTest() {
        String body = "{ \"email\": \"sydney@fife\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void unSuccessfulRegistrationWithMissingEmailTest() {
        String body = "{ \"password\": \"pistol\" }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void checkSingleUserJsonSchemeTest() {

        given()
                .log().uri()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemes/single-user-scheme.json"));
    }

    @Test
    void checkSingleUserNotFoundTest() {
        String body = "{ }";

        given()
                .log().uri()
                .body(body)
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }

    @Test
    void createUserTest() {
            String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

            given()
                    .log().uri()
                    .body(body)
                    .contentType(JSON)
                    .when()
                    .post("https://reqres.in/api/users")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(201)
                    .body("name", is("morpheus"))
                    .body("job", is("leader"));
        }

}
