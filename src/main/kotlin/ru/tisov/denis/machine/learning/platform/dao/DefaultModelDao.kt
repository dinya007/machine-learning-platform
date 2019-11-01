//package ru.tisov.denis.machine.learning.platform.dao
//
//import org.springframework.stereotype.Repository
//import ru.tisov.denis.machine.learning.platform.entity.Model
//import java.util.*
//import java.util.concurrent.ConcurrentHashMap
//
//@Repository
//class DefaultModelDao : ModelDao {
//
//    private val jobs = ConcurrentHashMap<UUID, Model>()
//
//    override fun save(job: Model) {
//        jobs[job.id] = job
//    }
//
//    override fun get(id: UUID): Model {
//        return jobs[id] ?: throw IllegalStateException("Model with id $id not found")
//    }
//
//    override fun getAllByDatasetId(datasetId: UUID): List<Model> {
//        return jobs.values.filter { it.datasetId == datasetId }
//    }
//}