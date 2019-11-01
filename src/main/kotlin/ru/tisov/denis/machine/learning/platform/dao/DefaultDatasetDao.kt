//package ru.tisov.denis.machine.learning.platform.dao
//
//import org.springframework.stereotype.Repository
//import ru.tisov.denis.machine.learning.platform.entity.Dataset
//import java.util.*
//import java.util.concurrent.ConcurrentHashMap
//
//@Repository
//class DefaultDatasetDao : DatasetDao {
//
//    private val datasets = ConcurrentHashMap<UUID, Dataset>()
//
//    override fun save(dataset: Dataset) {
//        datasets[dataset.id] = dataset
//    }
//
//    override fun findOne(id: UUID): Dataset {
//        return datasets[id] ?: throw IllegalStateException("Dataset with id $id not found")
//    }
//
//    override fun findAll(): List<Dataset> {
//        return datasets.values.toList()
//    }
//}