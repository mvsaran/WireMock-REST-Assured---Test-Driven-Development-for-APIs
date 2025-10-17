package com.demo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    protected static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        // ✅ Start WireMock server dynamically on port 8080 (or random port if needed)
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();

        // ✅ Point REST Assured to this local mock server
        RestAssured.baseURI = "http://localhost:8080";

        System.out.println("🚀 WireMock started on: " + RestAssured.baseURI);
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("🧹 WireMock stopped.");
        }
    }
}
