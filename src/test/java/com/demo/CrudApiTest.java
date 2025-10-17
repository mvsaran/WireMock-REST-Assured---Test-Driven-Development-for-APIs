package com.demo;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;   // WireMock DSL
import static io.restassured.RestAssured.given;                   // REST calls
import static org.hamcrest.Matchers.equalTo;                      // ✅ Hamcrest for assertions

/**
 * CRUD API Tests using WireMock + REST Assured.
 */
public class CrudApiTest extends BaseTest {

    // ==================== GET ====================
    @Test
    @DisplayName("GET - Retrieve User")
    public void testGetUser() {
        String response = TestDataLoader.loadJson("user_get.json");

        stubFor(get(urlEqualTo("/api/users/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(response)));

        given()
            .contentType(ContentType.JSON)
        .when()
            .get("/api/users/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("John Doe"))
            .body("email", equalTo("john@example.com"));

        verify(getRequestedFor(urlEqualTo("/api/users/1")));
        System.out.println("✅ GET Test Passed");
    }

    // ==================== POST ====================
    @Test
    @DisplayName("POST - Create User")
    public void testPostUser() {
        String request = TestDataLoader.loadJson("user_post.json");
        String response = TestDataLoader.loadJson("user_post.json");

        stubFor(post(urlEqualTo("/api/users"))
            // ✅ Use WireMock.equalTo explicitly here
            .withHeader("Content-Type", com.github.tomakehurst.wiremock.client.WireMock.equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(201)
                .withHeader("Content-Type", "application/json")
                .withBody(response)));

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("id", equalTo(2))
            .body("name", equalTo("Jane Smith"))
            .body("email", equalTo("jane@example.com"));

        verify(postRequestedFor(urlEqualTo("/api/users")));
        System.out.println("✅ POST Test Passed");
    }

    // ==================== PUT ====================
    @Test
    @DisplayName("PUT - Update User")
    public void testPutUser() {
        String request = TestDataLoader.loadJson("user_put.json");
        String response = TestDataLoader.loadJson("user_put.json");

        stubFor(put(urlEqualTo("/api/users/1"))
            .withHeader("Content-Type", com.github.tomakehurst.wiremock.client.WireMock.equalTo("application/json"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(response)));

        given()
            .contentType(ContentType.JSON)
            .body(request)
        .when()
            .put("/api/users/1")
        .then()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("name", equalTo("John Updated"))
            .body("email", equalTo("john.updated@example.com"));

        verify(putRequestedFor(urlEqualTo("/api/users/1")));
        System.out.println("✅ PUT Test Passed");
    }

    // ==================== DELETE ====================
    @Test
    @DisplayName("DELETE - Remove User")
    public void testDeleteUser() {
        String response = TestDataLoader.loadJson("user_delete.json");

        stubFor(delete(urlEqualTo("/api/users/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(response)));

        given()
            .contentType(ContentType.JSON)
        .when()
            .delete("/api/users/1")
        .then()
            .statusCode(200)
            .body("message", equalTo("User deleted successfully"))
            .body("deletedId", equalTo(1));

        verify(deleteRequestedFor(urlEqualTo("/api/users/1")));
        System.out.println("✅ DELETE Test Passed");
    }
}
