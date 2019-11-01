package ru.tisov.denis.machine.learning.platform.service

import org.springframework.stereotype.Service
import ru.tisov.denis.machine.learning.platform.dao.DatasetDao
import ru.tisov.denis.machine.learning.platform.entity.Dataset
import java.util.*

@Service
class DatasetService(val datasetDao: DatasetDao) {

    fun save(dataset: Dataset) {
        datasetDao.save(dataset)
    }

    fun findOne(id: UUID): Dataset {
        return datasetDao.getById(id)
    }

    fun getAll(): List<Dataset> {
        return datasetDao.findAll()
    }

}