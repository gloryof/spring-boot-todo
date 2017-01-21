package jp.glory.test.tool.setup;

import java.time.LocalDateTime;

import io.restassured.RestAssured;
import io.restassured.config.DecoderConfig;
import io.restassured.config.EncoderConfig;
import io.restassured.config.SessionConfig;

public final class Setup {

    public static void setup() {

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.sessionId = "test-sesseion-" + LocalDateTime.now().getNano();
        RestAssured.config = RestAssured.config()
                                    .sessionConfig(new SessionConfig().sessionIdName("SESSION"))
                                    .encoderConfig(new EncoderConfig().defaultContentCharset("UTF-8"))
                                    .decoderConfig(new DecoderConfig().defaultContentCharset("UTF-8"));
    }

}
