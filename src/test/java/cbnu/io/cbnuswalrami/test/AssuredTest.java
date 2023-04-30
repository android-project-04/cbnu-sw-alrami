package cbnu.io.cbnuswalrami.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.restassured3.RestAssuredRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class AssuredTest {

    public static final String BASE_URL = "http://localhost";

    @LocalServerPort
    private int port = 8080;

    private RequestSpecification documentationSpec = new RequestSpecBuilder().setPort(port).setBaseUri(BASE_URL).build();

    @BeforeAll
    public void configureRestAssured() {
        RestAssured.port = port;
        baseURI = BASE_URL;
    }


    @BeforeEach
    public void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
        this.documentationSpec = new RequestSpecBuilder()
                .addFilter(RestAssuredRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    public void given_when_then() {
        // given - precondition or setup
        ExtractableResponse<Response> response = given().spec(documentationSpec)
                .contentType(ContentType.JSON)
                .accept("application/json")
                .filter(document("hello")
                )
                .when().get("/")
                .then().statusCode(HttpStatus.OK.value()).extract();

        System.out.println("==========" + response.body().asPrettyString());

        assertAll(
                () -> Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> Assertions.assertThat(response.body().asPrettyString()).isEqualTo("hello4")

        );

    }
}
