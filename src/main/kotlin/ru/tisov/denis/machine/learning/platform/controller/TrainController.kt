package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.TrainResponse
import ru.tisov.denis.machine.learning.platform.service.TrainService

@RestController
class TrainController(val brokerService: TrainService) {

    @GetMapping("/train")
    fun train(@RequestParam(defaultValue = "data/train.csv") filePath: String): TrainResponse {
        return TrainResponse(brokerService.train(filePath))
    }

    @PostMapping("/train")
    fun train(@RequestParam("file") file: MultipartFile): TrainResponse {
        return TrainResponse(brokerService.train(file))
    }

}