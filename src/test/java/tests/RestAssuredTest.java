package tests;

import constants.Urls;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import model.UserModel;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class RestAssuredTest {


    @Test
    public void delayResponseTest() {
        RestAssured
                .given()
                .when()
                .get(Urls._DELAY_RESPONSE_URL)
                .then()
                .statusCode(200)
                .body("page", instanceOf(Integer.class))
                .body("per_page", equalTo(6));
    }

    @Test
    public void updateUserTest() {
        UserModel updateBody=UserModel
                .builder()
                .name("morpheus")
                .job("zion resident")
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(updateBody)
                .when()
                .patch(Urls.USER_TWO_URL)
                .then()
                .statusCode(200);
    }


    @Test
    public void deleteUserTest(){
        RestAssured
                .given()
                .when()
                .delete(Urls.USER_TWO_URL)
                .then()
                .statusCode(204);
    }


    @Test
    public void registerIsSuccessfulTest(){
        UserModel updateBody=UserModel
                .builder()
                .email("eve.holt@reqres.in")
                .password("pistol")
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(updateBody)
                .when()
                .post(Urls.REGISTER_URL)
                .then()
                .statusCode(200);
    }


    @Test
    public void registerIsUnSuccessfulTest(){
        UserModel updateBody=UserModel
                .builder()
                .email("sydney@fife")
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(updateBody)
                .when()
                .post(Urls.REGISTER_URL)
                .then()
                .statusCode(400);
    }


    @Test
    public void loginIsSuccessfulTest(){
        UserModel updateBody=UserModel
                .builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(updateBody)
                .when()
                .post(Urls.LOGIN_URL)
                .then()
                .statusCode(200);
    }


    @Test
    public void loginIsUnSuccessfulTest(){
        UserModel updateBody=UserModel
                .builder()
                .email("peter@klaven")
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .and()
                .body(updateBody)
                .when()
                .post(Urls.LOGIN_URL)
                .then()
                .statusCode(400);
    }


    @Test
    public void checkUserNotFound() {
        RestAssured
                .given()
                .when()
                .get(Urls.USER_NOT_FOUND_URL)
                .then()
                .statusCode(404);
    }


    @Test
    public void checkBodyValueTest() {
        RestAssured
                .given()
                .when()
                .get(Urls.RESOURCE_NOT_FOUND_URL)
                .then()
                .statusCode(200)
                .body("page", instanceOf(Integer.class))
                .body("per_page", equalTo(6));
    }


    @Test
    public void checkStaticResponseTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/user.json"));
        RestAssured
                .given()
                .when()
                .get(Urls.USER_TWO_URL)
                .then()
                .statusCode(200)
                .body("", equalTo(expectedJson.getMap("")));
    }


    @Test
    public void getWithQueryParamTest() {
        RestAssured
                .given()
                .queryParam("page", "2")
                .when()
                .get(Urls.USERS_URL)
                .then()
                .statusCode(200)
                .body("page", equalTo(2));

    }

}

