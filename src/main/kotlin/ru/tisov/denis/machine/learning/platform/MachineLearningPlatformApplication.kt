package ru.tisov.denis.machine.learning.platform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.tisov.denis.machine.learning.platform.service.TrainService


@SpringBootApplication
class MachineLearningPlatformApplication

fun main(args: Array<String>) {
    val context = runApplication<MachineLearningPlatformApplication>(*args)

    val brokerService = context.getBean(TrainService::class.java)

    brokerService.train("train.csv")
}


