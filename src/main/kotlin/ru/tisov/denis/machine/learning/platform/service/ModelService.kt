package ru.tisov.denis.machine.learning.platform.service

import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.JmsHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import ru.tisov.denis.machine.learning.platform.controller.dto.TrainResponse
import ru.tisov.denis.machine.learning.platform.dao.DatasetDao
import ru.tisov.denis.machine.learning.platform.dao.FSDao
import ru.tisov.denis.machine.learning.platform.dao.ModelDao
import ru.tisov.denis.machine.learning.platform.entity.*
import ru.tisov.denis.machine.learning.platform.service.dto.ModelTrainRequest
import ru.tisov.denis.machine.learning.platform.service.dto.ModelTrainResponse
import java.util.*


@Service
class ModelService(val jmsTemplate: JmsTemplate, val datasetDao: DatasetDao, val modelDao: ModelDao, val fsDao: FSDao) {

    fun train(file: ByteArray): TrainResponse {
        val datasetId = UUID.randomUUID()
        val modelId = UUID.randomUUID()
        val datasetPath = "${Folders.DATA.path}/$datasetId/data.csv"
        val modelPath = "${Folders.DATA.path}/$datasetId/$modelId/model"

        fsDao.writeFile(datasetPath, file)
        datasetDao.save(Dataset(datasetId, datasetPath))
        modelDao.save(Model(modelId, datasetId, ModelType.REGRESSION, Algorithm.XG_BOOST, ModelStatus.STARTED, modelPath))

        val message = ModelTrainRequest(datasetPath, modelPath)
        jmsTemplate.convertAndSend(REGRESSION_TRAIN_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(modelId.toString(), REGRESSION_TRAIN_RESPONSE_QUEUE))
        println("Training model request was sent $message with correlation id: $modelId")
        return TrainResponse(datasetId, modelId)
    }

    @JmsListener(destination = REGRESSION_TRAIN_RESPONSE_QUEUE)
    fun listenQueue(@Header(JmsHeaders.CORRELATION_ID) modelId: UUID, response: ModelTrainResponse) {
        println("Training model response was received: $response with correlation id: $modelId")
        val model = modelDao.getById(modelId)
        modelDao.save(model.copy(status = response.status))
        println(modelDao.getById(modelId))
    }

    fun getAllByDatasetId(datasetId: UUID): List<Model> {
        return modelDao.findAllByDatasetId(datasetId)
    }

    fun get(id: UUID): Model {
        return modelDao.getById(id)
    }

}

