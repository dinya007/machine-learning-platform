package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.data.jpa.repository.JpaRepository
import ru.tisov.denis.machine.learning.platform.entity.Dataset
import java.util.*

interface DatasetDao : JpaRepository<Dataset, UUID> {

    fun save(dataset: Dataset)

    fun getById(id: UUID): Dataset

    override fun findAll(): List<Dataset>

}