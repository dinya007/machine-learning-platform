package ru.tisov.denis.machine.learning.platform.controller

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import ru.tisov.denis.machine.learning.platform.IntegrationTest
import ru.tisov.denis.machine.learning.platform.dao.ModelDao
import ru.tisov.denis.machine.learning.platform.entity.Algorithm
import ru.tisov.denis.machine.learning.platform.entity.Model
import ru.tisov.denis.machine.learning.platform.entity.ModelStatus
import ru.tisov.denis.machine.learning.platform.entity.ModelType
import java.util.*

internal class PredictControllerTest : IntegrationTest() {

    @Autowired
    lateinit var modelDao: ModelDao

    @Test
    fun shouldSaveJobAndPublishMessage() {
        val modelId = UUID.randomUUID()
        modelDao.save(Model(modelId, UUID.randomUUID(), ModelType.REGRESSION, Algorithm.XG_BOOST, ModelStatus.SUCCESS, ""))

        RestAssuredMockMvc.given()
                .multiPart("/data/test.csv".classpathFile())
                .param("modelId", modelId)
                .`when`()
                .post("/predict")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body("id", Matchers.notNullValue())
    }

}