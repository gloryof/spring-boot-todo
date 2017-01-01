package jp.glory.common.setup;

import java.time.LocalDateTime;

import io.restassured.RestAssured;
import io.restassured.config.SessionConfig;

public final class Setup {

    public static void setup() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.sessionId = "test-sesseion-" + LocalDateTime.now().getNano();
        RestAssured.config = RestAssured.config().sessionConfig(new SessionConfig().sessionIdName("SESSION"));
    }

}
