package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.TrainResponse
import ru.tisov.denis.machine.learning.platform.service.ModelService

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class TrainController(val modelService: ModelService) {

//    @GetMapping("/train")
//    fun train(@RequestParam(defaultValue = "data/train.csv") filePath: String): TrainResponse {
//        return TrainResponse(modelService.train(filePath))
//    }

    @PostMapping("/train")
    fun train(@RequestParam("file") file: MultipartFile): TrainResponse {
        println(file)
//        return TrainResponse(UUID.randomUUID())
        return TrainResponse(modelService.train(file))
    }

}