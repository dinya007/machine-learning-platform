package ru.tisov.denis.machine.learning.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.tisov.denis.machine.learning.platform.service.DatasetService
import ru.tisov.denis.machine.learning.platform.service.ModelService
import ru.tisov.denis.machine.learning.platform.service.PredictionService
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.concurrent.timer


@SpringBootApplication
class MachineLearningPlatformApplication

fun main(args: Array<String>) {
    val context = runApplication<MachineLearningPlatformApplication>(*args)

    val datasetService = context.getBean(DatasetService::class.java)
    val modelService = context.getBean(ModelService::class.java)
    val predictionService = context.getBean(PredictionService::class.java)
    val response = datasetService.create(Files.readAllBytes(Paths.get("/Users/denis/IdeaProjects/machine-learning-analyzer/data/train.csv")))
    Thread.sleep(5_000)
    val trainResponse = modelService.train(response.datasetId)
    timer(initialDelay = 60000, period = 600000, action = {
        predictionService.predict(trainResponse.modelId, Files.readAllBytes(Paths.get("/Users/denis/IdeaProjects/machine-learning-analyzer/data/test.csv")))
    })
}


