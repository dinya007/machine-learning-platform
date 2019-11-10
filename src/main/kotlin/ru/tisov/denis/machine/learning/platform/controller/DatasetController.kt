package ru.tisov.denis.machine.learning.platform.controller

import org.springframework.core.io.InputStreamResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.tisov.denis.machine.learning.platform.controller.dto.AnalyzeResponse
import ru.tisov.denis.machine.learning.platform.entity.Dataset
import ru.tisov.denis.machine.learning.platform.service.DatasetService
import java.io.FileInputStream
import java.util.*

@RestController
@RequestMapping("/datasets")
@CrossOrigin(origins = ["http://localhost:3000"])
class DatasetController(val datasetService: DatasetService) {

    @PostMapping("/create")
    fun train(@RequestParam("file") file: MultipartFile): AnalyzeResponse {
        return datasetService.create(file.bytes)
    }

    @GetMapping
    fun getAllDataSets(): List<Dataset> {
        return datasetService.getAll()
    }

    @GetMapping("/{datasetId}/downloadData")
    fun downloadData(@PathVariable datasetId: UUID): ResponseEntity<Resource> {
        val dataset = datasetService.findOne(datasetId)
        val resource = InputStreamResource(FileInputStream(dataset.dataPath))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=train.csv")
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
