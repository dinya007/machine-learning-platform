package ru.tisov.denis.machine.learning.platform.service

import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.JmsHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import ru.tisov.denis.machine.learning.platform.dao.FSDao
import ru.tisov.denis.machine.learning.platform.dao.ModelDao
import ru.tisov.denis.machine.learning.platform.dao.PredictionDao
import ru.tisov.denis.machine.learning.platform.entity.Prediction
import ru.tisov.denis.machine.learning.platform.entity.PredictionStatus
import ru.tisov.denis.machine.learning.platform.service.dto.PredictionRequest
import ru.tisov.denis.machine.learning.platform.service.dto.PredictionResponse
import java.util.*


@Service
class PredictionService(val jmsTemplate: JmsTemplate, val predictionDao: PredictionDao, val modelDao: ModelDao, val fsDao: FSDao) {

    fun predict(modelId: UUID, file: ByteArray): UUID {
        val predictionId = UUID.randomUUID()
        val model = modelDao.getById(modelId)
        val dataPath = "${Folders.DATA.path}/${model.datasetId}/$modelId/$predictionId/data.csv"
        val resultPath = "${Folders.DATA.path}/${model.datasetId}/$modelId/$predictionId/result.csv"

        fsDao.writeFile(dataPath, file)
        predictionDao.save(Prediction(predictionId, modelId, dataPath, PredictionStatus.STARTED, resultPath))

        val message = PredictionRequest(dataPath, model.modelPath, resultPath)
        jmsTemplate.convertAndSend(REGRESSION_PREDICT_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(predictionId.toString(), REGRESSION_PREDICT_RESPONSE_QUEUE))
        println("Prediction request was sent $message with correlation id: $predictionId")
        return predictionId
    }

    @JmsListener(destination = REGRESSION_PREDICT_RESPONSE_QUEUE)
    fun listenPredictResultsQueue(@Header(JmsHeaders.CORRELATION_ID) predictionId: UUID, response: PredictionResponse) {
        println("Prediction response was received: $response with correlation id: $predictionId")

        val prediction = predictionDao.getById(predictionId)
        predictionDao.save(prediction.copy(status = response.status))

        println(predictionDao.getById(predictionId))
    }

    fun findAllByModelId(modelId: UUID): List<Prediction> {
        return predictionDao.findAllByModelId(modelId)
    }

    fun get(predictionId: UUID): Prediction {
        return predictionDao.getById(predictionId)
    }

}

