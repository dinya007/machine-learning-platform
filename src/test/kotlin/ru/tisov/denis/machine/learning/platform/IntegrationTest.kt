package ru.tisov.denis.machine.learning.platform

import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.context.WebApplicationContext
import java.io.File
import java.nio.file.Paths


@RunWith(SpringRunner::class)
@SpringBootTest
abstract class IntegrationTest {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Before
    fun setUp() {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext)
    }

    @Test
    fun contextLoads() {
    }

    fun String.classpathFile(): File {
        return Paths.get(MachineLearningPlatformApplication::class.java.getResource(this).toURI()).toFile()
    }

}
