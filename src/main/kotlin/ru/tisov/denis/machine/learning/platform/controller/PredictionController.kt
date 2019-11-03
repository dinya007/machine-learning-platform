package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.PredictResponse
import ru.tisov.denis.machine.learning.platform.entity.Prediction
import ru.tisov.denis.machine.learning.platform.service.PredictionService
import java.io.FileInputStream
import java.util.*


@RestController
@RequestMapping("/predictions")
@CrossOrigin(origins = ["http://localhost:3000"])
class PredictionController(val predictionService: PredictionService) {

    @PostMapping("/predict")
    fun predict(@RequestParam modelId: UUID, file: MultipartFile): PredictResponse {
        return PredictResponse(predictionService.predict(modelId, file.bytes))
    }

    @GetMapping("/modelId/{modelId}")
    fun getPredictionsByModelId(@PathVariable modelId: UUID): List<Prediction> {
        return predictionService.findAllByModelId(modelId)
    }

    @GetMapping("/{predictionId}/downloadData")
    fun downloadData(@PathVariable predictionId: UUID): ResponseEntity<Resource> {
        val prediction = predictionService.get(predictionId)
        val resource = InputStreamResource(FileInputStream(prediction.dataPath))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.csv")
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @GetMapping("/{predictionId}/downloadResult")
    fun downloadResult(@PathVariable predictionId: UUID): ResponseEntity<Resource> {
        val prediction = predictionService.get(predictionId)
        val resource = InputStreamResource(FileInputStream(prediction.resultPath))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=predictions.csv")
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}