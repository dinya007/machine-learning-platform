package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Repository
import ru.tisov.denis.machine.learning.platform.entity.TrainingJob
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class DefaultTrainingJobDao : TrainingJobDao {

    private val jobs = ConcurrentHashMap<UUID, TrainingJob>()

    override fun save(job: TrainingJob) {
        jobs[job.id] = job
    }

    override fun get(id: UUID): TrainingJob? {
        return jobs[id]
    }
}