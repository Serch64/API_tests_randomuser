package me.randomuser.clients;

import me.randomuser.pojo.Results;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class GenderClient extends Spec {

    private static final String RESULTS_PATH = "results";
    private static final String GENDER_QUERY_PARAM = "gender";

    public List<Results> genderPositiveClient(String value) {
        return given()
                .spec(getSpec())
                .queryParam(GENDER_QUERY_PARAM, value)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract()
                .jsonPath()
                .getList(RESULTS_PATH, Results.class);
    }

    public List<Results> genderNegativeClient(String gender, String value) {
        return given()
                .spec(getSpec())
                .queryParam(gender, value)
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract()
                .jsonPath()
                .getList(RESULTS_PATH, Results.class);
    }
}