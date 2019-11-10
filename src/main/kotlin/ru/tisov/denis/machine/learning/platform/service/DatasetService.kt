package ru.tisov.denis.machine.learning.platform.service

import org.springframework.jms.annotation.JmsListener
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.support.JmsHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import ru.tisov.denis.machine.learning.platform.controller.dto.AnalyzeResponse
import ru.tisov.denis.machine.learning.platform.dao.DatasetDao
import ru.tisov.denis.machine.learning.platform.dao.FSDao
import ru.tisov.denis.machine.learning.platform.entity.Dataset
import ru.tisov.denis.machine.learning.platform.entity.DatasetStatus
import ru.tisov.denis.machine.learning.platform.service.dto.DatasetAnalyzeRequest
import ru.tisov.denis.machine.learning.platform.service.dto.DatasetAnalyzeResponse
import java.util.*

@Service
class DatasetService(val jmsTemplate: JmsTemplate, val datasetDao: DatasetDao, val fsDao: FSDao) {

    fun save(dataset: Dataset) {
        datasetDao.save(dataset)
    }

    fun findOne(id: UUID): Dataset {
        return datasetDao.getById(id)
    }

    fun getAll(): List<Dataset> {
        return datasetDao.findAll()
    }

    fun create(bytes: ByteArray): AnalyzeResponse {
        val datasetId = UUID.randomUUID()
        val datasetPath = "${Folders.DATA.path}/$datasetId/data.csv"
        val analyzePath = "${Folders.DATA.path}/$datasetId/analyze.json"

        fsDao.writeFile(datasetPath, bytes)
        datasetDao.save(Dataset(datasetId, DatasetStatus.CREATED, datasetPath, analyzePath))

        val message = DatasetAnalyzeRequest(datasetPath, analyzePath)
        jmsTemplate.convertAndSend(DATASET_ANALYZE_REQUEST_QUEUE, message,
                CorrelationIdPostProcessor(datasetId.toString(), DATASET_ANALYZE_RESPONSE_QUEUE))
        println("Dataset analyze request was sent $message with correlation id: $datasetId")
        return AnalyzeResponse(datasetId)
    }

    @JmsListener(destination = DATASET_ANALYZE_RESPONSE_QUEUE)
    fun listenQueue(@Header(JmsHeaders.CORRELATION_ID) datasetId: UUID, response: DatasetAnalyzeResponse) {
        println("Dataset analyze response was received: $response with correlation id: $datasetId")
        val dataset = datasetDao.getById(datasetId)
        datasetDao.save(dataset.copy(status = response.status))
        println(datasetDao.getById(datasetId))
    }

}