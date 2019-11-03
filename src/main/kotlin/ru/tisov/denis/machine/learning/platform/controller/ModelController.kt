package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.TrainResponse
import ru.tisov.denis.machine.learning.platform.entity.Model
import ru.tisov.denis.machine.learning.platform.service.ModelService
import java.util.*

@RestController
@RequestMapping("/models")
@CrossOrigin(origins = ["http://localhost:3000"])
class ModelController(val modelService: ModelService) {

    @GetMapping("/{id}")
    fun getModel(@PathVariable id: UUID): Model {
        return modelService.get(id)
    }

    @GetMapping("/datasetId/{datasetId}")
    fun getModelsByDatasetId(@PathVariable datasetId: UUID): List<Model> {
        return modelService.getAllByDatasetId(datasetId)
    }

    @PostMapping("/train")
    fun train(@RequestParam("file") file: MultipartFile): TrainResponse {
        return modelService.train(file.bytes)
    }

}