package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.tisov.denis.machine.learning.platform.entity.DatasetAnalyze
import ru.tisov.denis.machine.learning.platform.service.ChartService
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class ChartController(val chartService: ChartService) {

    @GetMapping("/datasets/{datasetId}/featureAnalyse")
    fun getFeatureAnalyze(@PathVariable datasetId: UUID): DatasetAnalyze {
        return chartService.getFeatureAnalyze(datasetId)
    }

}