package ru.tisov.denis.machine.learning.platform.service

import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.JmsHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.dao.TrainingJobDao
import ru.tisov.denis.machine.learning.platform.entity.TrainingJob
import ru.tisov.denis.machine.learning.platform.entity.TrainingJobStatus
import ru.tisov.denis.machine.learning.platform.service.dto.MachineLearningTrainRequest
import ru.tisov.denis.machine.learning.platform.service.dto.MachineLearningTrainResponse
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@Service
class TrainService(val jmsTemplate: JmsTemplate, val trainingJobDao: TrainingJobDao, val predictService: PredictService) {

    fun train(filePath: String): UUID {
        val jobId = UUID.randomUUID()
        val message = MachineLearningTrainRequest(filePath)

        trainingJobDao.save(TrainingJob(jobId, TrainingJobStatus.STARTED))

        jmsTemplate.convertAndSend(REGRESSION_TRAIN_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(jobId.toString(), REGRESSION_TRAIN_RESPONSE_QUEUE))

        println("Training request was sent $message with correlation id: $jobId")
        return jobId
    }

    fun train(file: MultipartFile): UUID {
        val jobId = UUID.randomUUID()

        val directory = "${Folders.DATA.path}/$jobId"
        val path = Paths.get("${Folders.DATA.path}/$jobId/train.csv")
        Files.createDirectories(Paths.get(directory))
        Files.write(path, file.bytes)

        trainingJobDao.save(TrainingJob(jobId, TrainingJobStatus.STARTED))

        val message = MachineLearningTrainRequest(path.toAbsolutePath().toString())
        jmsTemplate.convertAndSend(REGRESSION_TRAIN_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(jobId.toString(), REGRESSION_TRAIN_RESPONSE_QUEUE))

        println("Training request was sent $message with correlation id: $jobId")
        return jobId
    }

    @JmsListener(destination = REGRESSION_TRAIN_RESPONSE_QUEUE)
    fun listenQueue(@Header(JmsHeaders.CORRELATION_ID) jobId: UUID, response: MachineLearningTrainResponse) {
        println("Training response was received: $response with correlation id: $jobId")

        val learnJob = trainingJobDao.get(jobId) ?: throw IllegalStateException("Job with id $jobId not found")
        trainingJobDao.save(TrainingJob(learnJob.id, status = response.status))

        println(trainingJobDao.get(jobId))

        predictService.predict(jobId, "data/test.csv")
    }

}

