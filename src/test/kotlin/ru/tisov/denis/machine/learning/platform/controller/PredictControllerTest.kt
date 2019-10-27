package ru.tisov.denis.machine.learning.platform.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.Test
import org.springframework.http.HttpStatus
import ru.tisov.denis.machine.learning.platform.IntegrationTest
import java.util.*

internal class PredictControllerTest : IntegrationTest() {

    @Test
    fun shouldSaveJobAndPublishMessage() {
        val trainingJobId = UUID.randomUUID()

        RestAssuredMockMvc.given()
                .multiPart("/data/test.csv".classpathFile())
                .param("trainingJobId", trainingJobId)
                .`when`()
                .post("/predict")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body("id", Matchers.notNullValue())
    }

}