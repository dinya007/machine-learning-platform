package ru.tisov.denis.machine.learning.platform.dao

import org.springframework.stereotype.Repository
import ru.tisov.denis.machine.learning.platform.entity.PredictingJob
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Repository
class DefaultPredictingJobDao : PredictingJobDao {

    private val jobs = ConcurrentHashMap<UUID, PredictingJob>()

    override fun save(job: PredictingJob) {
        jobs[job.id] = job
    }

    override fun get(id: UUID): PredictingJob? {
        return jobs[id]
    }

}
