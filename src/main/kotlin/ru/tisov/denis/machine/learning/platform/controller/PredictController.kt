package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.PredictResponse
import ru.tisov.denis.machine.learning.platform.service.PredictionService
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class PredictController(val predictionService: PredictionService) {

//    @GetMapping("/predict")
//    fun testPredict(@RequestParam trainingJobId: UUID, @RequestParam(defaultValue = "data/test.csv") filePath: String): PredictResponse {
//        return PredictResponse(predictionService.predict(trainingJobId, filePath))
//    }

    @PostMapping("/predict")
    fun predict(@RequestParam modelId: UUID, file: MultipartFile): PredictResponse {
        return PredictResponse(predictionService.predict(modelId, file))
//        println(file)
//        return PredictResponse(UUID.randomUUID())
    }

}