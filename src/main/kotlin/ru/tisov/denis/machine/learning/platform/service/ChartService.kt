package ru.tisov.denis.machine.learning.platform.service

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Service
import ru.tisov.denis.machine.learning.platform.dao.FSDao
import ru.tisov.denis.machine.learning.platform.entity.DatasetAnalyze
import java.util.*

@Service
class ChartService(val fsDao: FSDao, val datasetService: DatasetService) {

    private val mapper = jacksonObjectMapper().enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS)

    fun getFeatureAnalyze(datasetId: UUID): DatasetAnalyze {
        val dataset = datasetService.findOne(datasetId)
        return mapper.readValue(fsDao.readFile(dataset.analyzePath))
    }

}