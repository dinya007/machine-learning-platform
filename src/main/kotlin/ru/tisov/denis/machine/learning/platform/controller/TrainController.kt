package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.TrainResponse
import ru.tisov.denis.machine.learning.platform.service.TrainService

@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
class TrainController(val trainService: TrainService) {

    @GetMapping("/train")
    fun train(@RequestParam(defaultValue = "data/train.csv") filePath: String): TrainResponse {
        return TrainResponse(trainService.train(filePath))
    }

    @PostMapping("/train")
    fun train(@RequestParam("file") file: MultipartFile): TrainResponse {
        println(file)
//        return TrainResponse(UUID.randomUUID())
        return TrainResponse(trainService.train(file))
    }

}