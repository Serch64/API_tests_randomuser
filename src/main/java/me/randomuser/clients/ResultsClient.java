package me.randomuser.clients;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class ResultsClient extends Spec {
    private static final String RESULTS_QUERY_PARAM = "results";

    public ValidatableResponse resultsPositiveClient(int value) {
        return given()
                .spec(getSpec())
                .queryParam(RESULTS_QUERY_PARAM, value)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(SC_OK);
    }

    public ValidatableResponse resultsNegativeClient(String results, String value) {
        return given()
                .spec(getSpec())
                .queryParam(results, value)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(SC_OK);
    }
}