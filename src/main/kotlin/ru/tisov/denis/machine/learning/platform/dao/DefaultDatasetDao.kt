package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Repository
import ru.tisov.denis.machine.learning.platform.entity.Dataset
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class DefaultDatasetDao : DatasetDao {

    private val datasets = ConcurrentHashMap<UUID, Dataset>()

    override fun save(dataset: Dataset) {
        datasets[dataset.id] = dataset
    }

    override fun get(id: UUID): Dataset? {
        return datasets[id]
    }
}