package me.randomuser;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import me.randomuser.clients.ResultsClient;
import me.randomuser.pojo.Results;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

public class ResultsTests {
    private static final String RESULTS_PATH = "results";
    private static final String INFO_RESULTS_PATH = "info.results";
    private static final int EXPECTED_RESULT = 1;
    private ResultsClient resultsClient = new ResultsClient();
    private SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    public void turnOnLogs() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ResultsPositiveTestData.csv")
    public void resultsPositiveTests(int value, int expectedResult) {
        ValidatableResponse validatableResponse = resultsClient.resultsPositiveClient(value);
        List<Results> resultsList = validatableResponse.extract().jsonPath().getList(RESULTS_PATH, Results.class);
        int infoResult = validatableResponse.extract().jsonPath().getInt(INFO_RESULTS_PATH);
        softAssertions.assertThat(resultsList).hasSize(expectedResult);
        softAssertions.assertThat(infoResult).isEqualTo(expectedResult);
        softAssertions.assertAll();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ResultsNegativeTestData.csv")
    public void resultsNegativeTests(String results, String value) {
        ValidatableResponse validatableResponse = resultsClient.resultsNegativeClient(results, value);
        List<Results> resultsList = validatableResponse.extract().jsonPath().getList(RESULTS_PATH, Results.class);
        int infoResult = validatableResponse.extract().jsonPath().getInt(INFO_RESULTS_PATH);
        softAssertions.assertThat(resultsList).hasSize(EXPECTED_RESULT);
        softAssertions.assertThat(infoResult).isEqualTo(EXPECTED_RESULT);
        softAssertions.assertAll();
    }

    @AfterEach
    public void resetLogs() {
        RestAssured.reset();
    }
}
