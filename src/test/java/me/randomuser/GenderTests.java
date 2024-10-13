package me.randomuser;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

import me.randomuser.clients.GenderClient;
import me.randomuser.pojo.Results;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GenderTests {
    private static final int EXPECTED_SIZE = 1;
    private static final String MALE_EXPECTED_RESULT = "male";
    private GenderClient genderClient = new GenderClient();

    @BeforeEach
    public void turnOnLogs() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/GenderPositiveTestData.csv")
    public void genderPositiveTests(String value, String expectedResult) {
        List<Results> resultsList = genderClient.genderPositiveClient(value);
        List<Results> filteredResults = resultsList.stream().filter(x->x.getGender().equals(expectedResult)).toList();
        assertThat(filteredResults).hasSize(EXPECTED_SIZE);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/GenderNegativeTestData.csv")
    public void genderNegativeTests(String gender, String value) {
        List<Results> genderResultsList = genderClient.genderNegativeClient(gender, value);
        List<Results> filteredResults = genderResultsList.stream().filter(x->x.getGender().contains(MALE_EXPECTED_RESULT)).toList();
        assertThat(filteredResults).hasSize(EXPECTED_SIZE);
    }
    
    @AfterEach
    public void resetLogs() {
        RestAssured.reset();
    }
}
