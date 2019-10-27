package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.PredictResponse
import ru.tisov.denis.machine.learning.platform.service.PredictService
import java.util.*

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class PredictController(val predictService: PredictService) {

    @GetMapping("/predict")
    fun testPredict(@RequestParam trainingJobId: UUID, @RequestParam(defaultValue = "data/test.csv") filePath: String): PredictResponse {
        return PredictResponse(predictService.predict(trainingJobId, filePath))
    }

    @PostMapping("/predict")
    fun predict(@RequestParam trainingJobId: UUID, file: MultipartFile): PredictResponse {
        return PredictResponse(predictService.predict(trainingJobId, file))
//        println(file)
//        return PredictResponse(UUID.randomUUID())
    }

}