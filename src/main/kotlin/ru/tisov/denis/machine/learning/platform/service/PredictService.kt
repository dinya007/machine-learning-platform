package ru.tisov.denis.machine.learning.platform.service

import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.JmsHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.dao.PredictingJobDao
import ru.tisov.denis.machine.learning.platform.entity.PredictingJob
import ru.tisov.denis.machine.learning.platform.entity.PredictingJobStatus
import ru.tisov.denis.machine.learning.platform.service.dto.MachineLearningPredictRequest
import ru.tisov.denis.machine.learning.platform.service.dto.MachineLearningPredictResponse
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*


@Service
class PredictService(val jmsTemplate: JmsTemplate, val predictingJobDao: PredictingJobDao) {

    fun predict(trainingJobId: UUID, dataPath: String): UUID {
        val predictingJobId = UUID.randomUUID()

        val resultFilePath = "${Folders.DATA.path}/$trainingJobId/$predictingJobId.csv"

        predictingJobDao.save(PredictingJob(predictingJobId, PredictingJobStatus.STARTED))

        val message = MachineLearningPredictRequest(trainingJobId, dataPath, resultFilePath)
        jmsTemplate.convertAndSend(REGRESSION_PREDICT_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(predictingJobId.toString(), REGRESSION_PREDICT_RESPONSE_QUEUE))
        println("Predicting request was sent $message with correlation id: $predictingJobId")
        return predictingJobId
    }

    fun predict(trainingJobId: UUID, file: MultipartFile): UUID {
        val predictingJobId = UUID.randomUUID()

        val directory = "${Folders.DATA.path}/$trainingJobId/$predictingJobId"
        val predictFilePath = "$directory/test.csv"
        val resultFilePath = "$directory/result.csv"
        Files.createDirectories(Paths.get(directory))
        Files.write(Paths.get(predictFilePath), file.bytes)

        predictingJobDao.save(PredictingJob(predictingJobId, PredictingJobStatus.STARTED))

        val message = MachineLearningPredictRequest(trainingJobId, predictFilePath, resultFilePath)
        jmsTemplate.convertAndSend(REGRESSION_PREDICT_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(predictingJobId.toString(), REGRESSION_PREDICT_RESPONSE_QUEUE))
        println("Predicting request was sent $message with correlation id: $predictingJobId")
        return predictingJobId
    }

    @JmsListener(destination = REGRESSION_PREDICT_RESPONSE_QUEUE)
    fun listenPredictResultsQueue(@Header(JmsHeaders.CORRELATION_ID) jobId: UUID, response: MachineLearningPredictResponse) {
        println("Predicting response was received: $response with correlation id: $jobId")

        val predictingJob = predictingJobDao.get(jobId) ?: throw IllegalStateException("Job with id $jobId not found")
        predictingJobDao.save(PredictingJob(predictingJob.id, status = response.status, resultPath = predictingJob.resultPath))

        println(predictingJobDao.get(jobId))
    }

}

