package ru.tisov.denis.machine.learning.platform.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc.given
import org.hamcrest.Matchers.*
import org.junit.Test
import org.springframework.http.HttpStatus
import ru.tisov.denis.machine.learning.platform.IntegrationTest


internal class ModelControllerTest : IntegrationTest() {

    @Test
    fun shouldSaveJobAndPublishMessage() {
        given()
                .multiPart("/data/train.csv".classpathFile())
                .`when`()
                .post("/models/train")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body("id",  notNullValue())
    }
}