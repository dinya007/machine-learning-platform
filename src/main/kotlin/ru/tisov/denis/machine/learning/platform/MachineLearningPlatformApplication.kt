package ru.tisov.denis.machine.learning.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.tisov.denis.machine.learning.platform.service.ModelService
import ru.tisov.denis.machine.learning.platform.service.PredictionService
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.concurrent.timer


@SpringBootApplication
class MachineLearningPlatformApplication

fun main(args: Array<String>) {
    val context = runApplication<MachineLearningPlatformApplication>(*args)

    val modelService = context.getBean(ModelService::class.java)
    val predictionService = context.getBean(PredictionService::class.java)
    val trainResponse = modelService.train(Files.readAllBytes(Paths.get("/Users/denis/IdeaProjects/machine-learning-analyzer/data/train.csv")))
    timer(initialDelay = 30000, period = 600000, action = {
        predictionService.predict(trainResponse.modelId, Files.readAllBytes(Paths.get("/Users/denis/IdeaProjects/machine-learning-analyzer/data/test.csv")))
    })
}


