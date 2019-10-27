package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.PredictResponse
import ru.tisov.denis.machine.learning.platform.service.PredictService
import java.util.*

@RestController
class PredictController(val predictService: PredictService) {

    @GetMapping("/predict")
    fun testPredict(@RequestParam trainingJobId: UUID, @RequestParam(defaultValue = "data/test.csv") filePath: String): PredictResponse {
        return PredictResponse(predictService.predict(trainingJobId, filePath))
    }

    @PostMapping("/predict")
    fun predict(@RequestParam trainingJobId: UUID, file: MultipartFile): PredictResponse {
        return PredictResponse(predictService.predict(trainingJobId, file))
    }

}