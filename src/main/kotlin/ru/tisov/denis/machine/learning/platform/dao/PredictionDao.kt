package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.data.jpa.repository.JpaRepository
import ru.tisov.denis.machine.learning.platform.entity.Prediction
import java.util.*

interface PredictionDao : JpaRepository<Prediction, UUID> {

    fun save(job: Prediction)

    fun getById(id: UUID): Prediction

    fun findAllByModelId(modelId: UUID): List<Prediction>

}