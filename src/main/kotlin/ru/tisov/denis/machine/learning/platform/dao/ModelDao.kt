package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.data.jpa.repository.JpaRepository
import ru.tisov.denis.machine.learning.platform.entity.Model
import java.util.*

interface ModelDao: JpaRepository<Model, UUID> {

    fun save(job: Model)

    fun getById(id: UUID): Model

    fun findAllByDatasetId(datasetId: UUID): List<Model>

}