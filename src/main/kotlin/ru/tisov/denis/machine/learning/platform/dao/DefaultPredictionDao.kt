package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Repository
import ru.tisov.denis.machine.learning.platform.entity.Prediction
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class DefaultPredictionDao : PredictionDao {

    private val jobs = ConcurrentHashMap<UUID, Prediction>()

    override fun save(job: Prediction) {
        jobs[job.id] = job
    }

    override fun get(id: UUID): Prediction {
        return jobs[id] ?: throw IllegalStateException("Prediction with id $id not found")
    }

}
